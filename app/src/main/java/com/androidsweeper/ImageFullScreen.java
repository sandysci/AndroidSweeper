package com.androidsweeper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ImageFullScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        ImageView myImg=(ImageView)findViewById(R.id.imageDetail);
        Bitmap bMap = BitmapFactory.decodeFile(getIntent().getStringExtra("image_path"));
//        myImg.setImageURI(Uri.withAppendedPath(
//                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + getIntent().getIntExtra("image_id",0)));
        myImg.setImageBitmap(bMap);


    }


}
