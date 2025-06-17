package org.example.springproject.web;

import jakarta.validation.Valid;
import org.example.springproject.model.AppUser;
import org.example.springproject.model.AppUserDTO;
import org.example.springproject.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String getAllUsers(Model model) {
        List<AppUser> users = appUserService.getAll();
        model.addAttribute("appusers", users);
        return "users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        appUserService.deleteById(id);
        return "redirect:/users";
    }
}
