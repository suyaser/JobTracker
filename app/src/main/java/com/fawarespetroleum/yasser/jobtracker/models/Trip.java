package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yasser on 11/10/2016.
 */

public class Trip extends Operation implements Parcelable {
    public static final int TRIP_KEY = 3;

    private String Actions;
    private String Comments;
    private String tripReasons;
    private Date informTime;
    private Date startTime;

    public Trip() {
    }

    public Trip(Date date, String workPermitNumber, String actions,
                String generator, String comments, String tripReasons,
                Date informTime, Date startTime) {
        super(date, workPermitNumber, generator, TRIP_KEY);
        Actions = actions;
        Comments = comments;
        this.tripReasons = tripReasons;
        this.informTime = informTime;
        this.startTime = startTime;
    }

    public Trip(Date date, String workPermitNumber, String actions,
                String comments, String tripReasons,
                Date informTime, Date startTime) {
        super(date, workPermitNumber, null, TRIP_KEY);
        Actions = actions;
        Comments = comments;
        this.tripReasons = tripReasons;
        this.informTime = informTime;
        this.startTime = startTime;
    }

    protected Trip(Parcel in) {
        type = in.readInt();
        generator = in.readString();
        workPermitNumber = in.readString();
        Actions = in.readString();
        Comments = in.readString();
        tripReasons = in.readString();
        long temp = in.readLong();
        date = temp == -1 ? null : new Date(temp);
        temp = in.readLong();
        informTime = temp == -1 ? null : new Date(temp);
        temp = in.readLong();
        startTime = temp == -1 ? null : new Date(temp);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeString(generator);
        parcel.writeString(workPermitNumber);
        parcel.writeString(Actions);
        parcel.writeString(Comments);
        parcel.writeString(tripReasons);
        parcel.writeLong(date == null ? -1 : date.getTime());
        parcel.writeLong(informTime == null ? -1 : informTime.getTime());
        parcel.writeLong(startTime == null ? -1 : startTime.getTime());
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

    public String getTripReasons() {
        return tripReasons;
    }

    public void setTripReasons(String tripReasons) {
        this.tripReasons = tripReasons;
    }

    public Date getInformTime() {
        return informTime;
    }

    public void setInformTime(Date informTime) {
        this.informTime = informTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

}
