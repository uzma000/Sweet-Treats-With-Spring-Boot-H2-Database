package com.sweettreats.Sweet_Treats_With_Spring_Boot.web;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.service.CourierService;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(Controller.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private CourierService courierService;
    @MockBean
    private OrderService orderService;
    Courier courier1 = new Courier("id1", "Bobby", "09:00", "13:00", 1.5, 5, true);
    Courier courier2 = new Courier("id2", "Martin", "09:00", "17:00", 1.50, 3.0, false);
    Courier courier3 = new Courier("id3", "Geoff", "10:00", "16:00", 2.00, 4.0, true);


    @Test
    public void shouldCreateMockMVC() {
        assertNotNull(mockMvc);
    }

    @Test
    public void getAllCouriers() throws Exception {
        List<Courier> couriers = new ArrayList<>(Arrays.asList(courier1, courier2, courier3));
        Mockito.when(courierService.getAllCouriers()).thenReturn(couriers);
        this.mockMvc.perform(get("/sweet_treats/couriers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Bobby"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Martin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Geoff"));
        verify(courierService, times(1)).getAllCouriers();
    }

    @Test
    public void findCourier() throws Exception {
        Mockito.when(courierService.findCourier(courier1.getId())).thenReturn(courier1);
        //create a mock Http request to verify the expected result
        this.mockMvc.perform(get("/sweet_treats/courier/id1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bobby"));
        verify(courierService, times(1)).findCourier("id1");
    }


    @Test
    public void getCheapestCourier() throws Exception {

        Order order1 = new Order("orderId1","12:30", true, 4.0);
        Mockito.when(courierService.getBestSuitableCourier(any())).thenReturn(courier1);
        this.mockMvc.perform(get("/sweet_treats/courier/12:30/4.0/true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Bobby")));
    }


    @Test
    public void deleteCourier() throws Exception {
        Mockito.when(courierService.findCourier(courier1.getId())).thenReturn(courier1);
        this.mockMvc.perform(delete("/sweet_treats/courier/id1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(courierService, times(1)).deleteCourier(courier1.getId());
    }


}