package com.example.emotion_dairy;

public class GroupList {
    int tno;
    String tname;

    public void setTno(int tno) {
        this.tno = tno;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getTno() {
        return tno;
    }

    public String getTname() {
        return tname;
    }

    public GroupList() {
    }

    public GroupList(int tno, String tname) {
        this.tno = tno;
        this.tname = tname;
    }

    @Override
    public String toString() {
        return "GroupList{" +
                "tno=" + tno +
                ", tname='" + tname + '\'' +
                '}';
    }
}
