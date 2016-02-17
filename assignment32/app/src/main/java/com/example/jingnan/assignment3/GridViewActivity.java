package com.example.jingnan.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class GridViewActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    GridView grid;
    GridViewAdapter adapter;
    File file;
    ImageButton camera;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collection);

        camera = (ImageButton) findViewById(R.id.camera);
        camera.setOnClickListener(this);

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            file = Helper.getPhotoDir();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();

            }
        }
        grid = (GridView) findViewById(R.id.gallery);
        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(GridViewActivity.this, FullScreenImageActivity.class);
                i.putExtra("filepath", FilePathStrings);
                i.putExtra("filename", FileNameStrings);
                i.putExtra("pos", position);
                startActivity(i);
            }

        });
    }

    public void onClick(View view) {
        intent = new Intent(GridViewActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
