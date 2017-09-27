package com.safebackhome.j6.safeme;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by yunjisung on 2015. 10. 25..
 */
public class SetCall extends Activity {
    EditText smsNumber;
    Button Phone_list;
    String number;
    String name;
    File dir = Environment.getExternalStorageDirectory();
    String filePath = dir.getAbsolutePath() + "/number.txt";
    DBManagerHandler handler;
    ListView receiverListView;


    int current_receiver_cnt;
    TextView receiver_tv;
    RelativeLayout init;
    Button Phone_list2;
    RelativeLayout delete;
    Button NotInitAdd;
    RelativeLayout receiverList;

    Button delete_test; //test_delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_call);
        handler = new DBManagerHandler(getApplicationContext());
        Phone_list=(Button)findViewById(R.id.picture_receiver_button);
        Phone_list.setOnClickListener(mClickListener);
        Phone_list2=(Button)findViewById(R.id.add_text_button);
        Phone_list2.setOnClickListener(mClickListener);
        //not_init_add_button
        NotInitAdd=(Button)findViewById(R.id.not_init_add_button);
        NotInitAdd.setOnClickListener(mClickListener);
        //receiver_tv=(TextView) findViewById(R.id.receiver_cnt);
        current_receiver_cnt=handler.getCountByTableName("MAN");
        init = (RelativeLayout) this.findViewById(R.id.init_add);
        delete = (RelativeLayout) this.findViewById(R.id.delete_receiver_layout);
        receiverList = (RelativeLayout) this.findViewById(R.id.receiver_list_layout);
        receiverListView = (ListView) this.findViewById(R.id.receiver_list_lv);

        // INIT VIEW
        if(current_receiver_cnt==0) {
            init.setVisibility(RelativeLayout.VISIBLE);
            delete.setVisibility(RelativeLayout.GONE);
            receiverList.setVisibility(RelativeLayout.GONE);
        }
        else// NOT INIT
        {
            init.setVisibility(RelativeLayout.GONE);
            delete.setVisibility(RelativeLayout.VISIBLE);
            receiverList.setVisibility(RelativeLayout.VISIBLE);
            updateReceiverList();
        }
    }//VISIBLE
    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.picture_receiver_button:
                case R.id.add_text_button:
                case R.id.not_init_add_button:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, 0);
                    break;
            }
        }
    };
    public void updateReceiverList()
    {
        Cursor cursor =  handler.select_quick("MAN");
        ReceiverCursorAdapter rca = new ReceiverCursorAdapter(this, cursor);
        receiverListView.setAdapter(rca);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            number = cursor.getString(1);   //1은 번호를 받아옵니다.
            handler.insert3("MAN", name, number);
            updateReceiverList();
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
        //지정인번호를 계속 저장해둬야 하므로 DB대신 txt파일에 저장
//
//        try {
//            OutputStream out = null;
//            out = new FileOutputStream(filePath);
//            out.write(number.getBytes());
//            Toast.makeText(this, "지정인이 등록되었습니다", Toast.LENGTH_SHORT).show();
//
//
//            out.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//
//        }



    }
    public void sendSMS(View v){
        Cursor phone_cursor = handler.select_quick("man");
        //String smsNum = smsNumber.getText().toString();
        //String smsText = smsTextContext.getText().toString();
        String smsText="000씨가 0역근처에서 위험에 처해있습니다!! ";

        while (phone_cursor.moveToNext()) {
                    String smsNum = phone_cursor.getString(phone_cursor
                            .getColumnIndex("name"));
                    Log.d("test", smsNum);
            if (smsNum.length()>0 && smsText.length()>0){
                sendSMS(smsNum, smsText);
            }else{
                Toast.makeText(this, "지정인이 없습니다!", Toast.LENGTH_SHORT).show();
            }
                }

    }
    public void sendSMS(String smsNumber, String smsText){

        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(context, "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(context, "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(context, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }
}
