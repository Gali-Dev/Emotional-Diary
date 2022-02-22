package com.example.emotion_dairy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.extensions.CalendarViewPager;
import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardWrite extends AppCompatActivity {

    ArrayList<GroupList> list = new ArrayList<GroupList>();

    EditText edWriteTitle,edWriteContent;
    TextView tvSelectedDate;
    RadioGroup rgSelectGroup;
    RadioButton rbViewPrivate;
    Button btnBoardWrite,btnDateSelect;
    MaterialCalendarView mcv;
    BoardData boardData = new BoardData();
    ApiInterface api;
    Gson gson;
    Map<Integer,String> groupMap = new HashMap<Integer,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        edWriteTitle=findViewById(R.id.edWriteTitle);
        edWriteContent=findViewById(R.id.edWriteContent);
        rgSelectGroup=findViewById(R.id.rgSelectGroup);
        rbViewPrivate=findViewById(R.id.rbViewPrivate);
        btnBoardWrite=findViewById(R.id.btnBoardWrite);
        btnDateSelect=findViewById(R.id.btnDateSelect);
        tvSelectedDate=findViewById(R.id.tvSelectedDate);
        mcv=findViewById(R.id.cvSelect);
        api= HttpClient.getRetrofit().create(ApiInterface.class);
        String jsonGroupList = PreferenceManager.getString(this,"Group");
        Log.d("log",jsonGroupList);
//        Type listType = new TypeToken<ArrayList<GroupList>>() {}.getType();
//        ArrayList<GroupList> gList = gson.fromJson(jsonGroupList,listType);
        toJson(jsonGroupList);
        for(GroupList data : list){
            Log.d("log","groupList : "+data);
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(data.getTname());
            radioButton.setId(data.getTno());
            rgSelectGroup.addView(radioButton);
        }
        rgSelectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int tno=-1;
                if(i==R.id.rbViewPrivate){
                    tno=0;
                }
                else {
                    tno = radioGroup.getCheckedRadioButtonId();
                }
                boardData.setTno(tno);
                Log.d("log","radio id : " +tno);
            }
        });


//
//        for(int i=0;i<gList.size();i++){
//            RadioButton radioButton = new RadioButton(this);
//            radioButton.setText(gList.get(i).getTname());
//            radioButton.setId(gList.get(i).getTno());
//            rgSelectGroup.addView(radioButton);
////        }

        btnBoardWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardData.setTitle(edWriteTitle.getText().toString());
                boardData.setContents(edWriteContent.getText().toString());
                Log.d("log",boardData.toString());
                boardWrite(boardData);
            }
        });
        btnDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mcv.getVisibility()==View.VISIBLE) {

                }else {
                    mcv.setVisibility(View.VISIBLE);
                }
            }
        });
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String selectedDate = simpleDateFormat.format(date.getDate());
                tvSelectedDate.setText(selectedDate);
                Log.d("log","selectedDate : "+selectedDate);
                boardData.setCreateTime(selectedDate);
                mcv.setVisibility(View.GONE);
            }
        });


    }

    public void toJson(String strJson){

        if(strJson!=null){
            try {
                JSONArray a = new JSONArray(strJson);
                for(int i=0; i<a.length();i++){
                    GroupList g = new GroupList();
                    JSONObject jsonObject=a.getJSONObject(i);
                    g.setTno(jsonObject.getInt("tno"));
                    g.setTname(jsonObject.getString("tname"));
                    list.add(g);

                    groupMap.put(jsonObject.getInt("tno"),jsonObject.getString("tname"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void boardWrite(BoardData boardData){
        String auth = PreferenceManager.getString(this,"Auth");
        Log.d("log",auth);
        Call<String> call = api.boardWrite(auth,boardData);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("ok")){
                    Log.d("log","통신완료 -> 글등록 성공");
                    Intent intent = new Intent(BoardWrite.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.d("log","통신완료 -> 글등록 실패");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("log","에러메세지 : "+t.getMessage());
                Log.d("log","onFailure");
            }
        });
    }

}