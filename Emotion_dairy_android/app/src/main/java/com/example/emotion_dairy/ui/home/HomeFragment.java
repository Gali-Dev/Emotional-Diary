package com.example.emotion_dairy.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.DrawableUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.emotion_dairy.BoardWrite;
import com.example.emotion_dairy.MainActivity;
import com.example.emotion_dairy.R;
import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.ReqLoginDTO;
import com.example.emotion_dairy.Retrofit.DTO.SoloBoardDTO;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_NO_LOCALIZED_COLLATORS;

public class HomeFragment extends Fragment {
    MaterialCalendarView mvc;

    MainActivity activity;

    private Retrofit mRetrofit;
    private ApiInterface api;
    private HomeViewModel HomeViewModel;
    private ArrayList<SoloBoardDTO> result;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        api = HttpClient.getRetrofit().create(ApiInterface.class);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        HomeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        TextView contentView = (TextView) root.findViewById(R.id.tv_contentview);
        Button updateBtn = (Button) root.findViewById(R.id.btn_update);
        Button deleteBtn = (Button) root.findViewById(R.id.btn_delete);
        TextView titleView = (TextView) root.findViewById(R.id.tv_main_titleview);
        Button btnWrite;
        btnWrite = (Button) root.findViewById(R.id.btnWrite);

            contentView.setLines(5);
        //추가
        List<EventDay> events = new ArrayList<>();


        CalendarView calendarView = (CalendarView) root.findViewById(R.id.calendarView);
        getBoard(calendarView);
        calendarView.setEvents(events);


        //추가 끝

        TextView tv_date;
        tv_date = (TextView) root.findViewById(R.id.tv_date);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                Calendar selectedDate = calendarView.getFirstSelectedDate();
                Log.d("log", "날짜 찍음");
                Log.d("log", clickedDayCalendar.getTime().toString());


                //선택날짜 출력하기
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = simpleDateFormat.format(clickedDayCalendar.getTime());
                tv_date.setText(strDate);

                //출력된 date값이랑 ArrayList<SoloBoardDTO>에서 createTime이랑 비교해서
                //값이 같으면 참조값 찾아서 findbyid해서 set해줌.
                int index = -1;
                for (int i = 0; i < result.size(); i++) {
                    if (strDate.equals(result.get(i).getCreateTime())) {
                        index = i;
                        break;
                    }
                }
                if(index!=-1) {
                    btnWrite.setVisibility(View.INVISIBLE);
                    updateBtn.setVisibility(View.VISIBLE);
                    deleteBtn.setVisibility(View.VISIBLE);
                    titleView.setVisibility(View.VISIBLE);
                    contentView.setVisibility(View.VISIBLE);
                    contentView.setText(result.get(index).getContents());
                    Log.d("log",result.get(index).getTitle());
                    titleView.setText((result.get(index).getTitle()));
                    titleView.requestFocus();
                }else{
                    //글쓰기 페이지 이동
                    Intent intent = new Intent(getContext(),BoardWrite.class);

                    btnWrite.setVisibility(View.VISIBLE);
                    updateBtn.setVisibility(View.INVISIBLE);
                    titleView.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);
                    contentView.setVisibility(View.INVISIBLE);
                }
            }
        });

        //글쓰기
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BoardWrite.class);
                startActivity(intent);
            }
        });


        return root;

    }


    public void getBoard(CalendarView cv) {
        String auth = PreferenceManager.getString(getContext(),"Auth");
        Log.d("log","토큰 찍힘");
        Log.d("log",auth);
        Call<ArrayList<SoloBoardDTO>> call = api.requestSoloBoard(auth);
        call.enqueue(new Callback<ArrayList<SoloBoardDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<SoloBoardDTO>> call, Response<ArrayList<SoloBoardDTO>> response) {
                result = response.body();
                List<EventDay> events = new ArrayList<>();
                Log.d("log", "데이터 통신 성공");
                Log.d("log", result.toString());

                for(int i=0;i<result.size();i++){
                    Log.d("log","result"+i+" : "+result.get(i).getCreateTime());

                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
                    try {
                        Date date = dateFormat.parse(result.get(i).getCreateTime());
                        cal.setTime(date);
                        Log.d("log",cal.toString());
                        //분석 결과별로 이모티콘 뿌리기
                        switch (result.get(i).getEmotion()) {
                            case "Anger":
                                events.add(new EventDay(cal, R.drawable.anger));
                                cv.setEvents(events);
                                break;
                            case "Anticipation":
                                events.add(new EventDay(cal, R.drawable.anticipation));
                                cv.setEvents(events);
                                break;
                            case "Disgust":
                                events.add(new EventDay(cal, R.drawable.disgust));
                                cv.setEvents(events);
                                break;
                            case "Fear":
                                events.add(new EventDay(cal, R.drawable.fear));
                                cv.setEvents(events);
                                break;
                            case "Joy":
                                events.add(new EventDay(cal, R.drawable.joy));
                                cv.setEvents(events);
                                break;
                            case "Sadness":
                                events.add(new EventDay(cal, R.drawable.sadness));
                                cv.setEvents(events);
                                break;
                            case "Surprise":
                                events.add(new EventDay(cal, R.drawable.surprise));
                                cv.setEvents(events);
                                break;
                            case "Trust":
                                events.add(new EventDay(cal, R.drawable.trust));
                                cv.setEvents(events);
                                break;
                        }


                    } catch (ParseException e) {
                        Log.d("log",e.getMessage());
                        e.printStackTrace();
                    }




//                    SoloBoardDTO a1 = new SoloBoardDTO(getString())
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SoloBoardDTO>> call, Throwable t) {
                Log.d("log","에러:"+t.getMessage());
                Log.d("log", "통신 실패");
            }
        });
    }



}

