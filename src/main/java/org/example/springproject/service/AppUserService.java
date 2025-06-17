package org.example.springproject.service;

import org.example.springproject.db.AppUserRepository;
import org.example.springproject.model.AppUser;
import org.example.springproject.model.AppUserDTO;
import org.example.springproject.util.LoggingComponent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggingComponent loggingComponent;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, LoggingComponent loggingComponent) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggingComponent = loggingComponent;
    }

    public void save(AppUserDTO appUserDTO) {
        AppUser user = toAppUser(appUserDTO);
        user.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        appUserRepository.save(user);
        loggingComponent.log("Registrerade användare: " + user.getUsername());
    }

    public void deleteById(Long id) {
        if (!appUserRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        AppUser user = appUserRepository.findById(id).orElseThrow();
        appUserRepository.deleteById(id);
        loggingComponent.log("Tog bort användare: " + user.getUsername());
    }

    private AppUser toAppUser(AppUserDTO dto) {
        AppUser user = new AppUser();
        if (dto.getId() != null) {
            user.setId(dto.getId()); // Bara sätt om det finns
        }
       // user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        user.setConsentGiven(dto.isConsentGiven());
        return user;
    }
    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }


    }





