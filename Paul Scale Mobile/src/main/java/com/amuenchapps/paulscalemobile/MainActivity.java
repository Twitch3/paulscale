package com.amuenchapps.paulscalemobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {

    private RelativeLayout mainLayout;
    private TextView scale0, scale1, scale2, scale3, scale4, scale5, scale6, scale7, scale8, scale9;

    private TextView lastSelectedScale;

    private int prevClick = 0; //The ID of the previously selected scale
    private String prevText; //The text of the previously selected scale

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Function executed when the app is set into a paused state. If a new scale selection
     * has taken place, we will store the ID, text, and color of that scale to use upon
     * re-initialization of the app.
     **/
    @Override
    protected void onPause() {
        super.onPause();

        // Store values between instances here
        SharedPreferences saveState = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = saveState.edit();  // Put the values from the UI

        if (prevClick != 0) {
            View colorView = findViewById(prevClick);
            int savedColor = ((ColorDrawable)colorView.getBackground()).getColor();
            editor.putInt("scaleID", prevClick);
            editor.putString("oldText", prevText);
            editor.putInt("oldColor", savedColor);
            // Commit to storage
            editor.commit();
        }
    }

    /**
     * Function executed when the app is started. Depending on if the device is in portrait mode or
     * landscape mode, we will grab all applicable scale TextViews and set up the appropriate
     * OnClick and OnLongClick listeners. We will also attempt to read a save state and set the
     * appropriate ID, text, and color of our ContentView for any previously selected scale.
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Portrait mode initialization code
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.activity_main);

            //Initialize basic TextView components
            scale0 = (TextView) findViewById(R.id.scale0);
            scale1 = (TextView) findViewById(R.id.scale1);
            scale2 = (TextView) findViewById(R.id.scale2);
            scale3 = (TextView) findViewById(R.id.scale3);
            scale4 = (TextView) findViewById(R.id.scale4);
            scale5 = (TextView) findViewById(R.id.scale5);
            scale6 = (TextView) findViewById(R.id.scale6);
            scale7 = (TextView) findViewById(R.id.scale7);
            scale8 = (TextView) findViewById(R.id.scale8);
            scale9 = (TextView) findViewById(R.id.scale9);

            //Set on click listeners
            scale0.setOnClickListener(this);
            scale1.setOnClickListener(this);
            scale2.setOnClickListener(this);
            scale3.setOnClickListener(this);
            scale4.setOnClickListener(this);
            scale5.setOnClickListener(this);
            scale6.setOnClickListener(this);
            scale7.setOnClickListener(this);
            scale8.setOnClickListener(this);
            scale9.setOnClickListener(this);

            //Set on long click listeners
            scale0.setOnLongClickListener(this);
            scale1.setOnLongClickListener(this);
            scale2.setOnLongClickListener(this);
            scale3.setOnLongClickListener(this);
            scale4.setOnLongClickListener(this);
            scale5.setOnLongClickListener(this);
            scale6.setOnLongClickListener(this);
            scale7.setOnLongClickListener(this);
            scale8.setOnLongClickListener(this);
            scale9.setOnLongClickListener(this);

            //Attempt to get a save state with a selected scale
            SharedPreferences saveState = getPreferences(MODE_PRIVATE);
            int sample = saveState.getInt("scaleID", 0);
            View scale = (View)findViewById(sample);
            View mainView = findViewById(R.id.layout);
            if(scale != null)
            {
                lastSelectedScale = (TextView)findViewById(scale.getId());
                prevClick = sample;
            }

            if(lastSelectedScale != null)
            {
                TextView clickedTV = (TextView)findViewById(scale.getId());
                prevText = clickedTV.getText().toString();
                clickedTV.setTextSize(20);
                clickedTV.setTypeface(null, Typeface.BOLD);
                ColorDrawable lastBackground = (ColorDrawable)lastSelectedScale.getBackground();
                mainView.setBackgroundColor(lastBackground.getColor());
            }
            else
            {
                Log.d("ON_RESUME", "NoView");
            }

        }
        //Landscape mode initialization code
        else {
            setContentView(R.layout.layout_landscape);
            SharedPreferences saveState = getPreferences(MODE_PRIVATE);
            TextView l_fullText = (TextView) findViewById(R.id.fs_textView);
            LinearLayout l_layout = (LinearLayout) findViewById(R.id.l_layout);
            String oldText = saveState.getString("oldText", null);

            if (!(oldText.equals("IT'S OVER")))
            {
                l_fullText.setText(oldText);
                l_layout.setBackgroundColor(saveState.getInt("oldColor", 0));
            }else{
                l_fullText.setText("IT\'S OVER");
                l_fullText.setTextColor(Color.WHITE);
                l_layout.setBackgroundColor(saveState.getInt("oldColor", 0));
            }
        }
    }


    /**
     * Simple onCreateOptionsMenu without any special functionality added.
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * Simple onOptionsItemSelected without any special functionality added.
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Function executed whenever one of our scales is selected. We need to set up the scale to be
     * highlighted and change the border of the app to match the corresponding color of the scale
     * that was now selected.
     **/
    @Override
    public void onClick(View scale) {
        if(scale.getId() == prevClick) //If the scale selected is already set as active
        {
            //Do nothing
            System.out.println("Same button!");

        }else if (prevClick == 0){ //If no scale has been selected yet

            //Set new button
            prevClick = scale.getId();
            Drawable clickedColor = scale.getBackground();
            TextView clickedTV = (TextView)findViewById(scale.getId());
            prevText = clickedTV.getText().toString();
            clickedTV.setTextSize(20);
            clickedTV.setTypeface(null, Typeface.BOLD);
            mainLayout = (RelativeLayout) findViewById(R.id.layout);
            mainLayout.setBackground(clickedColor);

        }else{ //If a scale has been previously selected

            //Reset previous button
            TextView oldTV = (TextView)findViewById(prevClick);
            oldTV.setText(prevText);
            oldTV.setTextSize(14);
            oldTV.setTypeface(Typeface.SANS_SERIF);

            //Set new button
            prevClick = scale.getId();
            Drawable clickedColor = scale.getBackground();
            TextView clickedTV = (TextView)findViewById(scale.getId());
            prevText = clickedTV.getText().toString();
            clickedTV.setTextSize(20);
            clickedTV.setTypeface(null, Typeface.BOLD);
            mainLayout = (RelativeLayout) findViewById(R.id.layout);
            mainLayout.setBackground(clickedColor);

        }
    }

    /**
     * Function executed whenever one of our scales is pressed down for a long click. Apart from
     * activating a normal click even on the selected scale, we also trigger our share functionality
     * here to share the status out via various sending methods.
     **/
    @Override
    public boolean onLongClick(View scale) {
        onClick(scale);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "CURRENT PAUL SCALE STATUS: \"" + ((TextView) findViewById(scale.getId())).getText().toString() + "\"");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        return true;
    }
}
