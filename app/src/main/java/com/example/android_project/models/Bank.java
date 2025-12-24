package com.example.android_project.models;

public class Bank {
    private int logo;
    private String ten;
    private String tenDayDu;

    public Bank(int logo, String ten, String tenDayDu) {
        this.logo = logo;
        this.ten = ten;
        this.tenDayDu = tenDayDu;
    }

    public int getLogo() {
        return logo;
    }

    public String getTen() {
        return ten;
    }

    public String getTenDayDu() {
        return tenDayDu;
    }
}
