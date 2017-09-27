package com.safebackhome.j6.safeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String first="";

    private ListView m_ListView;
    //private ArrayAdapter<Person> m_Adapter;
    MyCursorAdapter myAdapter;
    static ArrayList<String> sub_st = new ArrayList<String>();
    static ArrayList<String> sub_gu = new ArrayList<String>();
    public static final String mPath = "db_subway.txt";
    private QuoteBank mQuoteBank;
    private List<String> mLines;
    DBManagerHandler handler;
    ImageView fab;
    private ViewPager mPager;
    TextView title;
    SQLiteDatabase db;
    private Button alarm;
    private MediaPlayer mp;
    private AudioManager am=null;
    int StreamType=0;
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView find_gu_tv;
    private ListView listView;
    String call_gu;
    private double cur_lat;
    private double cur_lon;
    private GPSTracker gps;
    private StationXMLParser mXMLParser;
    private CNM_XMLParser cnmXMLParser;
    boolean isCNM;
    double cur_x;
    double cur_y;
    String cnm_str;
    String phone1;
    String phone2;
    String phone3;
    AlertDialog.Builder aDialog;
    AlertDialog.Builder sDialog;
    AlertDialog select;
    Boolean station_ok=true;
    String station_list;
    ImageView photoView;
    EditText smsNumber;
    Button Phone_list;
    String number;
    Long photo;
    String[] arrStr;
    File dir = Environment.getExternalStorageDirectory();
    String filePath = dir.getAbsolutePath() + "/number.txt";
    public TextView tv;
    public AlertDialog ad;
    boolean flag=true;
    Context mContext;
    Counter c;
    TextView alarm_text;
    TextView alarm_text1;
    TextView alarm_text2;
    boolean send_text=false;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setLayout();

        //requestGPS();
        title=(TextView)findViewById(R.id.title_tv);
        addListenerOnButton();
        handler = new DBManagerHandler(getApplicationContext());

        if(!handler.getCountByTableName1("phone")) {
            handler.insert("phone", "종로구", "0221481111");
            handler.insert("phone", "중구", "0233964001");
            handler.insert("phone", "용산구", "0221996300");
            handler.insert("phone", "성동구", "0222866262");
            handler.insert("phone", "광진구", "024501330");
            handler.insert("phone", "동대문구", "0221274000");
            handler.insert("phone", "중랑구", "0220940114");
            handler.insert("phone", "성북구", "029203000");
            handler.insert("phone", "강북구", "029016112");
            handler.insert("phone", "도봉구", "0220913109");
            handler.insert("phone", "노원구", "0221163300");
            handler.insert("phone", "은평구", "023518000");
            handler.insert("phone", "서대문구", "023301119");
            handler.insert("phone", "마포구", "0231538104");
            handler.insert("phone", "양천구", "0226203399");
            handler.insert("phone", "강서구", "0226001280");
            handler.insert("phone", "구로구", "028602330");
            handler.insert("phone", "금천구", "0226272414");
            handler.insert("phone", "영등포구", "0226704070");
            handler.insert("phone", "동작구", "028201040");
            handler.insert("phone", "관악구", "028797640");
            handler.insert("phone", "서초구", "0221558510");
            handler.insert("phone", "강남구", "0234236000");
            handler.insert("phone", "송파구", "0221472799");
            handler.insert("phone", "강동구", "0234255009");

            mQuoteBank = new QuoteBank(this);
            mLines = mQuoteBank.readLine(mPath);
            for (String string : mLines) {
                String[] sp = string.split("/");
                Log.d("station", sp[0]);
                handler.insert2("subway", sp[0], sp[1], sp[2]);
                sub_st.add(sp[1]);

            }

        }else {
            mQuoteBank = new QuoteBank(this);
            mLines = mQuoteBank.readLine(mPath);
            for (String string : mLines) {
                String[] sp = string.split("/");
                //andler.insert2("subway", sp[0], sp[1], sp[2]);

                sub_st.add(sp[1]);

            }
        }
        sub_gu.add("종로구");
        sub_gu.add("중구");
        sub_gu.add("용산구");
        sub_gu.add("성동구");
        sub_gu.add("광진구");
        sub_gu.add("동대문구");
        sub_gu.add("중랑구");
        sub_gu.add("성북구");
        sub_gu.add("강북구");
        sub_gu.add("도봉구");
        sub_gu.add("노원구");
        sub_gu.add("은평구");
        sub_gu.add("서대문구");
        sub_gu.add("마포구");
        sub_gu.add("양천구");
        sub_gu.add("강서구");
        sub_gu.add("구로구");
        sub_gu.add("금천구");
        sub_gu.add("영등포구");
        sub_gu.add("동작구");
        sub_gu.add("관악구");
        sub_gu.add("서초구");
        sub_gu.add("강남구");
        sub_gu.add("송파구");
        sub_gu.add("강동구");

        mp = MediaPlayer.create(this, R.raw.aa);
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));

        handler.close();



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    public void addListenerOnButton() {
        //사이렌버튼
        int StreamType = 0;
        //  alarm = (Button) findViewById(R.id.btn_siren);
        //사이렌 울리는 버튼 클릭이벤트
        fab = (ImageButton) findViewById(R.id.btn_siren);
        fab.setBackgroundResource(R.drawable.btn_siren);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (arg0.getId() == R.id.btn_siren) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.naver.com"));
                    //startActivity(browserIntent);

                    // 소리 조절
                    am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    // 소리 크기 50/
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,50, AudioManager.FLAG_PLAY_SOUND);
                    mp.start();
                    mContext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                    View layout = inflater.inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popupid));
                    aDialog = new AlertDialog.Builder(MainActivity.this);

                    //aDialog.setTitle("title"); //타이틀바 제목
                    aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
                    tv = (TextView) layout.findViewById(R.id.TimeText);
                    alarm_text=(TextView)layout.findViewById(R.id.alarm_text1);
                    alarm_text1=(TextView)layout.findViewById(R.id.alarm_text2);
                    alarm_text2=(TextView)layout.findViewById(R.id.alarm_text3);
                    send=(Button)layout.findViewById(R.id.send);
                    send.setOnClickListener(mPagerListener);
                    //그냥 닫기버튼을 위한 부분
                    Button close=(Button)layout.findViewById(R.id.close);
                    close.setOnClickListener(mPagerListener);

