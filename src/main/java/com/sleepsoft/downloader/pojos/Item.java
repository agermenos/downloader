package com.sleepsoft.downloader.pojos;

/**
 * Created by Alejandro on 3/20/16.
 */
public class Item {
    private String output;
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

    public String getOutput() {
        String folderName=title.replace(' ','_').concat("_" + year).toLowerCase();
        return (output!=null?output+"/"+folderName:folderName);
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Item(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Item(String title, int year, String output) {
        this.title=title;
        this.year=year;
        this.output=output;
    }

    public Item(String title) {
        this.title = title;
    }

    public Item(){

    }
}
