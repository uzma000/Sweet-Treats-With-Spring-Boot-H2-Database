package com.sweettreats.Sweet_Treats_With_Spring_Boot.repository;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
