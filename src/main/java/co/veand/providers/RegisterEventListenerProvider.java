package co.veand.providers;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.jpa.entities.UserAttributeEntity;
import org.keycloak.models.jpa.entities.UserEntity;
import org.keycloak.models.RealmModel;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.keycloak.common.util.RandomString;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;

public class RegisterEventListenerProvider implements EventListenerProvider {
    
    private KeycloakSession session;
    private RealmProvider model;
    //keycloak utility to generate random strings, anything can be used e.g UUID,..
    private RandomString randomString;
    private EntityManager entityManager;

    public RegisterEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
        this.entityManager = session.getProvider(JpaConnectionProvider.class).getEntityManager();
        this.randomString = new RandomString(50);
    }

    public void onEvent(Event event) {
        //we are only interested in the register event
        if (event.getType().equals(EventType.REGISTER)) {
            RealmModel realm = model.getRealm(event.getRealmId());
            String userId = event.getUserId();
            addApiKeyAttribute(userId);
        }
    }

    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
        // in case the user is created from admin or rest api
        if (Objects.equals(adminEvent.getResourceType(), ResourceType.USER) && Objects.equals(adminEvent.getOperationType(), OperationType.CREATE)) {
            String userId = adminEvent.getResourcePath().split("/")[1];
            if (Objects.nonNull(userId)) {
                addApiKeyAttribute(userId);
            }
        }
    }

    public void addApiKeyAttribute(String userId) {
          String apiKey = randomString.nextString();
          UserEntity userEntity = entityManager.find(UserEntity.class, userId);
          UserAttributeEntity attributeEntity = new UserAttributeEntity();
          attributeEntity.setName("api-key");
          attributeEntity.setValue(apiKey);
          attributeEntity.setUser(userEntity);
          attributeEntity.setId(UUID.randomUUID().toString());
          entityManager.persist(attributeEntity);
     }

    public void close() {
        //belongs to the interface, used in case there is some clean up to do, before destroying instances.  
    }
}