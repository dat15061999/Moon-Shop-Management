package com.cg.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class CustomerResDTO {
    private String name;

    private String email;

    private String phone;

    private LocalDate dob;


}
