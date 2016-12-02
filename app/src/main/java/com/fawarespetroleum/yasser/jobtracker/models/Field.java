package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasser on 12/11/2016.
 */

public class Field implements Parcelable {

    String mFieldName;
    String mSiteName;
    String mContractor;

    public Field(String mFieldName, String mSiteName, String mContractor) {
        this.mFieldName = mFieldName;
        this.mSiteName = mSiteName;
        this.mContractor = mContractor;
    }

    public Field() {
    }

    protected Field(Parcel in) {
        mFieldName = in.readString();
        mSiteName = in.readString();
        mContractor = in.readString();
    }

    public static final Creator<Field> CREATOR = new Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel in) {
            return new Field(in);
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };

    public String getmFieldName() {
        return mFieldName;
    }

    public void setmFieldName(String mFieldName) {
        this.mFieldName = mFieldName;
    }

    public String getmSiteName() {
        return mSiteName;
    }

    public void setmSiteName(String mSiteName) {
        this.mSiteName = mSiteName;
    }

    public String getmContractor() {
        return mContractor;
    }

    public void setmContractor(String mContractor) {
        this.mContractor = mContractor;
    }


    @Override
    public String toString() {
        return mSiteName + "_" + mFieldName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFieldName);
        parcel.writeString(mSiteName);
        parcel.writeString(mContractor);
    }
}
