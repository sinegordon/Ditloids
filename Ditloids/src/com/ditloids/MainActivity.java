package com.ditloids;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.audiofx.AudioEffect.OnControlStatusChangeListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private static Game game = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        setContentView(R.layout.main);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        findViewById(R.id.StartButton).setOnClickListener(this);
        findViewById(R.id.RulesButton).setOnClickListener(this);
        findViewById(R.id.SettingsButton).setOnClickListener(this);
        if(game == null)
	       	try {
				game = new Game(getApplicationContext(), 4);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        TasksActivity.SetGame(game);
        LevelsActivity.SetGame(game);
        OptionsActivity.SetGame(game);
        TaskActivity.SetGame(game);
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
    @Override
     public void onConfigurationChanged(Configuration newConfig) {  
         super.onConfigurationChanged(newConfig); 
    }
}