package com.cg.service.userService.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserListResponse {
    private Long id;

    private String name;

    private String email;
    private String password;

    private String phone;

    private LocalDate dob;
    private String avatar;

    private String statusCustomer;

    private String eLock;

}

