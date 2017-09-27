package com.safebackhome.j6.safeme;

/**
 * Created by Administrator on 2015-10-16.
 */

import android.os.Handler;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

//import datas.BusStationDatas;

public class CNM_XMLParser extends XMLParser implements Runnable {
    private ArrayList<CNMDatas> mDataList;
    private Handler mHandler;

    public CNM_XMLParser(String addr, Handler handler) {
        super(addr);
        mHandler = handler;
    }

    public void startParsing() {
        XmlPullParser parser = getXMLParser("utf-8");

        if(parser == null){
            mDataList = null;
            Log.d("CNM_XMLParser", "Parser Object is null");
        }else{
            mDataList = new ArrayList<CNMDatas>();
            String x = null; String y = null;
            String tag;
            try{
                int parserEvent = parser.getEventType();
                int tagIdentifier = 0;

                while(parserEvent != XmlPullParser.END_DOCUMENT){
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:                            break;
                        case XmlPullParser.END_DOCUMENT:                            break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if (tag.equals("result")) {
                                x=parser.getAttributeValue(null,"x").toString().trim();
                                y=parser.getAttributeValue(null,"y").toString().trim();
                                CNMDatas data = new CNMDatas(x,y);
                                mDataList.add(data);
                            }
                            break;
                        case XmlPullParser.END_TAG:                            break;
                        case XmlPullParser.TEXT:                          break;
                    }
                    parserEvent = parser.next();
                }
            }catch(Exception e){
                Log.d("CNM_XMLParser", e.getMessage());
            }
            //CNMDatas tmp=new CNMDatas("1.0","1.0");
            //mDataList.add(tmp);
        }
        //Log.d("CNM_XMLParser", Integer.toString(mDataList.size()));
    }

    public ArrayList<CNMDatas> getResult(){
        return mDataList;
    }
    public void run() {
        startParsing();
        mHandler.sendEmptyMessage(0);
    }
}
