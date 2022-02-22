package com.example.emotion_dairy;

import java.util.Date;

public class BoardData {
    private String createTime;
    private String title;
    private String contents;
    private int tno;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getTno() {
        return tno;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
    public BoardData(){

    }

    public BoardData(String createTime, String title, String contents){
        this.createTime = createTime;
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "BoardData{" +
                "date=" + createTime +
                ", title='" + title + '\'' +
                ", content='" + contents + '\'' +
                ", tno='" + tno + '\'' +
                '}';
    }
}
