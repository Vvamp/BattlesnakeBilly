package nl.hu.bep.billy.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hu.bep.billy.models.Snake;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Principal {
    @JsonIgnore
    private static final List<User> users = new ArrayList<>();

    @JsonProperty
    private final String name;
    @JsonProperty
    private final String role;
    @JsonProperty
    private final String password;
    @JsonProperty
    private final Snake snake;


    public User(String name, String password, String role) {
        this.name = name;
        this.role = role;
        this.password = password;
        snake = new Snake();
        users.add(this);
    }

    @JsonCreator
    public User(@JsonProperty("name") String name, @JsonProperty("password") String password, @JsonProperty("role") String role, @JsonProperty("snake") Snake snake){
        this.name = name;
        this.role = role;
        this.password = password;
        this.snake = snake;
        users.add(this);
    }

    public User(String name, String password) {
        this.name = name;
        this.role = "user";
        this.password = password;
        snake = new Snake();
        users.add(this);
    }

    public static boolean addUser(User user) {
        if (users.contains(user)) return false;
        users.add(user);
        return true;
    }

    public static User getUserByName(String user) {
        return users.stream().filter(u -> u.getName().equals(user)).findFirst().orElse(null);
    }

    public boolean matchCredentials(String username, String password) {
        return this.password.equals(password) && this.name.equals(username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User myUser = (User) o;
        return Objects.equals(name, myUser.name);
    }

    public String getRole() {
        return role;
    }

    public Snake getSnake() {
        return snake;
    }

    public static List<User> getAll() {
        return users;
    }

    @Override
    public String getName() {
        return name;
    }
}

