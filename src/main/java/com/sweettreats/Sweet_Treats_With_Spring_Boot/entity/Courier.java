package com.sweettreats.Sweet_Treats_With_Spring_Boot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.annotation.Documented;
import java.time.LocalTime;

@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private double chargePerMile;
    private double maxDistance;
    private boolean hasRefrigeratorBox;


    //    will make constructor
    public Courier(String name, String startTime, String endTime,
                   double chargePerMile, double maxDistance, boolean hasRefrigeratorBox) {
        this.name = name;
        this.maxDistance = maxDistance;
        this.chargePerMile = chargePerMile;
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.hasRefrigeratorBox = hasRefrigeratorBox;

    }

    //No Arguments Constructor
    public Courier() {

    }

    //    getter and setter method

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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
