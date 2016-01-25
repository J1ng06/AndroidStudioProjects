package com.example.jingnan.assignment2;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by JINGNAN on 2016-01-24.
 */
public class ViewActivity extends Activity {
    public ListView all_info;
    String[] info_from_one_file;
    ArrayList<String> all_ppl_info = new ArrayList<String>();
    FileInputStream fis = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Context context = getApplicationContext();
        all_info = (ListView) findViewById(R.id.allInfolistView);
        System.out.println("viewviewviewview");
        Bundle bundle = this.getIntent().getExtras();
        String[] file_list = bundle.getStringArray(Integer.toString(R.string.viewActivityTitle));

        for (int i = 0; i < file_list.length; i++) {
            // get all people's information from a file

            try {
                fis = context.openFileInput(file_list[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            info_from_one_file = sb.toString().split("/n");
            for (int j = 0; j < info_from_one_file.length; j++) {
                // append each person's information into array list
                all_ppl_info.add(info_from_one_file[j]);
            }
        }
        // display them in list view
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item2, android.R.id.text2, all_ppl_info);
        all_info.setAdapter(adapter);
    }
}
