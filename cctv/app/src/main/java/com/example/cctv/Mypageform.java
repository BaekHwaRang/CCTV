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
    LinearLayout loginLayout;
    LinearLayout logoutLayout;
    LinearLayout addressLayout;
    LinearLayout lockLayout;
    LinearLayout patrolLayout;
    LinearLayout userInfoLayout;
    Button LoginButton;
    Button NtestButton;
    Button userInfoButton;
    ImageButton addAddressButton;
    ImageButton addLockButton;
    ImageButton addPoliceButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mypageform, container, false);

        NtestButton = (Button)v.findViewById(R.id.NtestButton);
        NtestButton.setOnClickListener(this);

        loginLayout = (LinearLayout)v.findViewById(R.id.profile_loginLayout);
        logoutLayout = (LinearLayout)v.findViewById(R.id.profile_logoutLayout);

        LoginButton = (Button)v.findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(this);

        userInfoButton = (Button)v.findViewById(R.id.userInfoButton);
        userInfoButton.setOnClickListener(this);

        addAddressButton = (ImageButton)v.findViewById(R.id.voteButton);
        addAddressButton.setOnClickListener(this);
        addLockButton = (ImageButton)v.findViewById(R.id.lockAddButton);
        addLockButton.setOnClickListener(this);
        addPoliceButton = (ImageButton)v.findViewById(R.id.policeAddButton);
        addPoliceButton.setOnClickListener(this);
        addressLayout = (LinearLayout)v.findViewById(R.id.voteLayout);
        addressLayout.setOnClickListener(this);
        lockLayout = (LinearLayout)v.findViewById(R.id.lockLayout);
        lockLayout.setOnClickListener(this);
        patrolLayout = (LinearLayout)v.findViewById(R.id.patrolLayout);
        patrolLayout.setOnClickListener(this);
        userInfoLayout = (LinearLayout)v.findViewById(R.id.userInfoLayout);
        userInfoLayout.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.NtestButton:
                loginLayout.setVisibility(View.GONE);
                logoutLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.LoginButton:
                ((MainActivity)getActivity()).Login_select();
                break;
            case R.id.voteLayout:
            case R.id.voteButton:
                ((MainActivity)getActivity()).Vote(true);
                break;
            case R.id.lockLayout:
            case R.id.lockAddButton:
                Intent intent = new Intent(getActivity(),Mypage_Lockform.class);
                startActivity(intent);
                break;
            case R.id.patrolLayout:
            case R.id.policeAddButton:
                Intent ptintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://patrol.police.go.kr/map/map.do"));
                startActivity(ptintent);
                break;
            case R.id.userInfoLayout:
            case R.id.userInfoButton:
                ((MainActivity)getActivity()).UserInfo();
                break;
        }
    }
    public void LogoutClick(){
        logoutLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
    }
}
