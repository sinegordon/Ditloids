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
	// Индекс текущего уровня на экране
	private int checkedLevelIndex = 1;
	private static Game game = null;
	// Диалог
	private AlertDialog.Builder adb = null;
        

	@Override
    public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
    	Button but = (Button)findViewById(R.id.level_button);
        game.SetPauseMusic(false);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        radioGroup = (RadioGroup) findViewById(R.id.tabs);

        // Построение диалога
        adb = new AlertDialog.Builder(this);
        // Иконка диалога
        adb.setIcon(android.R.drawable.ic_dialog_info);
        // Кнопка положительного ответа диалога
        adb.setPositiveButton(R.string.yes, null);
        // Создаем диалог
        adb.create();   

        // Выставляем обработчик события кнопок
    	but.setOnClickListener(LevelsActivity.this);
        findViewById(R.id.arrowButton).setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        findViewById(R.id.arrowButton).bringToFront();
        
    }
	
    // Обработчик клика по радиобуттонам
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
		// Если кнопка назад
	    case R.id.arrowButton:
	    	finish();
	    	break;
	    default:
	    	// Если что-то другое (проверяем не нажата ли кнопка перехода на уровень)
	    	for(int i = 1; i < game.GetCountLevels() + 1; i++){
	    		int id = getResources().getIdentifier("level" + Integer.toString(i) +"button", "id", getApplicationContext().getPackageName());
	    		// Еcли нажата
	    		if (id == view.getId()){
	    			// Проверяем доступность уровня
	    			checkedLevelIndex = i;
	    	    	if(game.GetLevelAccess(i)){
	    	    		// Если доступен - переходим
	    		    	game.LoadLevel(i);
	    		    	startActivity(new Intent(LevelsActivity.this, TasksActivity.class));
	    		    	//finish();
	    		    	break;
	    	    	}
	    	    	else{
	    	    		// Если недоступен - сообщаем пользователю сколько нужно еще решить для его открытия
	    	    		adb.setMessage("Для доступа к этому уровню осталось ответить на " + 
	    			    		  Integer.toString(game.GetLevelsDivisor()*(checkedLevelIndex - 1) - game.GetCountRight()) + " дитлоид(а,ов).");
	    		    	adb.show();
	    	    	}
	    		}
	    	}
	    	break;
	    }
	}
	
	// Запрет поворота экрана
    @Override
	public void onConfigurationChanged(Configuration newConfig) {  
    	super.onConfigurationChanged(newConfig);  
	}

    // Пауза медиа-плеера при сворачивании приложения
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
    }
    
    // Снять паузу медиа-плеера при возврате на активность  
    // Обновить отображение информации об уровне при возврате на активность 
    @Override
    protected void onResume() {
    	super.onResume();
    	game.SetPauseMusic(false);
    	TextView countView = (TextView)findViewById(R.id.level_text_view);
    	Button but = (Button)findViewById(R.id.level_button);
        // Выставляем правильные текст и картинки
    	countView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Ukrainian-Play.ttf"));
    	if(!game.GetLevelAccess(checkedLevelIndex)) {
    		countView.setText("Уровень " + Integer.toString(checkedLevelIndex));
    		but.setBackgroundResource(R.drawable.level_lock);
    	}
    	else {
    		int drawableId = getResources().getIdentifier("level" + Integer.toString(checkedLevelIndex), "drawable", getApplicationContext().getPackageName());
    		countView.setText(Integer.toString(game.AnswersCount(checkedLevelIndex)) + " из " + Integer.toString(game.GetLevel(checkedLevelIndex).GetDitloidsCount()));
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
