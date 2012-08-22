package com.ditloids;

import android.R.drawable;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;

public class OptionsActivity extends Activity implements OnClickListener, OnKeyListener {
	/** Called when the activity is first created. */
	private static Game game = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Устанавливаем фон
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
		// Устанавливаем обработчики событий
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.sfxButton).setOnClickListener(this);
		findViewById(R.id.resetButton).setOnClickListener(this);
		findViewById(R.id.musicButton).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// На экран уровня
	    	game.SaveMuteSound();
	    	game.SaveMuteMusic();
	    	startActivity(new Intent(OptionsActivity.this, MainActivity.class));
	    	finish();
	    	break;
	    case R.id.sfxButton:
	    	Button sfxButton = (Button)findViewById(R.id.sfxButton);
	    	if(game.GetMuteSound()){
	    		game.SetMuteSound(false);
	    		sfxButton.setBackgroundResource(R.drawable.sfx_off);
	    	}else{
	    		game.SetMuteSound(true);
	    		sfxButton.setBackgroundResource(R.drawable.sfx_on);	    		
	    	}
	    	break;
	    case R.id.musicButton:
	    	Button musicButton = (Button)findViewById(R.id.musicButton);
	    	if(game.GetMuteMusic()){
	    		game.SetMuteMusic(false);
	    		musicButton.setBackgroundResource(R.drawable.music_off);
	    	}else{
	    		game.SetMuteMusic(true);
	    		musicButton.setBackgroundResource(R.drawable.music_on);	    		
	    	}
	    	break;
	    case R.id.resetButton:
	    	game.ClearAllSettings();
	    	break;
	    default:
	    	break;
	    }
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// Если нажата хардварная кнопка назад
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	// На экран уровня
	    	game.SaveMuteSound();
	    	game.SaveMuteMusic();
	    	startActivity(new Intent(OptionsActivity.this, MainActivity.class));
	    	finish();
	    } else {
	        return super.onKeyDown(keyCode, event);
	    }
		return false;
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig); 
   }

}
