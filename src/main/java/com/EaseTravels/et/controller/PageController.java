package com.EaseTravels.et.controller;

import com.EaseTravels.et.config.AppConfig;
import com.EaseTravels.et.entity.User;
import com.EaseTravels.et.forms.UserForm;
import com.EaseTravels.et.models.Message;
import com.EaseTravels.et.models.types.MessageColors;
import com.EaseTravels.et.models.types.MessageType;
import com.EaseTravels.et.models.types.Providers;
import com.EaseTravels.et.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class PageController {

    public final UserService userService;
    public  final AppConfig appConfig;

    public PageController(UserService userService, AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }

    @GetMapping("/")
    public String home() {
        log.info("Insdie home page handler");
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        log.info("Insdie register page handler");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session, Model model) {
        log.info("Insdie do-register page handler");
        if (bindingResult.hasErrors()) {
            log.error("Validation Error");
            bindingResult.getAllErrors().forEach(error -> {
                log.error("Validation error: {}", error.toString());
            });
            return "register";
        } else if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            log.error("Password and Confirm Password does not match");
            return "register";
        }
        log.info("User Details: {}", userForm);

        // Save userForm to database

        String providerType = Providers.SELF.toString();

        User user = User.builder()
                .name(userForm.getFirstName() + " " + userForm.getLastName())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .phoneNo(userForm.getPhoneNo())
                .providerType(providerType)
                .profilePic(appConfig.getDefaultProfilePic())
                .build();

        userService.saveUser(user);

//        model.addAttribute("message", "User registered successfully");

        Message message = Message.builder()
                .content("User registered successfully")
                .type(MessageType.SUCCESS.toString())
                .color(MessageColors.green.toString())
                .build();

        session.setAttribute("message", message);

        return "redirect:/register";
    }
}
