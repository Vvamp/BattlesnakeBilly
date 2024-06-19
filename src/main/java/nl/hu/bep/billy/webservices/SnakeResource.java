package nl.hu.bep.billy.webservices;

import nl.hu.bep.billy.ApiModels.*;
import nl.hu.bep.billy.models.Battle;
import nl.hu.bep.billy.models.Snake;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.hu.bep.billy.authentication.User;
import nl.hu.bep.billy.security.MySecurityContext;

@Path("/snakes")
public class SnakeResource {
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
        if(sc == null) return Response.status(Response.Status.BAD_REQUEST).build();
        if(!(sc.getUserPrincipal() instanceof User)) return Response.status(Response.Status.FORBIDDEN).build();

        User passedUser = User.getUserByName(user);
        User currentUser = (User) sc.getUserPrincipal();
        if(passedUser != currentUser) return Response.status(Response.Status.FORBIDDEN).build();

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

        Snake snake = currentUser.getSnake();
        Map<String, String> messages = new HashMap<>();
        messages.put("move", "up");
        messages.put("shout", "GOING UP UP UP");
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
        return Response.status(Response.Status.OK).entity(messages).build();
    }




}
