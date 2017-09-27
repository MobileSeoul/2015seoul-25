package com.safebackhome.j6.safeme;

/**
 * Created by Administrator on 2015-10-16.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class StationDatas implements Parcelable {
    private String statnNm, subwayNm,subX,subY;

    public StationDatas(String statnNm, String subwayNm, String subX, String subY){
        this.statnNm = statnNm;
        this.subwayNm = subwayNm;
        this.subX=subX;
        this.subY=subY;
    }
    public StationDatas(Parcel in) { readFromParcel(in);}
    public StationDatas(){}

    public String getStatnNm(){ return statnNm;}
    public void setStatnNm(String statnNm){this.statnNm=statnNm;}
    public String getSubwayNm(){return subwayNm;}
    public void setSubwayNm(String subwayNm){this.subwayNm=subwayNm;}
    public String getSubX(){return subX;}
    public void setSubX(String subX){this.subX=subX;}
    public String getSubY(){return subY;}
    public void setSubY(String subY){this.subY=subY;}

    public int describeContents() { return 0;}
    public void writeToParcel(Parcel arg0, int arg1){
        arg0.writeString(statnNm);
        arg0.writeString(subwayNm);
        arg0.writeString(subX);
        arg0.writeString(subY);
    }
    private void readFromParcel(Parcel in){
        statnNm = in.readString();
        subwayNm = in.readString();
        subX = in.readString();
        subY = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public StationDatas createFromParcel(Parcel source) {
            return new StationDatas(source);
        }
        public StationDatas[] newArray(int size){
            return new StationDatas[size];
        }
    };
}
