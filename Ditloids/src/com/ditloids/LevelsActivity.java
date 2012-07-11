//package com.ditloids;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.widget.Button;
//
//public class LevelsActivity extends Activity implements OnClickListener, OnTouchListener {
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.levels);
//        Button arrowButton = (Button)findViewById(R.id.arrowButton);
//        arrowButton.setOnClickListener(this);
//        arrowButton.setOnTouchListener(this);
//    }
//
//	@Override
//	public boolean onTouch(View view, MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void onClick(View view) {
//		switch (view.getId()) {
//	    case R.id.arrowButton:
//	    	Intent intent = new Intent(this, MainActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//	    	startActivity(intent);    	
//	    	break;
//	    default:
//	    	break;
//	    }
//	}
//}
//
//
