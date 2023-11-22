package com.cg.model.dto;

import com.cg.model.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class CustomerReqDTO {

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

}
