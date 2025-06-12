package org.example.springproject.web;

import jakarta.validation.Valid;
import org.example.springproject.model.AppUser;
import org.example.springproject.model.AppUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppUserController {

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("appuser", new AppUserDTO());
        return "register";
    }

    /*@PostMapping("/addAppUser")
    public String handleForm(@ModelAttribute AppUser appUser) {
        return "result";
    }*/

    @PostMapping("/register")
    public String handleSubmit(@Valid @ModelAttribute("appuser") AppUserDTO appUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        return "register";
    }
    //CONTROLLER VIEW PDF
}
