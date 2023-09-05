package com.aptech.group.service;

import com.aptech.group.dto.user.UserCriteria;
import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.dto.user.UserUpdateRequest;
import com.aptech.group.model.UserEntity;
import org.springframework.data.domain.Page;


import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;

public interface UserService {
    void create(UserRequest request);
    List<UserResponse> getAll();

    UserResponse getById(Integer id);
    Page<UserResponse> getAllByCriteria(UserCriteria userCriteria);

    void updateUser(Integer id, UserUpdateRequest userUpdateRequest) throws NotFoundException;

    void deleteUsers(Map<Integer, Long> ids);

    List<UserEntity> findAllUsersByTitleIds(List<Integer> ids);
    void updateKeyCloakId(Integer id, String keycloakId);

    void createKafka(UserRequest userRequest);

    void updateKCId(UserRequest userRequest);


}
