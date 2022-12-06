package com.sweettreats.Sweet_Treats_With_Spring_Boot.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private LocalTime orderTime;
    private boolean isRefrigeratorRequire;
    private double distance;

    public Order(String orderTime, boolean isRefrigeratorRequire, double distance) {
//
        if (orderTime == null || orderTime == "") {
            throw new IllegalArgumentException("Invalid order time");
        } else this.orderTime = getTimePattern(orderTime);
        this.isRefrigeratorRequire = isRefrigeratorRequire;
        this.distance = distance;
    }


    public Order(String id, String orderTime, boolean isRefrigeratorRequire, double distance) {
        this.id = id;
        this.orderTime = LocalTime.parse(orderTime);
        this.isRefrigeratorRequire = isRefrigeratorRequire;
        this.distance = distance;
    }

    //to make a formatter for time by using ofPattern
    private LocalTime getTimePattern(String orderTime) {
        return LocalTime.parse(orderTime, ofPattern("HH:mm"));
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public boolean isRefrigeratorRequire() {
        return isRefrigeratorRequire;
    }

    public void setRefrigeratorRequire(boolean refrigeratorRequire) {
        isRefrigeratorRequire = refrigeratorRequire;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Check if time is in HH:mm format
    public static boolean isValidTime(String time) {
        // Regex to check valid time in 24-hour format.
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the time is empty
        // return false
        if (time == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given time
        // and regular expression.
        Matcher m = p.matcher(time);

        // Return if the time
        // matched the ReGex
        return m.matches();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderTime=" + orderTime +
                ", isRefrigeratorRequire=" + isRefrigeratorRequire +
                ", distance=" + distance +
                '}';
    }
}
