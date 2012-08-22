package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FaqActivity extends Activity implements OnClickListener, OnKeyListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// На экран уровня
	    	startActivity(new Intent(FaqActivity.this, MainActivity.class));
	    	finish();
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
	    	startActivity(new Intent(FaqActivity.this, MainActivity.class));
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