package com.cg.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeAPI {

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
        return "login/login";
    }
}
