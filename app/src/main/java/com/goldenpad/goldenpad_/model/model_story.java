package com.goldenpad.goldenpad_.model;

public class model_story {
    private String id, author, title, genre, date, story_desc, story;

    public model_story() {}

    public model_story(String title, String date){
        this.title = title;
        this.date = date;
    }

    public model_story(String id, String title, String author,String genre, String story_desc){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.story_desc = story_desc;
    }

    public model_story(String id, String author, String title, String genre, String date, String story_desc, String story) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.date = date;
        this.story_desc = story_desc;
        this.story = story;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStory_desc() {
        return story_desc;
    }

    public void setStory_desc(String story_desc) {
        this.story_desc = story_desc;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }


//
//    public String getTitle() { return title; }
//    public String getDate() { return date; }
//    public void setTitle(String title) { this.title = title; }
//    public void setDate(String date) { this.date = date; }
}
