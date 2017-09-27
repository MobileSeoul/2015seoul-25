package com.safebackhome.j6.safeme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by yunjisung on 2015. 10. 30..
 */
public class CustomAdapter extends ArrayAdapter<String> {
    private ArrayList<String> items;
    Context context;
    public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects){
        super(context,textViewResourceId,objects);
        this.items=objects;
        this.context=context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if(v == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_item,parent, false);
        }
//        ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
//        TextView textView = (TextView)v.findViewById(R.id.textView);
//        textView.setText(items.get(position));

        return v;
    }
}
