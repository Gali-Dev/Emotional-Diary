package com.example.emotion_dairy.Retrofit.DTO;

public class ReqJoinGroup {
    String tname;
    String tcode;

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public String getTname() {
        return tname;
    }

    public String getTcode() {
        return tcode;
    }

    @Override
    public String toString() {
        return "ReqJoinGroup{" +
                "tname='" + tname + '\'' +
                ", tcode='" + tcode + '\'' +
                '}';
    }

    public ReqJoinGroup() {
    }

    public ReqJoinGroup(String tname, String tcode) {
        this.tname = tname;
        this.tcode = tcode;
    }
}
