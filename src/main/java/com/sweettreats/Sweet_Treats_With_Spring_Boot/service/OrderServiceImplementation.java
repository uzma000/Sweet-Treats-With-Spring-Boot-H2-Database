package com.sweettreats.Sweet_Treats_With_Spring_Boot.service;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImplementation implements OrderService{

    @Override
    public Order makeOrder(String time, boolean refrigerator, double distance) {
       return new Order(time,refrigerator,distance);
    }
}
