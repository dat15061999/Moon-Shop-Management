package com.cg.model.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartDetailChangeAmountReqDTO {

    @Min(value = 1, message = "Giá trị tối thiểu là 1")
    @Max(value = 50, message = "Giá trị tối đa là 50")
    private int quantity;
}
