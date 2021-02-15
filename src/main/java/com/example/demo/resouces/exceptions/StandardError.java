package com.example.demo.resouces.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {
    private Integer status;
    private String error;
    private long timestamp;
    private String message;
    private String path;

    public StandardError(Integer status, String error, long timestamp, String message, String path) {
        this.status = status;
        this.error = error;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
