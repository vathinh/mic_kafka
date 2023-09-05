package com.aptech.group.controller;

import com.aptech.group.dto.user.UserCriteria;
import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.dto.user.UserUpdateRequest;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;

import static com.aptech.group.controller.UserServiceEndpoints.USER_SERVICE_PATH;

@RequestMapping(USER_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;
    @GetMapping
    @PreAuthorize("hasAnyRole(@environment.getProperty('user.read'), @environment.getProperty('user.full'))")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(@environment.getProperty('user.create'), @environment.getProperty('user.full'))")
    public void create(@RequestBody UserRequest userRequest) {
        userService.create(userRequest);
    }

    @GetMapping("getByCriteria")
    public Page<UserResponse> getAllByCriteria(UserCriteria userCriteria) {
        return userService.getAllByCriteria(userCriteria);
    }

    @PutMapping
    public void update(Integer id, UserUpdateRequest userUpdateRequest) throws NotFoundException {
        userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping
    public void deleteUsers(Map<Integer, Long> ids) {
        userService.deleteUsers(ids);
    }

    @PostMapping("createKC")
    public void createKC(@RequestBody UserRequest userRequest) {
        userService.createKafka(userRequest);
    }
}
