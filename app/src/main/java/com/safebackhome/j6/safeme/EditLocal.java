package com.safebackhome.j6.safeme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


/**
 * Created by yunjisung on 2015. 10. 31..
 */
public class EditLocal extends Activity implements View.OnClickListener {
    ListView listview;
    DBManagerHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_local);
        Button add=(Button)findViewById(R.id.add);
        handler = new DBManagerHandler(getApplicationContext());
        add.setOnClickListener(this);
        listview=(ListView)findViewById(R.id.listview);
        Cursor cursor=handler.select_quick("quick");
        RelativeLayout init = (RelativeLayout)findViewById(R.id.init_add);
        LinearLayout desti=(LinearLayout)findViewById(R.id.desti);
        Button picture_receiver_button=(Button)findViewById(R.id.picture_receiver_button);
        picture_receiver_button.setOnClickListener(this);
        //Button add_text_button=(Button)findViewById(R.id.add_text_button);
        //add_text_button.setOnClickListener(this);
        // INIT VIEW
       // LinearLayout addd=(LinearLayout)findViewById(R.id.addd);
        int current_receiver_cnt=handler.getCountByTableName("quick");

        if(current_receiver_cnt==0) {
            init.setVisibility(RelativeLayout.VISIBLE);
            desti.setVisibility(LinearLayout.GONE);

        }
        else// NOT INIT
        {
            init.setVisibility(RelativeLayout.GONE);
            desti.setVisibility(LinearLayout.VISIBLE);


        MyCursorAdapter_local adapter = new MyCursorAdapter_local(getApplicationContext(), cursor);
            listview.setAdapter(adapter);
           // adapter.requery();
            //adapter.swapCursor(cursor);
            adapter.getCursor().requery();
            adapter.notifyDataSetChanged();

        //    listview.setAdapter(tagCursorAdapter);
           ;
            //Cursor cursor_update = handler.select_quick("quick");

        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                Intent intentSubActivity =
                        new Intent(EditLocal.this, SetLocal.class);
                startActivity(intentSubActivity);
                break;
            case R.id.picture_receiver_button:
                Intent intentSubActivity2 =
                        new Intent(EditLocal.this, SetLocal.class);
                startActivity(intentSubActivity2);
                break;
            case R.id.add_text_button:
                Intent intentSubActivity3 =
                        new Intent(EditLocal.this, SetLocal.class);
                startActivity(intentSubActivity3);

                break;
        }
    }
}
