package co.veand.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.UserModel;

public class ApiKeyResource {

    private KeycloakSession session;

    public ApiKeyResource(KeycloakSession session) {
        this.session = session;
    }

    @GET
    @Produces("application/json")
    public Response checkApiKey(@QueryParam("apiKey") String apiKey) {
        //if key exists return 200 otherwise 401
        List<UserModel> result = session.userStorageManager().searchForUserByUserAttribute("api-key", apiKey, session.realms().getRealm("example"));
        return result.isEmpty() ? Response.status(401).build(): Response.ok().build();
    }
}