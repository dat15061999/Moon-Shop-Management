package com.cg.controller;



import com.cg.model.Customer;
import com.cg.service.auth.AuthService;
import com.cg.service.auth.request.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import com.cg.service.userService.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;


@AllArgsConstructor
@Controller
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;

    private  final UserService userService;
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       ModelAndView modelAndView = new ModelAndView();
        if (auth != null
                && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                && auth.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            Optional<Customer> user = userService.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username);

            if (user.isPresent()) {
                modelAndView.addObject("loggedIn", true);
                modelAndView.addObject("user", user.get());
            } else {
                modelAndView.addObject("loggedIn", false);
            }
            return "redirect:/home/user";
        }else{

            return "redirect:/home" ;

        }
    }


    @GetMapping("/register")
    private ModelAndView product(){
        return new ModelAndView("/register");
    }

}