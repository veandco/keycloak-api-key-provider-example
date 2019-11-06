package co.veand.providers;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class RegisterEventListenerProviderFactory implements EventListenerProviderFactory {

    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new RegisterEventListenerProvider(keycloakSession);
    }

    public void init(Config.Scope scope) {
    }

    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    public void close() {
    }

    public String getId() {
        //unique id for the provider
        return "api-key-registration-generation";
    }
}