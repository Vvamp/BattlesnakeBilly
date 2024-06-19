package nl.hu.bep.billy.webservices;

import nl.hu.bep.billy.models.Battle;
import nl.hu.bep.billy.models.Snake;
import nl.hu.bep.billy.models.SnakeDen;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/games")
public class GameResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGameIds() {
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        List<String> gameIds = snake.getBattles().stream().map(Battle::getId).distinct().collect(Collectors.toList());


        return Response.status(Response.Status.OK).entity(gameIds).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameStats(@PathParam("id") String id) {
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        Battle battle = snake.getBattles().stream().filter((b) -> b.getId().equals(id)).findFirst().orElse(null);
        if(battle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(battle).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@PathParam("id") String id) {
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        Battle battle = snake.getBattles().stream().filter((b) -> b.getId().equals(id)).findFirst().orElse(null);
        if(battle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(battle).build();
    }
}
