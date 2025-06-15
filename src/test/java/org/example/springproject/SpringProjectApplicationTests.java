package org.example.springproject;

import org.example.springproject.db.AppUserRepository;
import org.example.springproject.model.AppUser;
import org.example.springproject.model.AppUserDTO;
import org.example.springproject.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SpringProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserService appUserService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testRegistrationReturnsResultView() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("password", "Abcd12!@") // Valideringsgodkänt lösenord
                        .param("role", "newrole")
                        .param("consentGiven", "true")) // om du kräver detta i formuläret
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/success"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testInvalidRegistrationReturnsResultErrorMessage() throws Exception {
        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("username", "newuser")
                        .param("password", "Anka") // Valideringsgodkänt lösenord
                        .param("role", "newrole")
                        .param("consentGiven", "true")) // om du kräver detta i formuläret
                .andExpect(view().name("register")); // still on same page
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUserIsRemoved() throws Exception {
        // 1. Skapa en användare via tjänstelagret
        AppUserDTO dto = new AppUserDTO();
        dto.setUsername("tobedeleted");
        dto.setPassword("Abcd12!@");
        dto.setRole("USER");
        dto.setConsentGiven(true);
        appUserService.save(dto);

        // 2. Hämta ID
        AppUser user = appUserRepository.findByUsername("tobedeleted");
        Long id = user.getId();

        // 3. Skicka delete-request
        mockMvc.perform(post("/users/delete/" + id).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));

        // 4. Verifiera att användaren raderats
        AppUser deleted = appUserRepository.findByUsername("tobedeleted");
        assertNull(deleted);
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        // Skapa användare i databasen
        AppUserDTO dto = new AppUserDTO();
        dto.setUsername("loginuser");
        dto.setPassword("Abcd12!@"); // Valideringsgodkänt
        dto.setRole("USER");
        dto.setConsentGiven(true);
        appUserService.save(dto);

        // Utför login
        mockMvc.perform(formLogin().user("loginuser").password("Abcd12!@"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index")); // eller "/home", beroende på säkerhetskonfiguration
    }






}
