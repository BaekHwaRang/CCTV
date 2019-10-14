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
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                ProfileTask task = new ProfileTask();
                task.execute(accessToken);
            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };

    class ProfileTask extends AsyncTask<String, Void, String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            String token = strings[0];// 네이버 로그인 접근 토큰;
            String header = "Bearer " + token; // Bearer 다음에 공백 추가
            try {
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                result = response.toString();
                br.close();
                System.out.println(response.toString());
            } catch (Exception e) {
                System.out.println(e);
            }
            //result 값은 JSONObject 형태로 넘어옵니다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //넘어온 result 값을 JSONObject 로 변환해주고, 값을 가져오면 되는데요.
                // result 를 Log에 찍어보면 어떻게 가져와야할 지 감이 오실거에요.
                JSONObject object = new JSONObject(result);
                if (object.getString("resultcode").equals("00")) {
                    JSONObject jsonObject = new JSONObject(object.getString("response"));
                    Log.d("jsonObject", jsonObject.toString());
                    Log.i("테스트", result);
//                    editor.putString("email", jsonObject.getString("email"));
//                    editor.putString("name", jsonObject.getString("name"));
//                    editor.putString("nickname", jsonObject.getString("nickname"));
//                    editor.putString("profile", jsonObject.getString("profile_image"));
//                    editor.apply();

                    ((MainActivity) getActivity()).Mainpage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
