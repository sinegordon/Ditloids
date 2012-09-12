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
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    	if(game.GetCountHints() == 0)
    		((Button)findViewById(R.id.buttonHint)).setEnabled(false);
		// ������ ������� �������
		Level currentLevel = game.GetCurrentLevel();
		// ���������� ����� ������ � �������
		((TextView)findViewById(R.id.textView1)).setText("������� "+Integer.toString(currentLevel.GetLevelIndex()));
		// ��������� ���������� ��������� � �������
		((TextView)findViewById(R.id.textView2)).setText(Integer.toString(game.GetCountHints()));
		// ������������� ����������� �������
		findViewById(R.id.buttonCheck).setOnClickListener(this);
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.buttonHint).setOnClickListener(this);
		findViewById(R.id.editText1).setOnKeyListener(this);
		// ���� ������� �� �������
		if(!game.GetAnswer(game.GetCurrentDitloidIndex())){
			// ��������� ���� ��������
			((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));
			// ���������� ����������
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			//imm.showSoftInput(findViewById(R.id.editText1), InputMethodManager.SHOW_IMPLICIT);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			// ���������� ��������� ������������ �����, ���� �� ���� (��� ���� ��� �������� ��� �� ������������)
			String wrong_ans = game.GetLastWrongAnswer(game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
			if(!wrong_ans.equals("")){
				((EditText)findViewById(R.id.editText1)).setText(wrong_ans);
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
			}
		}
		else{
			// ��������� ���� ��������
			((TextView)findViewById(R.id.textView3)).setText(currentLevel.GetDitloid(game.GetCurrentDitloidIndex()));	
			// ���������� ���������� ����� � ���� �����
			((EditText)findViewById(R.id.editText1)).setText(currentLevel.GetDitloidAnswer(game.GetCurrentDitloidIndex()));
			// ����������� ����������� �������������� ���� �����
			((EditText)findViewById(R.id.editText1)).setKeyListener(null);
			// ������ ��� ������
			findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_right);
			// ������� ������� �� ������� ������
			findViewById(R.id.buttonCheck).setOnClickListener(null);
		};
		// ���� �� ���� ������� ��� ����� ���������
		if(game.GetHint(game.GetCurrentDitloidIndex())){
			// ��������� ���������
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setText(game.GetCurrentLevel().GetDitloidHint(game.GetCurrentDitloidIndex()));		
		}else{
			// ��������� ���������
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.VISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.INVISIBLE);
		};	
	}
	
	@Override
	public void onClick(View view) {
		InputMethodManager imm = null;
		switch (view.getId()) {
	    case R.id.arrowButton:
			// ������� ����������
			imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
	    	// �� ����� ������
	    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
	    	finish();
	    	break;
	    case R.id.buttonHint:
	    	// ���� ���� ���������
	    	if(game.GetCountHints() > 0){
		    	// ��������� ���������� ��������� ���������
		    	game.DecrementCountHints();
		    	// �������� ������ ��������� �� �������
		    	game.SetHint(game.GetCurrentDitloidIndex(), true);
		    	// ������ �������
		    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.INVISIBLE);
		    	((TextView)findViewById(R.id.textHint)).setVisibility(View.VISIBLE);
		    	// ���������� ���������
		    	((TextView)findViewById(R.id.textHint)).setText(game.GetCurrentLevel().GetDitloidHint(game.GetCurrentDitloidIndex()));
		    	// ���������� ���������� ���������� ���������
				((TextView)findViewById(R.id.textView2)).setText(Integer.toString(game.GetCountHints()));
	    	};
	    	break;
	    case R.id.buttonCheck:
			// ���� ����� ������
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString())){
				game.PlaySound(1);
				// ����������� ����������� �������������� ���� �����
				((EditText)findViewById(R.id.editText1)).setKeyListener(null);
				// ������� ����������
				imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
				// ������ ��� ������
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_right);
				// ������� ������� �� ������� ������
				findViewById(R.id.buttonCheck).setOnClickListener(null);
				// ���������� ��������� ��� ������ �����
				Toast.makeText(this, "�����", Toast.LENGTH_LONG).show();
				// ������������� ���� ������� ������
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// �������� ���������� ������ �������
				game.IncrementCountRight();
				// ���� ��� ������ ����� ������� ���� �������� ���������� ��������� ���������
				if(game.GetCountRight() % game.GetHintsDivisor() == 0)
					game.IncrementCountHints();
				// �� ����� ������
		    	//startActivity(new Intent(TaskActivity.this, TasksActivity.class));
		    	finish();
			}
			else{
				game.PlaySound(2);
				// ��������� ��������� �������� �����
				game.SetLastWrongAnswer(((EditText)findViewById(R.id.editText1)).getText().toString(), game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
				// ������ ��� ������
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
			};
	    	break;
	    default:
	    	break;
	    }
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		/*
		// ���� ����� Enter
		if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
			// ���� ����� ������
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString())){
				// ���������� ��������� ��� ������ �����
				Toast.makeText(this, "�����", Toast.LENGTH_LONG).show();
				// ������������� ���� ������� ������
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// �������� ���������� ������ �������
				game.IncrementCountRight();
				// ���� ��� ������ ����� ������� ���� �������� ���������� ��������� ���������
				if(game.GetCountRight() % game.GetDivisor() == 0)
					game.IncrementCountHints();
				TasksActivity.SetGame(game);
				// �� ����� ������
		    	startActivity(new Intent(TaskActivity.this, TasksActivity.class));
		    	finish();
			}
			else{
				// ���������� ��������� ��� �������� �����
				Toast.makeText(this, "�� �����", Toast.LENGTH_LONG).show();
			};
			return true;
		};
		*/
		// ���� ������ ���������� ������ �����
	    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN) {
			// ������� ����������
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
	    	// �� ����� ������
	    	//startActivity(new Intent(TaskActivity.this, TasksActivity.class));
	    	finish();
			return super.onKeyDown(keyCode, event);
	    } else {
	        return false;
	    }

	}

	// ������ �������� ������
    @Override
	public void onConfigurationChanged(Configuration newConfig) {  
    	super.onConfigurationChanged(newConfig);  
	}
    
    // ����� �����-������ ��� ������������ ����������
    @Override
    protected void onPause() {
        super.onPause();
        game.SetPauseMusic(true);
    }
    
    // ����� ����� �����-������ ��� �������������� ����������
    @Override
    protected void onResume() {
        super.onResume();
        game.SetPauseMusic(false);
    }

}