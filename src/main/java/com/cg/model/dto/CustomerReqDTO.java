package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.service.product.request.ProductEditRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class CustomerReqDTO implements Validator {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;

    @NotBlank(message = "Phone is mandatory")
    private String phone;


    private LocalDate dob;


    public Customer toCustomer(){
        return new Customer()
                .setEmail(email)
                .setName(name)
                .setPhone(phone)
                .setDob(dob)
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerReqDTO customerReqDTO = (CustomerReqDTO) target;
        String name = customerReqDTO.name;
        String email = customerReqDTO.email;
        String phone = customerReqDTO.phone;
        String dobString = String.valueOf(customerReqDTO.dob);
        if (name.length() < 6) {
            errors.rejectValue("name", "name.length", "Tên phải có ít nhất là 6 ký tự");
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
        if (StringUtils.hasText(email)) {
            String emailPattern = "^\\S+@(gmail\\.com|yahoo\\.com|email\\.com|mailinator\\.com)$";
            if (!email.matches(emailPattern)) {
                errors.rejectValue("email", "email.invalid", "Email không hợp lệ");
            }
        }
        if (email.length() < 6) {
            errors.rejectValue("email", "email.length", "Email không được để trống");
        }
    }
    private int calculateAge(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dob, currentDate).getYears();
    }
}
