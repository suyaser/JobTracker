package com.fawarespetroleum.yasser.jobtracker.models;

import java.util.Date;

/**
 * Created by yasser on 11/10/2016.
 */

public class Operation {
    Date date;
    String workPermitNumber;
    String field;
    String site;
    int type;

    public Operation(Date date, String workPermitNumber, String field, String site, int type) {
        this.date = date;
        this.workPermitNumber = workPermitNumber;
        this.field = field;
        this.site = site;
        this.type = type;
    }

    public Operation(){}

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

    public int getType(){
        return type;
    }
}
