package com.safebackhome.j6.safeme;

/**
 * Created by Administrator on 2015-10-16.
 */

import android.os.Handler;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

//import datas.BusStationDatas;

public class StationXMLParser extends XMLParser implements Runnable {
    private ArrayList<StationDatas> mDataList;
    private Handler mHandler;

    public StationXMLParser(String addr, Handler handler) {
        super(addr);
        mHandler = handler;
    }

    public void startParsing() {
        XmlPullParser parser = getXMLParser("utf-8");

        if(parser == null){
            mDataList = null;
            Log.d("StationXMLParser", "Parser Object is null");
        }else{
            mDataList = new ArrayList<StationDatas>();
            String statnNm = null; String subwayNm = null;
            String subX = null;  String subY = null;
            String tag;
            try{
                int parserEvent = parser.getEventType();
                int tagIdentifier = 0;

                while(parserEvent != XmlPullParser.END_DOCUMENT){
                    switch(parserEvent) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = parser.getName();
                            if (tag.equals("statnNm")) {
                                tagIdentifier = 1;
                            } else if (tag.equals("subwayNm")) {
                                tagIdentifier = 2;
                            } else if (tag.equals("subwayXcnts")) {
                                tagIdentifier = 3;
                            } else if (tag.equals("subwayYcnts")) {
                                tagIdentifier = 4;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            if (tagIdentifier == 1) {
                                statnNm = parser.getText().trim();
                            } else if (tagIdentifier == 2) {
                                subwayNm = parser.getText().trim();
                            } else if (tagIdentifier == 3) {
                                subX = parser.getText().trim();
                            } else if (tagIdentifier == 4) {
                                subY = parser.getText().trim();
                                StationDatas data = new StationDatas(statnNm, subwayNm, subX, subY);
                                mDataList.add(data);
                            }
                            tagIdentifier = 0;
                            break;
                    }
                    parserEvent = parser.next();
                }
            }catch(Exception e){
                Log.d("StationXMLParser", e.getMessage());
            }
        }
        Log.d("StationXMLParser", Integer.toString(mDataList.size()));
    }

    public ArrayList<StationDatas> getResult(){
        return mDataList;
    }
    public void run() {
        startParsing();
        mHandler.sendEmptyMessage(0);
    }
}
