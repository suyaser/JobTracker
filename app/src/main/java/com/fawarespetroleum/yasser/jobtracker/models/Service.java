package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yasser on 11/10/2016.
 */

public class Service extends Operation implements Parcelable {
    public static final int SERVICE_KEY = 2;

    private String Actions;
    private String Comments;
    private int RHS;
    private int oilPressure;
    private int temp;
    private int l2lVoltage;
    private int HZ;
    private int batteryCharge;

    public Service() {
    }

    public Service(Date date, String workPermitNumber, String actions, String generator
            , String comments, int RHS, int oilPressure, int temp
            , int l2lVoltage, int HZ, int batteryCharge) {
        super(date, workPermitNumber, generator, SERVICE_KEY);
        Actions = actions;
        Comments = comments;
        this.RHS = RHS;
        this.oilPressure = oilPressure;
        this.temp = temp;
        this.l2lVoltage = l2lVoltage;
        this.HZ = HZ;
        this.batteryCharge = batteryCharge;
    }

    public Service(Date date, String workPermitNumber, String actions
            , String comments, int RHS, int oilPressure, int temp
            , int l2lVoltage, int HZ, int batteryCharge) {
        super(date, workPermitNumber, null, SERVICE_KEY);
        Actions = actions;
        Comments = comments;
        this.RHS = RHS;
        this.oilPressure = oilPressure;
        this.temp = temp;
        this.l2lVoltage = l2lVoltage;
        this.HZ = HZ;
        this.batteryCharge = batteryCharge;
    }

    protected Service(Parcel in) {
        type = in.readInt();
        workPermitNumber = in.readString();
        generator = in.readString();
        Actions = in.readString();
        Comments = in.readString();
        RHS = in.readInt();
        oilPressure = in.readInt();
        temp = in.readInt();
        l2lVoltage = in.readInt();
        HZ = in.readInt();
        batteryCharge = in.readInt();
        long tmp = in.readLong();
        date = tmp == -1 ? null : new Date(tmp);
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(workPermitNumber);
        parcel.writeString(generator);
        parcel.writeString(Actions);
        parcel.writeString(Comments);
        parcel.writeInt(RHS);
        parcel.writeInt(oilPressure);
        parcel.writeInt(temp);
        parcel.writeInt(l2lVoltage);
        parcel.writeInt(HZ);
        parcel.writeInt(batteryCharge);
        parcel.writeLong(date == null ? -1 : date.getTime());
    }

    public String getActions() {
        return Actions;
    }

    public void setActions(String actions) {
        Actions = actions;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public int getRHS() {
        return RHS;
    }

    public void setRHS(int RHS) {
        this.RHS = RHS;
    }

    public int getOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(int oilPressure) {
        this.oilPressure = oilPressure;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getL2lVoltage() {
        return l2lVoltage;
    }

    public void setL2lVoltage(int l2lVoltage) {
        this.l2lVoltage = l2lVoltage;
    }

    public int getHZ() {
        return HZ;
    }

    public void setHZ(int HZ) {
        this.HZ = HZ;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

}
