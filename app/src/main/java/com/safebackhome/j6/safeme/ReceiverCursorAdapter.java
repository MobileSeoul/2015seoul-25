package com.safebackhome.j6.safeme;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2015-10-31.
 */
public class ReceiverCursorAdapter  extends CursorAdapter {
    @SuppressWarnings("deprecation")
    DBManagerHandler handler;
            //Context context
    String tvName_str;
    int cnt;
    //int
    //  String gu;

    public ReceiverCursorAdapter(Context context, Cursor c) {
        super(context, c);
        cnt=0;
        Log.d("스트링 확인", context.toString());
        handler = new DBManagerHandler(context);
        //  this.context=context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        cnt++;
        TextView tvName = (TextView) view.findViewById( R.id.receiver_item_name );
        TextView tvNumber = (TextView) view.findViewById( R.id.receiver_item_number );
        Button btDelete = (Button) view.findViewById(R.id.receiver_delete_button);
        tvName_str=cursor.getString(cursor.getColumnIndex("name"));
        final String tvNumber_str=cursor.getString(cursor.getColumnIndex("number"));
        // final Context context1=context;
        Log.d("스트링 확인", tvName_str + ", " + tvNumber_str);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.deleteByTableName2("MAN", "number", tvNumber_str);
            }
        });
        tvName.setText( tvName_str );
        tvNumber.setText(tvNumber_str);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.receiver_item, parent, false );
        return v;
    }



}