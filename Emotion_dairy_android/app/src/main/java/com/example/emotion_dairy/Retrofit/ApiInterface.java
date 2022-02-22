package com.example.emotion_dairy.Retrofit;

import com.example.emotion_dairy.BoardData;
import com.example.emotion_dairy.Retrofit.DTO.Authorization;
import com.example.emotion_dairy.Retrofit.DTO.ReqJoinDTO;
import com.example.emotion_dairy.Retrofit.DTO.ReqJoinGroup;
import com.example.emotion_dairy.Retrofit.DTO.ReqLoginDTO;
import com.example.emotion_dairy.Retrofit.DTO.ResGetGroup;
import com.example.emotion_dairy.Retrofit.DTO.ResLoginDTO;
import com.example.emotion_dairy.Retrofit.DTO.ResMyInfo;
import com.example.emotion_dairy.Retrofit.DTO.SoloBoardDTO;
import com.example.emotion_dairy.Retrofit.DTO.Together;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    //로그인
    @POST("loginProc")
    Call<String> requestPostLogin(@Body ReqLoginDTO reqLoginDTO);

    @POST("member/join")
    Call<String> requestPostJoin(@Body ReqJoinDTO reqJoinDTO);

    @GET("board/my")
    Call<ArrayList<SoloBoardDTO>> requestSoloBoard(@Header("Authorization") String auth);

    @GET("board/get/2")
    Call<SoloBoardDTO> requestSoloBoardTest(@Header("Authorization") String auth);

    @POST("board/save")
    Call<String> boardWrite(@Header("Authorization") String auth, @Body BoardData boardData);

    @GET("tmember/get")
    Call<List<ResGetGroup>> getGroup(@Header("Authorization") String auth);

    @GET("together/get/{id}")
    Call<Together> getGroupName(@Header("Authorization") String auth, @Path("id") int id);

    @GET("board/group/{id}")
    Call<ArrayList<SoloBoardDTO>> getGroupBoard(@Header("Authorization") String auth, @Path("id") int id);

    @GET("member/get")
    Call<ResMyInfo> getMyInfo(@Header("Authorization") String auth);

    //내글 분석
    @GET("board/analysis/my")
    Call<String> analysisMy(@Header("Authorization") String auth);

    //그룹 글 분석
    @GET("board/analysis/group/{tno}")
    Call<String> analysisGroup(@Header("Authorization") String auth, @Path("tno") int tno);

    //그룹 가입
    @POST("tmember/save")
    Call<String> joinGroup(@Header("Authorization") String auth, @Body ReqJoinGroup reqJoinGroup);

    //그룹 생성
    @POST("together/save")
    Call<String> createGroup(@Header("Authorization") String auth, @Body ReqJoinGroup reqJoinGroup);

    //로그 아웃
    @GET("member/logout")
    Call<String> logout(@Header("Authorization") String auth);
}
