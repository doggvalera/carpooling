package lv.ctco.controllers;

import lv.ctco.entities.Ride;
import lv.ctco.entities.User;
import lv.ctco.repository.RideRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(path = RIDE_PATH)
public class RideController {

    @Autowired
    RideRepository rideRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> showAllRides() {
        rideRepository.findAll();
        return new ResponseEntity<>(rideRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = BY_DRIVER_PATH + "/{ID}", method = RequestMethod.GET)
    public ResponseEntity<?> showRidesByDriver(@PathVariable("ID") int id) {
        User user = userRepository.findOne(id);
        List<Ride> rideList = rideRepository.getByDriver(user);
        return new ResponseEntity<>(rideList, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = BY_PASSANGER_PATH + "/{ID}", method = RequestMethod.GET)
    public ResponseEntity<?> showRidesPassenger(@PathVariable("ID") int id) {
        rideRepository.findAll();
        User user = userRepository.findOne(id);
        List<Ride> rideList = new ArrayList<>(); //rideRepository.getByPassenger(user);
        return new ResponseEntity<>(rideList, HttpStatus.OK);
    }
}
