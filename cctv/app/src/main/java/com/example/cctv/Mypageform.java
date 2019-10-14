package com.example.cctv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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

    /* 네이버 로그인 */
    private static String OAUTH_CLIENT_ID = "fJECNAV766XxJKZ4AQU8";
    private static String OAUTH_CLIENT_SECRET = "aKa_jmn84Q";
    private static String OAUTH_CLIENT_NAME = "감시자들";

    public static OAuthLoginButton mOAuthLoginButton;
    public static OAuthLogin mOAuthLoginInstance;
    private Mypageform activity;

    String accessToken="";

    public static Context mContext;

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

        myloginLayout = (LinearLayout)v.findViewById(R.id.loginLayout);
        myNameText = (TextView) v.findViewById(R.id.mypage_username);
        mylogoutLayout = (LinearLayout)v.findViewById(R.id.logoutLayout);

        naverLogoutButton = (Button)v.findViewById(R.id.naverLogoutButton);
        naverLogoutButton.setOnClickListener(this);

        mContext = MainActivity.mContext;

        /* 네이버 아이디로 로그인 */
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);

        if (mOAuthLoginInstance.getAccessToken(getActivity()) != null)
        {
            Toast.makeText(getActivity(), "로그인중", Toast.LENGTH_SHORT).show();
            myloginLayout.setVisibility(View.GONE);
            mylogoutLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getActivity(), "로그인중아님", Toast.LENGTH_SHORT).show();
            myloginLayout.setVisibility(View.VISIBLE);
            mylogoutLayout.setVisibility(View.GONE);
        }
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
                        accessToken = mOAuthLoginInstance.getAccessToken(mContext);
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

    public class NaverProfileGet extends AsyncTask<String, Void, String> {
        //네이버 프로필 조회 API에 보낼 헤더. 그대로 쓰면 된다.
        String header = "Bearer " + accessToken;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            //네이버 프로필 조회 API에서 프로필을 jSON 형식으로 받아오는 부분.
            //이 부분도 그대로 사용하면 된다.
            StringBuffer response = new StringBuffer();
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", header);
                int responseCode = conn.getResponseCode();
                BufferedReader br;
                if(responseCode == 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                String inputLine;
                while((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response.toString();
        }

        //네이버 프로필 조회 API에서 받은 jSON에서 원하는 데이터를 뽑아내는 부분
        //여기서는 닉네임, 프로필사진 주소, 이메일을 얻어오지만, 다른 값도 얻어올 수 있다.
        //이 부분을 원하는 대로 수정하면 된다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject1 = new JSONObject(result);
                JSONObject jsonObject2 = (JSONObject)jsonObject1.get("response");
                String image = jsonObject2.getString("profile_image");
                String name = jsonObject2.getString("name");
                String email = jsonObject2.getString("email");

                myNameText.setText(name);
            } catch (Exception e) {}
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
