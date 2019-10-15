package com.example.cctv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Boardform extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ImageButton boardWriteButton;
    ArrayList<BoardList> data = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardform);

        listView = (ListView) findViewById(R.id.post_lv);
        boardWriteButton = (ImageButton)findViewById(R.id.board_writeButton);
        boardWriteButton.setOnClickListener(this);




        /* 데이터 넣는 곳 */
        data = new ArrayList<>();

        BoardList data1 = new BoardList(1,"쪼릇쪼릇","쪼릇뚜는 쪼릇쪼릇");
        BoardList data2 = new BoardList(2,"뽀롱뽀롱","뽀로로는 뽀롱뽀롱");
        data.add(data1);
        data.add(data2);

        /* 리스트 아이템 연결 */
        BoardAdapter adapter = new BoardAdapter(this,R.layout.board_listview_layout,data);
        listView.setAdapter(adapter);

        /* 리스트뷰 아이템 클릭 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Board_Readform.class);
                intent.putExtra("title",data.get(position).getTitle());
                intent.putExtra("Ds",data.get(position).getDescription());
                startActivity(intent);
            }
        });




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
