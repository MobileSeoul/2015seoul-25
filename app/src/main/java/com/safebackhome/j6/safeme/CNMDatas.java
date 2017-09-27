package com.safebackhome.j6.safeme;

/**
 * Created by Administrator on 2015-10-16.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class CNMDatas implements Parcelable {
    private String x,y;

    public CNMDatas(String x, String y){
        this.x=x;
        this.y=y;
    }
    public CNMDatas(Parcel in) { readFromParcel(in);}
    public CNMDatas(){}

    public String getX(){return x;}
    public void setX(String subX){this.x=x;}
    public String getY(){return y;}
    public void setY(String subY){this.y=y;}

    public int describeContents() { return 0;}
    public void writeToParcel(Parcel arg0, int arg1){
        arg0.writeString(x);
        arg0.writeString(y);
    }
    private void readFromParcel(Parcel in){
        x = in.readString();
        y = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public CNMDatas createFromParcel(Parcel source) {
            return new CNMDatas(source);
        }
        public CNMDatas[] newArray(int size){
            return new CNMDatas[size];
        }
    };
}
