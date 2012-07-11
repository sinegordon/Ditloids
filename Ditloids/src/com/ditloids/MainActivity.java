package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        setContentView(R.layout.main);
        Button startButton = (Button)findViewById(R.id.StartButton);
        startButton.setOnClickListener(this);
        startButton.setOnTouchListener(this);
        //final LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Button startButton = (Button)inflater.inflate(R.id.StartButton, null);
        /* Inflate Example
        final LayoutInflater  inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView v = (TextView)inflater.inflate(R.layout.textview, null);
        LinearLayout lLayout = (LinearLayout)findViewById(R.id.layout1);
        lLayout.addView(v);
        Level lvl = new Level(this, 1);
        for(int i=0; i < lvl.GetDitloidsCount(); i++)
            v.append(lvl.GetDitloid(i)+"\n");
        */
    }

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (view.getId()) {
	    case R.id.StartButton:
	    	break;
	    default:
	    	break;
	    }
    	return false;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.StartButton:
	    	Intent intent = new Intent(this, LevelsActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    	startActivity(intent);
	    	break;
	    default:
	    	break;
	    }	
	}
}