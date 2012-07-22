package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
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
		// Узнаем текущий уровень
		Level currentLevel = game.GetCurrentLevel();
		// Выставляем номер уровня в надписи
		((TextView)findViewById(R.id.textView1)).setText("Уровень "+Integer.toString(currentLevel.GetLevelIndex()));
		// Заполняем поле дитлоида
		((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));
		// Устанавливаем обработчики событий
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.buttonHint).setOnClickListener(this);
		findViewById(R.id.editText1).setOnKeyListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
	    	finish();
	    	break;
	    case R.id.buttonHint:
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setText("Здесь скоро будет Ваша подсказка ;)");
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_ENTER){
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString())){
				Toast.makeText(this, "Верно", Toast.LENGTH_LONG).show();
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				TasksActivity.SetGame(game);
		    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
		    	finish();
			}
			else{
				Toast.makeText(this, "Не верно", Toast.LENGTH_LONG).show();
			};
			return true;
		};
		return false;
	}

}