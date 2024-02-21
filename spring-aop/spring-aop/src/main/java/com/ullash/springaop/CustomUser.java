package com.ullash.springaop;

public class CustomUser {

    private String username;
    private String email;

    public CustomUser() {

        super();
    }

    public CustomUser(String username, String email) {
        this.username = username;
        this.email = email;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString method
    @Override
    public String toString() {
        return "CustomUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
