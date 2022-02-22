package com.example.emotion_dairy.Retrofit.DTO;

import java.util.List;

public class ResMyInfo {
    int mno;
    String id;
    String pw;
    String name;
    List<SoloBoardDTO> board;

    public void setMno(int mno) {
        this.mno = mno;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(List<SoloBoardDTO> board) {
        this.board = board;
    }

    public int getMno() {
        return mno;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public List<SoloBoardDTO> getBoard() {
        return board;
    }
}
