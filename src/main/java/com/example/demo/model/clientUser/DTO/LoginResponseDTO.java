package com.example.demo.model.clientUser.DTO;

public class LoginResponseDTO {
    private String token;
    private String firstName;
    private String lastName;
    private String email;

    public LoginResponseDTO(String token, String firstName, String lastName, String email) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Getters and setters (or use Lombok with @Data if you prefer)
    public String getToken() { return token; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
}
