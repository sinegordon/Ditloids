package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.graphics.Typeface;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A more complex demo including using a RadioGroup as "tabs" for the pager and showing the
 * dual-scrolling capabilities when a vertically scrollable element is nested inside the pager.
 */
public class LevelsActivity extends Activity implements OnClickListener, OnKeyListener {
    private RadioGroup radioGroup;
	private HorizontalPager pager;
	private Game game;

	private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener =
            new HorizontalPager.OnScreenSwitchListener() {
                @Override
                public void onScreenSwitched(final int screen) {
                    // Check the appropriate button when the user swipes screens.
                    switch (screen) {
                        case 0:
                            radioGroup.check(R.id.radio_btn_0);
                            break;
                        case 1:
                            radioGroup.check(R.id.radio_btn_1);
                            break;
                        case 2:
                            radioGroup.check(R.id.radio_btn_2);
                            break;
                        case 3:
                            radioGroup.check(R.id.radio_btn_3);
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
                            pager.setCurrentScreen(0, true);
                            break;
                        case R.id.radio_btn_1:
                            pager.setCurrentScreen(1, true);
                            break;
                        case R.id.radio_btn_2:
                            pager.setCurrentScreen(2, true);
                            break;
                        case R.id.radio_btn_3:
                            pager.setCurrentScreen(3, true);
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
        radioGroup = (RadioGroup) findViewById(R.id.tabs);
        radioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        pager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        pager.setOnScreenSwitchListener(onScreenSwitchListener);
        
        game = new Game(getApplicationContext(), 4);

        // узнать, можно ли объединить 4 кода для задания шрифта в один
        final TextView countView1 = (TextView)findViewById(R.id.TextView1);
        countView1.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));       
        countView1.setText(Integer.toString(game.AnswersCount(1)) + " из " + Integer.toString(game.GetLevel(1).GetDitloidsCount()));
        
        final TextView countView2 = (TextView)findViewById(R.id.TextView2);
        countView2.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf")); 
        countView2.setText(Integer.toString(game.AnswersCount(2)) + " из " + Integer.toString(game.GetLevel(2).GetDitloidsCount()));
        
        final TextView countView3 = (TextView)findViewById(R.id.TextView3);
        countView3.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));
        countView3.setText(Integer.toString(game.AnswersCount(3)) + " из " + Integer.toString(game.GetLevel(3).GetDitloidsCount()));
        
        final TextView countView4 = (TextView)findViewById(R.id.TextView4);
        countView4.setTypeface(Typeface.createFromAsset(
        		getAssets(), "fonts/Ukrainian-Play.ttf"));
        countView4.setText(Integer.toString(game.AnswersCount(4)) + " из " + Integer.toString(game.GetLevel(4).GetDitloidsCount()));
        
//        TextView tvName = (TextView) findViewById(R.id.TextView1);
//        Typeface digitalFont = Typeface.createFromAsset(this.getAssets(), "fonts/digital.ttf");
//        tvName.setTypeface(digitalFont);        

        findViewById(R.id.arrowButton).setOnClickListener(this);
        findViewById(R.id.level1button).setOnClickListener(this);
        findViewById(R.id.level2button).setOnClickListener(this);
        findViewById(R.id.level3button).setOnClickListener(this);
        findViewById(R.id.level4button).setOnClickListener(this);
    }
    
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	startActivity(new Intent(LevelsActivity.this, MainActivity.class));  
	    	finish();
	    	break;
	    case R.id.level1button:
	    	TasksActivity.SetGame(game);
	    	game.LoadLevel(1);
	    	startActivity(new Intent(LevelsActivity.this, TasksActivity.class));    	
	    	break;
	    case R.id.level2button:
	    	TasksActivity.SetGame(game);
	    	game.LoadLevel(2);
	    	startActivity(new Intent(LevelsActivity.this, TasksActivity.class));    	
	    	break;
	    case R.id.level3button:
	    	TasksActivity.SetGame(game);
	    	game.LoadLevel(3);
	    	startActivity(new Intent(LevelsActivity.this, TasksActivity.class));    	
	    	break;
	    case R.id.level4button:
	    	TasksActivity.SetGame(game);
	    	game.LoadLevel(4);
	    	startActivity(new Intent(LevelsActivity.this, TasksActivity.class));    	
	    	break;
	    default:
	    	break;
	    }
	}
	
    @Override
	public void onConfigurationChanged(Configuration newConfig) {  
    	super.onConfigurationChanged(newConfig);  
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// Если нажата хардварная кнопка назад (вопрос будет ли работать ???)
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	// На главный экран
	    	startActivity(new Intent(LevelsActivity.this, MainActivity.class));
	    	finish();       
	    } else {
	        return super.onKeyDown(keyCode, event);
	    }
		return false;
	}

}
