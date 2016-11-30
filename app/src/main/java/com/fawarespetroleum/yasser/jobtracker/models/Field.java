package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yasser on 12/11/2016.
 */

public class Field implements Parcelable {

    String mFieldName;
    String mSiteName;
    String mContractor;
    List<Generator> mGenerators;
    List<Install> mHistory;

    public Field(String mFieldName, String mSiteName, String mContractor) {
        this.mFieldName = mFieldName;
        this.mSiteName = mSiteName;
        this.mContractor = mContractor;
    }

    public Field() {
    }

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

    public List<Install> getmHistory() {
        return mHistory;
    }

    public void setmHistory(List<Install> mHistory) {
        this.mHistory = mHistory;
    }

    public List<Generator> getmGenerators() {
        return mGenerators;
    }

    public void setmGenerators(List<Generator> mGenerators) {
        this.mGenerators = mGenerators;
    }

    protected Field(Parcel in) {
        mFieldName = in.readString();
        mSiteName = in.readString();
        mContractor = in.readString();
        mHistory = in.createTypedArrayList(Install.CREATOR);
        mGenerators = in.createTypedArrayList(Generator.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFieldName);
        parcel.writeString(mSiteName);
        parcel.writeString(mContractor);
        parcel.writeTypedList(mHistory);
        parcel.writeTypedList(mGenerators);
    }

    @Override
    public String toString() {
        return mSiteName + "_" + mFieldName;
    }
}
