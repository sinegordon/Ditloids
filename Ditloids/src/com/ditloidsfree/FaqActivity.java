package com.ditloidsfree;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

public class FaqActivity extends Activity implements OnClickListener, OnKeyListener {
	private static Game game = null;
	private static BitmapDrawable bmd = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
    	View v = findViewById(R.id.faqLayout);
    	v.setBackgroundDrawable(bmd);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		findViewById(R.id.arrowButton).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// На экран уровня
	    	finish();
	    	break;
	    default:
	    	break;
	    }
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}
   
    // Сворачивание приложения
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
    }
    
    // Разворачивание приложения
    @Override
    protected void onResume() {
        super.onResume();
        game.SetPauseMusic(false);
    }
    
    public static void SetDrawable(BitmapDrawable _bmd){
    	bmd = _bmd;
    }

}