package com.sweettreats.Sweet_Treats_With_Spring_Boot.web;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.service.CourierService;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/sweet_treats")
public class Controller {
    private final CourierService courierService;
    private OrderService order;

    @Autowired
    public Controller(CourierService courierService, OrderService orderService) {
        this.courierService = courierService;
        this.order = orderService;
    }


    //    all the methods with "@RequestMapping()", the path they are mapping to "path = "/courier""
//    and the request type we are sending, "method = RequestMethod.POST".
    @RequestMapping(path = "/courier", method = RequestMethod.POST)

//     In addCourier() method it is taking a new courier details as a payload.
    public ResponseEntity<Courier> addCourier(@RequestBody Courier courier) throws Exception {
        if (courierService.isCourierInformationValid(courier)) {
            Courier newCourier = courierService.addCourier(courier);
            return new ResponseEntity<Courier>(newCourier, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correct Courier info needed");
        }

    }

    @RequestMapping(path = "/couriers")
    public ResponseEntity<List<Courier>> getAllCouriers() {
        List<Courier> couriers = courierService.getAllCouriers();
        return new ResponseEntity<List<Courier>>(couriers, HttpStatus.OK);

    }

//    Path variable will search for the courier on the basis of the id provided in the path variable.
    @RequestMapping(path = "/courier/{id}")
    public ResponseEntity<Courier> getCourier(@PathVariable("id") String id) throws Exception {
//        using try catch block to find courier on the basis of provided id
        try {
            return new ResponseEntity<Courier>(courierService.findCourier(id), HttpStatus.OK);
        } catch (Exception exception) {
//            if courier not found then will throw an error with appropriate http response
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier " + id + " not found");
        }

    }

    @RequestMapping(path = "/courier/{time}/{distance}/{refrigerator}")
    public ResponseEntity<Courier> getBestCourier(@PathVariable("time") String time, @PathVariable("refrigerator") boolean refrigerator, @PathVariable("distance") double distance) throws Exception {
        if (!Order.isValidTime(time) || distance <= 0 || (!refrigerator && refrigerator)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correct Time and Distance are required.");
        }
        Order makeOrder = order.makeOrder(time, refrigerator, distance);
        try {
            return new ResponseEntity<Courier>(courierService.getBestSuitableCourier(makeOrder), HttpStatus.OK);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No courier available for this order");
        }

    }

    @RequestMapping(path = "/courier/{courier_id}", method = RequestMethod.PUT)
    public ResponseEntity<Courier> updateCourier(@PathVariable("courier_id") String id, @RequestBody Courier courierDetails) throws Exception {

        try {
            new ResponseEntity<Courier>(courierService.findCourier(id), HttpStatus.OK);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier " + id + " not found");
        }

        if (!courierService.isCourierInformationValid(courierDetails)) {
            throw new Exception("Appropriate Courier Name & Working Hours are required.");
        }
        return new ResponseEntity<Courier>(courierService.updateCourierDetails(id, courierDetails), HttpStatus.OK);

    }

    @RequestMapping(path = "/courier/{courier_id}", method = RequestMethod.DELETE)
    public String deleteCourier(@PathVariable("courier_id") String id) {
        try {
            courierService.deleteCourier(id);
            return "Courier: " + id + " deleted";
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Courier " + id + " not found");
        }
    }
}
