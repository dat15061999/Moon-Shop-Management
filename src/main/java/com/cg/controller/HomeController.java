package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.userService.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final UserService userService;
    @GetMapping("")
    public ModelAndView index(HttpSession session) {
        ModelAndView view = new ModelAndView("index");
        Long id = (Long) session.getAttribute("idCustomer");
        view.addObject("customerID", id);
        return view;
    }

    @GetMapping("/shop")
    public ModelAndView showShop(HttpSession session){
        ModelAndView view = new ModelAndView("shop");
        Long id = (Long) session.getAttribute("idCustomer");
        view.addObject("customerID", id);
        return view;
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

    @GetMapping("/price")
    public ModelAndView price() {
        modelAndView.setViewName("price");
        ModelAndView modelAndView = Login();
        modelAndView.addObject("someKey", "someValue");
        return modelAndView;
    }

    public ModelAndView Login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
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
    public ModelAndView showContact(HttpSession session){
        ModelAndView view = new ModelAndView("contact");
        Long id = (Long) session.getAttribute("idCustomer");
        view.addObject("customerID", id);
        return view;
    }

    @GetMapping("/checkout")
    public ModelAndView showCheckout(HttpSession session){
        ModelAndView view = new ModelAndView("checkout");
        Long id = (Long) session.getAttribute("idCustomer");
        view.addObject("customerID", id);
        return view;
    }

}
