package com.sweettreats.Sweet_Treats_With_Spring_Boot;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.repository.CourierRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SweetTreatsWithSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SweetTreatsWithSpringBootApplication.class, args);
		System.out.println("Sweet Treats App Started");
	}
//	    @Bean
//    public CommandLineRunner demo(CourierRepository courierRepository) {
//        return (args) -> {
//            courierRepository.save(new Courier("Bobby", "09:00", "13:00", 1.75,5.0, true));
//            courierRepository.save(new Courier("Martin", "09:00", "17:00",1.50, 3.0, false));
//            courierRepository.save(new Courier("Geoff",  "10:00", "16:00", 2.00, 4.0,true));
//            courierRepository.save(new Courier("John",  "10:00", "16:00",1.25, 3.0, true));
//
//            for (Courier courier : courierRepository.findAll()) {
//                System.out.println("Courier is:" + courier.toString());
//            }
//        };
//    }

}
