package org.example.springproject.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final PasswordEncoder passwordEncoder;

    public AppUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void save(AppUserDTO appUserDTO) {
        AppUser appUser = toAppuser(appUserDTO);
    }

    /*public Person toPerson(PersonDTO dto) {
        Person p = new Person();
        p.setName(dto.getName());
        p.setAge(dto.getAge());
        return p*/

    private AppUser toAppuser(AppUserDTO appUserDTO) {
        AppUser appUser = new AppUser();
        appUser.setId(appUserDTO.getId());
        appUser.setUsername(appUserDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUserDTO.getPassword()));
        appUser.setRole(appUserDTO.getRole());
        appUser.setConsentGiven(appUserDTO.isConsentGiven());
        return appUser;
    }

    private AppUserDTO toAppUserDTO(AppUser appUser) {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setId(appUser.getId());
        appUserDTO.setUsername(appUser.getUsername());
        appUserDTO.setPassword(appUser.getPassword());
        appUserDTO.setRole(appUser.getRole());
        appUserDTO.setConsentGiven(appUser.isConsentGiven());
        return appUserDTO;
    }


}
