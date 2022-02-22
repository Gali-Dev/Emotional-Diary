package com.example.emotion_dairy.Retrofit.DTO;

public class MemberDTO {
    private String pw;
    private String name;
    private String id;
    private int mno;

    public String getPw ()
    {
        return pw;
    }

    public void setPw (String pw)
    {
        this.pw = pw;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public int getMno ()
    {
        return mno;
    }

    public void setMno (int mno)
    {
        this.mno = mno;
    }

    public MemberDTO() {
    }

    public MemberDTO(String pw, String name, String id, int mno) {
        this.pw = pw;
        this.name = name;
        this.id = id;
        this.mno = mno;
    }

    @Override
    public String toString()
    {
        return "MemberDTO [pw = "+pw+", name = "+name+", id = "+id+", mno = "+mno+"]";
    }
}
