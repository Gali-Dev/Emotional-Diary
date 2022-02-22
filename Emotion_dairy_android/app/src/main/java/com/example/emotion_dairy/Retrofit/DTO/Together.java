package com.example.emotion_dairy.Retrofit.DTO;

public class Together {
    int tno;
    String tname;
    String tcode;
    MemberDTO member;

    public void setTno(int tno) {
        this.tno = tno;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public int getTno() {
        return tno;
    }

    public String getTname() {
        return tname;
    }

    public String getTcode() {
        return tcode;
    }

    public MemberDTO getMember() {
        return member;
    }

    @Override
    public String toString() {
        return "Together{" +
                "tno=" + tno +
                ", tname='" + tname + '\'' +
                ", tcode='" + tcode + '\'' +
                ", member=" + member +
                '}';
    }
}
