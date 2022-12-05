package com.sweettreats.Sweet_Treats_With_Spring_Boot.repository;

import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import org.springframework.data.mongodb.repository.MongoRepository;
//Courier repository interface extends "JpaRepository" Interface which takes two parameters
// the entity class and type of it's unique parameter. In this case Courier & Long(which is type of Id).

public interface CourierRepository extends MongoRepository<Courier, String> {
}
