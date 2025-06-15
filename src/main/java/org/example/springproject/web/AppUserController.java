package org.example.springproject.web;

import jakarta.validation.Valid;
import org.example.springproject.model.AppUser;
import org.example.springproject.model.AppUserDTO;
import org.example.springproject.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("appuser", new AppUserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String handleSubmit(@Valid @ModelAttribute("appuser") AppUserDTO appUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        appUserService.save(appUserDTO);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", appUserService.findAll());
        return "users"; // users.html
    }

    //CONTROLLER VIEW PDF
}
