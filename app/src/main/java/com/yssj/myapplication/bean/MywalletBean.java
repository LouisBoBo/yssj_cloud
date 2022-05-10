package com.yssj.myapplication.bean;

import java.io.Serializable;

public class MywalletBean implements Serializable {

    private double balance;
    private double freeze_balance;
    private String message;
    private String status;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFreeze_balance() {
        return freeze_balance;
    }

    public void setFreeze_balance(double freeze_balance) {
        this.freeze_balance = freeze_balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
