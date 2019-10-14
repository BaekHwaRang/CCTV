package com.example.cctv;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Board_Writeform extends AppCompatActivity {
    ImageButton Subject;
    TextView Tv_title;
    TextView Tv_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_writeform);

        Subject = (ImageButton) findViewById(R.id.imageButton3);
        Tv_title = (TextView) findViewById(R.id.board_title_text);
        Tv_text = (TextView) findViewById(R.id.board_description_text);

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

        Subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mContent = mDatabase.getReference();
                Map<String , Object> childUpdate = new HashMap<>();
                Map<String , Object> postValues = null;

                if(Tv_title.getText()==null || Tv_title.getText().length()==0){
                    Toast.makeText(Board_Writeform.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if(Tv_text.getText()==null || Tv_text.getText().length()==0){
                    Toast.makeText(Board_Writeform.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebasePost post = new FirebasePost(Tv_title.getText().toString() , Tv_text.getText().toString());
                    mContent.child("post_id").push().setValue(Tv_title.getText().toString());
                    Tv_title.setText("");

                    mContent.child("post_id").push().setValue(Tv_text.getText().toString());
                    Tv_text.setText("");
                }

            }
        });

    }
}
