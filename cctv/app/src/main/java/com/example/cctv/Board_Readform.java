package com.example.cctv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class Board_Readform extends Activity {

    ImageView Logo;
    ImageButton GoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_readform);

        Intent intent = getIntent();

        TextView title = (TextView)findViewById(R.id.boardTitleText);
        TextView Ds = (TextView)findViewById(R.id.boardDsText);

        title.setText(intent.getStringExtra("title"));
        Ds.setText(intent.getStringExtra("Ds"));


        /* 이미지 비트맵 */
        Logo = (ImageView)findViewById(R.id.postLogo);
        GoodButton = (ImageButton)findViewById(R.id.GoodButton);

        Bitmap LogoBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.logo);
        Logo.setImageBitmap(LogoBitmap);
        Bitmap GoodBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.board_best);
        GoodButton.setImageBitmap(GoodBitmap);
    }
}
