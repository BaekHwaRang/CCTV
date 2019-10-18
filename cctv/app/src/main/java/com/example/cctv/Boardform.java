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
import android.widget.Toast;

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
    BoardAdapter adapter;

    int number=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boardform);

        listView = (ListView) findViewById(R.id.post_lv);
        boardWriteButton = (ImageButton)findViewById(R.id.board_writeButton);
        boardWriteButton.setOnClickListener(this);

        data = new ArrayList<>();
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mContent = mDatabase.getReference();
        mContent.keepSynced(true);

        mContent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("ondatachange",""+dataSnapshot.child("id_list").getChildrenCount());
                for(int i=1 ; i<= dataSnapshot.child("id_list").getChildrenCount(); i++){
                    Log.e("number",""+number);
                    Log.e("null",""+dataSnapshot.child("id_list").child(""+number).child("post").child("p_id").getValue());
                    while(dataSnapshot.child("id_list").child(""+number).child("post").child("p_id").getValue()==null)
                    {
                        number++;
                        Log.e("null_Num", String.valueOf(number));
                    }
                    String pid = dataSnapshot.child("id_list").child(""+number).child("post").child("p_id").getValue().toString();
                    String ptitle = dataSnapshot.child("id_list").child(""+number).child("post").child("p_title").getValue().toString();
                    String ptext = dataSnapshot.child("id_list").child(""+number).child("post").child("p_text").getValue().toString();
                    int count = Integer.parseInt(dataSnapshot.child("id_list").child(""+number).child("post").child("p_good").getValue().toString());
                    String writer= "익명이";

                    BoardList data1 = new BoardList(count,pid,ptitle,ptext,writer);
                    data.add(data1);
                    Log.e("pid",""+pid);
                    adapter  = new BoardAdapter(getApplicationContext(),R.layout.board_listview_layout,data);
                    listView.setAdapter(adapter);
                    number++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        /* 리스트 아이템 연결 */

//        adapter  = new BoardAdapter(this,R.layout.board_listview_layout,data);
//        listView.setAdapter(adapter);

        /* 리스트뷰 아이템 클릭 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Board_Readform.class);
                intent.putExtra("title",data.get(position).getTitle());
                intent.putExtra("Ds",data.get(position).getDescription());
                intent.putExtra("writer",data.get(position).getWriter());
                intent.putExtra("index",data.get(position).getId());
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
                finish();
                break;
        }
    }
}