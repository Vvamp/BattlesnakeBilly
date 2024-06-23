package nl.hu.bep.billy;

import nl.hu.bep.billy.authentication.LoginManager;
import nl.hu.bep.billy.authentication.User;
import nl.hu.bep.billy.models.Snake;
import nl.hu.bep.billy.webservices.SnakeResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakeResourceTest {

    @Test
    public void testGetSnakeByNameTest(){
        String testName = "testUser";
        String testPassword = "testPassword";
        String testRole = "testRole";
        User testUser = new User(testName, testPassword, testRole);
        LoginManager loginManager = new LoginManager();
        loginManager.addUser(testUser);

        String head = Customizations.getRandomHead();
        String tail = Customizations.getRandomTail();
        testUser.getSnake().setHead(head);
        testUser.getSnake().setTail(tail);

        SnakeResource snakeResource = new SnakeResource();
        Response response = snakeResource.getSnakeByName(testName);

        Snake responseEntity = (Snake)response.getEntity();

        assertEquals(testUser.getSnake(), responseEntity, "The snakes/<user> resource should return the right snake for a given user.");
    }



}
