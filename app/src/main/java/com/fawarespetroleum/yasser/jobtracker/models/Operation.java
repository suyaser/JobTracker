package com.fawarespetroleum.yasser.jobtracker.models;

import java.util.Date;

/**
 * Created by yasser on 11/10/2016.
 */

public class Operation {
    Date date;
    String workPermitNumber;
    String generator;
    int type;

    public Operation(Date date, String workPermitNumber, String generator, int type) {
        this.date = date;
        this.workPermitNumber = workPermitNumber;
        this.generator = generator;
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

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
