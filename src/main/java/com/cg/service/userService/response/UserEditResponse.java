package com.cg.service.userService.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class UserEditResponse {
    private Long id;

    private String name;

    private String email;


    private String passWord;

    private LocalDate dob;

    private String phone;
    private String avatar;

    private String idAvatar;
    private String eLock;
    private String eRole;
}
