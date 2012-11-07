package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FaqActivity extends Activity implements OnClickListener, OnKeyListener {
	private static Game game = null;
	BitmapDrawable bmd = null;
	Bitmap bm = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
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

	
    // Запрет поворота экрана
    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {  
        super.onConfigurationChanged(newConfig); 
    }*/
    

    // Сворачивание приложения
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
        bm.recycle();
    }
    
	// Прорисовка фона
	public void Draw() {
    	bm = BitmapFactory.decodeResource(getResources(), R.drawable.fon_header);
    	//LayoutInflater inf = LayoutInflater.from(getApplicationContext());
    	bmd = new BitmapDrawable(getResources(), bm);
    	View v = findViewById(R.id.faqLayout);
    	v.setBackgroundDrawable(bmd);
    }
    
    // Разворачивание приложения
    @Override
    protected void onResume() {
        super.onResume();
        game.SetPauseMusic(false);
        Draw();
    }

}