package com.ditloids;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A more complex demo including using a RadioGroup as "tabs" for the pager and showing the
 * dual-scrolling capabilities when a vertically scrollable element is nested inside the pager.
 */
public class LevelsActivity extends Activity implements OnClickListener, OnKeyListener {
    private RadioGroup radioGroup = null;
	private HorizontalPager pager = null;
	// ������ ��������� ����� �������
	private TextView[] countViews = null;
	// ������ ������ �������� �� ������
	private Button[] countButtons = null;
	// ������ �������� ������ �� ������
	private int checkedLevelIndex = -1;
	private static Game game = null;
	// ������
	private AlertDialog.Builder adb = null;
	private static BitmapDrawable bmd = null;
	
	// ���������� �������� ������
	private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener =
            new HorizontalPager.OnScreenSwitchListener() {
                @Override
                public void onScreenSwitched(final int screen) {
                	int id = getResources().getIdentifier("radio_btn_" + Integer.toString(screen), "id", getApplicationContext().getPackageName());
                	radioGroup.check(id);
                	checkedLevelIndex = screen + 1;
                }
            };
    
    // ���������� ����� �� �������������
    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                	for(int i = 0; i < game.GetCountLevels(); i++){
                		int id = getResources().getIdentifier("radio_btn_" + Integer.toString(i), "id", getApplicationContext().getPackageName());
                		if (id == checkedId){
                			pager.setCurrentScreen(i, true);
                			checkedLevelIndex = i + 1;
                			break;
                		}
                	}
                }
            };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
    	View v = findViewById(R.id.levelsLayout);
    	v.setBackgroundDrawable(bmd);
    	
    	//DisplayMetrics metrics = new DisplayMetrics();
    	//getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	//ImageView v1 = (ImageView)findViewById(R.id.imageView1);
    	//v1.setMaxWidth(metrics.widthPixels);
    	
        game.SetPauseMusic(false);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        radioGroup = (RadioGroup) findViewById(R.id.tabs);
        radioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        pager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        pager.setOnScreenSwitchListener(onScreenSwitchListener);
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
        for(int i = 1; i < game.GetCountLevels() + 1; i++){
        	int id = getResources().getIdentifier("TextView" + Integer.toString(i), "id", getApplicationContext().getPackageName());
        	TextView countView = (TextView)findViewById(id);
        	int idb = getResources().getIdentifier("level" + Integer.toString(i) + "button", "id", getApplicationContext().getPackageName());
        	Button but = (Button)findViewById(idb);
        	countViews[i-1] = countView;
        	countButtons[i-1] = but;
        };
        // ���������� ����������� �������
    	for(int i = 1; i < game.GetCountLevels() + 1; i++){
    		int id = getResources().getIdentifier("level" + Integer.toString(i) +"button", "id", getApplicationContext().getPackageName());
    		findViewById(id).setOnClickListener(this);
    	}
        findViewById(R.id.arrowButton).setOnClickListener(this);
        findViewById(R.id.arrowButton).bringToFront();
    }
    
    
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
	
	// ���������� ����
	public void DrawLevelInfo() {
        // ���������� ���������� ����� � ��������
        for(int i = 1; i < game.GetCountLevels() + 1; i++) {
        	countViews[i-1].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Ukrainian-Play.ttf"));
        	if(!game.GetLevelAccess(i)) {
        		countButtons[i-1].setBackgroundResource(R.drawable.level_lock);
            	countViews[i-1].setText("������� " + Integer.toString(i));
        	}
        	else {
        		int drawableId = getResources().getIdentifier("level" + Integer.toString(i), "drawable", getApplicationContext().getPackageName());
        		countButtons[i-1].setBackgroundResource(drawableId);
            	countViews[i-1].setText(Integer.toString(game.AnswersCount(i)) + " �� " + Integer.toString(game.GetLevel(i).GetDitloidsCount()));
        	}
        }
    }
	
    // ������������ ����������
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
    	DrawLevelInfo();
    }
	
	static public void SetGame(Game _game) {
		game = _game;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}
	
    public static void SetDrawable(BitmapDrawable _bmd){
    	bmd = _bmd;
    }

}
