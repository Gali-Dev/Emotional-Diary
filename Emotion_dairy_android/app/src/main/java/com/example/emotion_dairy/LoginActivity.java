package com.example.emotion_dairy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.ReqLoginDTO;
import com.example.emotion_dairy.Retrofit.DTO.ResLoginDTO;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    ApiInterface api;
    EditText edId,edPw;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edId=findViewById(R.id.edId);
        edPw=findViewById(R.id.edPw);
        iv=findViewById(R.id.ivWordCloud);
        String imageUrl = "http://10.100.102.90:7000/static/wordcloud0.png";
        Glide.with(this).load(imageUrl).into(iv);
        api = HttpClient.getRetrofit().create(ApiInterface.class);

        File cache = this.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                //다운로드 파일은 지우지 않도록 설정
                //if(s.equals("lib") || s.equals("files")) continue;
                deleteDir(new File(appDir, s));
                Log.d("test", "File /data/data/"+this.getPackageName()+"/" + s + " DELETED");
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    //로그인 버튼 이벤트
    public void btn_login(View view){
        ReqLoginDTO reqLoginDTO = new ReqLoginDTO(edId.getText().toString(),edPw.getText().toString());
        Log.d("login","sdlkfsjdlkfsdjf");
        Log.d("login",reqLoginDTO.toString() );
        login(reqLoginDTO);

     //   finish(); //뒤로가기 눌러도 메인으로 로그인화면으로 안넘어감
    }

    //로그인 통신
    public void login(ReqLoginDTO reqLoginDTO){
        Call<String> call = api.requestPostLogin(reqLoginDTO);
        call.enqueue(new Callback<String>(){

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("login",response.body().toString());
                String auth = response.headers().get("Authorization");
                Log.d("login",auth);
                if(response.body().toString().equals("ok")){
                    Log.d("login","로그인 성공");

                    //SharedPreferences 로그인 정보 저장
                    PreferenceManager.clear(LoginActivity.this);
                    PreferenceManager.setString(LoginActivity.this,"Auth",auth);
                    //저장데이터 확인
                    String text = PreferenceManager.getString(LoginActivity.this,"auth");
                    Log.d("login","auth = "+auth);

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.d("login","아이디/패스워드 오류");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("logind",t.getMessage());
                Log.d("login","로그인 실패");
            }
        });
    }

    //회원가입 버튼 이벤트
    public void btn_join(View view){
        Intent intent = new Intent(this,JoinActivity.class);
        startActivity(intent);
        //   finish(); //뒤로가기 눌러도 메인으로 로그인화면으로 안넘어감
    }

}

