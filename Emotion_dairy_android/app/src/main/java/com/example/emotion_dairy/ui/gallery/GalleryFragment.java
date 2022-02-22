package com.example.emotion_dairy.ui.gallery;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.emotion_dairy.CustomDialog;
import com.example.emotion_dairy.GroupList;
import com.example.emotion_dairy.ListViewAdapter;
import com.example.emotion_dairy.ListViewItem;
import com.example.emotion_dairy.MainActivity;
import com.example.emotion_dairy.R;
import com.example.emotion_dairy.Retrofit.ApiInterface;
import com.example.emotion_dairy.Retrofit.DTO.SoloBoardDTO;
import com.example.emotion_dairy.Retrofit.HttpClient;
import com.example.emotion_dairy.SharedPreferences.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    ArrayList<SoloBoardDTO> soloBoardDTOList = new ArrayList<SoloBoardDTO>();
    private ListView listview;
    private ListViewAdapter adapter;
    List<GroupList> list = new ArrayList<GroupList>();

    Map<Integer,String> groupMap = new HashMap<Integer,String>();

    ApiInterface api;

    private GalleryViewModel galleryViewModel;
    MaterialCalendarView mvc;
    TextView tx,tvGroupName;
    Spinner spinnerGroup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        api= HttpClient.getRetrofit().create(ApiInterface.class);
        // Adapter 생성


        mvc = root.findViewById(R.id.calendarView3);
        mvc.state().edit().isCacheCalendarPositionEnabled(false)
                .setFirstDayOfWeek(1)
                .setMinimumDate(CalendarDay.from(2018, 1, 1))
                .setMaximumDate(CalendarDay.from(2020, 12, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        //클릭이벤트
mvc.setOnDateChangedListener(new OnDateSelectedListener() {
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        adapter = new ListViewAdapter();

        // 리스트뷰 객체 생성 및 Adapter 설정
        listview = (ListView) root.findViewById(R.id.listView);
        listview.setAdapter(adapter);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = simpleDateFormat.format(date.getCalendar().getTime());
        Log.d("log",strDate);



        for (int i = 0; i < soloBoardDTOList.size(); i++) {
            if (strDate.equals(soloBoardDTOList.get(i).getCreateTime())) {
                Log.d("log","if문 들어옴");
                Log.d("log",soloBoardDTOList.get(i).toString());
                Log.d("log",soloBoardDTOList.get(i).getTitle());
                Log.d("log",soloBoardDTOList.get(i).getMember().getName());
                Log.d("log",soloBoardDTOList.get(i).getCreateTime());

                adapter.addItem(soloBoardDTOList.get(i).getTitle(),soloBoardDTOList.get(i).getMember().getName(),soloBoardDTOList.get(i).getEmotion(),soloBoardDTOList.get(i).getContents());
            }
        }

    }
});


        //그룹작업
        spinnerGroup=root.findViewById(R.id.spinnerGroup);
        tvGroupName=root.findViewById(R.id.tvGroupName);
        //내 그룹 가져옴
        String jsonGroupList = PreferenceManager.getString(getContext(),"Group");
        Log.d("log",jsonGroupList);
        toJson(jsonGroupList);

        List<String> tnameList = new ArrayList<String>();
        for(GroupList a : list){
            tnameList.add(a.getTname());
            Log.d("log","group data : "+a.toString());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,tnameList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGroup.setAdapter(spinnerAdapter);
        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tvGroupName.setText(list.get(i).getTname());
                Log.d("log","그룹 선택 : " + list.get(i).getTno()+" 선택됨");
                //그룹 선택시 그룹 리스트 뽑아옴(SharedPreference or retrofit2)
                String auth = PreferenceManager.getString(getContext(),"Auth");
                Call<ArrayList<SoloBoardDTO>> call = api.getGroupBoard(auth,list.get(i).getTno());

                call.enqueue(new Callback<ArrayList<SoloBoardDTO>>() {
                    @Override
                    public void onResponse(Call<ArrayList<SoloBoardDTO>> call, Response<ArrayList<SoloBoardDTO>> response) {
                        Log.d("log","그룹 게시물 데이터 통신 성공");

                        soloBoardDTOList=response.body();
                        Log.d("log",soloBoardDTOList.toString());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<SoloBoardDTO>> call, Throwable t) {
                        Log.d("log","그룹 게시물 데이터 통신 실패");
                    }
                });

            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return root;
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



}