package com.ditloids;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LevelsActivity extends Activity implements OnClickListener, OnKeyListener {
    private RadioGroup radioGroup = null;
	// ������ �������� ������ �� ������
	private int checkedLevelIndex = 1;
	private static Game game = null;
	// ������
	private AlertDialog.Builder adb = null;
        

	@Override
    public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
    	Button but = (Button)findViewById(R.id.level_button);
        game.SetPauseMusic(false);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        radioGroup = (RadioGroup) findViewById(R.id.tabs);

        // ���������� �������
        adb = new AlertDialog.Builder(this);
        // ������ �������
        adb.setIcon(android.R.drawable.ic_dialog_info);
        // ������ �������������� ������ �������
        adb.setPositiveButton(R.string.yes, null);
        // ������� ������
        adb.create();   

        // ���������� ���������� ������� ������
    	but.setOnClickListener(LevelsActivity.this);
        findViewById(R.id.arrowButton).setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        findViewById(R.id.arrowButton).bringToFront();
        
    }
	
    // ���������� ����� �� �������������
    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                	for(int i = 0; i < game.GetCountLevels(); i++){
                		int id = getResources().getIdentifier("radio_btn_" + Integer.toString(i+1), "id", getApplicationContext().getPackageName());
                		if (id == checkedId) {
                			checkedLevelIndex = i+1;
                			onResume();
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

    // ����� �����-������ ��� ������������ ����������
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
    }
    
    // ����� ����� �����-������ ��� �������� �� ����������  
    // �������� ����������� ���������� �� ������ ��� �������� �� ���������� 
    @Override
    protected void onResume() {
    	super.onResume();
    	game.SetPauseMusic(false);
    	TextView countView = (TextView)findViewById(R.id.level_text_view);
    	Button but = (Button)findViewById(R.id.level_button);
        // ���������� ���������� ����� � ��������
    	countView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Ukrainian-Play.ttf"));
    	if(!game.GetLevelAccess(checkedLevelIndex)) {
    		countView.setText("������� " + Integer.toString(checkedLevelIndex));
    		but.setBackgroundResource(R.drawable.level_lock);
    	}
    	else {
    		int drawableId = getResources().getIdentifier("level" + Integer.toString(checkedLevelIndex), "drawable", getApplicationContext().getPackageName());
    		countView.setText(Integer.toString(game.AnswersCount(checkedLevelIndex)) + " �� " + Integer.toString(game.GetLevel(checkedLevelIndex).GetDitloidsCount()));
        	but.setBackgroundResource(drawableId);
    	}
    }

	
	static public void SetGame(Game _game) {
		game = _game;
	}


	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}

}
