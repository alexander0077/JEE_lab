package pl.edu.pg.eti.kask.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.agent.controller.api.AgentController;
import pl.edu.pg.eti.kask.agent.dto.PatchAgentRequest;
import pl.edu.pg.eti.kask.agent.dto.PutAgentRequest;
import pl.edu.pg.eti.kask.player.controller.api.PlayerController;
import pl.edu.pg.eti.kask.player.dto.PatchPlayerRequest;
import pl.edu.pg.eti.kask.player.dto.PutPlayerRequest;
import pl.edu.pg.eti.kask.team.controller.api.TeamController;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final AgentController agentController;
    private final PlayerController playerController;
    private final TeamController teamController;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern AGENTS = Pattern.compile("/agents/?");
        public static final Pattern AGENT = Pattern.compile("/agents/(%s)".formatted(UUID.pattern()));
        public static final Pattern AGENT_PORTRAIT = Pattern.compile("/agents/(%s)/portrait".formatted(UUID.pattern()));
        public static final Pattern PLAYERS = Pattern.compile("/players/?");
        public static final Pattern PLAYER = Pattern.compile("/players/(%s)".formatted(UUID.pattern()));
        public static final Pattern TEAMS = Pattern.compile("/teams/?");
        public static final Pattern TEAM = Pattern.compile("/teams/(%s)".formatted(UUID.pattern()));
        public static final Pattern TEAM_PLAYERS = Pattern.compile("/teams/(%s)/players/?".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(AgentController agentController,
                      PlayerController playerController,
                      TeamController teamController) {
        this.agentController = agentController;
        this.playerController = playerController;
        this.teamController = teamController;
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AGENTS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(agentController.getAgents()));
                return;
            } else if(path.matches(Patterns.PLAYERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(playerController.getPlayers()));
                return;
            } else if(path.matches(Patterns.TEAMS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(teamController.getTeams()));
                return;
            } else if(path.matches(Patterns.PLAYER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                response.getWriter().write(jsonb.toJson(playerController.getPlayer(uuid)));
                return;
            } else if (path.matches(Patterns.TEAM_PLAYERS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.TEAM_PLAYERS, path);
                response.getWriter().write(jsonb.toJson(playerController.getTeamPlayers(uuid)));
                return;
            } else if (path.matches(Patterns.AGENT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.AGENT, path);
                response.getWriter().write(jsonb.toJson(agentController.getAgent(uuid)));
                return;
            } else if (path.matches(Patterns.TEAM.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.TEAM, path);
                response.getWriter().write(jsonb.toJson(teamController.getTeam(uuid)));
                return;
            } else if (path.matches(Patterns.AGENT_PORTRAIT.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.AGENT_PORTRAIT, path);
                byte[] portrait = agentController.getAgentPortrait(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AGENT.pattern())) {
                UUID uuid = extractUuid(Patterns.AGENT, path);
                agentController.putAgent(uuid, jsonb.fromJson(request.getReader(), PutAgentRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "agents", uuid.toString()));
                return;
            } else if(path.matches(Patterns.PLAYER.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                playerController.putPlayer(uuid, jsonb.fromJson(request.getReader(), PutPlayerRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "players", uuid.toString()));
                return;
            } else if (path.matches(Patterns.AGENT_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.AGENT_PORTRAIT, path);
                agentController.putAgentPortrait(uuid, request.getPart("portrait").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AGENT.pattern())) {
                UUID uuid = extractUuid(Patterns.AGENT, path);
                agentController.deleteAgent(uuid);
                return;
            } else if (path.matches(Patterns.PLAYER.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                playerController.deletePlayer(uuid);
                return;
            } else if (path.matches(Patterns.AGENT_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.AGENT_PORTRAIT, path);
                agentController.deleteAgentPortrait(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.AGENT.pattern())) {
                UUID uuid = extractUuid(Patterns.AGENT, path);
                agentController.patchAgent(uuid, jsonb.fromJson(request.getReader(), PatchAgentRequest.class));
                return;
            } else if(path.matches(Patterns.PLAYER.pattern())) {
                UUID uuid = extractUuid(Patterns.PLAYER, path);
                playerController.patchPlayer(uuid, jsonb.fromJson(request.getReader(), PatchPlayerRequest.class));
                return;
            } else if (path.matches(Patterns.AGENT_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.AGENT_PORTRAIT, path);
                agentController.patchAgentPortrait(uuid, request.getPart("portrait").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
