package com.example.demo.resouces.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable{
    private Integer status;
    private String mdg;
    private long timeStamp;

    public StandardError(Integer status, String mdg, long timeStamp) {
        this.status = status;
        this.mdg = mdg;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMdg() {
        return mdg;
    }

    public void setMdg(String mdg) {
        this.mdg = mdg;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
