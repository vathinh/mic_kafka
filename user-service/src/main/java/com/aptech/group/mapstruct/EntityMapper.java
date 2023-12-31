package com.aptech.group.mapstruct;

import com.aptech.group.model.TitleEntity;
import com.aptech.group.model.UserEntity;
import com.aptech.group.repository.TitleRepository;
import com.aptech.group.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TitleRepository titleRepository;

    public UserEntity mappingUser(Integer userId) {
        if (userId == null) {
            return null;
        }
        Optional<UserEntity> userEntityMono = userRepository.findById(userId);
        return userEntityMono.orElse(null);
    }

    public TitleEntity mappingTitle(Integer titleID) {
        if (titleID == null) {
            return null;
        }
        Optional<TitleEntity> titleEntity = titleRepository.findById(titleID);
        return titleEntity.orElse(null);
    }



}
