package com.cg.service.product.request;

import com.cg.service.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Data
@NoArgsConstructor
public class ProductEditRequest implements Validator {
    private String id;
    private String name;

    private String price;

    private String description;


    private List<SelectOptionRequest> images;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductEditRequest productEditRequest = (ProductEditRequest) target;

        String name = productEditRequest.name;
        String description = productEditRequest.description;
        String price = productEditRequest.price;
        if (name.length() < 6) {
            errors.rejectValue("name", "name.length", "Tên phải có ít nhất là 6 ký tự.");
        }

        if (description.length() < 6) {
            errors.rejectValue("description", "description.length", "Miêu tả chỉ phải có ít nhất là 6 ký tự.");
        }

        if (!isNumeric(price)) {
            errors.rejectValue("price", "price.notNumeric", "Giá sản phẩm phải là một số.");
        } else {
            double priceValue = Double.parseDouble(price);
            if (priceValue < 10000 || priceValue > 1000000) {
                errors.rejectValue("price", "price.range", "Giá sản phẩm phải lớn hơn 10000 và bé hơn 1000000.");
            }
        }
    }
}