//                    aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            mp.stop();
//                            //requestGPS();
//
//                        }
//                    });

                    ad = aDialog.create();
                    ad.show();

                    c= new Counter();
                    c.start();


                }
            }
        });

    }
    class Counter extends Thread {


        @Override
        public void run() { // 핸들러로 카운터를 보낸다.

            int count = 0;
            flag=true;
            while(flag) {
                mHandler.sendEmptyMessage(count);
//                if ( count == 6) {
//                    //ad.dismiss();
//                    //flag=false;
//                   // alarm_text.setText("");
//                }

                count++;

                SystemClock.sleep(1000L);
            }

        }
    }
    final Handler mHandler = new Handler() { //핸들러를 통해 UI스레드에 접근한다.

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what!=6) {
                tv.setText(5 - msg.what + "");

            }else
            {
                if(!send_text){
                    alarm_text.setText("신고가 접수되었습니다");
                    alarm_text1.setText("경보음을 끄고싶으시면");
                    alarm_text2.setText("취소하기 버튼을 눌러주세요.");
                    requestGPS();
                    tv.setVisibility(TextView.GONE);
                }
            }

        }



    };

    protected void onDestroy(){
        super.onDestroy();

        if(mp!=null){
            mp.release();
        }

        // am.setStreamVolume(AudioManager.STREAM_MUSIC, 0,AudioManager.FLAG_PLAY_SOUND);
    }

    // Request Input 이 함수를 부르면 위치정보 가져옴!!!!
    public void requestGPS()    {
        // create class object
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            convertWGS84toCNM(longitude, latitude);
            // \n is for new line
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    // Convert to CNM
    public void convertWGS84toCNM(double x, double y)    {
        int tmp_x=(int)(x*1000000);
        int tmp_y=(int)(y*1000000);
        final double posX=((double)tmp_x)/1000000;
        final double posY=((double)tmp_y)/1000000;
        String url = "https://apis.daum.net/local/geo/transcoord?apikey=e2824aa5f065d79fcccd2718889409cc&fromCoord=WGS84&y="+posY+"&x="+posX+"&toCoord=CONGNAMUL&output=xml";
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                ArrayList<CNMDatas> dataList = cnmXMLParser.getResult();
                int dataListSize = dataList.size();
                Log.d("data List Size", Integer.toString(dataListSize));
                for(int i=0; i<dataListSize; i++){
                    Log.d("XML Parsing Result", "Result" + ">> "+ i);
                    double transX=Double.parseDouble(dataList.get(i).getX());
                    double transY=Double.parseDouble(dataList.get(i).getY());
                    cur_x=transX/2.5;
                    cur_y=transY/2.5;
                    cnm_str = cnm_str + "\n X: "+transX/2.5+" ,\n Y: "+ transY/2.5;
                    //Toast.makeText(getApplicationContext(), cnm_str, Toast.LENGTH_LONG).show();
                    //requestS
                    requestStation();
                }
                //stat.setText(cnm_str);
            }
        };
        cnmXMLParser = new CNM_XMLParser(url,mHandler);
        //cnmXMLParser.run();
        //여기서 잠시 주석

        Thread thread = new Thread(cnmXMLParser);
        thread.start();
