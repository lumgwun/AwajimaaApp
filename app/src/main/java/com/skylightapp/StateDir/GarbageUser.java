package com.skylightapp.StateDir;

public class GarbageUser {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getGarbageCapacity() {
        return garbageCapacity;
    }

    public void setGarbageCapacity(int garbageCapacity) {
        this.garbageCapacity = garbageCapacity;
    }

    private String name;
    private String address;
    private String reply;
    private int garbageCapacity; // in liters

    public GarbageUser(){

    }

    public GarbageUser(String name, String address, String reply, int garbageCapacity){
        this.name = name;
        this.address = address;
        this.reply = reply;
        this.garbageCapacity = garbageCapacity;
    }
}
