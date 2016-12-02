package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yasser on 11/10/2016.
 */

public class Install extends Operation implements Parcelable {
    public static final int INSTALL_KEY = 1;

    private String field;
    private String tankSerial;
    private String syncPanel;
    private String fireExtinguisher;
    private Date FEExpiryDate;
    private String Comments;

    public Install() {
    }

    public Install(Date date, String workPermitNumber, String field,
                   String generator, String tankSerial, String syncPanel,
                   String fireExtinguisher, Date FEExpiryDate, String comments) {
        super(date, workPermitNumber, generator, INSTALL_KEY);
        this.field = field;
        this.tankSerial = tankSerial;
        this.syncPanel = syncPanel;
        this.fireExtinguisher = fireExtinguisher;
        this.FEExpiryDate = FEExpiryDate;
        Comments = comments;
    }

    public Install(Date date, String workPermitNumber, String field, String tankSerial, String syncPanel,
                   String fireExtinguisher, Date FEExpiryDate, String comments) {
        super(date, workPermitNumber, null, INSTALL_KEY);
        this.field = field;
        this.tankSerial = tankSerial;
        this.syncPanel = syncPanel;
        this.fireExtinguisher = fireExtinguisher;
        this.FEExpiryDate = FEExpiryDate;
        Comments = comments;
    }

    public Install(Parcel in) {
        type = in.readInt();
        workPermitNumber = in.readString();
        generator = in.readString();
        field = in.readString();
        tankSerial = in.readString();
        syncPanel = in.readString();
        fireExtinguisher = in.readString();
        Comments = in.readString();
        long tmpDate = in.readLong();
        date = tmpDate == -1 ? null : new Date(tmpDate);
        tmpDate = in.readLong();
        FEExpiryDate = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<Install> CREATOR = new Creator<Install>() {
        @Override
        public Install createFromParcel(Parcel in) {
            return new Install(in);
        }

        @Override
        public Install[] newArray(int size) {
            return new Install[size];
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
        parcel.writeString(field);
        parcel.writeString(tankSerial);
        parcel.writeString(syncPanel);
        parcel.writeString(fireExtinguisher);
        parcel.writeString(Comments);
        parcel.writeLong(date != null ? date.getTime() : -1);
        parcel.writeLong(FEExpiryDate != null ? FEExpiryDate.getTime() : -1);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

}
