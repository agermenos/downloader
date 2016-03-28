package com.sleepsoft.downloader.pojos;

/**
 * Created by Alejandro on 3/20/16.
 */
public class Item {
    private String title;
    private int year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Item(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Item(String title) {
        this.title = title;
    }

    public Item(){

    }
}
