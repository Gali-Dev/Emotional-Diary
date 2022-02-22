package com.example.emotion_dairy.Retrofit.DTO;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Member;
import java.sql.Timestamp;
import java.util.Date;

public class SoloBoardDTO {
    private String bno;

    private String contents;

    private String createTime;

    private MemberDTO member;

    private String title;

    private int tno;

    private String emotion;

    public int getTno() {
        return tno;
    }

    public void setTno(int tno) {
        this.tno = tno;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }


    public String getBno ()
    {
        return bno;
    }

    public void setBno (String bno)
    {
        this.bno = bno;
    }

    public String getContents ()
    {
        return contents;
    }

    public void setContents (String contents)
    {
        this.contents = contents;
    }

    public String getCreateTime ()
    {
        return createTime;
    }

    public void setCreateTime (String createTime)
    {
        this.createTime = createTime;
    }

    public MemberDTO getMember ()
    {
        return member;
    }

    public void setMember (MemberDTO member)
    {
        this.member = member;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public SoloBoardDTO() {
    }

    public SoloBoardDTO(String bno, String contents, String createTime, MemberDTO member, String title, int tno, String emotion) {
        this.bno = bno;
        this.contents = contents;
        this.createTime = createTime;
        this.member = member;
        this.title = title;
        this.tno = tno;
        this.emotion = emotion;
    }

    @Override
    public String toString() {
        return "SoloBoardDTO{" +
                "bno='" + bno + '\'' +
                ", contents='" + contents + '\'' +
                ", createTime='" + createTime + '\'' +
                ", member=" + member +
                ", title='" + title + '\'' +
                ", tno=" + tno +
                ", emotion='" + emotion + '\'' +
                '}';
    }
}
