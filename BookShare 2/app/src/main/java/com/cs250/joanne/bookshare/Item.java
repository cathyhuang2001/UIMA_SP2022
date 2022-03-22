package com.cs250.joanne.bookshare;

import java.lang.String;

/**
 * Holds data for one item
 */
public class Item {
    private String title;
    private String author;
    private String availability;

    Item(String title, String author) {

        this.title = title;
        this.author = author;
        this.availability = "IN";
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getAvailability() { return availability; }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
