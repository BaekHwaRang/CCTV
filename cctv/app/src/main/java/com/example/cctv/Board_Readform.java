package com.example.cctv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Board_Readform extends Activity {

    ImageView Logo;
    ImageButton GoodButton;

    TextView commentText;
    Button commentButton;

    Map<String , Object> childUpdate = new HashMap<>();
    Map<String , Object> postValues = null;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_readform);

        Intent intent = getIntent();

        TextView title = (TextView)findViewById(R.id.boardTitleText);
        TextView Ds = (TextView)findViewById(R.id.boardDsText);

        commentText = (TextView)findViewById(R.id.comment_write);   //댓글 입력창
        commentButton = (Button)findViewById(R.id.comment_submit);  //댓글 등록 버튼

        title.setText(intent.getStringExtra("title"));
        Ds.setText(intent.getStringExtra("Ds"));

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference mContent = mDatabase.getReference();

                if(commentText.getText()==null || commentText.getText().length()==0){
                    Toast.makeText(Board_Readform.this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    mContent.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            if(dataSnapshot.exists()){
                                maxid=(dataSnapshot.getChildrenCount());
                            }
                            long id = maxid+1;
                            FirebaseComment post = new FirebaseComment(id,commentText.getText().toString());
                            postValues = post.toMapC();

                            childUpdate.put("/id_list/"+id+"/comment",postValues);

                            mContent.updateChildren(childUpdate);

                            commentText.setText("");

                            //새로고침하는 코드 적어야됨
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


        /* 이미지 비트맵 변환*/
        Logo = (ImageView)findViewById(R.id.postLogo);
        GoodButton = (ImageButton)findViewById(R.id.GoodButton);

        Bitmap LogoBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.logo);
        Logo.setImageBitmap(LogoBitmap);
        Bitmap GoodBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.board_best);
        GoodButton.setImageBitmap(GoodBitmap);

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
}
