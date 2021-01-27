package com.example.ratatouille.models;

public class AboutUs {
    private Integer profilePicture;
    private String name;
    private String description;

    public AboutUs(Integer profilePicture, String name, String description) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.description = description;
    }

    public Integer getProfilePicture() {
        return profilePicture;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
