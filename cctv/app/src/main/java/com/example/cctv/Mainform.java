package com.example.cctv;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.AUDIO_SERVICE;

public class Mainform extends Fragment implements View.OnClickListener{
    View v;

    LinearLayout boardLayout;
    LinearLayout newsLayout;
    LinearLayout callLayout;
    ImageButton bellButton;
    public MediaPlayer mediaPlayer;

    Button button_One;
    Button button_Two;
    Button button_three;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mainform, container, false);
        button_One = (Button)v.findViewById(R.id.button4);
        button_Two = (Button)v.findViewById(R.id.button3);
        button_three = (Button)v.findViewById(R.id.button);

        boardLayout = (LinearLayout)v.findViewById(R.id.boardButton);
        boardLayout.setOnClickListener(this);
        newsLayout = (LinearLayout)v.findViewById(R.id.newsButton);
        newsLayout.setOnClickListener(this);
        callLayout = (LinearLayout)v.findViewById(R.id.main2_112call);
        callLayout.setOnClickListener(this);

        ((MainActivity)MainActivity.mContext).checkPermission();

        bellButton = (ImageButton)v.findViewById(R.id.bellButton);
        bellButton.setOnClickListener(this);

        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mContent = mDatabase.getReference();

        Log.e("id_list",""+mContent.child("1").child("post").child("p_good"));
        mContent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int p_count = (int) dataSnapshot.child("id_list").getChildrenCount();
                int number=1;
                int best=0;
                Log.e("pcount", String.valueOf(p_count));
                for (int i=1; i<= p_count;i++)
                {
                    Log.e("while",""+dataSnapshot.child("id_list").child(""+number).child("post").getValue());
                    Log.e("index2",""+dataSnapshot.child("id_list").child(""+2).child("post").getValue());
                   /* while(dataSnapshot.child(""+number).child("post").getValue()==null){
                        number++;
                        Log.e("number", String.valueOf(number));
                    }*/
                    Log.e("best",""+dataSnapshot.child("id_list").child(""+number).child("post").child("p_good").getValue());
                    //best = Integer.parseInt(String.valueOf(dataSnapshot.child(""+number).child("post").child("p_good").getValue()));
                   // Log.e("best", ""+String.valueOf(best));
                    number++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boardButton:
                Intent intent_board = new Intent(getActivity(),Boardform.class);
                startActivity(intent_board);
                break;
            case R.id.newsButton:
                Intent intent_news = new Intent(getActivity(),Newsform.class);
                startActivity(intent_news);
                break;
            case R.id.bellButton:
                if(mediaPlayer != null) noiseOn();
                else
                {
                    mediaPlayer = MediaPlayer.create(getActivity(),R.raw.noise);
                    mediaPlayer.setLooping(true);
                    noiseOn();
                }
                break;

            case R.id.main2_112call:
                Context c = v.getContext();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:112"));

                try {
                    c.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public void noiseOn(){
        if(!mediaPlayer.isPlaying()) mediaPlayer.start();
        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // MediaPlayer 해지
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}