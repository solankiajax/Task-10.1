package com.example.TruckSharingApp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

public class User implements Serializable {

    private Integer user_id;
    private String username;
    private String password;
    private String phone;
    private String full_name;
    private byte[] img;

    public User() {
    }

    public User(String username, String password, String phone, String full_name, byte[] img) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.full_name = full_name;
        this.img = img;
    }


    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
