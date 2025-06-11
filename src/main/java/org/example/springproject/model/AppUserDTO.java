package org.example.springproject.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AppUserDTO {

    private Long id;

    @NotBlank(message = "Användarnamn får ej vara tomt")
    private String username;

    @NotBlank(message = "Roll får ej vara tom")
    private String role;

    @NotBlank(message = "Lösenord krävs")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2,})(?=(?:.*[!@#$%&*]){2,}).{8,}$",
            message = "Lösenordet måste ha minst 8 tecken, 1 versal, 2 siffror och 2 specialtecken (!@#$%&*)"
    )
    private String password;

    private boolean consentGiven;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }
}
