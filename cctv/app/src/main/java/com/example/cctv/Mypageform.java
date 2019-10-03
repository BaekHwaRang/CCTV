package com.example.cctv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Mypageform extends Fragment implements View.OnClickListener{
    View v;

    LinearLayout LoginLayout;
    LinearLayout LockLayout;
    LinearLayout PatrolLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mypageform, container, false);

        LoginLayout = (LinearLayout)v.findViewById(R.id.mypage_login_layout);
        LoginLayout.setOnClickListener(this);
        LockLayout = (LinearLayout)v.findViewById(R.id.mypage_lockLayout);
        LockLayout.setOnClickListener(this);
        PatrolLayout = (LinearLayout)v.findViewById(R.id.mypage_patrolLayout);
        PatrolLayout.setOnClickListener(this);
//        userInfoLayout = (LinearLayout)v.findViewById(R.id.userInfoLayout);
//        userInfoLayout.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mypage_login_layout:
                ((MainActivity)getActivity()).Login_select();
                break;
            case R.id.mypage_lockLayout:
                Intent intent = new Intent(getActivity(),Mypage_Lockform.class);
                startActivity(intent);
                break;
            case R.id.mypage_patrolLayout:
                Intent ptintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://patrol.police.go.kr/map/map.do"));
                startActivity(ptintent);
                break;
//            case R.id.userInfoLayout:
//            case R.id.userInfoButton:
//                ((MainActivity)getActivity()).UserInfo();
//                break;
        }
    }
}
