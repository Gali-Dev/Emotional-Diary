package com.example.emotion_dairy.Retrofit.DTO;

public class ReqJoinDTO {
    String id;
    String pw;
    String name;

    public ReqJoinDTO(String id, String pw, String name){
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReqJoinDTO{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
