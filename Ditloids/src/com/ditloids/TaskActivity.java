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
		// Устанавливаем обработчики событий
		findViewById(R.id.buttonCheck).setOnClickListener(this);
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.buttonHint).setOnClickListener(this);
		findViewById(R.id.editText1).setOnKeyListener(this);
		// Если дитлоид не отгадан
		if(!game.GetAnswer(game.GetCurrentDitloidIndex())){
			// Заполняем поле дитлоида
			((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));
			// Показываем клавиатуру
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.showSoftInput(findViewById(R.id.editText1), InputMethodManager.SHOW_FORCED);
		}
		else{
			// Заполняем поле дитлоида
			((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));	
			// Выставляем правильный ответ в поле ввода
			((EditText)findViewById(R.id.editText1)).setText(currentLevel.GetDitloidAnswer(game.GetCurrentDitloidIndex()));
			// Перекрываем возможность редактирования поля ввода
			((EditText)findViewById(R.id.editText1)).setKeyListener(null);
		}
		//imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// На экран уровня
	    	TasksActivity.SetGame(game);
	    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
	    	finish();
	    	break;
	    case R.id.buttonHint:
	    	// Если есть подсказки
	    	if(game.GetCountHints() > 0){
		    	// Умен      ьшаем количество доступных подсказок
		    	game.DecrementCountHints();
		    	// Меняем виджеты
		    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
		    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
		    	// Показываем подсказку
		    	((TextView)findViewById(R.id.textHint)).setText(game.GetCurrentLevel().GetDitloidHint(game.GetCurrentDitloidIndex()));
	    	};
	    	break;
	    case R.id.buttonCheck:
			// Если ответ верный
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString())){
				// Перекрываем возможность редактирования поля ввода
				((EditText)findViewById(R.id.editText1)).setKeyListener(null);
				// Убираем клавиатуру
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(findViewById(R.id.editText1).getWindowToken(), 0);
				// Меняем фон кнопки
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_right);
				// Меняем надпись на кнопке
				((Button)findViewById(R.id.buttonCheck)).setText("Верно!");
				// Показываем сообщение что верный ответ
				//Toast.makeText(this, "Верно", Toast.LENGTH_LONG).show();
				// Устанавливаем флаг верного ответа
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// Повышаем количество верных ответов
				game.IncrementCountRight();
				// Если это верный ответ кратный трем повышаем количество доступных подсказок
				if(game.GetCountRight() % game.GetDivisor() == 0)
					game.IncrementCountHints();
				//TasksActivity.SetGame(game);
				// На экран уровня
		    	//startActivity(new Intent(TaskActivity.this, TasksActivity.class));
		    	//finish();
			}
			else{
				// Меняем фон кнопки
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
				// Показываем сообщение что неверный ответ
				//Toast.makeText(this, "Не верно", Toast.LENGTH_LONG).show();
			};
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		/*
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
				if(game.GetCountRight() % game.GetDivisor() == 0)
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
		*/
		// Если нажата хардварная кнопка назад
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	// На экран уровня
	    	TasksActivity.SetGame(game);
	    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
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