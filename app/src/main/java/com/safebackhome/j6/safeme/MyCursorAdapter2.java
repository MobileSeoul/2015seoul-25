package com.safebackhome.j6.safeme;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by yunjisung on 2015. 10. 30..
 */
public class MyCursorAdapter2 extends CursorAdapter {
    @SuppressWarnings("deprecation")
    //DBManagerHandler handler;
    //Context context;
    String line;
    String station;
  //  String gu;

    public MyCursorAdapter2(Context context, Cursor c) {
        super(context, c);
        Log.d("스트링 확인",context.toString());
      //  this.context=context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tLine = (TextView) view.findViewById( R.id.find_line );
        TextView tStation = (TextView) view.findViewById( R.id.find_station );
       // TextView tGu = (TextView)view.findViewById(R.id.home_ward_tv);
        //TextView tPhone= (TextView)view.findViewById(R.id.phone);
        //Button call = (Button)view.findViewById(R.id.call_home);
        line=cursor.getString(cursor.getColumnIndex("line"));
        station=cursor.getString(cursor.getColumnIndex("station"));
       // final Context context1=context;
        Log.d("스트링 확인", line + ", " + station);

        tLine.setText( line );
        tStation.setText(station);

        //tPhone.setText(phone);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.listview_item, parent, false );
        return v;
    }



}
