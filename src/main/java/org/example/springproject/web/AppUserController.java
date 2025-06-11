package org.example.springproject.web;

import jakarta.validation.Valid;
import org.example.springproject.model.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppUserController {

    @GetMapping("/addAppUser")
    public String showForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "form";
    }

    @PostMapping("/addAppUser")
    public String handleForm(@ModelAttribute AppUser appUser) {
        return "result";
    }

    @PostMapping("/addAppUser")
    public String handleSubmit(@Valid @ModelAttribute AppUser appUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "result";
    }
}
