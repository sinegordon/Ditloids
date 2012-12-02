package com.ditloidseng;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class TaskArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final Integer[] ditloidIndexes;
    private final Game game;
    private final int notanscount;
   

    public TaskArrayAdapter(Context context, String[] values, Integer[] ditloidIndexes, Game game, int notanscount) {
        super(context, R.layout.task_item, values);
        this.context = context;
        this.values = values;
        this.game = game;
        this.ditloidIndexes = ditloidIndexes;
        //  оличество еще не отвеченных на этом уровне
        this.notanscount = notanscount;
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        // ≈сли отвеченный, ставим другой фон
        if(position >= notanscount)
        	textView.setBackgroundResource(R.drawable.bg_task_check);
        else{
        	// ≈сли была неверна€ попытка ответа ставим другой фон 
        	String str = game.GetLastWrongAnswer(game.GetCurrentLevel().GetLevelIndex(), ditloidIndexes[position].intValue());
        	if(!str.equals(""))
        		textView.setBackgroundResource(R.drawable.bg_task_wrong);
        }
        textView.setText(values[position]);
        return rowView;
    }
}
