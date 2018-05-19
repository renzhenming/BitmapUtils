package com.rzm.bitmaputils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = findViewById(R.id.image);
    }

    public void load(View view) {
        String url ="http://b.hiphotos.baidu.com/image/h%3D300/sign=ce11dbfb922f070840052c00d925b865/d8f9d72a6059252d9dedd4b0389b033b5ab5b988.jpg";
        BitmapUtils.display(this,mImage,url);
    }
}
