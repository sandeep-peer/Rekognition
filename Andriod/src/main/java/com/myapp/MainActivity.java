package com.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button btnUpload;
    Button btnTakePic;
    Bitmap image;
    final int RC_TAKE_PHOTO =1 ;
    File dir;
    final String fileName = "recognition.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        btnTakePic =  findViewById(R.id.btnTakePic);
        imageView  =  findViewById(R.id.image);
        btnUpload  =  findViewById(R.id.btnUpload);

        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RC_TAKE_PHOTO);

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new S3FileUpload().execute(fileName,dir.getPath());
            }
        });
    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            image = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(image);

            //BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
            //Bitmap bitmap = draw.getBitmap();

            FileOutputStream outStream = null;
            File sdCard = Environment.getExternalStorageDirectory();
            dir = new File(sdCard.getAbsolutePath() + "/Recognition");
            dir.mkdirs();
            File outFile = new File(dir, fileName);
            System.out.println(fileName);
            System.out.println(dir);
            try {
                outStream = new FileOutputStream(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            try {
                outStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}