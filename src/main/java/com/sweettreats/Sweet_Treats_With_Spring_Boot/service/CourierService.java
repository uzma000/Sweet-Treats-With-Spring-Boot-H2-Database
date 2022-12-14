package com.sweettreats.Sweet_Treats_With_Spring_Boot.service;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order;

import java.util.List;

public interface CourierService {
    Courier addCourier(Courier newCourier) throws Exception;

    List<Courier> getAllCouriers();

    Courier findCourier(String id) throws Exception;

    Courier getBestSuitableCourier(Order order) throws Exception;

    Courier updateCourierDetails(String id, Courier courierDetails) throws Exception;

    void deleteCourier(String id) throws Exception;

    boolean isCourierInformationValid(Courier courierDetails) throws Exception;

}
