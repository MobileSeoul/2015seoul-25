package com.safebackhome.j6.safeme;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yunjisung on 2015. 10. 30..
 */
public class MyCursorAdapter_local extends CursorAdapter {
    @SuppressWarnings("deprecation")
    DBManagerHandler handler;
    Context context;
//    String name;
//    int icon;
//    String station;
//    String gu;
//    String phone;
//    int id;
    public MyCursorAdapter_local(Context context, Cursor c) {
        super(context, c);
        this.context=context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        handler = new DBManagerHandler(context);
        TextView tName = (TextView) view.findViewById( R.id.home_tv );
        ImageView image = (ImageView)view.findViewById(R.id.home_ic);
        TextView tStation = (TextView) view.findViewById( R.id.home_sub_tv );
        TextView tGu = (TextView)view.findViewById(R.id.home_ward_tv);
        ImageView image1=(ImageView)view.findViewById(R.id.home_ward);
        ImageView image2=(ImageView)view.findViewById(R.id.home_sub_img);
        //TextView tPhone= (TextView)view.findViewById(R.id.phone);
        Button call = (Button)view.findViewById(R.id.call_home);
        final int id=cursor.getInt(cursor.getColumnIndex("_id"));
        final String name=cursor.getString(cursor.getColumnIndex("name"));
        image1.setBackgroundResource(R.drawable.add);
        image2.setBackgroundResource(R.drawable.subway);
        int icon=cursor.getInt(cursor.getColumnIndex("icon"));
        if(icon==0)
            icon=R.drawable.home;
        else if(icon==1)
            icon=R.drawable.office;

        final String station=cursor.getString(cursor.getColumnIndex("station"));
        final String gu=cursor.getString(cursor.getColumnIndex("gu"));
        //fianl String phone=cursor.getString(cursor.getColumnIndex("phone"));
        //final Context context1=context;
        final Context context1=context;
        //Log.d("스트링 확인", name + ", " + station);

        tName.setText( name );
        call.setBackgroundResource(R.drawable.delete);
        image.setBackgroundResource(icon);
        tStation.setText(station);
        tGu.setText(gu);

       // tGu.setText( gu );
        final Cursor oldCursor =getCursor();
        //tPhone.setText(phone);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do things Here
                Log.d("delete", name);
                handler.deleteByTableName("quick", id);
                //MyCursorAdapter.getCursor();
                //notifyDataSetChanged();
              //  MyCursorAdapter_local.swapCursor(cursor_update);
//                startActivity(new Intent("android.intent.action.DIAL", Uri
//                        .parse("tel:" + phone + "")));
            //삭제되야함

//                try {
//                    Intent intent = new Intent(Intent.ACTION_DIAL);
//                    intent.setData(Uri.parse("tel:" + phone));
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context1.startActivity(intent);
//                } catch (Exception e) {
//                    Log.e("전화걸기", "전화걸기에 실패했습니다.", e);
//                }
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.edit_local_item, parent, false );
        return v;
    }



}
