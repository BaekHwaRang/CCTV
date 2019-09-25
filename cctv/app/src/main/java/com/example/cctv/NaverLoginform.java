package com.example.cctv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NaverLoginform extends Fragment implements View.OnClickListener{
    View v;
    Button naverGo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.naverlogin, container, false);

        naverGo = (Button)v.findViewById(R.id.naverGo);
        naverGo.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.naverGo:
//                ((MainActivity)getActivity()).Mypage();
//                ((MainActivity)getActivity()).NaverLogintrue();
                break;
        }
    }
}
