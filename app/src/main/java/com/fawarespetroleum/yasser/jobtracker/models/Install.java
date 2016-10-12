package com.fawarespetroleum.yasser.jobtracker.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yasser on 11/10/2016.
 */

public class Install extends Operation implements Parcelable{
    private String contractor;
    private String generatorSerial;
    private int generatorSize;
    private String tankSerial;
    private String syncPanel;
    private String fireExtinguisher;
    private Date FEExpiryDate;
    private String Comments;

    public Install(Date date, String workPermitNumber, String field, String site, String contractor,
                   String generatorSerial, int generatorSize, String tankSerial, String syncPanel,
                   String fireExtinguisher, Date FEExpiryDate, String comments) {
        super(date, workPermitNumber, field, site);
        this.contractor = contractor;
        this.generatorSerial = generatorSerial;
        this.generatorSize = generatorSize;
        this.tankSerial = tankSerial;
        this.syncPanel = syncPanel;
        this.fireExtinguisher = fireExtinguisher;
        this.FEExpiryDate = FEExpiryDate;
        Comments = comments;
    }

    public Install(Parcel in) {
        workPermitNumber = in.readString();
        field = in.readString();
        site = in.readString();
        contractor = in.readString();
        generatorSerial = in.readString();
        generatorSize = in.readInt();
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
        parcel.writeString(workPermitNumber);
        parcel.writeString(field);
        parcel.writeString(site);
        parcel.writeString(contractor);
        parcel.writeString(generatorSerial);
        parcel.writeInt(generatorSize);
        parcel.writeString(tankSerial);
        parcel.writeString(syncPanel);
        parcel.writeString(fireExtinguisher);
        parcel.writeString(Comments);
        parcel.writeLong(date != null ? date.getTime() : -1);
        parcel.writeLong(FEExpiryDate != null ? FEExpiryDate.getTime() : -1);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWorkPermitNumber() {
        return workPermitNumber;
    }

    public void setWorkPermitNumber(String workPermitNumber) {
        this.workPermitNumber = workPermitNumber;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getGeneratorSerial() {
        return generatorSerial;
    }

    public void setGeneratorSerial(String generatorSerial) {
        this.generatorSerial = generatorSerial;
    }

    public int getGeneratorSize() {
        return generatorSize;
    }

    public void setGeneratorSize(int generatorSize) {
        this.generatorSize = generatorSize;
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

    @Override
    public String getType(){
        return "Install_key";
    }
}
