package org.example.springproject.db;

import jakarta.annotation.PostConstruct;
import org.example.springproject.model.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (appUserRepository.findByUsername("admin") == null) {
            AppUser user = new AppUser();
            user.setUsername("admin");
            user.setRole("ADMIN");
            //Lösenord hashas
            user.setPassword(passwordEncoder.encode("admin"));
            appUserRepository.save(user);
        }
    }
}
