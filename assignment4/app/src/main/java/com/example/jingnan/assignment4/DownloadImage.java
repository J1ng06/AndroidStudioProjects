package com.example.jingnan.assignment4;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
/**
 * Created by JINGNAN on 2016-02-16.
 */
public class DownloadImage extends AsyncTask<String, Void, Void> {
    Context mCtx;
    Bitmap[] bm;
    protected DownloadImage(Context ctx){
        mCtx = ctx;
    }
    @Override
    protected Void doInBackground(String... urls) {
        bm = new Bitmap[urls.length];
        InputStream in;
        for (int i = 0; i < urls.length; i++) {
            //load image directly
            try {
                in = new java.net.URL(urls[i]).openStream();
                bm[i] = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                Log.e("error", "Downloading Image Failed");
            }
            saveToInternalStorage(bm[i], i);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void notused) {

    }

    private String saveToInternalStorage(Bitmap bitmapImage, int index){
        ContextWrapper cw = new ContextWrapper(mCtx);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile" + index + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}
