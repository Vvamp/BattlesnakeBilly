package nl.hu.bep.billy.authentication;

import io.jsonwebtoken.SignatureAlgorithm;
import nl.hu.bep.billy.models.Snake;

import javax.crypto.SecretKey;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Principal {
    private String name;
    private String role;
    private String password;
    private static List<User> users = new ArrayList<>();
    private Snake snake;

    public User(String name, String password, String role) {
        this.name = name;
        this.role = role;
        this.password = password;
        snake = new Snake();
        users.add(this);
    }
    public User(String name, String password) {
        this.name = name;
        this.role = "user";
        this.password = password;
        snake = new Snake();
        users.add(this);
    }


    public boolean matchCredentials(String username, String password) {
        return this.password.equals(password) && this.name.equals(username);
    }

    public String getRole() {
        return role;
    }

    public Snake getSnake(){
        return snake;
    }

    public static List<User> getAll(){
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User myUser = (User) o;
        return Objects.equals(name, myUser.name);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
    public static boolean addUser(User user) {
        if (users.contains(user)) return false;
        users.add(user);
        return true;
    }
    public static User getUserByName(String user) {
        return users.stream().filter(u ->
                u.getName().equals(user)).findFirst().orElse(null);
    }
    @Override
    public String getName() {
        return name;
    }
}

