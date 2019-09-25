package com.example.cctv;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

public class Loginform extends Fragment implements View.OnClickListener{
    View v;

    Button naver;
    Button kakao;

    private static String OAUTH_CLIENT_ID = "fJECNAV766XxJKZ4AQU8";
    private static String OAUTH_CLIENT_SECRET = "aKa_jmn84Q";
    private static String OAUTH_CLIENT_NAME = "감시자들";

    public static OAuthLoginButton mOAuthLoginButton;
    public static OAuthLogin mOAuthLoginInstance;

    public static Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.loginform, container, false);

//        naver = (Button)v.findViewById(R.id.naverLoginButton);
//        naver.setOnClickListener(this);

        mContext = MainActivity.mContext;

        /* 네이버 아이디로 로그인 */
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);

        mOAuthLoginButton = (OAuthLoginButton) v.findViewById(R.id.naverLoginButton);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

        return v;
    }

    /* 네이버 아이디로 로그인 */
    static public OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.naverLoginButton:
                ((MainActivity)getActivity()).NaverLogin();
                break;
        }
    }
}
