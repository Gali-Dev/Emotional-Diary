package com.example.emotion_dairy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.bumptech.glide.Glide;
import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.ResGetGroup;
import com.example.emotion_dairy.Retrofit.DTO.ResMyInfo;
import com.example.emotion_dairy.Retrofit.DTO.SoloBoardDTO;
import com.example.emotion_dairy.Retrofit.DTO.Together;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    TextView tvId;
    ImageView ivHeaderWC;
    Button btnHboardWrite,btnGroup,btnLogout;

    ApiInterface api;

    private AppBarConfiguration mAppBarConfiguration;
    List<ResGetGroup> list = new ArrayList<ResGetGroup>();
    List<GroupList> groupLists = new ArrayList<GroupList>();
    List<Integer> gList=new ArrayList<Integer>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        api= HttpClient.getRetrofit().create(ApiInterface.class);

        //헤더 뷰 찾기
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        tvId=(TextView) headerView.findViewById(R.id.navHeaderId);
        ivHeaderWC=(ImageView) headerView.findViewById(R.id.navHeaderImageView);
        btnHboardWrite=headerView.findViewById(R.id.btnHboardWrite);
        btnGroup=headerView.findViewById(R.id.btnGroup);
        btnLogout=headerView.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,groupJoin.class);
                startActivity(intent);
            }
        });

        String imageUrl = "http://10.100.102.90:7000/static/my/wordcloud2.png";
        Glide.with(this).load(imageUrl).into(ivHeaderWC);

        btnHboardWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BoardWrite.class);
                startActivity(intent);
            }
        });


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //그룹 가져옴;
        getData();
        //회원 정보 가져옴;
        getMyInfo();
        //Fragment Manager

    }

    public void logout(){
        String auth = PreferenceManager.getString(this,"Auth");
        Call<String> call = api.logout(auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                PreferenceManager.clear(MainActivity.this);
                Log.d("log","logout return message : " + response.body());
                if(response.body().equals("logout")){
                    Log.d("log","로그아웃 성공");
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("log","로그아웃 실패 :" +t.getMessage());
            }
        });
    }

    public void getMyInfo(){
        String auth = PreferenceManager.getString(this,"Auth");
        Call<ResMyInfo> call = api.getMyInfo(auth);
        call.enqueue(new Callback<ResMyInfo>() {
            @Override
            public void onResponse(Call<ResMyInfo> call, Response<ResMyInfo> response) {
                ResMyInfo resMyInfo = response.body();
                Log.d("log","나의 정보 : "+resMyInfo.getMno());
                PreferenceManager.setInt(MainActivity.this,"myNo",resMyInfo.getMno());
                tvId.setText(resMyInfo.getName());
            }

            @Override
            public void onFailure(Call<ResMyInfo> call, Throwable t) {
                Log.d("log","getMyInfo() 에러 : "+t.getMessage());
            }
        });
    }




    public void getData(){

        String auth = PreferenceManager.getString(this,"Auth");
        Call<List<ResGetGroup>> call=api.getGroup(auth);
        call.enqueue(new Callback<List<ResGetGroup>>() {
            @Override
            public void onResponse(Call<List<ResGetGroup>> call, Response<List<ResGetGroup>> response) {
                list=response.body();
                for(int i=0;i<list.size();i++){
                    GroupList groupList = new GroupList(list.get(i).getTogether().getTno(),list.get(i).getTogether().getTname());
                    groupLists.add(groupList);
                }
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<ArrayList<GroupList>>(){}.getType();
                String jsonGroup = gson.toJson(groupLists,listType);
                PreferenceManager.removeKey(MainActivity.this,"Group");
                PreferenceManager.setString(MainActivity.this,"Group",jsonGroup);

                //SharedPreference Group 데이터 테스트
                String strGroup1 = PreferenceManager.getString(MainActivity.this,"Group");
                Log.d("log","마지막테스트 그룹 제이슨 : "+strGroup1);
            }


            @Override
            public void onFailure(Call<List<ResGetGroup>> call, Throwable t) {
                Log.d("log","통신실패: "+t.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}