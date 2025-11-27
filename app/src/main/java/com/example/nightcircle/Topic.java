package com.example.nightcircle;

public class Topic {
    private String title;
    private String mood;
    private int currentUsers;
    private int capacity;

    public Topic(String title, String mood, int currentUsers, int capacity) {
        this.title = title;
        this.mood = mood;
        this.currentUsers = currentUsers;
        this.capacity = capacity;
    }

    public String getTitle() { return title; }
    public String getMood() { return mood; }
    public int getCurrentUsers() { return currentUsers; }
    public int getCapacity() { return capacity; }
}

