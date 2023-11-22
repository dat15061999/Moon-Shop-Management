package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping
    public String showHome(){
        return "index";
    }

    @GetMapping("/shop")
    public String showShop(){
        return "shop";
    }

    @GetMapping("/cart")
    public String showCart(){
        return "cart";
    }

    @GetMapping("/detail")
    public String showDetail(){
        return "detail";
    }
    private final ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/")
    public ModelAndView getHome() {
        modelAndView.setViewName("index");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }


    @GetMapping("/dashboard")
    public ModelAndView dash() {
        modelAndView.setViewName("dashboard");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }
    @GetMapping("/product")
    public ModelAndView pro() {
        modelAndView.setViewName("product");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }
    @GetMapping("/bill")
    public ModelAndView bill() {
        modelAndView.setViewName("bill");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }
    @GetMapping("/user")
    public ModelAndView user() {
        modelAndView.setViewName("user");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }

    @GetMapping("/price")
    public ModelAndView price() {
        modelAndView.setViewName("price");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }

    public ModelAndView Login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            // Tìm người dùng theo username
            Optional<Customer> user = userService.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username);

            if (user.isPresent()) {
                modelAndView.addObject("loggedIn", true);
                modelAndView.addObject("user", user.get());
            } else {
                modelAndView.addObject("loggedIn", false);
            }
        } else {
            modelAndView.addObject("loggedIn", false);
        }

        return modelAndView;
    }
    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }

    @GetMapping("/checkout")
    public String showCheckout(){
        return "checkout";
    }
    @GetMapping("/login")
    public String showLogin(){
        return "/login";
    }
}
