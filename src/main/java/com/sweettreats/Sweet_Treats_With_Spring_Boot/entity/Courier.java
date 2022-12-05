package com.sweettreats.Sweet_Treats_With_Spring_Boot.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "couriers")
public class Courier {
    @Id
    private String id;
    private String name;
    private String startTime; // Changed to String to deal with database issue in MongoDb
    private String endTime; // Changed to String to deal with database issue in MongoDb
    private double chargePerMile;
    private double maxDistance;
    private boolean hasRefrigeratorBox;


    //    will make constructor
    public Courier(String name, String startTime, String endTime,
                   double chargePerMile, double maxDistance, boolean hasRefrigeratorBox) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.chargePerMile = chargePerMile;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.startTime = LocalTime.parse(startTime);
//        this.endTime = LocalTime.parse(endTime);
        this.hasRefrigeratorBox = hasRefrigeratorBox;

    }

    //No Arguments Constructor
    public Courier() {

    }

    //    getter and setter method

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getChargePerMile() {
        return chargePerMile;
    }

    public void setChargePerMile(double chargePerMile) {
        this.chargePerMile = chargePerMile;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public boolean isHasRefrigeratorBox() {
        return hasRefrigeratorBox;
    }

    public void setHasRefrigeratorBox(boolean hasRefrigeratorBox) {
        this.hasRefrigeratorBox = hasRefrigeratorBox;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", chargePerMile=" + chargePerMile +
                ", maxDistance=" + maxDistance +
                ", hasRefrigeratorBox=" + hasRefrigeratorBox +
                '}';
    }

}
