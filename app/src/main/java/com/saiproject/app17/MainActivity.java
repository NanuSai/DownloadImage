package com.saiproject.app17;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    ImageView imageView;
    Button btnDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        imageView = findViewById(R.id.imageView);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {



        DownloadImageTask downloadImageTask = new DownloadImageTask(MainActivity.this);
        downloadImageTask.execute("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDRmB0MU1OQIYQTMJji-zHD1VXUIs16q2ZA1DOsM1RM5YYWlgW");


    }


    //Creating inner class
    private class DownloadImageTask extends AsyncTask<String,Void, Bitmap> { //params: type of data,progress: how much has been downloaded,result: the type of result which is showed

        ProgressDialog progressDialog;
        Context context;


        public DownloadImageTask(Context context){

            this.context = context;
            progressDialog = new ProgressDialog(context);

        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Showed just before download
            progressDialog.setMessage("Download Image.....");
            progressDialog.show();
        }



        @Override //return type is Bitmap since output type is set as Bitmap
        //Process done in background
        protected Bitmap doInBackground(String... params) {
            //Strings... means input size is unknown

            String stringurl = params[0]; //Index 0 is the first URL
            Bitmap bitmap = null;

            try{

                URL url = new URL(stringurl);
                InputStream inputStream = url.openStream(); // Opens the website to download
                bitmap = BitmapFactory.decodeStream(inputStream); //Download images from inputStream
            }
            catch (Exception e){


                e.printStackTrace();

            }

            return bitmap;
        }





        @Override   //After process is done, the bitmap object is passed as argument here
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);


            imageView.setImageBitmap(bitmap);

            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }


        }
    }
}
