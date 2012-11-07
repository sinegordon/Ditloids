package com.ditloids;

import android.R.drawable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;

public class OptionsActivity extends Activity implements OnClickListener, OnKeyListener {
	// ������ �������
	final int DIALOG_EXIT = 1;
	BitmapDrawable bmd = null;
	Bitmap bm = null;
	
	protected Dialog onCreateDialog(int id) {
	    if (id == DIALOG_EXIT) {
	      AlertDialog.Builder adb = new AlertDialog.Builder(this);
	      // ���������
	      adb.setTitle(R.string.reset_title);
	      // ���������
	      adb.setMessage(R.string.reset_message);
	      // ������
	      adb.setIcon(android.R.drawable.ic_dialog_info);
	      // ������ �������������� ������
	      adb.setPositiveButton(R.string.yes, resetClickListener);
	      // ������ ������������ ������
	      adb.setNeutralButton(R.string.cancel, resetClickListener);
	      // ������� ������
	      return adb.create();
	   }
	   return super.onCreateDialog(id);
	}
	
	android.content.DialogInterface.OnClickListener resetClickListener = new android.content.DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which){
			switch (which) {
		    // ������������� ������
		    case Dialog.BUTTON_POSITIVE:
		    	// ������� ��� ���������
		    	game.ClearProgress();
		    	// ������������� ���� ������ �� ������
		    	// Button sfxButton = (Button)findViewById(R.id.sfxButton);
		    	// Button musicButton = (Button)findViewById(R.id.musicButton);
		    	// sfxButton.setBackgroundResource(R.drawable.sfx_off);
		    	// musicButton.setBackgroundResource(R.drawable.music_off);
		    	break;
		    // ����������� ������  
		    case Dialog.BUTTON_NEUTRAL:
		    	break;
		    }
		}
	};
	// ����� ������ �������
	
	private static Game game = null;
		
	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // ������������� ���� ������ �� ������
    	Button sfxButton = (Button)findViewById(R.id.sfxButton);
    	Button musicButton = (Button)findViewById(R.id.musicButton);
        if(game.GetMuteSound()){
    		sfxButton.setBackgroundResource(R.drawable.sfx_on);
    	}else{
    		sfxButton.setBackgroundResource(R.drawable.sfx_off);	    		
    	}
    	if(game.GetMuteMusic()){
    		musicButton.setBackgroundResource(R.drawable.music_on);
    	}else{
    		musicButton.setBackgroundResource(R.drawable.music_off);	    		
    	}
		// ������������� ����������� �������
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.sfxButton).setOnClickListener(this);
		findViewById(R.id.resetButton).setOnClickListener(this);
		findViewById(R.id.musicButton).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		Button sfxButton = null;
		Button musicButton = null;
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// �� ������� �����
	    	finish();
	    	break;
	    case R.id.sfxButton:
	    	sfxButton = (Button)findViewById(R.id.sfxButton);
	    	if(game.GetMuteSound()){
	    		game.SetMuteSound(false);
	    		game.SaveMuteSound();
	    		sfxButton.setBackgroundResource(R.drawable.sfx_off);
	    	}else{
	    		game.SetMuteSound(true);
	    		game.SaveMuteMusic();
	    		sfxButton.setBackgroundResource(R.drawable.sfx_on);	    		
	    	}
	    	break;
	    case R.id.musicButton:
	    	musicButton = (Button)findViewById(R.id.musicButton);
	    	if(game.GetMuteMusic()){
	    		game.SetMuteMusic(false);
	    		musicButton.setBackgroundResource(R.drawable.music_off);
	    	}else{
	    		game.SetMuteMusic(true);
	    		musicButton.setBackgroundResource(R.drawable.music_on);	    		
	    	}
	    	break;
	    case R.id.resetButton:
	    	showDialog(DIALOG_EXIT);
	    	break;
	    default:
	    	break;
	    }
	}
		
	/*@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// ���� ������ ���������� ������ �����
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN) {
	    	// �� ������� �����
	    	finish();
			return super.onKeyDown(keyCode, event);
	    } else {
	        return false;
	    }
	}*/

	// ������ �������� ������
	/*@Override
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig); 
   }*/
	
    //������������ ����������
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
        bm.recycle();
    }
    
	// ���������� ����
	public void Draw() {
    	bm = BitmapFactory.decodeResource(getResources(), R.drawable.fon_header);
    	//LayoutInflater inf = LayoutInflater.from(getApplicationContext());
    	bmd = new BitmapDrawable(getResources(), bm);
    	View v = findViewById(R.id.optionsLayout);
    	v.setBackgroundDrawable(bmd);
    }
    
    // �������������� ����������
    @Override
    protected void onResume() {
        super.onResume();
        game.SetPauseMusic(false);
        Draw();
    }

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}


}
