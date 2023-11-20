package com.cg.service.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String phone;

    private String passWord;

    private String email;

}
