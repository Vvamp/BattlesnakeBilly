package nl.hu.bep.billy.webservices;

import nl.hu.bep.billy.ApiModels.*;
import nl.hu.bep.billy.models.Battle;
import nl.hu.bep.billy.models.Snake;
import nl.hu.bep.billy.models.SnakeDen;

import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/snake")
public class SnakeResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSnakes() {
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        return Response.status(Response.Status.OK).entity(snake).build();
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSnake(SnakePatchRequest snakePatchRequest) {
        // TODO: default first snake
        SnakeDen.updateSnake(snakePatchRequest.color, snakePatchRequest.head, snakePatchRequest.tail); // TODO: get from index
        return Response.status(Response.Status.OK).entity(SnakeDen.getSnakes().get(0)).build();


    }

    @POST
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response start(GameRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("shout", "GLHF :)");
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        snake.addBattle(new Battle(request));
        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @POST
    @Path("/move")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response move(GameRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("move", "up");
        messages.put("shout", "GOING UP UP UP");
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        snake.playTurn(request);
        //TODO:
        /*
        * playTurn returns enum: UP,DOWN,LEFT,RIGHT,NONE/UNKNOWN
        * algorithm:
        * Find nearest piece
        * Check if I am closer than the rest
        * If so: GO
        * if not, find second nearest piece
        * etc
        *
        * if none, try and kill/stay alive
        * */
        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @POST
    @Path("/end")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response end(GameRequest request) {
        Map<String, String> messages = new HashMap<>();
        messages.put("shout", "OK. GG");
        List<Snake> snakes = SnakeDen.getSnakes();
        Snake snake = snakes.get(0);
        snake.endBattle(request);
        return Response.status(Response.Status.OK).entity(messages).build();
    }




    @Path("/init")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response init(){
        // create 3 random snakes
        String[] colors = {"#FF0000", "#00FF00", "#0000FF"};
        for(int i = 0; i < 1; i++){
            Snake snake = new Snake();
            snake.setHead("evil");
            snake.setTail("coffee");
            snake.setColor(colors[i]);
            SnakeDen.addSnake(snake);
        }

        return Response.status(Response.Status.OK).build();
    }
}