//        try{thread.join();}catch (InterruptedException e){}
        // Station List
        //  stat.setText(station_list);
    }
    // Display View
    public void requestStation()     {
        final double posX=cur_x;
        final double posY=cur_y;
        if(posX==0.0 || posY==0.0 )
        {
            Toast.makeText(getApplicationContext(), "Not Setting CNM", Toast.LENGTH_LONG).show();
            return;
        }
        String url = "http://swopenapi.seoul.go.kr/api/subway/636a79646768616e39316e6a536a53/xml/nearBy/1/5/"+posX+"/"+posY;
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                ArrayList<StationDatas> dataList = mXMLParser.getResult();
                int dataListSize = dataList.size();
                Log.d("data List Size", Integer.toString(dataListSize));
                station_list="";
                for(int i=0; i<dataListSize; i++){
                    if(i==3) break;
                    Log.d("XML Parsing Result", "Result" + ">> "+ i);
                    double endX=Double.parseDouble(dataList.get(i).getSubX());
                    double endY=Double.parseDouble(dataList.get(i).getSubY());
                    station_list = station_list +dataList.get(i).getStatnNm()+"("+dataList.get(i).getSubwayNm()+")"
                            +", "+distance(posX,posY,endX,endY)+"m";
                    if(i<2) station_list = station_list+"\n";
                }
                Toast.makeText(getApplicationContext(),station_list,Toast.LENGTH_SHORT).show();
                sendSMS(station_list);

                //station의 정보가 station_list에 담겨있다. --> 문자로 보내야됨.
                //stat.setText(station_list);



            }
        };
        mXMLParser = new StationXMLParser(url,mHandler);
        Thread thread = new Thread(mXMLParser);
        thread.start();
//        try{thread.join();
//            }catch (InterruptedException e){}
        // Station List
        //  stat.setText(station_list);
    }
    public String distance(double startPointX,double startPointY, double endPointX, double endPointY)    {
        double result=((startPointX-endPointX)*(startPointX-endPointX))
                +((startPointY-endPointY)*(startPointY-endPointY));
        result = Math.sqrt(result);
        int mid_result=(int)(result*100);
        result = ((double)mid_result)/100;
        if(result>1000.0)
        {
            int tmp=(int)(result/10);
            result=((double)tmp)/100;
            return result+"k";
        }
        return result+"";
    }

    //화면아래에 있는 버튼
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quick_request_btn://quick전송
                setCurrentInflateItem(0);
                quick_btn.setBackgroundResource(R.drawable.quick_request_active);
                find_btn.setBackgroundResource(R.drawable.find_gu_in_active);
                sett_btn.setBackgroundResource(R.drawable.setting_in_active);
                title.setText("안심귀가 신청");
                break;
            case R.id.find_gu_btn://지정 전송
                setCurrentInflateItem(1);
                quick_btn.setBackgroundResource(R.drawable.quick_request_in_active);
                find_btn.setBackgroundResource(R.drawable.find_gu_active);
                sett_btn.setBackgroundResource(R.drawable.setting_in_active);
                title.setText("동네찾기");
                break;
            case R.id.setting_btn://설정
                setCurrentInflateItem(2);
                quick_btn.setBackgroundResource(R.drawable.quick_request_in_active);
                find_btn.setBackgroundResource(R.drawable.find_gu_in_active);
                sett_btn.setBackgroundResource(R.drawable.setting_active);
                title.setText("설정");
                break;
