package com.example.emotion_dairy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.ReqJoinDTO;
import com.example.emotion_dairy.Retrofit.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    ApiInterface api;
    EditText edId,edPw,edName;
    Button btnJoin,btnJoinCancle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        edId=findViewById(R.id.edJoinId);
        edPw=findViewById(R.id.edJoinPw);
        edName=findViewById(R.id.edJoinName);
        btnJoin=findViewById(R.id.btnJoin);


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqJoinDTO reqJoinDTO = new ReqJoinDTO(edId.getText().toString(),edPw.getText().toString(),edName.getText().toString());
                join(reqJoinDTO);
            }
        });
        api = HttpClient.getRetrofit().create(ApiInterface.class);
    }

    public void join(ReqJoinDTO reqJoinDTO){
        Call<String> call = api.requestPostJoin(reqJoinDTO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("join","join 통신 성공");
                if(response.body().equals("ok")){
                    Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d("join","join 통신 성공 -> 오류");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("join","join 오류");

            }
        });
    }
}