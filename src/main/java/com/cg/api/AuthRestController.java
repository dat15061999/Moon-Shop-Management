package com.cg.api;

import com.cg.repository.CustomerRepository;
import com.cg.service.auth.AuthService;
import com.cg.service.auth.request.RegisterRequest;
import com.cg.service.userService.UserService;
import com.cg.service.userService.request.UserCreateRequest;
import com.cg.service.userService.request.UserEditRequest;
import com.cg.until.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthRestController {
    private AuthService authService;
    private UserService userService;
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody RegisterRequest registerRequest, BindingResult bindingResult){
        registerRequest.setUserService(userService);
        registerRequest.validate(registerRequest,bindingResult);
        if(bindingResult.hasFieldErrors()){
            return AppUtil.mapErrorToResponse(bindingResult);
        }
        return new ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> check(@RequestBody LoginRequest loginRequest, BindingResult bindingResult){
//        loginRequest.setUserService(userService);
//        loginRequest.validate(loginRequest,bindingResult);
//        if(bindingResult.hasFieldErrors()){
//            return AppUtil.mapErrorToResponse(bindingResult);
//        }
//        return ResponseEntity.ok("oke");
//    }

}
