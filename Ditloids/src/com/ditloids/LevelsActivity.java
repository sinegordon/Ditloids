package com.ditloids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * A more complex demo including using a RadioGroup as "tabs" for the pager and showing the
 * dual-scrolling capabilities when a vertically scrollable element is nested inside the pager.
 */
public class LevelsActivity extends Activity implements OnClickListener, OnKeyListener {
    private RadioGroup radioGroup = null;
    // "���������"
	private android.support.v4.view.ViewPager pager = null;
	// ������ ��������� ����� �������
	private TextView[] countViews = null;
	// ������ ������ �������� �� ������
	private Button[] countButtons = null;
	// ������ �������� ������ �� ������
	private int checkedLevelIndex = 0;
	private static Game game = null;
	// ������
	private AlertDialog.Builder adb = null;
	List<View> pages = null;
	         

	@Override
    public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levels);
		/*
		LayoutInflater maininflater = LayoutInflater.from(this);
		View levelspage = maininflater.inflate(R.layout.levels, null);
        //setContentView(R.layout.levels);
		LayoutInflater inflater = LayoutInflater.from(this);
        pages = new ArrayList<View>();
        View page = inflater.inflate(R.layout.pager_items, null);
        for(int i = 1; i < game.GetCountLevels() + 1; i++){
	    	int id = getResources().getIdentifier("rl" + Integer.toString(i), "id", getApplicationContext().getPackageName());
	        RelativeLayout rl = (RelativeLayout) page.findViewById(id);
	        pages.add(rl);
        }
        LevelsPagerAdapter pagerAdapter = new LevelsPagerAdapter(pages);
        pager = new android.support.v4.view.ViewPager(getApplicationContext());
        RelativeLayout rel = (RelativeLayout)findViewById(R.id.horizontal_pager);
        rel.addView(pager);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        */
        
        game.SetPauseMusic(false);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //radioGroup = (RadioGroup) findViewById(R.id.tabs);

        // ���������� �������
        adb = new AlertDialog.Builder(this);
        // ������ �������
        adb.setIcon(android.R.drawable.ic_dialog_info);
        // ������ �������������� ������ �������
        adb.setPositiveButton(R.string.yes, null);
        // ������� ������
        adb.create();
        countViews = new TextView[game.GetCountLevels()];
        countButtons = new Button[game.GetCountLevels()];
        
        // ��������� ������� ������ ������� � �������� �� �������
        for(int i = 1; i < game.GetCountLevels() + 1; i++) {
        	int id = getResources().getIdentifier("TextView" + Integer.toString(i), "id", getApplicationContext().getPackageName());
        	TextView countView = (TextView)findViewById(id);
        	int idb = getResources().getIdentifier("level" + Integer.toString(i) + "button", "id", getApplicationContext().getPackageName());
        	Button but = (Button)findViewById(idb);
        	countViews[i-1] = countView;
        	countButtons[i-1] = but;
            // ���������� ����������� ������� ������
        	countButtons[i-1].setOnClickListener(LevelsActivity.this);
        };
        // ���������� ���������� ������� ������
        findViewById(R.id.arrowButton).setOnClickListener(this);
        //radioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        findViewById(R.id.arrowButton).bringToFront();
    }
	
    // ���������� ����� �� �������������
    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                	for(int i = 0; i < game.GetCountLevels(); i++){
                		int id = getResources().getIdentifier("radio_btn_" + Integer.toString(i), "id", getApplicationContext().getPackageName());
                		if (id == checkedId) {
                			//pager.setCurrentItem(i);
                			RelativeLayout rel = (RelativeLayout)findViewById(R.id.horizontal_pager);
                			rel.addView(pages.get(i));
                			checkedLevelIndex = i + 1;
                			break;
                		}
                	}
                }
            };
    
    
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// ���� ������ �����
	    case R.id.arrowButton:
	    	finish();
	    	break;
	    default:
	    	// ���� ���-�� ������ (��������� �� ������ �� ������ �������� �� �������)
	    	for(int i = 1; i < game.GetCountLevels() + 1; i++){
	    		int id = getResources().getIdentifier("level" + Integer.toString(i) +"button", "id", getApplicationContext().getPackageName());
	    		// �c�� ������
	    		if (id == view.getId()){
	    			// ��������� ����������� ������
	    			checkedLevelIndex = i;
	    	    	if(game.GetLevelAccess(i)){
	    	    		// ���� �������� - ���������
	    		    	game.LoadLevel(i);
	    		    	startActivity(new Intent(LevelsActivity.this, TasksActivity.class));
	    		    	//finish();
	    		    	break;
	    	    	}
	    	    	else{
	    	    		// ���� ���������� - �������� ������������ ������� ����� ��� ������ ��� ��� ��������
	    	    		adb.setMessage("��� ������� � ����� ������ �������� �������� �� " + 
	    			    		  Integer.toString(game.GetLevelsDivisor()*(checkedLevelIndex - 1) - game.GetCountRight()) + " �������(�,��).");
	    		    	adb.show();
	    	    	}
	    		}
	    	}
	    	break;
	    }
	}
	
	// ������ �������� ������
    @Override
	public void onConfigurationChanged(Configuration newConfig) {  
    	super.onConfigurationChanged(newConfig);  
	}

	/*@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// ���� ������ ���������� ������ �����
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN) {
	    	// �� ������� �����
	    	finish();
			return true;
	    } else {
	        return false;
	    }
	}*/
	
    // ����� �����-������ ��� ������������ ����������
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
    }
    
    // ����� ����� �����-������ ��� �������� �� ����������  
    // �������� ����������� ���������� �� ������� ��� �������� �� ���������� 
    @Override
    protected void onResume() {
    	super.onResume();
    	game.SetPauseMusic(false);
        // ���������� ���������� ����� � ��������
        for(int i = 1; i < game.GetCountLevels() + 1; i++) {
        	countViews[i-1].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Ukrainian-Play.ttf"));
        	countViews[i-1].setText(Integer.toString(game.AnswersCount(i)) + " �� " + Integer.toString(game.GetLevel(i).GetDitloidsCount()));
        	if(!game.GetLevelAccess(i)) {
        		countButtons[i-1].setBackgroundResource(R.drawable.level_lock);
        	}
        	else {
        		int drawableId = getResources().getIdentifier("level" + Integer.toString(i), "drawable", getApplicationContext().getPackageName());
        		countButtons[i-1].setBackgroundResource(drawableId);
        	}
        }
    }

	
	static public void SetGame(Game _game) {
		game = _game;
	}


	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
