package com.example.emotion_dairy.Retrofit.DTO;

public class ResGetGroup {
    int tmno;
    MemberDTO member;
    Together together;

    public void setTmno(int tmno) {
        this.tmno = tmno;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public void setTogether(Together together) {
        this.together = together;
    }

    public int getTmno() {
        return tmno;
    }

    public MemberDTO getMember() {
        return member;
    }

    public Together getTogether() {
        return together;
    }

    @Override
    public String toString() {
        return "ResGetGroup{" +
                "tmno=" + tmno +
                ", member=" + member +
                ", together=" + together +
                '}';
    }
}
