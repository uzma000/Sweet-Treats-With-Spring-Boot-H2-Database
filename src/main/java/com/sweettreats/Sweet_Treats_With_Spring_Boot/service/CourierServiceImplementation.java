package com.sweettreats.Sweet_Treats_With_Spring_Boot.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Courier;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order;
import com.sweettreats.Sweet_Treats_With_Spring_Boot.repository.CourierRepository;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

import static com.sweettreats.Sweet_Treats_With_Spring_Boot.entity.Order.isValidTime;

@Service

public class CourierServiceImplementation implements CourierService {

    private MongoTemplate mongoTemplate;

    ConnectionString connectionString = new ConnectionString("mongodb+srv://m001-student:m001-mongodb-basics@sandbox.j6gomz9.mongodb.net/?retryWrites=true&w=majority");
    CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
    CodecRegistry fromProviderRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProviderRegistry);
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
            .codecRegistry(pojoCodecRegistry)
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    MongoDatabase database = mongoClient.getDatabase("Sweet_Treats");

    // Getting a Logger with Class Name
    private static final Logger LOGGER = Logger.getLogger(CourierServiceImplementation.class.getName());

    static {
        FileHandler fileHandler = null; // Declare File Handler
        try {
            // Setting name of our file
            fileHandler = new FileHandler(CourierServiceImplementation.class.getSimpleName() + ".log");
            // Setting format of our logs
            fileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Adding a File Handler to LOGGER, so we can see logs in a File.
        LOGGER.addHandler(fileHandler);
    }

    @Autowired
    private CourierRepository courierRepository;


    public boolean isCourierInformationValid(Courier courierDetails) {
        if (courierDetails.getName().isEmpty() || courierDetails.getName().length() < 3 ||
                !isValidTime(String.valueOf((courierDetails.getStartTime()))) ||
                courierDetails.getStartTime() == null ||
                courierDetails.getEndTime() == null ||
                courierDetails.getChargePerMile() < 1 ||
                courierDetails.getMaxDistance() < 1) {
            return false;
        } else {
            return true;
        }
    }

    // Add a courier to couriers database
    @Override
    public Courier addCourier(Courier newCourier) throws Exception {
        if (isCourierInformationValid(newCourier)) {
            LOGGER.log(Level.INFO, "New Courier added: " + newCourier.getName());
            database.getCollection("couriers", Courier.class).insertOne(newCourier);
            return null;
        } else {
            throw new Exception("Correct Courier info needed");
        }
    }

    // Get all Couriers
    @Override
    public List<Courier> getAllCouriers() {
        LOGGER.log(Level.INFO, "Getting All Couriers.");
        return (List<Courier>) courierRepository.findAll();
    }

    // Get a courier with an ID
    @Override
    public Courier findCourier(String id) throws Exception {
        Optional<Courier> courier = courierRepository.findById(id);
        if (courier.isPresent()) {
            LOGGER.log(Level.INFO, "Getting a Courier With ID: " + id);
            return courier.get();
        } else {
            throw new Exception("Courier not found with id: " + id);
        }

    }

    // Check if courier is available for the delivery by using isAfter and isBefore method
    public boolean isAvailable(Order order, Courier courier) {
        return order.getOrderTime().isAfter(LocalTime.parse(courier.getStartTime())) && order.getOrderTime().isBefore(LocalTime.parse(courier.getEndTime())) &&
                order.getDistance() <= courier.getMaxDistance() && order.isRefrigeratorRequire() == courier.isHasRefrigeratorBox();
    }

    //    Checking which courier is the best
    @Override
    public Courier getBestSuitableCourier(Order order) throws Exception {
        List<Courier> couriers = (List<Courier>) courierRepository.findAll();
        LOGGER.log(Level.INFO, "Getting best suitable courier for order: " +
                "\n" + "Order Time: " + order.getOrderTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                "\n" + "Distance Between Shop and Customer: " + order.getDistance() + "miles" +
                "\n" + "Refrigeration Requirement: " + order.isRefrigeratorRequire());

//      If the distance is 0 then will throw an error
        if (order.getDistance() <= 0) {
            LOGGER.log(Level.WARNING, "Distance cannot be less than or equal to 0");
            throw new Exception("Distance is too short for delivery.");
        }
//        we took the earliest and latest courier time to check the accurate order time for delivery
        if (order.getOrderTime().isBefore(LocalTime.parse("09:00", DateTimeFormatter.ofPattern("HH:mm")))
                || order.getOrderTime().isAfter(LocalTime.parse("17:00", DateTimeFormatter.ofPattern("HH:mm")))) {

            LOGGER.log(Level.WARNING, "Order time is not between business hours: 09:00 - 17:00");
            throw new Exception("Order time is outside of working hours.");
        }

        // Getting List of available couriers by using list to reach out every courier easily
        List<Courier> availableCouriers = couriers.stream().filter(courier -> isAvailable(order, courier)).collect(Collectors.toList()); // List of available couriers

//      If Available Couriers list it not empty then perform the following action
        if (!availableCouriers.isEmpty()) {
            // Compare by price per mile
            Comparator<Courier> comparator = Comparator.comparing(courier -> courier.getChargePerMile());
            // Gets the cheapest courier
            Courier cheapestCourier = availableCouriers.stream().min(comparator).get();

            LOGGER.log(Level.INFO, "Best suitable courier for this order is: " + cheapestCourier.getName() +
                    "\n" + "Working Hours: " + cheapestCourier.getStartTime() + " - " + cheapestCourier.getEndTime() +
                    "\n" + "Has Refrigerated Box: " + cheapestCourier.isHasRefrigeratorBox() +
                    "\n" + "Price: £" + cheapestCourier.getChargePerMile() + " per mile" +
                    "\n" + "Total Delivery Price For This Order is: £" + cheapestCourier.getChargePerMile() * order.getDistance());

            return cheapestCourier;
        } else {
            LOGGER.log(Level.WARNING, "No suitable courier found for this order: " +
                    "\n" + "Order Time: " + order.getOrderTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    "\n" + "Refrigerated Box Required: " + order.isRefrigeratorRequire() +
                    "\n" + "Distance Between Customer And Restaurant: " + order.getDistance() + " miles.");

            throw new Exception("No suitable courier found for this order: " +
                    "\n" + "Order Time: " + order.getOrderTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    "\n" + "Refrigerated Box Required: " + order.isRefrigeratorRequire() +
                    "\n" + "Distance Between Customer And Restaurant: " + order.getDistance() + " miles.");

        }

    }

    // Update a courier's Details
    @Override
    public Courier updateCourierDetails(String id, Courier courierDetails) throws Exception {
        try {
            Courier courier = courierRepository.findById(id).get();
            LOGGER.log(Level.INFO, "Updating Courier Details of Id: " + id + " Name: " + courier.getName() +
                    "\n" + "Changing" +
                    "\n" + "Name: " + courier.getName() + " to: " + courierDetails.getName() +
                    "\n" + "Working Hours: " + courier.getStartTime() + " - " + courier.getEndTime() + " to: " + courierDetails.getStartTime() + "-" + courierDetails.getEndTime() +
                    "\n" + "Has Refrigerated Box: " + courier.isHasRefrigeratorBox() + " to: " + courierDetails.isHasRefrigeratorBox() +
                    "\n" + "Price Per Mile: £" + courier.getChargePerMile() + " to: £" + courierDetails.getChargePerMile());

            if (isCourierInformationValid(courierDetails)) {
                courier.setName(courierDetails.getName());
                courier.setStartTime(courierDetails.getStartTime());
                courier.setEndTime(courierDetails.getEndTime());
                courier.setMaxDistance(courierDetails.getMaxDistance());
                courier.setChargePerMile(courierDetails.getChargePerMile());
                courier.setHasRefrigeratorBox(courierDetails.isHasRefrigeratorBox());
            }
            return courierRepository.save(courier);
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Courier: " + id + " is not found.");
            throw new Exception(" No Courier Found with Id: " + id);

        }
    }

    // Delete a courier
    @Override
    public void deleteCourier(String id) throws Exception {
        Optional<Courier> courier = courierRepository.findById(id);
        if (courier.isPresent()) {
            LOGGER.log(Level.INFO, "Courier: " + id + " has been Deleted.");
            courierRepository.deleteById(id);
        } else {
            LOGGER.log(Level.INFO, "Courier: " + id + " is not found.");
            throw new Exception("Courier " + id + " not found");
        }
    }
}

