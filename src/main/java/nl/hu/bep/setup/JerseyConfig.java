package nl.hu.bep.setup;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("nl.hu.bep.billy.webservices, nl.hu.bep.billy.security");
        register(RolesAllowedDynamicFeature.class);
    }
}
