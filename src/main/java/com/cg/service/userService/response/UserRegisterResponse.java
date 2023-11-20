package com.cg.service.userService.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterResponse {
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private String phone;
}
