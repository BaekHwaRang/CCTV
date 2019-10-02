package com.example.cctv;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class LockScreenActivity extends Activity implements View.OnLongClickListener {

//    private final GestureDetector gestureDetector;
//    public boolean result = false;

    Button callButton;
    Button bellButton;
    public MediaPlayer mediaPlayer;
    Boolean noise = false;
    @SuppressLint("WrongViewCast")
    @Override

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockscreen);

        callButton = findViewById(R.id.callButton);
        bellButton = findViewById(R.id.bellButton);

        callButton.setOnLongClickListener(this);
        bellButton.setOnLongClickListener(this);


        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#F7E756"));
            }
        }else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.BLACK);
        }

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD); 작업창제거

        mediaPlayer = MediaPlayer.create(this,R.raw.noise);
        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onUserLeaveHint() {

        finish();
        super.onUserLeaveHint();
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.callButton:
                Context c = v.getContext();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:112"));

                try {
                    c.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bellButton:
                noiseOn();
                break;
        }
        return false;
    }

    public void noiseOn(){
        if(noise==false){
            mediaPlayer.start();
            noise=true;
        }
        else{
            mediaPlayer.stop();
            mediaPlayer.release();
            noise=false;
        }
    }

//    public LockScreenActivity(Context ctx){
//        gestureDetector = new GestureDetector(ctx, new GestureListener());
//    }
//
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }
//
//    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//
//        private static final int SWIPE_THRESHOLD = 100;
//        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                float diffY = e2.getY() - e1.getY();
//                float diffX = e2.getX() - e1.getX();
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffX > 0) {
//                            onSwipeRight();
//                        } else {
//                            onSwipeLeft();
//                        }
//                    }
//                    result = true;
//                }
//                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                    if (diffY > 0) {
//                        onSwipeBottom();
//                    } else {
//                        onSwipeTop();
//                    }
//                }
//                result = true;
//
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//            return result;
//        }
//    }
//
//    public void onSwipeRight() {
//        Toast.makeText(LockScreenActivity.this, "right", Toast.LENGTH_SHORT).show();
//    }
//
//    public void onSwipeLeft() {
//    }
//
//    public void onSwipeTop() {
//    }
//
//    public void onSwipeBottom() {
//    }
}
