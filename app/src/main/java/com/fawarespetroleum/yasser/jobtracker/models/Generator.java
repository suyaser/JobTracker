package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yasser on 12/11/2016.
 */
public class Generator implements Parcelable {

    String mSerial;
    int mSize;
    String tankSerial;
    String syncPanel;
    String fireExtinguisher;
    Date FEExpiryDate;
    Boolean inWorkshop;
    Field site;

    public Generator() {

    }

    public Generator(String serial, int size, boolean isInWorkshop) {
        mSerial = serial;
        mSize = size;
        this.inWorkshop = isInWorkshop;
    }


    protected Generator(Parcel in) {
        mSerial = in.readString();
        mSize = in.readInt();
        tankSerial = in.readString();
        syncPanel = in.readString();
        fireExtinguisher = in.readString();
        FEExpiryDate = new Date(in.readLong());
        inWorkshop = in.readInt() == 1;
        site = in.readParcelable(Field.class.getClassLoader());
    }

    public static final Creator<Generator> CREATOR = new Creator<Generator>() {
        @Override
        public Generator createFromParcel(Parcel in) {
            return new Generator(in);
        }

        @Override
        public Generator[] newArray(int size) {
            return new Generator[size];
        }
    };

    public Field getSite() {
        return site;
    }

    public void setSite(Field site) {
        this.site = site;
    }

    public String getmSerial() {
        return mSerial;
    }

    public void setmSerial(String mSerial) {
        this.mSerial = mSerial;
    }

    public int getmSize() {
        return mSize;
    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
    }

    public String getTankSerial() {
        return tankSerial;
    }

    public void setTankSerial(String tankSerial) {
        this.tankSerial = tankSerial;
    }

    public String getSyncPanel() {
        return syncPanel;
    }

    public void setSyncPanel(String syncPanel) {
        this.syncPanel = syncPanel;
    }

    public String getFireExtinguisher() {
        return fireExtinguisher;
    }

    public void setFireExtinguisher(String fireExtinguisher) {
        this.fireExtinguisher = fireExtinguisher;
    }

    public Date getFEExpiryDate() {
        return FEExpiryDate;
    }

    public void setFEExpiryDate(Date FEExpiryDate) {
        this.FEExpiryDate = FEExpiryDate;
    }

    public Boolean isInWorkshop() {
        return inWorkshop;
    }

    public void setInWorkshop(Boolean inWorkshop) {
        this.inWorkshop = inWorkshop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSerial);
        parcel.writeInt(mSize);
        parcel.writeString(tankSerial);
        parcel.writeString(syncPanel);
        parcel.writeString(fireExtinguisher);
        parcel.writeLong(FEExpiryDate.getTime());
        parcel.writeInt(inWorkshop ? 1 : 0);
        parcel.writeParcelable(site, i);
    }
}
