package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toast;

public class TaskActivity extends Activity implements OnClickListener, OnKeyListener {
	/** Called when the activity is first created. */
	private static Game game = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entertask);
    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.VISIBLE);
    	((TextView)findViewById(R.id.textHint)).setVisibility(View.INVISIBLE);
    	if(game.GetCountHints() == 0)
    		((Button)findViewById(R.id.buttonHint)).setEnabled(false);
		// Узнаем текущий уровень
		Level currentLevel = game.GetCurrentLevel();
		// Выставляем номер уровня в надписи
		((TextView)findViewById(R.id.textView1)).setText("Уровень "+Integer.toString(currentLevel.GetLevelIndex()));
		// Вставляем количество подсказок в надписи
		((TextView)findViewById(R.id.textView2)).setText(Integer.toString(game.GetCountHints()));
		// Заполняем поле дитлоида
		((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));
		// Показываем клавиатуру
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		//imm.showSoftInput(findViewById(R.id.editText1), InputMethodManager.SHOW_FORCED);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		// Устанавливаем обработчики событий
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.buttonHint).setOnClickListener(this);
		findViewById(R.id.editText1).setOnKeyListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// На экран уровня
	    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
	    	finish();
	    	break;
	    case R.id.buttonHint:
	    	// Уменьшаем количество доступных подсказок
	    	game.DecrementCountHints();
	    	// Меняем виджеты
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
	    	// Показываем подсказку
	    	((TextView)findViewById(R.id.textHint)).setText(game.GetCurrentLevel().GetDitloidHint(game.GetCurrentDitloidIndex()));
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// Если нажат Enter
		if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
			// Если ответ верный
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString())){
				// Показываем сообщение что верный ответ
				Toast.makeText(this, "Верно", Toast.LENGTH_LONG).show();
				// Устанавливаем флаг верного ответа
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// Повышаем количество верных ответов
				game.IncrementCountRight();
				// Если это верный ответ кратный трем повышаем количество доступных подсказок
				if(game.GetCountRight() % 3 == 0)
					game.IncrementCountHints();
				TasksActivity.SetGame(game);
				// На экран уровня
		    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
		    	finish();
			}
			else{
				// Показываем сообщение что неверный ответ
				Toast.makeText(this, "Не верно", Toast.LENGTH_LONG).show();
			};
			return true;
		};
		return false;
	}

    @Override
	public void onConfigurationChanged(Configuration newConfig) {  
    	super.onConfigurationChanged(newConfig);  
	}
}