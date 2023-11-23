package com.cg.service.auth.request;

import com.cg.model.Customer;
import com.cg.repository.CustomerRepository;
import com.cg.service.request.SelectOptionRequest;
import com.cg.service.userService.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Data
@NoArgsConstructor
public class RegisterRequest  implements Validator {

    private String name;

    private String phone;

    private String passWord;

    private String email;
    private String dob;
    private SelectOptionRequest avatar;
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) target;
        String name = registerRequest.name;
        String passWord = registerRequest.passWord;
        String phone = registerRequest.phone;
        String dobString = registerRequest.dob;
        String email = registerRequest.email;
        Customer customer = userService.findByName(name);
        Customer customerEmail = userService.findByEmail(email);
        Customer customerPhone = userService.findByPhone(phone);
        SelectOptionRequest avatar = registerRequest.avatar;

        if(customer != null){
            errors.rejectValue("name", "name.invalid", "Tài khoản bị trùng");

        }
        if(customerEmail != null){
            errors.rejectValue("email", "email.invalid", "Email bị trùng");

        }
        if(customerPhone != null){
            errors.rejectValue("phone", "phone.invalid", "Phone bị trùng");

        }


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
        if ( avatar.getId() == null) {
            errors.rejectValue("avatar", "avatar.null", "Avatar không được để trống.");
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
