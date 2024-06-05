package com.hanul.springJWT.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinDTO {

    private String username;
    private String password;
    private String displayName;

    public JoinDTO(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }
}
