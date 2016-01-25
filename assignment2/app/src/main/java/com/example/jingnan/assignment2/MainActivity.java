package com.example.jingnan.assignment2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ActionBarActivity implements EnterNameFragment.DoneListener, StoreFragment.StoreListener, LoadFragment.LoadListener,MainFragment.onClickedListnener {
    private ArrayList<String> name_list = new ArrayList<>();
    public String[] fileList;
    private FileOutputStream outputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFragmentContainer, mainFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void btnAddClicked(){}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickDoneBtn(ArrayList arrayList) {
        name_list = arrayList;
    }

    public ArrayList<String> getNameList(){
        return name_list;
    }

    @Override
    public void onClickStoreBtn(String filename){
        writeToFile(name_list,filename);
        name_list.clear();
    }

    public void writeToFile(ArrayList<String> name_list_to_save, String filename){
        String joined = TextUtils.join(", ", name_list_to_save);
        joined = joined.replaceAll(",", "/n");
        //System.out.println(joined);
        try {
            outputStream = openFileOutput(filename + ".txt", Context.MODE_PRIVATE);
            outputStream.write(joined.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickLoadBtn(ListView list_view) {
        // retrieve all the saved files
        fileList = getApplicationContext().fileList();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_item, android.R.id.text1, fileList);
        list_view.setAdapter(adapter);
    }
    public String FileContent(String filename, Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
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
        return sb.toString();
    }
    @Override
    public void ViewBtnClicked() {
        fileList = getApplicationContext().fileList();
        // launch another activity
        Bundle bundle = new Bundle();
        bundle.putStringArray(Integer.toString(R.string.viewActivityTitle), fileList);
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void ExitBtnClicked() {
        // if there are some created data that has not yet stored
        if (!name_list.isEmpty()) {
            Toast.makeText(this, "Terminating the Application and Saving unsaved data",
                    Toast.LENGTH_SHORT).show();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
            String currentDateTimeString = sdf.format(d);
            String joined = TextUtils.join(", ", name_list);
            joined = joined.replaceAll(",", "/n");
            try {
                outputStream = openFileOutput( "Unsaved data - "+ currentDateTimeString +".txt", Context.MODE_PRIVATE);
                outputStream.write(joined.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            name_list.clear();
        }
        finish();
    }
}
