package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
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
        //int memoryClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        //Log.d("sinegordon", Integer.toString(memoryClass));
    }

	@Override
	public void onClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
	    case R.id.StartButton:
	    	intent = new Intent(this, LevelsActivity.class);
	    	startActivity(intent);
	    	break;
	    case R.id.RulesButton:
	    	intent = new Intent(this, FaqActivity.class);
	    	startActivity(intent);
	    	break;
	    case R.id.SettingsButton:
	    	intent = new Intent(this, OptionsActivity.class);
	    	startActivity(intent);
	    	break;
	    default:
	    	break;
	    }	
	}

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