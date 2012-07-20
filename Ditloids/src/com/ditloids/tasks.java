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
		
		String[] values = new String[] { "0 Á Ï", "1 Ã ó Ö", "1 Í Ò Ä Ò", "3 Ñ â Ò", "4 Â Ã", "4 Ñ Ñ",
				"5 Î × ó ×", "7 Ä â Í", "8 Í ó Î", "9 Ê À", "10 Ï í Ð", "12 Ç Ç", "14 Ñ â Ñ", "24 × â Ñ", "29 Ä â Ô â Â Ã", "52 Ê â Ê", "60 Ì â ×",
				"60 Ñ â Ì", "90 Ã â Ï Ó", "1917 Â Î Ð" };

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