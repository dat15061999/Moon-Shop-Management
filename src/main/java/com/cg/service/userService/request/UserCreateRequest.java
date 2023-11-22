package com.cg.service.userService.request;

import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest implements Validator {
    private String name;
    private String passWord;
    private String email;
    private String phone;
    private String dob;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateRequest userCreateRequest = (UserCreateRequest) target;
        String name = userCreateRequest.name;
        String passWord = userCreateRequest.passWord;
        String dobString = userCreateRequest.dob;
        String email = userCreateRequest.email;

        if (name.length() < 6) {
            errors.rejectValue("name", "name.length", "Tên phải có ít nhất là 6 ký tự");
        }

        if (passWord.length() < 6) {
            errors.rejectValue("passWord", "passWord.length", "Mật khẩu phải có ít nhất là 6 ký tự");
        }
        if (StringUtils.hasText(email)) {
            String emailPattern = "^\\S+@(gmail\\.com|yahoo\\.com|email\\.com|mailinator\\.com)$";
            if (!email.matches(emailPattern)) {
                errors.rejectValue("email", "email.invalid", "Email không hợp lệ");
            }
        }
        if (email.length() < 6) {
            errors.rejectValue("email", "email.length", "Email không được để trống");
        }

        if (dobString != null) {
            try {
                LocalDate dob = LocalDate.parse(dobString);
                if (calculateAge(dob) < 16) {
                    errors.rejectValue("dob", "dob.tooYoung", "Người dùng phải đủ 16 tuổi.");
                }
            } catch (DateTimeParseException e) {
                errors.rejectValue("dob", "dob.invalidFormat", "Định dạng ngày tháng năm sinh không hợp lệ.");
            }
        }
    }
    private int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
}
