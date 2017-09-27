package com.safebackhome.j6.safeme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;


/**
 * Created by yunjisung on 2015. 10. 25..
 */
public class SetAlarm extends Activity{

    Button select_sound_button, select_sound_time_button, select_volume_button;

    RadioButton radio_song1;
    RadioButton radio_song2;
    RadioButton radio_song3;
    RadioButton radio_time1;
    RadioButton radio_time2;
    RadioButton radio_time3;

    MediaPlayer mp;
    AudioManager am;
    SeekBar seekbar;
    int selected_volume;
    String selected_siren;
    int selected_time;
    DBManagerHandler handler;
    int max_voulume;

    AlertDialog.Builder aDialog_siren, aDialog_volume, aDialog_time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm);

        handler = new DBManagerHandler(getApplicationContext());

        if(handler.getCountByTableName("siren")<1) {
            selected_siren="aaa";
            selected_volume=30;
            selected_time=1;
            handler.insert4("siren", selected_siren, selected_volume, selected_time);
        }

        Cursor cursor;
        cursor = handler.select_quick("siren");

        cursor.moveToFirst();

        selected_siren=cursor.getString(cursor.getColumnIndex("siren"));
        selected_time=cursor.getInt(cursor.getColumnIndex("time"));
        selected_volume=cursor.getInt(cursor.getColumnIndex("size"));

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        max_voulume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if(selected_siren.equals("1")) {
            mp = MediaPlayer.create(SetAlarm.this, R.raw.aa);
        }
        else if(selected_siren.equals("2")) {
            mp = MediaPlayer.create(SetAlarm.this, R.raw.bb);
        }
        else {
            mp = MediaPlayer.create(SetAlarm.this, R.raw.cc);
        }
        select_sound_button=(Button)findViewById(R.id.select_sound);
        select_sound_time_button=(Button)findViewById(R.id.select_sound_time);
        select_volume_button=(Button)findViewById(R.id.alarm_volume);

        select_sound_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                View layout = inflater.inflate(R.layout.pop_siren, (ViewGroup) findViewById(R.id.popupid_siren));
                aDialog_siren = new AlertDialog.Builder(SetAlarm.this);

                aDialog_siren.setTitle("Siren"); //타이틀바 제목
                aDialog_siren.setView(layout); //dialog.xml 파일을 뷰로 셋팅

                //팝업창 생성
                final AlertDialog ad_siren = aDialog_siren.create();
                ad_siren.show();//보여줌!

                Button send_but = (Button) ad_siren.findViewById(R.id.pop_siren_send);
                Button cancel_but = (Button) ad_siren.findViewById(R.id.pop_siren_close);

                send_but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //
                        handler.modify("siren", selected_siren, selected_time, selected_volume);
                        if(mp!=null){
                            mp.release();
                        }
                        //am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND);
                        ad_siren.dismiss();
                    }
                });
                //닫기
                cancel_but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if(mp!=null){
                            mp.release();
                        }
                        //am.setStreamVolume(AudioManager.STREAM_MUSIC, 0,AudioManager.FLAG_PLAY_SOUND);
                        ad_siren.dismiss();
                    }
                });

               radio_song1 = (RadioButton) ad_siren.findViewById(R.id.song1);
                radio_song2 = (RadioButton) ad_siren.findViewById(R.id.song2);
                radio_song3 = (RadioButton) ad_siren.findViewById(R.id.song3);
                am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                // 첫번째 버튼이 눌렸을 경우
                radio_song1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if(mp!=null){
                            mp.release();
                        }
                        radio_song2.setChecked(false);
                        radio_song3.setChecked(false);
                        selected_siren = "1";

                        mp = MediaPlayer.create(SetAlarm.this, R.raw.aa);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND);
                        mp.start();
                        /*if(Integer.parseInt(siren_name)==1) {
                        mp = MediaPlayer.create(MainActivity.this, R.raw.aa);
                    }
                    else if(Integer.parseInt(siren_name)==2) {
                        mp = MediaPlayer.create(MainActivity.this, R.raw.bb);
                    }
                    else if(Integer.parseInt(siren_name)==3){
                        mp = MediaPlayer.create(MainActivity.this, R.raw.cc);
                    }
                    else{
                        mp=MediaPlayer.create(MainActivity.this, R.raw.a);
                    }*/
                    }
                });

                radio_song2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_siren = "2";
                        if(mp!=null){
                            mp.release();
                        }
                        radio_song1.setChecked(false);
                        radio_song3.setChecked(false);
                        mp = MediaPlayer.create(SetAlarm.this, R.raw.bb);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND);
                        mp.start();

                    }
                });

                radio_song3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_siren = "3";
                        if(mp!=null){
                            mp.release();
                        }
                        radio_song2.setChecked(false);
                        radio_song1.setChecked(false);
                        mp = MediaPlayer.create(SetAlarm.this, R.raw.cc);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND);
                        mp.start();
                    }
                });
            }
        });

        select_sound_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                View layout = inflater.inflate(R.layout.pop_time, (ViewGroup) findViewById(R.id.popupid_time));
                aDialog_time = new AlertDialog.Builder(SetAlarm.this);

                aDialog_time.setTitle("Time"); //타이틀바 제목
                aDialog_time.setView(layout); //dialog.xml 파일을 뷰로 셋팅

                //팝업창 생성
                final AlertDialog ad_time = aDialog_time.create();
                ad_time.show();//보여줌!

                Button send_but = (Button) ad_time.findViewById(R.id.pop_time_send);
                Button cancel_but = (Button) ad_time.findViewById(R.id.pop_time_close);

                // SUBMIT
                send_but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        handler.modify("siren", selected_siren, selected_time, selected_volume);
                        ad_time.dismiss();
                    }
                });
                // CLOSE
                cancel_but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        ad_time.dismiss();
                    }
                });

                radio_time1 = (RadioButton) ad_time.findViewById(R.id.time1);
                radio_time2 = (RadioButton) ad_time.findViewById(R.id.time2);
                radio_time3 = (RadioButton) ad_time.findViewById(R.id.time3);

                // 첫번째 버튼이 눌렸을 경우
                radio_time1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_time = 5;
                        radio_time2.setChecked(false);
                        radio_time3.setChecked(false);
                    }
                });
                radio_time2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_time = 10;
                        radio_time1.setChecked(false);
                        radio_time3.setChecked(false);
                    }
                });
                radio_time3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_time = 15;
                        radio_time1.setChecked(false);
                        radio_time2.setChecked(false);
                    }
                });
            }
        });

        select_volume_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                View layout = inflater.inflate(R.layout.volume_popup, (ViewGroup) findViewById(R.id.volume_popup_id));
                aDialog_volume = new AlertDialog.Builder(SetAlarm.this);

                aDialog_volume.setTitle("Volume"); //타이틀바 제목
                aDialog_volume.setView(layout); //dialog.xml 파일을 뷰로 셋팅

                //팝업창 생성
                final AlertDialog ad_volume = aDialog_volume.create();
                ad_volume.show();//보여줌!

                Button send_but=(Button)ad_volume.findViewById(R.id.send_volume);
                Button cancel_but=(Button)ad_volume.findViewById(R.id.close_volume);

                send_but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        handler.modify("siren", selected_siren, selected_time, selected_volume);
                        if(mp!=null){
                            mp.release();
                        }
