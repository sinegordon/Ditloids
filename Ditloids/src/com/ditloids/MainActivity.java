package com.ditloids;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.audiofx.AudioEffect.OnControlStatusChangeListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.app.ActivityManager;

public class MainActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static Game game = null;
	private static BitmapDrawable bmd = null;
	private static Bitmap bm = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        setContentView(R.layout.main);
    	bm = BitmapFactory.decodeResource(getResources(), R.drawable.fon);
    	bmd = new BitmapDrawable(getResources(), bm);
    	View v = findViewById(R.id.mainLayout);
    	v.setBackgroundDrawable(bmd);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        findViewById(R.id.StartButton).setOnClickListener(this);
        findViewById(R.id.RulesButton).setOnClickListener(this);
        findViewById(R.id.SettingsButton).setOnClickListener(this);
        if(game == null)
	       	try {
				game = new Game(getApplicationContext(), 4);
			} catch (Exception e) {
				e.printStackTrace();
			};
        TasksActivity.SetGame(game);
        LevelsActivity.SetGame(game);
        OptionsActivity.SetGame(game);
        TaskActivity.SetGame(game);
        FaqActivity.SetGame(game);
    	TasksActivity.SetDrawable(bmd);
    	LevelsActivity.SetDrawable(bmd);
    	OptionsActivity.SetDrawable(bmd);
    	TaskActivity.SetDrawable(bmd);
    	FaqActivity.SetDrawable(bmd);
        int memoryClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        Log.d("sinegordon", Integer.toString(memoryClass));
    }

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
	    case R.id.StartButton:
	    	intent = new Intent(this, LevelsActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    	startActivity(intent);
	    	break;
	    case R.id.RulesButton:
	    	intent = new Intent(this, FaqActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    	startActivity(intent);
	    	break;
	    case R.id.SettingsButton:
	    	intent = new Intent(this, OptionsActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	    	startActivity(intent);
	    	break;
	    default:
	    	break;
	    }	
	}

	// Запрет поворота экрана
    /*@Override
     public void onConfigurationChanged(Configuration newConfig) {  
         super.onConfigurationChanged(newConfig); 
    }*/
	
    // Пауза медиа-плеера при сворачивании приложения
    @Override
    protected void onPause() {
   		super.onPause();
        game.SetPauseMusic(true);
    }
    
    // Снять паузу медиа-плеера при разворачивании приложения
    @Override
    protected void onResume() {   	
        super.onResume();
        game.SetPauseMusic(false);
    }
}