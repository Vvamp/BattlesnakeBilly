package nl.hu.bep.billy.webservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.ApiModels.SnakePatchRequest;
import nl.hu.bep.billy.algorithms.Move;
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/snakes")
public class SnakeResource {
    @GET
    @Path("{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSnakeByName(@PathParam("user") String user) {
        User currentUser = User.getUserByName(user);
        if (currentUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Snake snake = currentUser.getSnake();
        return Response.status(Response.Status.OK).entity(snake).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("{user}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSnake(@Context ContainerRequestContext requestCtx, @PathParam("user") String user, SnakePatchRequest snakePatchRequest) {
        MySecurityContext sc = (MySecurityContext) requestCtx.getSecurityContext(); // simply passing MySecurityContext as param didn't work(it was null)
        if (sc == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if (!(sc.getUserPrincipal() instanceof User currentUser))
            return Response.status(Response.Status.FORBIDDEN).build();

        User passedUser = User.getUserByName(user);
        if (passedUser != currentUser) return Response.status(Response.Status.FORBIDDEN).build();

        Snake snake = currentUser.getSnake();
        snake.update(snakePatchRequest.color, snakePatchRequest.head, snakePatchRequest.tail);
        return Response.status(Response.Status.OK).entity(snake).build();

    }

    @POST
    @Path("{user}/start")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response start(@PathParam("user") String user, GameRequest request) {
        User currentUser = User.getUserByName(user);
        if (currentUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Snake snake = currentUser.getSnake();
        Map<String, String> messages = new HashMap<>();
        messages.put("shout", "GLHF :)");
        snake.addBattle(new Battle(request));
        System.out.println("[Billy] Starting new battle with id " + request.game.id);
        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @POST
    @Path("{user}/move")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response move(@PathParam("user") String user, GameRequest request) {
        User currentUser = User.getUserByName(user);
        if (currentUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        logRequestToFile(user, request);

        Snake snake = currentUser.getSnake();
        snake.registerTurn(request);
//        System.out.println("[Billy] Server asking for my move (turn " + request.turn + ")");
        Move move = snake.play(request);
        System.out.println("[Billy] I want to play " + move.toString());

        Map<String, String> messages = new HashMap<>();
        messages.put("move", move.toString().toLowerCase());
//        messages.put("move", "right");
        messages.put("shout", "no shouts right now");


        return Response.status(Response.Status.OK).entity(messages).build();
    }

    private void logRequestToFile(String user, GameRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Serialize the request object to JSON
            String jsonRequest = objectMapper.writeValueAsString(request);

            // Create or append to the log file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("/home/vvamp/game_requests.log", true))) {
                writer.write("User: " + user + "\n");
                writer.write("Request: " + jsonRequest + "\n");
                writer.write("=====================================\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("{user}/end")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response end(@PathParam("user") String user, GameRequest request) {
        User currentUser = User.getUserByName(user);
        if (currentUser == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Snake snake = currentUser.getSnake();
        Map<String, String> messages = new HashMap<>();
        messages.put("shout", "OK. GG");
        snake.endBattle(request);
        System.out.println("[Billy] Done playing game " + request.game.id + "!");

        return Response.status(Response.Status.OK).entity(messages).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSnakes() {
        List<User> users = User.getAll();

        List<Snake> snakes = new ArrayList<>();
        for (User user : users) {
            snakes.add(user.getSnake());
        }

        return Response.status(Response.Status.OK).entity(snakes).build();
    }

}
