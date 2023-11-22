package com.cg.model;

import com.cg.model.dto.CustomerResDTO;
import com.cg.model.enums.ELock;
import com.cg.model.enums.ERole;
import com.cg.model.enums.EStatusCustomer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@Accessors(chain = true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String passWord;

    private String phone;

    private LocalDate dob;

    private String address;

    private boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private EStatusCustomer statusCustomer;


    @Enumerated(EnumType.STRING)
    private ELock eLock;


    @Enumerated(value = EnumType.STRING)
    private ERole role;
    public Customer(Long id) {
        this.id = id;
    }

    public CustomerResDTO toCustomerResDTO(){
        return new CustomerResDTO()
                .setName(name)
                .setEmail(email)
                .setPhone(phone)
                .setDob(dob)
                ;
    }
}
