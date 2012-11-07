package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
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
	BitmapDrawable bmd = null;
	Bitmap bm = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entertask);
		((EditText)findViewById(R.id.editText1)).setLines(1);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
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
			String dit = currentLevel.GetDitloid(game.GetCurrentDitloidIndex());
			((TextView)findViewById(R.id.textView3)).setText(dit);
			// Показываем клавиатуру
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			//imm.showSoftInput(findViewById(R.id.editText1), InputMethodManager.SHOW_FORCED);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			// Показываем последний неправильный ответ, если он есть (при этом еще меняется фон на неотвеченный)
			String wrong_ans = game.GetLastWrongAnswer(game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
			if(!wrong_ans.equals("")){
				((EditText)findViewById(R.id.editText1)).setText(wrong_ans);
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
				// Ставим курсор в конец строки
				((EditText)findViewById(R.id.editText1)).setSelection(wrong_ans.length());
			}
			// Если не было неверных попыток, ставим начальную цифру дитлоида
			else {
				String[] dit_str = dit.split(" ");
				((EditText)findViewById(R.id.editText1)).setText(dit_str[0] + " ");
				// Ставим курсор в конец строки
				((EditText)findViewById(R.id.editText1)).setSelection((dit_str[0] + " ").length());
			}
		}
		else{
			// Заполняем поле дитлоида
			((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));	
			// Выставляем правильный ответ в поле ввода
			((EditText)findViewById(R.id.editText1)).setText(currentLevel.GetDitloidAnswer(game.GetCurrentDitloidIndex()));
			// Перекрываем возможность редактирования поля ввода
			((EditText)findViewById(R.id.editText1)).setKeyListener(null);
			// Меняем фон кнопки
			findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_right);
			// Убираем реакцию на нажатие кнопки
			findViewById(R.id.buttonCheck).setOnClickListener(null);
		};
		// Если на этот дитлоид уже взята подсказка
		if(game.GetHint(game.GetCurrentDitloidIndex())){
			// Открываем подсказку
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setText(game.GetCurrentLevel().GetDitloidHint(game.GetCurrentDitloidIndex()));		
		}else{
			// Закрываем подсказку
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.VISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.INVISIBLE);
		};	
	}
	
	@Override
	public void onClick(View view) {
		InputMethodManager imm = null;
		switch (view.getId()) {
	    case R.id.arrowButton:
			// Убираем клавиатуру
			imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
	    	// На экран уровня
	    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
	    	finish();
	    	break;
	    case R.id.buttonHint:
	    	// Если есть подсказки
	    	if(game.GetCountHints() > 0){
		    	// Уменьшаем количество доступных подсказок
		    	game.DecrementCountHints();
		    	// Отмечаем взятие подсказки на дитлоид
		    	game.SetHint(game.GetCurrentDitloidIndex(), true);
		    	// Меняем виджеты
		    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
		    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
		    	// Показываем подсказку
		    	((TextView)findViewById(R.id.textHint)).setText(game.GetCurrentLevel().GetDitloidHint(game.GetCurrentDitloidIndex()));
		    	// Показываем оставшееся количество подсказок
				((TextView)findViewById(R.id.textView2)).setText(Integer.toString(game.GetCountHints()));
	    	};
	    	break;
	    case R.id.buttonCheck:
			// Если ответ верный
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString())){
				game.PlaySound(1);
				// Перекрываем возможность редактирования поля ввода
				((EditText)findViewById(R.id.editText1)).setKeyListener(null);
				// Убираем клавиатуру
				imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
				// Меняем фон кнопки
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_right);
				// Убираем реакцию на нажатие кнопки
				findViewById(R.id.buttonCheck).setOnClickListener(null);
				// Показываем сообщение что верный ответ
				Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
				// Устанавливаем флаг верного ответа
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// Повышаем количество верных ответов
				game.IncrementCountRight();
				// Если это верный ответ кратный трем повышаем количество доступных подсказок
				if(game.GetCountRight() % game.GetHintsDivisor() == 0)
					game.IncrementCountHints();
				// Сохраняем уровень
				game.SaveLevel();
				// На экран уровня
		    	finish();
			}
			else{
				game.PlaySound(2);
				// Сохраняем последний неверный ответ
				game.SetLastWrongAnswer(((EditText)findViewById(R.id.editText1)).getText().toString().trim(), game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
				// Меняем фон кнопки
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
			};
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		InputMethodManager imm = null;
		// Если нажат Enter
		if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
			// Если ответ верный
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString().trim())){
				game.PlaySound(1);
				// Перекрываем возможность редактирования поля ввода
				((EditText)findViewById(R.id.editText1)).setKeyListener(null);
				// Убираем клавиатуру
				imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
				// Меняем фон кнопки
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_right);
				// Убираем реакцию на нажатие кнопки
				findViewById(R.id.buttonCheck).setOnClickListener(null);
				// Показываем сообщение что верный ответ
				Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
				// Устанавливаем флаг верного ответа
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// Повышаем количество верных ответов
				game.IncrementCountRight();
				// Если это верный ответ кратный трем повышаем количество доступных подсказок
				if(game.GetCountRight() % game.GetHintsDivisor() == 0)
					game.IncrementCountHints();
				// Сохраняем уровень
				game.SaveLevel();
				// На экран уровня
		    	finish();
			}
			else{
				game.PlaySound(2);
				String str = ((EditText)findViewById(R.id.editText1)).getText().toString().trim();
				((EditText)findViewById(R.id.editText1)).setText("");
				String strnew = "";
				for(int i = 0; i < str.length(); i++)
					strnew += str.charAt(i); 
				// Сохраняем последний неверный ответ
				game.SetLastWrongAnswer(strnew, game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
				// Меняем фон кнопки
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
				// Ставим неверный ответ
				((EditText)findViewById(R.id.editText1)).setText(strnew);
				// Ставим курсор в конец строки
				((EditText)findViewById(R.id.editText1)).setSelection(strnew.length());

			};
			return true;
		};
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
    	View v = findViewById(R.id.entertaskLayout);
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