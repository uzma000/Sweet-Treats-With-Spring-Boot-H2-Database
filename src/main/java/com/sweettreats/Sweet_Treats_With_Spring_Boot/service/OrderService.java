package com.sweettreats.Sweet_Treats_With_Spring_Boot.service;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order;

public interface OrderService {
    Order makeOrder(String time, boolean refrigerator, double distance);
}
