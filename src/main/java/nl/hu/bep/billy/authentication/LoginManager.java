package nl.hu.bep.billy.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LoginManager {
    // map with username, user
    private static Map<String, User> users;
    private static List<String> validatedTokens;
    private static Key key;
    public LoginManager() {
        if(users == null) {
            users = new HashMap<>();
            users.put("Vvamp", new User("Vvamp", "admin", "user"));
            users.put("NietVvamp", new User("NietVvamp", "anders", "user"));
        }
        if(validatedTokens == null) {
            validatedTokens = new ArrayList<>();
        }
        if(key == null) {
            key = MacProvider.generateKey();
        }

    }
    public String validateLogin(String username, String password) {
        if(users.containsKey(username) == false) {
            throw new IllegalArgumentException("User '" + username + "' not found");
        }

        if(users.get(username).matchCredentials(username, password) == false) {
            throw new IllegalArgumentException("Wrong password");
        }
        String role = users.get(username).getRole();
        return role;
    }

    public String createToken(String username, String role) {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder().setSubject(username).setExpiration(expiration.getTime()).claim("role", role).setIssuedAt(Calendar.getInstance().getTime()).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    public void validateToken(String token) {
        validatedTokens.add(token);
    }
    public ValidationResult checkTokenValidity(String token) {
        return checkTokenValidity(token, null);
    }

    public ValidationResult checkTokenValidity(String token, String username) {
        if (!validatedTokens.contains(token)) {
            return new ValidationResult(ValidationStatus.INVALID, "The token was not in the authorisation list.");
        }

        JwtParser parser = Jwts.parser().setSigningKey(key);
        Claims claims = parser.parseClaimsJws(token).getBody();
        User user = users.get(claims.getSubject());
        if(username != null){
            if(! claims.getSubject().equals(username))
                return new ValidationResult(ValidationStatus.INVALID, "The token was not found in the user's token list.");
        }

        Calendar calendar = Calendar.getInstance();
        if(claims.getExpiration().before(calendar.getTime())) {
            return new ValidationResult(ValidationStatus.EXPIRED, String.format("The token has expired. ( %s < %s )", claims.getExpiration().toString(), calendar.getTime().toString()));
        }

        return new ValidationResult(ValidationStatus.VALID, "No issues with the token were found.", user);
    }

    public void invalidateToken(String token) {
        try{
            validatedTokens.remove(token);
        }catch(Exception e){
            return;
        }
    }
}
