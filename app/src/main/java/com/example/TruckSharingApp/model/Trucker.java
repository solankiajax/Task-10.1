package com.example.TruckSharingApp.model;

public class Trucker {
    private byte[] img;
    private String name;
    private String location;
    private int trucker_id;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTrucker_id() {
        return trucker_id;
    }

    public void setTrucker_id(int trucker_id) {
        this.trucker_id = trucker_id;
    }

    public Trucker(byte[] img, String name, String location) {
        this.img = img;
        this.name = name;
        this.location = location;
    }
    public Trucker(String name, String location, String id) {
        this.trucker_id = Integer.parseInt(id);
        this.name = name;
        this.location = location;
    }

    public Trucker() {
    }
}
