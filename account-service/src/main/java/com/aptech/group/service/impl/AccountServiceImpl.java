package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Value("${keycloak.auth-server-url}")
    private String keycloakAdminUrl;

    @Value("${admin.keycloak.client-id}")
    private String keycloakClientId;

    @Value("${admin.keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.realm}")
    private String keycloakReal;

    @Value("${keycloak.resource}")
    private String keycloakResource;

    @Autowired
    private KafkaProducer kafkaProducer;

    private Keycloak getKeycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(keycloakReal)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
    }
    @Override
    @Transactional
    public String createAccount(UserRequest userRequest) {
        if (userRequest == null) {
            throw new NotFoundException(new NotFoundException("Can not create user"));
        }

        Keycloak keycloak = getKeycloakClient();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        Map<String, List<String>> attributes =new HashMap<>();
        attributes.put("phone", List.of(userRequest.getPhone()));
        attributes.put("userType",  List.of(userRequest.getUserType()));
        user.setAttributes(attributes);

        RealmResource realmResource = keycloak.realm(keycloakReal);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);
        int status = response.getStatus();
        if (status == HttpStatus.CONFLICT.value()) {
            throw new RuntimeException("Error on response keycloak");
        }
        String userId = CreatedResponseUtil.getCreatedId(response);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userRequest.getPassword());

        UserResource userResource = usersResource.get(userId);

        userResource.resetPassword(passwordCred);

        userRequest.setKeycloakId(userId);
        kafkaProducer.sendMessage(userRequest);
        return userId;
    }
}
