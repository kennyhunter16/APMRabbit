package com.hunterit.APMRabbit;

public class WayPoints {

    //private variables
    long id;
    String timestamp;
    String location;
    String name;

    // Empty constructor
    public WayPoints(){ }

    // constructor
    public WayPoints(long id, String timestamp, String location, String name){
        this.id = id;
        this.timestamp = timestamp;
        this.location = location;
        this.name = name;
    }

    // getting ID
    public long getID(){
        return id;
    }

    // setting id
    public void setID(long id){
        this.id = id;
    }

    // getting timestamp
    public String getTimestamp(){
        return timestamp;
    }

    // setting timestamp
    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public String getLocation() { return location; }

    // setting location
    public void setLocation(String location){
        this.location = location;
    }

    // getting name
    public String getName(){
        return name;
    }

    // setting name
    public void setName(String name){
        this.name = name;
    }
}