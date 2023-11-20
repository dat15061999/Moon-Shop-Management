package com.cg.service.userService.response;

import com.cg.model.enums.ELock;
import com.cg.model.enums.ERole;
import com.cg.model.enums.EStatusCustomer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserEditResponse {
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private String avatar;

    private String phone;

    private LocalDate dob;
    private EStatusCustomer statusCustomer;
    private ELock eLock;
    private ERole eRole;
}
