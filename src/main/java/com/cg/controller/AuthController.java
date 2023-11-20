package com.cg.controller;



import com.cg.model.Customer;
import com.cg.service.auth.AuthService;
import com.cg.service.auth.request.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@AllArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;
    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        RegisterRequest user = new RegisterRequest();
        model.addAttribute("user", user);
        return "register";
    }
    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<Customer> customer = authService.getCustomerByName(auth.getName());

        Customer newCustomer = customer.get();

        session.setAttribute("idCustomer", newCustomer.getId());

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/dashboard";
        }else{
            return "redirect:/home";
        }
    }


    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") RegisterRequest request,
                               BindingResult result,
                               Model model)
    {
        authService.checkNameOrPhoneOrEmail(request, result);
        model.addAttribute("user",request);
        if(result.hasErrors()){
            return "/register";
        }

        authService.register(request);
        return "redirect:/register?success";
    }
}