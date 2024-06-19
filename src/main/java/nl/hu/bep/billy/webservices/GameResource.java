package nl.hu.bep.billy.webservices;

import nl.hu.bep.billy.authentication.User;
import nl.hu.bep.billy.models.Battle;
import nl.hu.bep.billy.models.Snake;
import nl.hu.bep.billy.security.MySecurityContext;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
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
    public Response getAllGameIds(@Context MySecurityContext sc) {
        if(!(sc.getUserPrincipal() instanceof User)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        User user = (User) sc.getUserPrincipal();
        Snake snake = user.getSnake();
        List<String> gameIds = snake.getBattles().stream().map(Battle::getId).distinct().collect(Collectors.toList());
        return Response.status(Response.Status.OK).entity(gameIds).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameStats(@Context MySecurityContext sc, @PathParam("id") String id) {
        if(!(sc.getUserPrincipal() instanceof User)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        User user = (User) sc.getUserPrincipal();
        Snake snake = user.getSnake();
        Battle battle = snake.getBattles().stream().filter((b) -> b.getId().equals(id)).findFirst().orElse(null);
        if(battle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(battle).build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@Context MySecurityContext sc, @PathParam("id") String id) {
        if(!(sc.getUserPrincipal() instanceof User)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        User user = (User) sc.getUserPrincipal();
        Snake snake = user.getSnake();
        Battle battle = snake.getBattles().stream().filter((b) -> b.getId().equals(id)).findFirst().orElse(null);
        if(battle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(battle).build();
    }
}
