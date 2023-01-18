package s.m.learning.footballapp.model;

import lombok.Data;

@Data
public class CredentialDTO {
    private String username;
    private String password;
    private String jwt;
}
