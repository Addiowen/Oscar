package com.compulynx.compas.models.additionsJwt;

import java.io.Serializable;

public class LoginRequest implements Serializable {


    private String username;
    private String password;

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

    //need default constructor for JSON Parsing
    public LoginRequest()
    {

    }

    public LoginRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}