// debug용
//                        Cursor cursor;
//                        cursor=handler.select_quick("siren");
//                        cursor.moveToFirst();
//                        Log.d("db", cursor.getString(cursor.getColumnIndex("size")));
//                        Log.d("db2",String.valueOf(selected_volume));
                        ad_volume.dismiss();
                    }
                });

                cancel_but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if(mp!=null){
                            mp.release();
                        }
                        ad_volume.dismiss();
                    }
                });

                seekbar = (SeekBar) ad_volume.findViewById(R.id.seekBar);

                seekbar.setMax(100);
                seekbar.incrementProgressBy(1);

                Cursor cursor;
                cursor=handler.select_quick("siren");
                cursor.moveToFirst();
                int progressed=cursor.getInt(cursor.getColumnIndex("size"));

                seekbar.setProgress(progressed);

                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        selected_volume = progress;
                        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, max_voulume * selected_volume / 100, AudioManager.FLAG_PLAY_SOUND);
                        if(mp!=null){
                            mp.release();
                        }
                        if(selected_siren.equals("1")) {
                            mp = MediaPlayer.create(SetAlarm.this, R.raw.aa);
                        }
                        else if(selected_siren.equals("2")) {
                            mp = MediaPlayer.create(SetAlarm.this, R.raw.bb);
                        }
                        else {
                            mp = MediaPlayer.create(SetAlarm.this, R.raw.cc);
                        }
                        mp.start();
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });
    }

    protected void onDestroy(){
        super.onDestroy();

        if(mp!=null){
            mp.release();
        }
    }
}
