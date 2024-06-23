package nl.hu.bep.billy.webservices;

import nl.hu.bep.billy.ApiModels.GameResult;
import nl.hu.bep.billy.authentication.User;
import nl.hu.bep.billy.models.Battle;
import nl.hu.bep.billy.models.Snake;
import nl.hu.bep.billy.security.MySecurityContext;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/games")
public class GameResource {
    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGameIds(@Context ContainerRequestContext requestCtx) {
        MySecurityContext sc = (MySecurityContext) requestCtx.getSecurityContext(); // simply passing MySecurityContext as param didn't work(it was null)
        if (sc == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if (!(sc.getUserPrincipal() instanceof User user)) return Response.status(Response.Status.FORBIDDEN).build();

        Snake snake = user.getSnake();
        List<String> gameIds = snake.getBattles().stream().map(Battle::getId).distinct().collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(gameIds).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameStats(@Context ContainerRequestContext requestCtx, @PathParam("id") String id) {
        MySecurityContext sc = (MySecurityContext) requestCtx.getSecurityContext();
        if (sc == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if (!(sc.getUserPrincipal() instanceof User user)) return Response.status(Response.Status.FORBIDDEN).build();


        Snake snake = user.getSnake();
        Battle battle = snake.getBattles().stream().filter((b) -> b.getId().equals(id)).findFirst().orElse(null);
        if (battle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        GameResult result = new GameResult(battle.getId(), battle.getResult(), battle.getTurnCount(), battle.isInProgress(), battle.getMostUsedAlgorithm(), battle.getPlayerId());
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@Context ContainerRequestContext requestCtx, @PathParam("id") String id) {
        MySecurityContext sc = (MySecurityContext) requestCtx.getSecurityContext();
        if (sc == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if (!(sc.getUserPrincipal() instanceof User user)) return Response.status(Response.Status.FORBIDDEN).build();


        Snake snake = user.getSnake();
        Battle battle = snake.getBattles().stream().filter((b) -> b.getId().equals(id)).findFirst().orElse(null);
        snake.deleteBattle(id);
        if (battle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(battle).build();
    }
}
