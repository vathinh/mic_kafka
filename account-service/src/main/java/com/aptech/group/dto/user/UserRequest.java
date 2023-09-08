package com.aptech.group.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer title;

    @NotBlank
    private String firstName;

    @NotBlank
    private String password;

    @NotBlank
    private String lastName;
    private String userType;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    private String keycloakId;

    private Long version;
}
