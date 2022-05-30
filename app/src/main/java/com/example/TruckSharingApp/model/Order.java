package com.example.TruckSharingApp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order{

    private String receiver_name,pickup_date,pickup_time,pickup_location,good_type,weight,length,height,width,vehicle_type;
    private Integer order_id,user_id;

    public Order() {
    }

    public Order(String receiver_name, String pickup_date, String pickup_time, String pickup_location, String good_type, String weight, String length, String height, String width, String vehicle_type, Integer user_id) {
        this.receiver_name = receiver_name;
        this.pickup_date = pickup_date;
        this.pickup_time = pickup_time;
        this.pickup_location = pickup_location;
        this.good_type = good_type;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.width = width;
        this.vehicle_type = vehicle_type;
        this.user_id = user_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
