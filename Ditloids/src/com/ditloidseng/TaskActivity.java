package com.ditloidseng;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

public class TaskActivity extends Activity implements OnClickListener, OnKeyListener {
	// ������ �������
	final int DIALOG_EXIT = 1;
	
	protected Dialog onCreateDialog(int id) {
	    if (id == DIALOG_EXIT) {
	      AlertDialog.Builder adb = new AlertDialog.Builder(this);
	      // ���������
	      adb.setTitle(R.string.hint_title);
	      // ���������
	      adb.setMessage(R.string.hint_message);
	      // ������
	      adb.setIcon(android.R.drawable.ic_dialog_info);
	      // ������ �������������� ������
	      adb.setPositiveButton(R.string.yes, dialogClickListener);
	      // ������ ������������ ������
	      // adb.setNeutralButton(R.string.no, dialogClickListener);
	      // ������� ������
	      return adb.create();
	   }
	   return super.onCreateDialog(id);
	}
	
	android.content.DialogInterface.OnClickListener dialogClickListener = new android.content.DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which){
			switch (which) {
		    // ������������� ������
		    case Dialog.BUTTON_POSITIVE:/*
		    	// ������
		    	AlertDialog.Builder adb = null;		    	
		        // ���������� �������
		        adb = new AlertDialog.Builder(TaskActivity.this);
		        // ������ �������
		        adb.setIcon(android.R.drawable.ic_dialog_info);
		        adb.setPositiveButton(R.string.yes, null);
		        // ������� ������
		        adb.create();
	    		adb.setMessage("������ ��� ����������� �� ��������. ������� �� ������������ ���������.");
		    	adb.show();
		    	*/
		        break;
		    // ����������� ������  
		    case Dialog.BUTTON_NEUTRAL:
		    	break;
		    }
		}
	};
	// ����� ������ �������

	private static Game game = null;
	private static BitmapDrawable bmd = null;

	public static void SetGame(Game _game){
		game = _game;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entertask);
    	View v = findViewById(R.id.entertaskLayout);
    	v.setBackgroundDrawable(bmd);
		((EditText)findViewById(R.id.editText1)).setLines(1);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// ������ ������� �������
		Level currentLevel = game.GetCurrentLevel();
		// ���������� ����� ������ � �������
		((TextView)findViewById(R.id.textView1)).setText("Level "+Integer.toString(currentLevel.GetLevelIndex()));
		// ��������� ���������� ��������� � �������
		((TextView)findViewById(R.id.textView2)).setText(Integer.toString(game.GetCountHints()));

		// ���� ������� �� �������
		if(!game.GetAnswer(game.GetCurrentDitloidIndex())){
			// ��������� ���� ��������
			String dit = currentLevel.GetDitloid(game.GetCurrentDitloidIndex());
			((TextView)findViewById(R.id.textView3)).setText(dit);
			// ���������� ����������
			//InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			//imm.showSoftInput(findViewById(R.id.editText1), InputMethodManager.SHOW_FORCED);
			//imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
			// ���������� ��������� ������������ �����, ���� �� ���� (��� ���� ��� �������� ��� �� ������������)
			String wrong_ans = game.GetLastWrongAnswer(game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
			if(!wrong_ans.equals("")){
				((EditText)findViewById(R.id.editText1)).setText(wrong_ans);
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
				// ������ ������ � ����� ������
				((EditText)findViewById(R.id.editText1)).setSelection(wrong_ans.length());
			}
			// ���� �� ���� �������� �������, ������ ��������� ����� ��������
			else {
				String[] dit_str = dit.split(" ");
				((EditText)findViewById(R.id.editText1)).setText(dit_str[0] + " ");
				// ������ ������ � ����� ������
				((EditText)findViewById(R.id.editText1)).setSelection((dit_str[0] + " ").length());
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
		} else {
			// ��������� ���������
	    	((Button)findViewById(R.id.buttonHint)).setVisibility(View.VISIBLE);
	    	((TextView)findViewById(R.id.textHint)).setVisibility(View.INVISIBLE);
		};	
		// ������������� ����������� �������
		findViewById(R.id.buttonCheck).setOnClickListener(this);
		findViewById(R.id.arrowButton).setOnClickListener(this);
		findViewById(R.id.buttonHint).setOnClickListener(this);
		findViewById(R.id.editText1).setOnKeyListener(this);
	}
	
	@Override
	public void onClick(View view) {
		InputMethodManager imm = null;
		switch (view.getId()) {
	    case R.id.arrowButton:
	    	// �� ����� ������
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
	    	}
	    	else {
	    		showDialog(DIALOG_EXIT);
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
				Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
				// ������������� ���� ������� ������
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// �������� ���������� ������ �������
				game.IncrementCountRight();
				// ���� ��� ������ ����� ������� ���� �������� ���������� ��������� ���������
				if(game.GetCountRight() % game.GetHintsDivisor() == 0)
					game.IncrementCountHints();
				// ��������� �������
				game.SaveLevel();
				// �� ����� ������
		    	finish();
			}
			else{
				game.PlaySound(2);
				// ��������� ��������� �������� �����
				game.SetLastWrongAnswer(((EditText)findViewById(R.id.editText1)).getText().toString().trim(), game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
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
		InputMethodManager imm = null;
		// ���� ����� Enter
		if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
			// ���� ����� ������
			if(game.GetCurrentLevel().Verify(game.GetCurrentDitloidIndex(), ((EditText)findViewById(R.id.editText1)).getText().toString().trim())){
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
				Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
				// ������������� ���� ������� ������
				game.SetAnswer(game.GetCurrentDitloidIndex(), true);
				// �������� ���������� ������ �������
				game.IncrementCountRight();
				// ���� ��� ������ ����� ������� ���� �������� ���������� ��������� ���������
				if(game.GetCountRight() % game.GetHintsDivisor() == 0)
					game.IncrementCountHints();
				// ��������� �������
				game.SaveLevel();
				// �� ����� ������
		    	finish();
			}
			else{
				game.PlaySound(2);
				String str = ((EditText)findViewById(R.id.editText1)).getText().toString().trim();
				((EditText)findViewById(R.id.editText1)).setText("");
				String strnew = "";
				for(int i = 0; i < str.length(); i++)
					strnew += str.charAt(i); 
				// ��������� ��������� �������� �����
				game.SetLastWrongAnswer(strnew, game.GetCurrentLevel().GetLevelIndex(), game.GetCurrentDitloidIndex());
				// ������ ��� ������
				findViewById(R.id.buttonCheck).setBackgroundResource(R.drawable.check_wrong);
				// ������ �������� �����
				((EditText)findViewById(R.id.editText1)).setText(strnew);
				// ������ ������ � ����� ������
				((EditText)findViewById(R.id.editText1)).setSelection(strnew.length());

			};
			return true;
		};
	    return false;
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
    
    public static void SetDrawable(BitmapDrawable _bmd){
    	bmd = _bmd;
    }

}