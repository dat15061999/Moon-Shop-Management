package com.cg.service.userService.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSaveRequest {

    private String name;

    private String email;

    private String passWord;

    private String phone;

    private String  dob;

    private String statusCustomer;

//    private String eLock;


}
