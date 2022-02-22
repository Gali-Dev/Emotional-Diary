package com.example.emotion_dairy.Retrofit.DTO;

public class ReqLoginDTO {
    String id;
    String pw;
    public ReqLoginDTO(String id, String pw){
        this.id = id;
        this.pw = pw;
    }
    @Override
    public String toString(){
        return "ReqLoginDTO] id : "+id+", pw : "+pw;
    }
}
