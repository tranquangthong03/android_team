package com.example.android_project;

public class AddressModel {

    int icon;
    String type;
    String address;

    // Đây là Constructor để tạo đối tượng
    public AddressModel(int icon, String type, String address) {
        this.icon = icon;
        this.type = type;
        this.address = address;
    }

    // Getters
    public int getIcon() {
        return icon;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }
}