package com.example.emotion_dairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.ReqJoinGroup;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class groupJoin extends AppCompatActivity {

    ApiInterface api;
    Button btnCreateGroup, btnJoinGroup;
    EditText edJoinName,edJoinCode,edCreateName,edCreateCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_join);

        api= HttpClient.getRetrofit().create(ApiInterface.class);

        edJoinName=findViewById(R.id.edJoinGroupName);
        edJoinCode=findViewById(R.id.edJoinGroupCode);
        edCreateName=findViewById(R.id.edAddGroupName);
        edCreateCode=findViewById(R.id.edAddGroupCode);

        btnJoinGroup=findViewById(R.id.btnGroupJoin);
        btnCreateGroup=findViewById(R.id.btnCreateGroup);

        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqJoinGroup reqJoinGroup = new ReqJoinGroup(edJoinName.getText().toString(),edJoinCode.getText().toString());
                joinGroup(reqJoinGroup);
            }
        });
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqJoinGroup reqJoinGroup = new ReqJoinGroup(edCreateName.getText().toString(),edCreateCode.getText().toString());
                createGroup(reqJoinGroup);
            }
        });

    }
    public void joinGroup(ReqJoinGroup reqJoinGroup){
        String auth = PreferenceManager.getString(groupJoin.this,"Auth");
        Call<String> call = api.joinGroup(auth,reqJoinGroup);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("log","그룹 가입 retrofit 성공");
                if(response.body().equals("ok")) {
                    Intent intent = new Intent(groupJoin.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.d("log","그룹 가입 비밀번호 틀림 ");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("log","그룹 가입 실패 : " + t.getMessage());
            }
        });
    }

    public void createGroup(ReqJoinGroup reqJoinGroup){
        String auth = PreferenceManager.getString(groupJoin.this,"Auth");
        Call<String> call = api.createGroup(auth,reqJoinGroup);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("log","그룹 생성 retrofit 성공");
                if(response.body().equals("ok")) {
                    Intent intent = new Intent(groupJoin.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d("log","그룹 생성 리턴 실패");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("log","reqJoinGroup : " +reqJoinGroup.toString());
                Log.d("log","그룹 생성 실패 : " + t.getMessage());
            }
        });
    }
}