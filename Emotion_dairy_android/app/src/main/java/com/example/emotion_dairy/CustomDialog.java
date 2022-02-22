package com.example.emotion_dairy;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emotion_dairy.Retrofit.DTO.SoloBoardDTO;

public class CustomDialog {
    private Context context;

    public CustomDialog(Context context){
        this.context=context;
    }

    public void callFunction(final String listTitle, final String listName, final String listContents){
        final Dialog dlg = new Dialog(context);
        //액티비티의 액션바 숨기기
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //다이얼로그의 레이아웃 설정
        dlg.setContentView(R.layout.custom_dialog);

        //각 위젯 정의
        final Button okButton = (Button) dlg.findViewById(R.id.di_button);
        final TextView title = (TextView) dlg.findViewById(R.id.di_title);
        final TextView name = (TextView) dlg.findViewById(R.id.di_name);
        final TextView contents = (TextView) dlg.findViewById(R.id.di_contents);

        Log.d("log","===================");
        Log.d("log",listTitle);
        Log.d("log",listName);
        Log.d("log",listContents);



        title.setText(listTitle);
        name.setText(listName);
        contents.setText(listContents);



        //다이얼로그 노출
        dlg.show();



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

    }
}
