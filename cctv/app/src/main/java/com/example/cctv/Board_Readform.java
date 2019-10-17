package com.example.cctv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Map;

public class Board_Readform extends Activity {

    ImageView Logo;
    ImageButton GoodButton;

    TextView commentText;
    Button commentButton;

    ListView listView;
    ArrayList<CommentList> data = null;
    CommentAdapter adapter;

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
        TextView writer = (TextView)findViewById(R.id.boardWriterText);
        listView = (ListView)findViewById(R.id.CommentList);

        commentText = (TextView)findViewById(R.id.comment_write);   //댓글 입력창
        commentButton = (Button)findViewById(R.id.comment_submit);  //댓글 등록 버튼

        title.setText(intent.getStringExtra("title"));
        Ds.setText(intent.getStringExtra("Ds"));
        writer.setText(intent.getStringExtra("writer"));

        data = new ArrayList<>();
        CommentList commentList1 = new CommentList("장주리","네이버 연결좀 해주셈..");
        CommentList commentList2 = new CommentList("백화랑","쪼릇쪼릇?");
        CommentList commentList3 = new CommentList("배콰랑","에베엥ㄴ네ㅔ네네배ㅏ베ㅔ베네ㅔ에ㅔ네ㅔ엔에ㅔㅔ에베ㅔ베베에ㅔ에에베ㅔ에베ㅔ에베ㅔ엥베베베베베ㅔ엥에ㅔㅇ엡에베ㅔㅇ베ㅔ");
        CommentList commentList4 = new CommentList("강아지","멍멍멍ㅁ엄멍멍멍ㅁ엄엄어엉ㅇ멍머엄어멈어멍멈어ㅓㅓㅇㅁㅇㅇㅁ엄어엉ㅇ멍멍멍ㅁ엄엄어엉ㅇ멍머엄어멈어멍멈어ㅓㅓㅇㅁㅇㅇㅁ멍머엄어멈어멍멈어ㅓㅓㅇㅁㅇㅇㅁ");
        data.add(commentList1);
        data.add(commentList2);
        data.add(commentList3);
        data.add(commentList4);
        adapter = new CommentAdapter(getApplicationContext(),R.layout.comment_listview_layout,data);
        listView.setAdapter(adapter);


        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference mContent = mDatabase.getReference("id_list");

                if(commentText.getText()==null || commentText.getText().length()==0){
                    Toast.makeText(Board_Readform.this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final List<String> s_list = new ArrayList<>();
                    mContent.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            int index = 1;
                            int tear = 0;
                            for (DataSnapshot filesnapshot : dataSnapshot.getChildren()) {
                                Log.e("snapshot",""+filesnapshot.getChildrenCount());

                                s_list.add(String.valueOf(filesnapshot.getChildrenCount()));
                            }
                            s_list.clear();

                        /*    long id = maxid+1;
                            String name = "장주리";
                            FirebasePost post = new FirebasePost(id,name,commentText.getText().toString());
                            postValues = post.toMapComment();

                            childUpdate.put("/id_list/"+id+"/comment",postValues);

                            mContent.updateChildren(childUpdate);

                            commentText.setText("");*/

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
                    commentText.setText("");
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
    }
}
