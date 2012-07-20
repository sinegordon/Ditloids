package com.ditloids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

public class TasksActivity extends Activity implements OnClickListener, OnItemClickListener {
	/** Called when the activity is first created. */
	private static Game game = null;
	
	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task);
		ListView listView = (ListView) findViewById(R.id.mylist);
		// ������ ������� �������
		Level currentLevel = game.GetCurrentLevel();
		// ���������� ����� ������ � �������
		((TextView)findViewById(R.id.textView1)).setText("������� "+Integer.toString(currentLevel.GetLevelIndex()));
		// ��������� ������ ���������
		String[] values = new String[currentLevel.GetDitloidsCount()];
		for(int i = 0; i < values.length; i++){
			values[i] = currentLevel.GetDitloid(i);
		}
		// ���������� �������� �� �����
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.task_item, R.id.label, values);
		// ������������� ����������� �������
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
		//((TextView)findViewById(R.id.textView1)).setText("����"+Integer.toString(arg2));
	}
	
}