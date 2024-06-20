package nl.hu.bep.billy.security;

import nl.hu.bep.billy.authentication.LoginManager;
import nl.hu.bep.billy.authentication.ValidationResult;
import nl.hu.bep.billy.authentication.ValidationStatus;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestCtx) {
        boolean isSecure = requestCtx.getSecurityContext().isSecure();
        String scheme = requestCtx.getUriInfo().getRequestUri().getScheme();
        // users are quests until valid jqt
        LoginManager loginManager = new LoginManager();
        MySecurityContext msc = new MySecurityContext(null, scheme);
        String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer".length()).trim();

            ValidationResult tokenResult = loginManager.checkTokenValidity(token);
            if (tokenResult.getStatus() == ValidationStatus.VALID) {
                System.out.println("Proceeding as user");
                msc = new MySecurityContext(tokenResult.getUser(), scheme);
            } else {// return guest
                System.out.println("Proceeding as quest");
            }

        }
        requestCtx.setSecurityContext(msc);
    }
}
