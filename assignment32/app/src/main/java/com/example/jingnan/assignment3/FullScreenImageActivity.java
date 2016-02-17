package com.example.jingnan.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class FullScreenImageActivity extends ActionBarActivity implements View.OnClickListener{

    Intent intent;
    ImageView imageview;
    int position;
    private ImageButton camera, gallery, delete;
    private String[] filepath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        camera = (ImageButton) findViewById(R.id.camera);
        gallery = (ImageButton) findViewById(R.id.gallery);
        delete = (ImageButton) findViewById(R.id.delete);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        delete.setOnClickListener(this);

        Intent i = getIntent();
        position = i.getExtras().getInt("pos");
        filepath = i.getStringArrayExtra("filepath");

        imageview = (ImageView) findViewById(R.id.picture);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        Bitmap bmp = BitmapFactory.decodeFile(filepath[position],options);
        Matrix m = new Matrix();
        m.setRotate(90);
        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
        imageview.setImageBitmap(bitmap);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera:
                intent = new Intent(FullScreenImageActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.gallery:
                intent = new Intent(FullScreenImageActivity.this, GridViewActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                File file = new File(filepath[position]);
                Toast.makeText(FullScreenImageActivity.this, "Deleting Photo", Toast.LENGTH_SHORT).show();
                file.delete();
                Toast.makeText(FullScreenImageActivity.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                onBackPressed();
                intent = new Intent(FullScreenImageActivity.this, GridViewActivity.class);
                startActivity(intent);

        }
    }
}