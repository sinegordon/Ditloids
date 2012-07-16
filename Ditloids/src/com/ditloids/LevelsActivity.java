package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Typeface;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A more complex demo including using a RadioGroup as "tabs" for the pager and showing the
 * dual-scrolling capabilities when a vertically scrollable element is nested inside the pager.
 */
public class LevelsActivity extends Activity {
    private RadioGroup mRadioGroup;
	private HorizontalPager mPager;
	private Game game;

	private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener =
            new HorizontalPager.OnScreenSwitchListener() {
                @Override
                public void onScreenSwitched(final int screen) {
                    // Check the appropriate button when the user swipes screens.
                    switch (screen) {
                        case 0:
                            mRadioGroup.check(R.id.radio_btn_0);
                            break;
                        case 1:
                            mRadioGroup.check(R.id.radio_btn_1);
                            break;
                        case 2:
                            mRadioGroup.check(R.id.radio_btn_2);
                            break;
                        case 3:
                            mRadioGroup.check(R.id.radio_btn_3);
                            break;                            
                        default:
                            break;
                    }
                }
            };

    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                    // Slide to the appropriate screen when the user checks a button.
                    switch (checkedId) {
                        case R.id.radio_btn_0:
                            mPager.setCurrentScreen(0, true);
                            break;
                        case R.id.radio_btn_1:
                            mPager.setCurrentScreen(1, true);
                            break;
                        case R.id.radio_btn_2:
                            mPager.setCurrentScreen(2, true);
                            break;
                        case R.id.radio_btn_3:
                            mPager.setCurrentScreen(3, true);
                            break;                            
                        default:
                            break;
                    }
                }
            };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        mRadioGroup = (RadioGroup) findViewById(R.id.tabs);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        mPager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        mPager.setOnScreenSwitchListener(onScreenSwitchListener);
        
        game = new Game(getApplicationContext(), 4);
       
        // ������, ����� �� ���������� 4 ���� ��� ������� ������ � ����
        final TextView countView1 = (TextView)findViewById(R.id.TextView1);
        countView1.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));
        countView1.setText(Integer.toString(game.AnswersCount(1)));
        
        final TextView countView2 = (TextView)findViewById(R.id.TextView2);
        countView2.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf")); 
        countView2.setText(Integer.toString(game.AnswersCount(2)));
        
        final TextView countView3 = (TextView)findViewById(R.id.TextView3);
        countView3.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));
        countView3.setText(Integer.toString(game.AnswersCount(3)));
        
        final TextView countView4 = (TextView)findViewById(R.id.TextView4);
        countView4.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));
        countView4.setText(Integer.toString(game.AnswersCount(4)));
        
//        TextView tvName = (TextView) findViewById(R.id.TextView1);
//        Typeface digitalFont = Typeface.createFromAsset(this.getAssets(), "fonts/digital.ttf");
//        tvName.setTypeface(digitalFont);        

        findViewById(R.id.arrowButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(LevelsActivity.this, MainActivity.class));
            }
        });
    }
}

//package com.ditloids;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.widget.Button;
//
//public class LevelsActivity extends Activity implements OnClickListener, OnTouchListener {
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.levels);
//        Button arrowButton = (Button)findViewById(R.id.arrowButton);
//        arrowButton.setOnClickListener(this);
//        arrowButton.setOnTouchListener(this);
//    }
//
//	@Override
//	public boolean onTouch(View view, MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onClick(View view) {
//		switch (view.getId()) {
//	    case R.id.arrowButton:
//	    	Intent intent = new Intent(this, MainActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//	    	startActivity(intent);    	
//	    	break;
//	    default:
//	    	break;
//	    }
//	}
//}
//
//
