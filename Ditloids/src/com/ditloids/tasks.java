package com.ditloids;

import android.app.Activity;
import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;

public class tasks extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task);
		ListView listView = (ListView) findViewById(R.id.mylist);
		
		String[] values = new String[] { "0 � �", "1 � � �", "1 � � � �", "3 � � �", "4 � �", "4 � �",
				"5 � � � �", "7 � � �", "8 � � �", "9 � �", "10 � � �", "12 � �", "14 � � �", "24 � � �", "29 � � � � � �", "52 � � �", "60 � � �",
				"60 � � �", "90 � � � �", "1917 � � �" };

//		String[] number =  new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
//				"18", "19", "20" };	
			

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.task_item, R.id.label, values);
		listView.setAdapter(adapter);
		
		
//		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//				R.layout.task_item, R.id.number, number);		
//		listView.setAdapter(adapter1);
		
	
		
		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Toast.makeText(getApplicationContext(),
//						"Click ListItem Number " + position, Toast.LENGTH_LONG)
//						.show();
//			}
//		});
	}
}