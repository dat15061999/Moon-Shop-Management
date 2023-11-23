package com.cg.service.bill.request;

import com.cg.service.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Data
@NoArgsConstructor
public class BillCreateRequest implements Validator {
    private String userName;
    private String productName;
    private String productPrice;
    private String quantity;
    private String totalAmount;
    private List<String> idProducts;
    private SelectOptionRequest user;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BillCreateRequest billCreateRequest = (BillCreateRequest) target;

    }
}
