package com.example.jingnan.assignment1_p2;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends ActionBarActivity {

    int counter = 0;
    TextView number_of_clicks;
    ImageView doggy;
    ToggleButton btnDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number_of_clicks = (TextView)findViewById(R.id.click_number_field);

        btnDisplay = (ToggleButton)findViewById(R.id.toggle_image);

        doggy = (ImageView)findViewById(R.id.dogImage);
        Drawable myDrawable = getResources().getDrawable(R.drawable.dog);
        doggy.setImageDrawable(myDrawable);
        doggy.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    // Accumulate Number of Clicks
    public void accumulateClicks(View view) {
        counter++;
        number_of_clicks.setText(Integer.toString(counter));
    }

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            doggy.setVisibility(View.VISIBLE);
        } else {
            doggy.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            doggy.setVisibility(View.INVISIBLE);
            number_of_clicks.setText("No Clicks Yet");
            counter = 0;
            btnDisplay.setChecked(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
