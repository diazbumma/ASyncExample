package com.diazbumma.asyncexample;

public class Hero {

    private String name;
    private String imageUrl;

    public Hero (String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
