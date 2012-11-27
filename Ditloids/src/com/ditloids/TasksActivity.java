package com.ditloids;

import java.util.ArrayDeque;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toast;

public class TasksActivity extends Activity implements OnClickListener, OnItemClickListener, OnKeyListener {
	/** Called when the activity is first created. */
	static private Game game = null;
	private static BitmapDrawable bmd = null;
	// ������� ���������� ��������� � ������� �� ������������ � ������
	private Integer[] ditloidIndexes = null;
	
	static public void SetGame(Game _game) {
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task);
    	View v = findViewById(R.id.tasksLayout);
    	v.setBackgroundDrawable(bmd);
		ListView listView = (ListView) findViewById(R.id.mylist);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// ������������� ����������� �������
		findViewById(R.id.arrowButton).setOnClickListener(this);
		listView.setOnItemClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ListView listView = (ListView) findViewById(R.id.mylist);
		// ������ ������� �������
		Level currentLevel = game.GetCurrentLevel();
		// ���������� ����� ������ � �������
		((TextView)findViewById(R.id.textView1)).setText("������� "+Integer.toString(currentLevel.GetLevelIndex()));
		// ��������� ���������� ��������� � �������
		((TextView)findViewById(R.id.textView2)).setText(Integer.toString(game.GetCountHints()));
		// ��������� ������ ��������� � ������ �� �������� (� ������� "������� ������������ - ����� ����������")
		ArrayDeque<String> ans = new ArrayDeque<String>();
		ArrayDeque<Integer> ansIndexes = new ArrayDeque<Integer>();
		int notAnswered = 0;
		for(int i = currentLevel.GetDitloidsCount()-1; i > -1; i--){
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
		// ���������� �������� �� �����
		TaskArrayAdapter ansadapter = new TaskArrayAdapter(this, values, ditloidIndexes, game, notAnswered);
		listView.setAdapter(ansadapter);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// ��������� �������
	    	game.SaveLevel();
	    	// �� ����� ������ ������
	    	finish();
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// ������������� ������ �������� �������� �� ������� ������
		game.SetCurrentDitloidIndex(ditloidIndexes[arg2].intValue());
		// �� ����� ����� ������
    	startActivity(new Intent(TasksActivity.this, TaskActivity.class));
	}
	
    // ������������ ����������
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
    }
    
    // �������������� ����������
    @Override
    protected void onResume() {
        super.onResume();
        game.SetPauseMusic(false);
    }

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}
	
    public static void SetDrawable(BitmapDrawable _bmd){
    	bmd = _bmd;
    }

}