package com.ditloids;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {
	private static Game game = null;
	private static BitmapDrawable bmd = null;
	private static Bitmap bm = null;
	private BillingService billingService = null;
	
    private static final int ERROR = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        setContentView(R.layout.main);
    	bm = BitmapFactory.decodeResource(getResources(), R.drawable.fon);
    	bmd = new BitmapDrawable(getResources(), bm);
    	View v = findViewById(R.id.mainLayout);
    	v.setBackgroundDrawable(bmd);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        findViewById(R.id.StartButton).setOnClickListener(this);
        findViewById(R.id.RulesButton).setOnClickListener(this);
        findViewById(R.id.SettingsButton).setOnClickListener(this);
        findViewById(R.id.NoAdsButton).setOnClickListener(this);
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
    	// Создаем сервис покупки
        billingService  = new BillingService();
        billingService.setContext(this);
        if(game.IsPurchase()) {
        	LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
	    	Button noadsButton = (Button)findViewById(R.id.NoAdsButton);
	    	mainLayout.removeView(noadsButton);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case ERROR:
            return createDialog(R.string.cannot_connect_title,
                    R.string.cannot_connect_message);
		default:
            return null;
        }
    }
    
    private Dialog createDialog(int titleId, int messageId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleId);
        builder.setIcon(android.R.drawable.stat_sys_warning);
        builder.setMessage(messageId);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, null);
        return builder.create();
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
	    case R.id.NoAdsButton:
            if ( billingService.requestPurchase("", Consts.ITEM_TYPE_INAPP, null) ) {
            	game.SetPurchase();
            	LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
            	Button noadsButton = (Button)findViewById(R.id.NoAdsButton);
            	mainLayout.removeView(noadsButton);
            }
            else 
            	onCreateDialog(ERROR);
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