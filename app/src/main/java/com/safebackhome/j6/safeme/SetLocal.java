package com.safebackhome.j6.safeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yunjisung on 2015. 10. 25..
 */
public class SetLocal extends Activity implements View.OnClickListener {
    //DBManagerHandler handler;
    String name;
    EditText set_name;
    DBManagerHandler handler;
    AlertDialog.Builder aDialog;
    public AlertDialog ad;
    Button cancel;
    Button next;
    RadioButton icon_home;
    RadioButton icon_office;
    Boolean home_ok;
    AutoCompleteTextView find_gu_tv;
    QuoteBank mQuoteBank;
    String call_gu;
    private List<String> mLines;
    public static final String mPath = "db_subway.txt";
    static ArrayList<String> sub_st = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;
    TextView set_gu;
    TextView set_gu_text;
    Button call;
    int icon;
    String station;
    String s_gu;
    ImageView subway;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_two );
        subway=(ImageView)findViewById(R.id.subway);
        handler = new DBManagerHandler(getApplicationContext());
       if(handler.getCountByTableName("quick")>=3) {
           Toast.makeText(getApplicationContext(),"목적지 수는 최대 3개만 가능합니다.",Toast.LENGTH_SHORT).show();
           this.finish();
       }// mContext = getApplicationContext();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.local_popup, (ViewGroup) findViewById(R.id.localpopupid));
        aDialog = new AlertDialog.Builder(this);

        //aDialog.setTitle("목적지 등록"); //타이틀바 제목
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
        set_name = (EditText) layout.findViewById(R.id.set_name);
        icon_home = (RadioButton) layout.findViewById(R.id.icon_home);
        icon_office = (RadioButton) layout.findViewById(R.id.icon_office);

        icon_home.setOnClickListener(this);
        icon_office.setOnClickListener(this);
        cancel = (Button) layout.findViewById(R.id.cancel);
        next = (Button) layout.findViewById(R.id.next);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);


        ad = aDialog.create();
        ad.show();
        Button find = (Button) findViewById(R.id.find_gu_btn_by_name);
        //Log.d("station_ok",String.valueOf(station_ok));
        find_gu_tv = (AutoCompleteTextView) findViewById(R.id.find_gu_ed);
        mQuoteBank = new QuoteBank(this);
        mLines = mQuoteBank.readLine(mPath);
        for (String string : mLines) {
            String[] sp = string.split("/");
            //andler.insert2("subway", sp[0], sp[1], sp[2]);
            sub_st.add(sp[1]);

        }

        String[] arrStr;
        arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
        find_gu_tv.setAdapter(adapter);

        // auto make text


        listView = (ListView) findViewById(R.id.listView);
        //Button change=(Button)findViewById(R.id.change);
        //change.setOnClickListener(mPagerListener);
        call = (Button) findViewById(R.id.call);
        set_gu = (TextView) findViewById(R.id.set_gu);
        set_gu_text = (TextView) findViewById(R.id.set_gu_text);
        final Context c = getApplicationContext();

        call.setOnClickListener(this);
        find.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.icon_home:
                home_ok = true;
                icon_office.setActivated(false);
                break;
            case R.id.icon_office:
                home_ok = false;
                break;
            case R.id.cancel:
                this.finish();
                break;
            case R.id.next:
                if (set_name.getText() != null) {
                    name = set_name.getText().toString();
                    ad.dismiss();
                }
                break;
            case R.id.call:
                if(home_ok)
                    icon=0;
                else
                icon=1;

              handler.insert_quick("quick",name,icon,station,s_gu,call_gu);
                Intent intentSubActivity =
                        new Intent(SetLocal.this, EditLocal.class);
                startActivity(intentSubActivity);
                break;
            case R.id.find_gu_btn_by_name:
                station = find_gu_tv.getText().toString();
                Log.d("station", station);
                Cursor cursor = handler.select2("subway", station);
//                                Boolean a=cursor.moveToNext();
//                                Log.d("yes",String.valueOf(a));
                //String gu = cursor.getString(cursor.getColumnIndex("gu"));
                if (!cursor.moveToNext()) {
                    set_gu.setText("검색결과가 없습니다");
                } else {
                    subway.setBackgroundResource(R.drawable.subway);
                   s_gu = cursor.getString(cursor.getColumnIndex("gu"));
                    Cursor cursor2 = handler.select("phone", s_gu);
                    cursor = handler.select("subway", s_gu);
                    call_gu = cursor2.getString(cursor2.getColumnIndex("number"));
                    set_gu.setText(s_gu);
                    int num_c = cursor.getCount();
                    String gu_text = String.valueOf(num_c) + "개 역이 검색되었습니다";
                    set_gu_text.setText(gu_text);
                    call.setBackgroundResource(R.drawable.setlocal);

                    MyCursorAdapter2 myadapter = new MyCursorAdapter2(getApplicationContext(), cursor);

                    //  CustomAdapter custom = new CustomAdapter(getApplicationContext(), 0, sub_st);
                    listView.setAdapter(myadapter);
                    break;
                }
        }

    }
}