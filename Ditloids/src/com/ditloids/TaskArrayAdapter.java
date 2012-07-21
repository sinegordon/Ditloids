package com.ditloids;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class TaskArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final int notanscount;

    public TaskArrayAdapter(Context context, String[] values, int notanscount) {
        super(context, R.layout.task_item, values);
        this.context = context;
        this.values = values;
        this.notanscount = notanscount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        if(position >= notanscount)
        	textView.setBackgroundResource(R.drawable.bg_task2);
        textView.setText(values[position]);
        return rowView;
    }
}