//            case R.id.setlocal:
//                setCurrentInflateItem(3);
//                break;
//        }
        }
    }

    private void setCurrentInflateItem(int type){
        if(type==0){
            mPager.setCurrentItem(0);
        }else if(type==1){
            mPager.setCurrentItem(1);
        }else if(type==2){
            mPager.setCurrentItem(2);
        }else if(type==3) {
            //Toast.makeText(getApplicationContext(),"흠",Toast.LENGTH_SHORT).show();
            mPager.setCurrentItem(3);
        }else if(type==4) {
            //Toast.makeText(getApplicationContext(),"흠",Toast.LENGTH_SHORT).show();
            mPager.setCurrentItem(4);
        }else if(type==5) {
            //Toast.makeText(getApplicationContext(),"흠",Toast.LENGTH_SHORT).show();
            mPager.setCurrentItem(5);
        }
    }

    private Button quick_btn;
    private Button find_btn;
    private Button sett_btn;

    /*
     * Layout
     */
    private void setLayout(){
        quick_btn = (Button) findViewById(R.id.quick_request_btn);
        find_btn = (Button) findViewById(R.id.find_gu_btn);
        sett_btn = (Button) findViewById(R.id.setting_btn);

        quick_btn.setOnClickListener(this);
        find_btn.setOnClickListener(this);
        sett_btn.setOnClickListener(this);
    }

    View.OnClickListener mPagerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){

                //page1에서 버튼 클릭했을 때 전화걸기
                case R.id.call_home:
//                    String station1 = "노원역";
//                Cursor subway_cursor1 = handler.select2("subway", station1);
//                while (subway_cursor1.moveToNext()) {
//                    first = subway_cursor1.getString(subway_cursor1
//                            .getColumnIndex("gu"));
//                }
//                String number1="";
//                Cursor phone_cursor1 = handler.select("phone",first);
//                while (phone_cursor1.moveToNext()) {
//                    number1 = phone_cursor1.getString((phone_cursor1
//                            .getColumnIndex("number")));
//                }
                    startActivity(new Intent("android.intent.action.DIAL", Uri
                            .parse("tel:" + phone1 + "")));
                    break;
                case R.id.call_one:
//                    String station2 = "종각역";
//                    Cursor subway_cursor2 = handler.select2("subway", station2);
//                while (subway_cursor2.moveToNext()) {
//                    first = subway_cursor2.getString(subway_cursor2
//                            .getColumnIndex("gu"));
//                }
//                String number2="";
//                Cursor phone_cursor2 = handler.select("phone",first);
//                while (phone_cursor2.moveToNext()) {
//                    number2 = phone_cursor2.getString((phone_cursor2
//                            .getColumnIndex("number")));
//                }
                    startActivity(new Intent("android.intent.action.DIAL", Uri
                            .parse("tel:" + phone2 + "")));
                    break;
                case R.id.call_two:
//                    String station3 = "강남역";
//                   Cursor subway_cursor3 = handler.select2("subway", station3);
//                while (subway_cursor3.moveToNext()) {
//                    first = subway_cursor3.getString(subway_cursor3
//                            .getColumnIndex("gu"));
//                }
//                String number3="";
//                Cursor phone_cursor3 = handler.select("phone",first);
//                while (phone_cursor3.moveToNext()) {
//                    number3 = phone_cursor3.getString((phone_cursor3
//                            .getColumnIndex("number")));
//                }
                    startActivity(new Intent("android.intent.action.DIAL", Uri
                            .parse("tel:" + phone3 + "")));
                    break;
//                case R.id.find_gu_btn_by_name:
//
//
//                    break;
                case R.id.setlocal:
                    //Toast.makeText(getApplicationContext(),"버튼눌림",Toast.LENGTH_SHORT).show();
                    Intent intentSubActivity =
                            new Intent(MainActivity.this, EditLocal.class);
                    startActivity(intentSubActivity);
                    //setCurrentInflateItem(3);
                    break;

                case R.id.setcall:
                    Intent intentSubActivity2 =
                            new Intent(MainActivity.this, SetCall.class);
                    startActivity(intentSubActivity2);
                    //setCurrentInflateItem(4);
                    break;
                case R.id.setalarm:
                    Intent intentSubActivity3 =
                            new Intent(MainActivity.this, SetAlarm.class);
                    startActivity(intentSubActivity3);
                    //setCurrentInflateItem(5);
                    break;
                case R.id.getGuide:
                    Intent intentSubActivity4 =
                            new Intent(MainActivity.this, GetGuide.class);
                    startActivity(intentSubActivity4);
                    //setCurrentInflateItem(5);
                    break;

                case R.id.call:
                    startActivity(new Intent("android.intent.action.DIAL", Uri
                            .parse("tel:" + call_gu + "")));
                    break;

                case R.id.send:
                    requestGPS();
                    send_text=true;

                    alarm_text.setText("신고가 접수되었습니다");
                    alarm_text1.setText("경보음을 끄고싶으시면");
                    alarm_text2.setText("취소하기 버튼을 눌러주세요.");
                    tv.setVisibility(TextView.GONE);
                    send.setVisibility(Button.GONE);

//                    mp.stop();
//                    ad.dismiss();
//                    flag=false;
                    break;
                case R.id.close:
                    mp.stop();
                    ad.dismiss();
                    flag=false;
                    break;
                case R.id.change:
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                    View layout = inflater.inflate(R.layout.popup_gu, (ViewGroup) findViewById(R.id.popupguid));
                    sDialog = new AlertDialog.Builder(MainActivity.this);


                    //aDialog.setTitle("title"); //타이틀바 제목
                    sDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

                    RadioButton search_st=(RadioButton)layout.findViewById(R.id.search_st);

                    RadioButton search_gu=(RadioButton)layout.findViewById(R.id.search_gu);

                    search_st.setOnClickListener(mPagerListener);
                    search_gu.setOnClickListener(mPagerListener);

                    select = sDialog.create();
                    select.show();
//                    find_gu_tv.setText("");
//                    if(station_ok){
//
//                        arrStr = (String[]) sub_gu.toArray(new String[sub_gu.size()]);
//                        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
//                        find_gu_tv.setAdapter(adapter);
//
//                        find_gu_tv.setHint("구로 검색");
//                        station_ok=false;
//
//                    }
//                       else {
//
//                        arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
//                        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
//                        find_gu_tv.setAdapter(adapter);
//                        //arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
//                        find_gu_tv.setHint("역으로검색");
//                        station_ok = true;
//                        //onRestart();
//                    }

                    break;
                case R.id.search_st:
                    Log.d("choice","1");
                    arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
                    find_gu_tv.setAdapter(adapter);
                    //arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
                    find_gu_tv.setHint("역으로검색");
                    station_ok = true;
                    select.dismiss();

                    break;
                case R.id.search_gu:

                    Log.d("choice","2");
                    arrStr = (String[]) sub_gu.toArray(new String[sub_gu.size()]);
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
                    find_gu_tv.setAdapter(adapter);

                    find_gu_tv.setHint("구로 검색");
                    station_ok=false;
                    select.dismiss();
                    break;

            }
        }
    };

    //주소록에서 번호 가져오기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ContentResolver cr = getContentResolver();

            Cursor cursor = cr.query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.CONTACT_ID}, null, null, null);
            cursor.moveToFirst();
            //name = cursor.getString(0);        //0은 이름을 얻어옵니다.
            number = cursor.getString(1);   //1은 번호를 받아옵니다.
            photo = cursor.getLong(2);//photo받아옴
            //Long contactId = cursor.getLong(contactId_idx);
            //Log.e("###", contactId_idx+" | "+contactId+ " | "+name);

            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, photo);
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);

            // 사진없으면 기본 사진 보여주기
            if (input != null)
            {
                Bitmap contactPhoto = BitmapFactory.decodeStream(input);
                photoView.setImageBitmap(contactPhoto);
            }
            else
            {
                photoView.setImageDrawable(getResources().getDrawable(R.drawable.school));
            }
            cursor.close();

        }
        super.onActivityResult(requestCode, resultCode, data);
        //smsNumber=(EditText)findViewById(R.id.pn);

        smsNumber.setText(number);
        //지정인번호를 계속 저장해둬야 하므로 DB대신 txt파일에 저장

        // File file = new File("number.txt");
        try {
            //this.deleteFile("number.txt");
            //File file = new File("number.txt");
            OutputStream out = null;
            out = new FileOutputStream(filePath);
            out.write(number.getBytes());
            Toast.makeText(this, "지정인이 등록되었습니다", Toast.LENGTH_SHORT).show();


            out.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
//    TimerTask myTask = new TimerTask() {
//        public void run() {
//            // <-- 요기에 반복할 작업을 넣으시면 됩니다.(예, sendSMS(View V);)
//
//            Handler mHandler = new Handler(Looper.getMainLooper());
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // 내용
//                    sendSMS();
//
//                }
//            }, 0);
//
//        }
//    };
    //문자발송

    public void sendSMS(String station){
        // String smsNum="01051070178";
        String smsText;
        smsText = station;
        Cursor phone_cursor = handler.select_quick("man");
        while (phone_cursor.moveToNext()) {
            String smsNum = phone_cursor.getString(phone_cursor
                    .getColumnIndex("number"));
            Log.d("test", smsNum);
            if (smsNum.length()>0 && smsText.length()>0){
                sendSMS(smsNum, smsText);
            }else{
                Toast.makeText(this, "지정인이 없습니다!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void sendSMS(String smsNumber, String smsText){


        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"),  PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(context, "전송실패", Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(getApplicationContext(),String.valueOf(getResultCode()), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this,"2"+smsText,Toast.LENGTH_SHORT).show();
    }
    /**
     * PagerAdapter
     */
    private class PagerAdapterClass extends PagerAdapter {

        private LayoutInflater mInflater;

        public PagerAdapterClass(Context c){
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if(position==0){//quick신청 코드
                v = mInflater.inflate(R.layout.inflate_one, null);
                //버튼들
                Button call_home=(Button)v.findViewById(R.id.call_home);
                Button call_one=(Button)v.findViewById(R.id.call_one);
                Button call_two=(Button)v.findViewById(R.id.call_two);


                v.findViewById(R.id.call_home).setOnClickListener(mPagerListener);
                v.findViewById(R.id.call_one).setOnClickListener(mPagerListener);
                v.findViewById(R.id.call_two).setOnClickListener(mPagerListener);
                //1번 주소지정지에 대한 내용
                TextView home_tv=(TextView)v.findViewById(R.id.home_tv);
                TextView home_sub_tv=(TextView)v.findViewById(R.id.home_sub_tv);
                TextView home_ward_tv=(TextView)v.findViewById(R.id.home_ward_tv);
                //2번
                TextView set1_tv=(TextView)v.findViewById(R.id.set1_tv);
                TextView set1_sub_tv=(TextView)v.findViewById(R.id.set1_sub_tv);
                TextView set1_ward_tv=(TextView)v.findViewById(R.id.set1_ward_tv);
                //3번
                TextView set2_tv=(TextView)v.findViewById(R.id.set2_tv);
                TextView set2_sub_tv=(TextView)v.findViewById(R.id.set2_sub_tv);
                TextView set2_ward_tv=(TextView)v.findViewById(R.id.set2_ward_tv);

                Cursor quick_cursor1 = handler.select_quick("QUICK");
                int t=3;

                while ( quick_cursor1.moveToNext()){
                    String name1 = quick_cursor1.getString(quick_cursor1
                            .getColumnIndex("name"));
                    String station1 = quick_cursor1.getString(quick_cursor1.getColumnIndex("station"));
                    String gu1 = quick_cursor1.getString(quick_cursor1.getColumnIndex("gu"));
                    String number1 = quick_cursor1.getString(quick_cursor1.getColumnIndex("phone"));
                    int icon1=quick_cursor1.getInt(quick_cursor1.getColumnIndex("icon"));
                    if(icon1==0)
                        icon1=R.drawable.home;
                    else if(icon1==1)
                        icon1=R.drawable.office;
                    // Toast.makeText(getApplicationContext(),String.valueOf(icon1),Toast.LENGTH_SHORT).show();
                    switch (t) {
                        case 3:
                            home_tv.setText(name1);
                            home_sub_tv.setText(station1);
                            home_ward_tv.setText(gu1);
                            phone1=number1;
                            v.findViewById(R.id.home_ic).setBackgroundResource(icon1);
                            v.findViewById(R.id.home_ward).setBackgroundResource(R.drawable.add);
                            v.findViewById(R.id.home_sub_img).setBackgroundResource(R.drawable.subway);
                            break;
                        case 2:
                            set1_tv.setText(name1);
                            set1_sub_tv.setText(station1);
                            set1_ward_tv.setText(gu1);
                            phone2=number1;
                            v.findViewById(R.id.set1_ic).setBackgroundResource(icon1);
                            v.findViewById(R.id.set1_ward_img).setBackgroundResource(R.drawable.add);
                            v.findViewById(R.id.set1_sub_img).setBackgroundResource(R.drawable.subway);

                            break;
                        case 1:
                            set2_tv.setText(name1);
                            set2_sub_tv.setText(station1);
                            set2_ward_tv.setText(gu1);
                            phone3=number1;
                            v.findViewById(R.id.set2_ic).setBackgroundResource(icon1);
                            v.findViewById(R.id.set2_ward_img).setBackgroundResource(R.drawable.add);
                            v.findViewById(R.id.set2_sub_img).setBackgroundResource(R.drawable.subway);

                            break;
                        default:
                            break;
                    }
                    t--;


                    //String msg=name1+station1+gu1+number1;
                    //Log.d("test",msg);
                }
                //myAdapter = new MyCursorAdapter ( getApplicationContext(), quick_cursor1);


                // PersonAdapter m_adapter = new PersonAdapter(getApplicationContext(), R.layout.list_view, m_orders);
// 어댑터를 생성합니다.
                // ListView listview = (ListView)v.findViewById(R.id.listview);
                //listview.setAdapter(myAdapter);
            }
            else if(position==1){//지정 신청 코드
                v = mInflater.inflate(R.layout.inflate_two, null);
                v.findViewById(R.id.iv_two);
                mContext=getApplicationContext();
                Button find=(Button)v.findViewById(R.id.find_gu_btn_by_name);
                Log.d("station_ok",String.valueOf(station_ok));
                find_gu_tv = (AutoCompleteTextView)v.findViewById(R.id.find_gu_ed);
                if(station_ok) {
                    arrStr = (String[]) sub_st.toArray(new String[sub_st.size()]);
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
                    find_gu_tv.setAdapter(adapter);
                }
                else {
                    arrStr = (String[]) sub_gu.toArray(new String[sub_gu.size()]);
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, arrStr);
                    find_gu_tv.setAdapter(adapter);
                }

                // auto make text



                listView = (ListView) v.findViewById(R.id.listView);
                Button change=(Button)v.findViewById(R.id.change);
                change.setOnClickListener(mPagerListener);
                final Button call = (Button)v.findViewById(R.id.call);
                final TextView set_gu=(TextView)v.findViewById(R.id.set_gu);
                final TextView set_gu_text=(TextView)v.findViewById(R.id.set_gu_text);
                final Context c=getApplicationContext();
                final ImageView subway = (ImageView)v.findViewById(R.id.subway);

                call.setOnClickListener(mPagerListener);
                find.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(station_ok){//역검색
                            String station = find_gu_tv.getText().toString();
                            Log.d("station",station);
                            Cursor cursor = handler.select2("subway",station);
//                                Boolean a=cursor.moveToNext();
//                                Log.d("yes",String.valueOf(a));
                            //String gu = cursor.getString(cursor.getColumnIndex("gu"));
                            if(!cursor.moveToNext()){
                                set_gu.setText("검색결과가 없습니다");
                            }else {
                                subway.setBackgroundResource(R.drawable.subway);
                                String s_gu = cursor.getString(cursor.getColumnIndex("gu"));
                                Cursor cursor2 = handler.select("phone", s_gu);
                                cursor = handler.select("subway", s_gu);
                                call_gu = cursor2.getString(cursor2.getColumnIndex("number"));
                                set_gu.setText(s_gu);
                                int num_c = cursor.getCount();
                                String gu_text = String.valueOf(num_c) + "개 역이 검색되었습니다";
                                set_gu_text.setText(gu_text);
                                call.setBackgroundResource(R.drawable.request_active);

                                MyCursorAdapter2 myadapter = new MyCursorAdapter2(c, cursor);

                                //  CustomAdapter custom = new CustomAdapter(getApplicationContext(), 0, sub_st);
                                listView.setAdapter(myadapter);
                            }

                        }
                        else{//구검색
                            subway.setBackgroundResource(R.drawable.subway);
                            String gu = find_gu_tv.getText().toString();
                            Log.d("station", gu);
                            Cursor cursor = handler.select("subway",gu);
//                                Boolean a=cursor.moveToNext();
//                                Log.d("yes",String.valueOf(a));
                            //String gu = cursor.getString(cursor.getColumnIndex("gu"));
                            if(!cursor.moveToNext()){
                                set_gu.setText("검색결과가 없습니다");
                            }else {
                                //String s_gu = cursor.getString(cursor.getColumnIndex("gu"));
                                Cursor cursor2 = handler.select("phone", gu);
                                //cursor = handler.select("subway", s_gu);
                                call_gu = cursor2.getString(cursor2.getColumnIndex("number"));
                                set_gu.setText(gu);
                                int num_c = cursor.getCount();
                                String gu_text = String.valueOf(num_c) + "개 역이 검색되었습니다";
                                set_gu_text.setText(gu_text);
                                call.setBackgroundResource(R.drawable.request_active);

                                MyCursorAdapter2 myadapter = new MyCursorAdapter2(c, cursor);

                                //  CustomAdapter custom = new CustomAdapter(getApplicationContext(), 0, sub_st);
                                listView.setAdapter(myadapter);
                            }
                        }

                    }
                });


                //String abc = find_gu_tv.getResources().getString(0);



            }else if(position==2) {//설정 코드
                v = mInflater.inflate(R.layout.inflate_three, null);
                v.findViewById(R.id.iv_three);
                v.findViewById(R.id.setlocal).setOnClickListener(mPagerListener);
                v.findViewById(R.id.setcall).setOnClickListener(mPagerListener);
                v.findViewById(R.id.setalarm).setOnClickListener(mPagerListener);
                v.findViewById(R.id.getGuide).setOnClickListener(mPagerListener);
                //v.findViewById(R.id.getTutorial).setOnClickListener(mPagerListener);
            }
//            }else if(position==3) {//setlocal
//                //Toast.makeText(getApplicationContext(),"흠2",Toast.LENGTH_SHORT);
//                v = mInflater.inflate(R.layout.set_local, null);
//
//            }
//            }else if(position==4){//setcall
//                v=mInflater.inflate(R.layout.set_call,null);
//                photoView = (ImageView)v.findViewById(R.id.image);
//                title.setText("보호자 연락처");
//                smsNumber=(EditText) v.findViewById(R.id.pn);
//                try {// 전에 지정해둔 지정인 불러오기
//                    InputStream fis = new FileInputStream(filePath);
//                    byte[] data= new byte[fis.available()];
//                    fis.read(data);
//                    fis.close();
//                    number = new String(data);
//
//                    smsNumber.setText(number);
//                    //Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//
//                v.findViewById(R.id.setpn).setOnClickListener(mPagerListener);
//            }else if(position==5)//setalarm
//            {
//                v=mInflater.inflate(R.layout.set_alarm,null);
//            }

            ((ViewPager)pager).addView(v, 0);

            return v;
        }

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
        @Override public Parcelable saveState() { return null; }
        @Override public void startUpdate(View arg0) {}
        @Override public void finishUpdate(View arg0) {}
    }

}
