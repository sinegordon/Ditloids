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
        
       
        
        // узнать, можно ли объединить 4 кода для задания шрифта в один
        final TextView countView1 = (TextView)findViewById(R.id.TextView1);
        countView1.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));  
        
        final TextView countView2 = (TextView)findViewById(R.id.TextView2);
        countView2.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf")); 
        
        final TextView countView3 = (TextView)findViewById(R.id.TextView3);
        countView3.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf")); 
        
        final TextView countView4 = (TextView)findViewById(R.id.TextView4);
        countView4.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));         
        
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
