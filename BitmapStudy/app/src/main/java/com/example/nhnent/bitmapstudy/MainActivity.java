package com.example.nhnent.bitmapstudy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);



        BitmapFactory.Options options = new BitmapFactory.Options();
//        BitmapFactory.Options options2 = new BitmapFactory.Options();
//        options2.inMutable = true;
//        options.inBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample1, options2);
//        options.inDensity = 160;
//        options.inTargetDensity = 320;
//        options.inDither = true;
//        options.inInputShareable = true;
        //options.inJustDecodeBounds = true; //bitmp 반환하지 않고 options 필드만 채운다.
        //options.inPreferQualityOverSpeed = true; //속도가 느려져도 항상 high quality 이미지 로드 (N부터 deprecated)
//        options.inSampleSize = 4; //이미지 축소?? ex. 4이면 1/4크기
//          options.inScreenDensity = 320; //cf) inTargetDensity
//        options.inScaled = true;
//        options.inPremultiplied = false;
//        options.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sample1, options);
        Log.d("BITMAP", "width: " + options.outWidth + ", height: " + options.outHeight +
                ", inDensity: " + options.inDensity + ", inScreenDensity: " + options.inScreenDensity +
                ", inTargetDensity: " + options.inTargetDensity);
        imageView.setImageBitmap(bitmap);
    }
}
