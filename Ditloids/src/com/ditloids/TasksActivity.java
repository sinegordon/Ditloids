package com.ditloids;

import java.util.ArrayDeque;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

public class TasksActivity extends Activity implements OnClickListener, OnItemClickListener {
	/** Called when the activity is first created. */
	private static Game game = null;
	private Integer[] ditloidIndexes = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task);
		ListView listView = (ListView) findViewById(R.id.mylist);
		// Узнаем текущий уровень
		Level currentLevel = game.GetCurrentLevel();
		// Выставляем номер уровня в надписи
		((TextView)findViewById(R.id.textView1)).setText("Уровень "+Integer.toString(currentLevel.GetLevelIndex()));
		// Заполняем массив дитлоидов
		ArrayDeque<String> ans = new ArrayDeque<String>();
		ArrayDeque<Integer> ansIndexes = new ArrayDeque<Integer>();
		int notAnswered = 0;
		for(int i = 0; i < currentLevel.GetDitloidsCount(); i++){
			if(!game.GetAnswer(i)){
				ans.addFirst(currentLevel.GetDitloid(i));
				ansIndexes.addFirst(Integer.valueOf(i));
				notAnswered += 1;
			}
			else{
				ans.addLast(currentLevel.GetDitloid(i));
				ansIndexes.addLast(Integer.valueOf(i));
			}
		}
		String[] values = new String[ans.size()];
		ditloidIndexes = new Integer[ansIndexes.size()];
		ans.toArray(values);
		ansIndexes.toArray(ditloidIndexes);
		// Выставляем дитлоиды на экран
		TaskArrayAdapter ansadapter = new TaskArrayAdapter(this, values, notAnswered);
		listView.setAdapter(ansadapter);
		// Устанавливаем обработчики событий
		findViewById(R.id.arrowButton).setOnClickListener(this);
		listView.setOnItemClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	game.SaveLevel();
	    	startActivity(new Intent(TasksActivity.this, LevelsActivity.class));
	    	finish();
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//((TextView)findViewById(R.id.textView1)).setText("Клик"+Integer.toString(arg2));
		game.SetCurrentDitloidIndex(ditloidIndexes[arg2].intValue());
		TaskActivity.SetGame(game);
    	startActivity(new Intent(TasksActivity.this, TaskActivity.class));
    	finish();
	}
	
}