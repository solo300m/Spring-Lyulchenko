package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.app.services.NewLoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer")
public class NewLoginController {
    private final Logger logger = Logger.getLogger(NewLoginController.class);
    private LoginService loginService;


    @Autowired
    public NewLoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @GetMapping
    public String customer(Model model){
        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm",new LoginForm());
        return "new_customer";
    }

    @PostMapping("/reg")
    public String registration(LoginForm loginForm){
        if(loginService.registration(loginForm)){
            logger.info("login OK redirect to book shelf");
            return "redirect:/login";
        }
        else{
            logger.info("login is not gud");
            return "new_customer";
        }
    }
}
