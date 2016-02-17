package com.example.jingnan.assignment4;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by JINGNAN on 2016-02-15.
 */
public class DownloadTxt extends AsyncTask<String, Integer, String>
{

    private String Content;
    private String Error = null;
    private ProgressDialog Dialog;
    private int status = DOWNLOADING; //status of current process
    private Context mCtx;
    private ProgressDialog progressDialog;
    private static final int DOWNLOADING = 0;
    private static final int COMPLETE = 1;
    private static final String DTAG = "DownloadTxt";

    public DownloadTxt(Context ctx){
        mCtx = ctx;
    }
    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        String[] image_links = new String[3];
        int imageCount = 0;
        try {
            URL url = new URL(urls[0]);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                //get lines
                result+=line;
                if (line.contains(".jpg")){
                    image_links[imageCount++] = "http://www.eecg.utoronto.ca/~jayar/" + line.toString();
                }
            }
            in.close();
            new DownloadImage(mCtx).execute(image_links);

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... changed)
    {
    }

    protected void onPreExecute() {
        //UI Element
        Dialog = new ProgressDialog(mCtx);
        Dialog.setMessage("Downloading source..");
        Dialog.show();
    }
    @Override
    protected void onPostExecute(String result) {
        Dialog.dismiss();
        //Log.d(DTAG, result);
    }

}

