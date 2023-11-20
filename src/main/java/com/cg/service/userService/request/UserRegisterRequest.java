package com.cg.service.userService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterRequest {
    private String name;

    private String email;

    private String passWord;

    private String phone;
}
