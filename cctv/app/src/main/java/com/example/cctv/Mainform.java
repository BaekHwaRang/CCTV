package com.example.cctv;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Mainform extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    View v;

    LinearLayout boardLayout;
    LinearLayout newsLayout;
    LinearLayout callLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mainform, container, false);

        boardLayout = (LinearLayout)v.findViewById(R.id.boardButton);
        boardLayout.setOnClickListener(this);
        newsLayout = (LinearLayout)v.findViewById(R.id.newsButton);
        newsLayout.setOnClickListener(this);
        callLayout = (LinearLayout)v.findViewById(R.id.main2_112call);
        callLayout.setOnLongClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boardButton:
                Toast.makeText(getActivity(), "기능 추가중입니다ㅠ", Toast.LENGTH_LONG).show();
                break;
            case R.id.newsButton:
                Intent intent = new Intent(getActivity(),Newsform.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
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
        return false;
    }
}
