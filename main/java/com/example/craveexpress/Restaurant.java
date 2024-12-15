package com.example.craveexpress;

public class Restaurant {
    private String name;
    private String rating;
    private int imageResource;

    public Restaurant(String name, String rating, int imageResource) {
        this.name = name;
        this.rating = rating;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public int getImageResource() {
        return imageResource;
    }
}
