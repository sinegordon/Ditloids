package com.ditloids;

import com.ditloids.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	/* Listener Example
	public class Listener implements View.OnClickListener {

    	public void onClick(View view) {
        	((TextView)findViewById(R.id.widget33)).setText("fdefefef");
    	}
	}
	*/
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
}