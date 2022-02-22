package com.example.emotion_dairy;

import android.graphics.drawable.Drawable;

import java.util.Date;

public class ListViewItem {
    private String date;
    private String name;
    private String title;
    private String content;
    private String emotion;

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    @Override
    public String toString() {
        return "BoardData{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public ListViewItem() {
    }

    public ListViewItem(String date, String name, String title, String content) {
        this.date = date;
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
