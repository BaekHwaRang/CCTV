package com.example.cctv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Boardform extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ImageButton boardWriteButton;
    ArrayList<BoardList> Board_list = new ArrayList<BoardList>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardform);

        boardWriteButton = (ImageButton)findViewById(R.id.board_writeButton);
        boardWriteButton.setOnClickListener(this);

        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
            }
        }else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.BLACK);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.board_writeButton:
                Intent intent = new Intent(this,Board_Writeform.class);
                startActivity(intent);
                break;
        }
    }
}
