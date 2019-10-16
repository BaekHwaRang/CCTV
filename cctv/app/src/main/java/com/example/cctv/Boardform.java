package com.example.cctv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Boardform extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    ImageButton boardWriteButton;
    ArrayList<BoardList> data = null;
    Map<String , Object> childUpdate = new HashMap<String,Object>();
    Map<String, Object> good_value = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardform);

        listView = (ListView) findViewById(R.id.post_lv);
        boardWriteButton = (ImageButton)findViewById(R.id.board_writeButton);
        boardWriteButton.setOnClickListener(this);
        data = new ArrayList<>();
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mContent = mDatabase.getReference("id_list");
        mContent.keepSynced(true);
        mContent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot filedata : dataSnapshot.getChildren()) {
                    String pid = filedata.child("post").child("p_id").getValue().toString();
                    String ptitle = filedata.child("post").child("p_title").getValue().toString();
                    String ptext = filedata.child("post").child("p_text").getValue().toString();
                    int count = Integer.parseInt(filedata.child("post").child("p_good").getValue().toString());
                    BoardList data1 = new BoardList(count,ptitle,ptext);
                    data.add(data1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d("", "connected");

                } else {
                    Log.d("", "not connected");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("", "Listener was cancelled");
            }
        });

        mContent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
                Log.e("실패","2");
            }
        });

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
