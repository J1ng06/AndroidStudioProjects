package com.example.jingnan.assignment3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

    private Activity activity;
    private String[] filepath;
    private String[] filename;
    private static LayoutInflater inflater = null;

    public GridViewAdapter(Activity a, String[] fpath, String[] fname) {
        activity = a;
        filepath = fpath;
        filename = fname;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return filepath.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.activity_gridview_adapter, null);
        TextView text = (TextView) vi.findViewById(R.id.title);
        ImageView imageView = (ImageView) vi.findViewById(R.id.images);
        text.setText(filename[position]);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        Bitmap bmp = BitmapFactory.decodeFile(filepath[position],options);
        Matrix m = new Matrix();
        m.setRotate(90);
        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
        imageView.setImageBitmap(bitmap);
        return vi;
    }

}