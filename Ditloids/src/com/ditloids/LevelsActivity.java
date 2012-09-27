package com.ditloids;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

/**
 * A more complex demo including using a RadioGroup as "tabs" for the pager and showing the
 * dual-scrolling capabilities when a vertically scrollable element is nested inside the pager.
 */
public class LevelsActivity extends Activity implements OnClickListener, OnKeyListener {
    private RadioGroup radioGroup = null;
	private HorizontalPager pager = null;
	// Массив текстовых полей уровней
	private TextView[] countViews = null;
	// Массив кнопок перехода на уровни
	private Button[] countButtons = null;
	// Индекс текущего уровня на экране
	private int checkedLevelIndex = -1;
	private static Game game = null;
	// Диалог
	private AlertDialog.Builder adb = null;
	// Признак выполнения create
	private boolean create = false;
	
	// Обработчик листания экрана
	private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener =
            new HorizontalPager.OnScreenSwitchListener() {
                @Override
                public void onScreenSwitched(final int screen) {
                	int id = getResources().getIdentifier("radio_btn_" + Integer.toString(screen), "id", getApplicationContext().getPackageName());
                	radioGroup.check(id);
                	checkedLevelIndex = screen + 1;
                }
            };
    
    // Обработчик клика по радиобуттонам
    private final RadioGroup.OnCheckedChangeListener onCheckedChangedListener =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                	for(int i = 0; i < game.GetCountLevels(); i++){
                		int id = getResources().getIdentifier("radio_btn_" + Integer.toString(i), "id", getApplicationContext().getPackageName());
                		if (id == checkedId){
                			pager.setCurrentScreen(i, true);
                			checkedLevelIndex = i + 1;
                			break;
                		}
                	}
                }
            };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        radioGroup = (RadioGroup) findViewById(R.id.tabs);
        radioGroup.setOnCheckedChangeListener(onCheckedChangedListener);
        pager = (HorizontalPager) findViewById(R.id.horizontal_pager);
        pager.setOnScreenSwitchListener(onScreenSwitchListener);
        // Построение диалога
        adb = new AlertDialog.Builder(this);
        // Иконка диалога
        adb.setIcon(android.R.drawable.ic_dialog_info);
        // Кнопка положительного ответа диалога
        adb.setPositiveButton(R.string.yes, null);
        // Создаем диалог
        adb.create();
        countViews = new TextView[game.GetCountLevels()];
        countButtons = new Button[game.GetCountLevels()];
        // Заполняем массивы конпок уровней и надписей на уровнях
        for(int i = 1; i < game.GetCountLevels() + 1; i++){
        	int id = getResources().getIdentifier("TextView" + Integer.toString(i), "id", getApplicationContext().getPackageName());
        	TextView countView = (TextView)findViewById(id);
        	int idb = getResources().getIdentifier("level" + Integer.toString(i) + "button", "id", getApplicationContext().getPackageName());
        	Button but = (Button)findViewById(idb);
        	countViews[i-1] = countView;
        	countButtons[i-1] = but;
        };
        // Выставляем обработчики событий
    	for(int i = 1; i < game.GetCountLevels() + 1; i++){
    		int id = getResources().getIdentifier("level" + Integer.toString(i) +"button", "id", getApplicationContext().getPackageName());
    		findViewById(id).setOnClickListener(this);
    	}
        findViewById(R.id.arrowButton).setOnClickListener(this);
        findViewById(R.id.arrowButton).bringToFront();
    }
    
    
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

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// Если нажата хардварная кнопка назад
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN) {
	    	// На главный экран
	    	finish();
			return true;
	    } else {
	        return false;
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
    
    // Обновление отображения информации об уровнях при возврате на активность 
    @Override
    protected void onStart() {
    	super.onStart();
    	if (!create) {
    		create = true;
    	}
    	else {
    		game.SaveLevel();
    	}
        // Выставляем правильные текст и картинки
        for(int i = 1; i < game.GetCountLevels() + 1; i++){
        	countViews[i-1].setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Ukrainian-Play.ttf"));
        	countViews[i-1].setText(Integer.toString(game.AnswersCount(i)) + " из " + Integer.toString(game.GetLevel(i).GetDitloidsCount()));
        	if(!game.GetLevelAccess(i)) {
        		countButtons[i-1].setBackgroundResource(R.drawable.level_lock);
        	}
        }
    }

	
	static public void SetGame(Game _game){
		game = _game;
	}
}
