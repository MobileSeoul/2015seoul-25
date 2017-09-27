package com.safebackhome.j6.safeme;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by yunjisung on 2015. 10. 30..
 */
public class MyCursorAdapter extends CursorAdapter {
    @SuppressWarnings("deprecation")
    DBManagerHandler handler;
    //Context context;
    String name;
    String station;
    String gu;
    String phone;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
      //  this.context=context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tName = (TextView) view.findViewById( R.id.home_tv );
        TextView tStation = (TextView) view.findViewById( R.id.home_sub_tv );
        TextView tGu = (TextView)view.findViewById(R.id.home_ward_tv);
        //TextView tPhone= (TextView)view.findViewById(R.id.phone);
        Button call = (Button)view.findViewById(R.id.call_home);
        name=cursor.getString(cursor.getColumnIndex("name"));
        station=cursor.getString(cursor.getColumnIndex("station"));
        gu=cursor.getString(cursor.getColumnIndex("gu"));
        phone=cursor.getString(cursor.getColumnIndex("phone"));
        final Context context1=context;
        Log.d("스트링 확인", name + ", " + station);

        tName.setText( name );
        tStation.setText(station);
        tGu.setText( gu );
        //tPhone.setText(phone);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do things Here
//                startActivity(new Intent("android.intent.action.DIAL", Uri
//                        .parse("tel:" + phone + "")));

                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phone));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context1.startActivity(intent);
                } catch (Exception e) {
                    Log.e("전화걸기", "전화걸기에 실패했습니다.", e);
                }
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list_view, parent, false );
        return v;
    }



}
