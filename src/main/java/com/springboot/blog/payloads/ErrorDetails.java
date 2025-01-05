package com.springboot.blog.payloads;

import lombok.Data;

import java.util.Date;

public class ErrorDetails {
    private Date date;
    private String message;
    private String errordetails;

    public ErrorDetails(Date date, String message, String errordetails) {
        this.date = date;
        this.message = message;
        this.errordetails = errordetails;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrordetails() {
        return errordetails;
    }

    public void setErrordetails(String errordetails) {
        this.errordetails = errordetails;
    }
}
