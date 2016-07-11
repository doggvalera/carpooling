package lv.ctco.controllers;

import lv.ctco.Entities.Ride;
import lv.ctco.Entities.User;
import lv.ctco.repository.RideRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(name = "/rides")
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
    @RequestMapping(path = "/bydriver/{ID}", method = RequestMethod.GET)
    public ResponseEntity<?> showRidesByDriver(@PathVariable("ID") int id) {
        User user = userRepository.findOne(id);
        List<Ride> rideList = rideRepository.getByDriver(user);
        return new ResponseEntity<>(rideList, HttpStatus.OK);
    }

//    @Transactional
//    @RequestMapping(path = "/bypassanger/{ID}", method = RequestMethod.GET)
//    public ResponseEntity<?> showRidesPassenger(@PathVariable("ID") int id) {
//        rideRepository.findAll();
//        List<Ride> passengerList = new ArrayList<>();
//        for (int i = 0; i < rideRepository.findAll().size(); i++) {
//            for(int j=0; j )
//            if (rideRepository.findAll().get(i).getRequestList().get(i).getId() == id) {
//                passengerList.add(rideRepository.findOne(i));
//            }
//        }
//        return new ResponseEntity<>(passengerList, HttpStatus.OK);
//    }

//}
}
