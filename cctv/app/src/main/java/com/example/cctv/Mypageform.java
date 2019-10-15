package com.example.cctv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Mypageform extends Fragment implements View.OnClickListener{
    View v;

    LinearLayout LoginLayout;
    LinearLayout LockLayout;
    LinearLayout PatrolLayout;

    LinearLayout myloginLayout;
    TextView myNameText;
    LinearLayout mylogoutLayout;
    Button naverLogoutButton;
    ImageView profileImage;

    Switch lockSwitch;

    /* 네이버 로그인 */
    private static String OAUTH_CLIENT_ID = "fJECNAV766XxJKZ4AQU8";
    private static String OAUTH_CLIENT_SECRET = "aKa_jmn84Q";
    private static String OAUTH_CLIENT_NAME = "감시자들";

    public static OAuthLoginButton mOAuthLoginButton;
    public static OAuthLogin mOAuthLoginInstance;

    Handler handler = new Handler();

    public static Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mypageform, container, false);

        LockLayout = (LinearLayout)v.findViewById(R.id.mypage_lockLayout);
        LockLayout.setOnClickListener(this);
        PatrolLayout = (LinearLayout)v.findViewById(R.id.mypage_patrolLayout);
        PatrolLayout.setOnClickListener(this);
//        userInfoLayout = (LinearLayout)v.findViewById(R.id.userInfoLayout);
//        userInfoLayout.setOnClickListener(this);

        myloginLayout = (LinearLayout)v.findViewById(R.id.mypage_login_layout);
        myloginLayout.setOnClickListener(this);
        myNameText = (TextView) v.findViewById(R.id.mypage_username);
        mylogoutLayout = (LinearLayout)v.findViewById(R.id.mypage_logout_layout);
        profileImage = (ImageView)v.findViewById(R.id.userProfileImage);

        naverLogoutButton = (Button)v.findViewById(R.id.naverLogoutButton);
        naverLogoutButton.setOnClickListener(this);

        lockSwitch = (Switch)v.findViewById(R.id.lockSwitch);
        switchCheck();
        lockSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCheck();
            }
        });

        mContext = MainActivity.mContext;

        /* 네이버 아이디로 로그인 */
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);

        if (mOAuthLoginInstance.getAccessToken(getActivity()) != null)
        {
            new RequestApiTask().execute();
            myNameText.setText("");
            Toast.makeText(getActivity(), "로그인중", Toast.LENGTH_SHORT).show();
            mylogoutLayout.setVisibility(View.GONE);
            myloginLayout.setVisibility(View.VISIBLE);

        }
        else
        {
            Toast.makeText(getActivity(), "로그인중아님", Toast.LENGTH_SHORT).show();
            mylogoutLayout.setVisibility(View.VISIBLE);
            myloginLayout.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onResume() {
        switchCheck();
        super.onResume();
    }

    private void switchCheck(){

        if(lockSwitch.isChecked()) {
            ((MainActivity)MainActivity.mContext).checkPermission();
            Intent intent = new Intent(getActivity(), ScreenService.class);
            getActivity().startService(intent);
        }
        else{
            Intent intent = new Intent(getActivity(), ScreenService.class);
            getActivity().stopService(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.mypage_login_layout:
//                ((MainActivity)getActivity()).UserInfo();
//                break;
//            case R.id.mypage_lockLayout:
//                Intent intent = new Intent(getActivity(),Mypage_Lockform.class);
//                startActivity(intent);
//                break;
            case R.id.mypage_patrolLayout:
                Intent ptintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://patrol.police.go.kr/map/map.do"));
                startActivity(ptintent);
                break;
//            case R.id.userInfoLayout:
//            case R.id.userInfoButton:
//                ((MainActivity)getActivity()).UserInfo();
//                break;
            case R.id.naverLogoutButton:
                mOAuthLoginInstance.logout(mContext);
                /*새로고침*/
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
        }
    }

    /* 네이버 아이디로 로그인 */
    public OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
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

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {//작업이 실행되기 전에 먼저 실행.
            myNameText.setText((String) "");//메일 란 비우기
        }

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.
        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                String name = response.getString("name");
                final String image = response.getString("profile_image");
                myNameText.setText(name);//메일 란 채우기

                /* 네이버 프로필 이미지 보여주기 */
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {    // 오래 거릴 작업을 구현한다
                        try{
                            final ImageView iv = (ImageView)v.findViewById(R.id.userProfileImage);
                            URL url = new URL(image);
                            InputStream is = url.openStream();
                            final Bitmap bm = BitmapFactory.decodeStream(is);
                            handler.post(new Runnable() {

                                @Override
                                public void run() {  // 화면에 그려줄 작업
                                    iv.setImageBitmap(bm);
                                }
                            });
                            iv.setImageBitmap(bm); //비트맵 객체로 보여주기
                        } catch(Exception e){
                        }
                    }
                });
                t.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
