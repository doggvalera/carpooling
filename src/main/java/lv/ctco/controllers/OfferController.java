package lv.ctco.controllers;

import com.jayway.restassured.internal.http.HTTPBuilder;
import lv.ctco.HeaderBuilder;
import lv.ctco.entities.*;
import lv.ctco.repository.OfferRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(path = OFFER_PATH)
public class OfferController {

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOffers() {
        return new ResponseEntity<>(offerRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOfferById(@PathVariable("id") int id) {
        if (offerRepository.exists(id)) {
            return new ResponseEntity<>(offerRepository.findOne(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOffersByDriverID(@PathVariable("id") int id) {
        if (userRepository.exists(id)) {
            User user = userRepository.findOne(id);
            List<Offer> offerList = offerRepository.getByDriverID(user);
            return new ResponseEntity<>(offerList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOfferById(@PathVariable("id") int id) {
        if (offerRepository.exists(id)) {
            offerRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/users/{userId}/without", method = RequestMethod.POST)
    public ResponseEntity<?> postOfferForUser(@PathVariable("userId") int userId, @RequestBody Offer offer,
                                                UriComponentsBuilder b) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            offer.setUser(user);
            offerRepository.save(offer);

            UriComponents uriComponents =
                    b.path("/offers/{id}").buildAndExpand(offer.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/users/{userId2}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> postOfferForUser(@PathVariable("userId2") String userId2, @RequestParam String earliestDeparture,
                                                String latestDeparture,
                                                String latitudeFrom,
                                                String longitudeFrom,
                                                String latitudeTo,
                                                String longitudeTo,
                                                String car,
                                                UriComponentsBuilder b) {
        int userId = Integer.parseInt(userId2);
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            Offer offer = new Offer();
            offer.setUser(user);

            Coordinate coordinateFrom = new Coordinate();
            Coordinate coordinateTo = new Coordinate();

            coordinateFrom.setLatitude(Double.parseDouble(latitudeFrom));
            coordinateFrom.setLongitude(Double.parseDouble(longitudeFrom));

            coordinateTo.setLatitude(Double.parseDouble(latitudeTo));
            coordinateTo.setLongitude(Double.parseDouble(longitudeTo));

            offer.setCoordinateFrom(coordinateFrom);
            offer.setCoordinateTo(coordinateTo);

            offer.setCarDescription(car);

            //e.g. 1986-04-08 12:30

            DateTimeRange dateTimeRange = new DateTimeRange();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime earliestDateTime = LocalDateTime.parse(earliestDeparture, formatter);
            LocalDateTime latestDateTime = LocalDateTime.parse(latestDeparture, formatter);

            dateTimeRange.setEarliestDeparture(earliestDateTime);
            dateTimeRange.setLatestDeparture(latestDateTime);

            offer.setDate(dateTimeRange);
            offerRepository.save(offer);

            UriComponents uriComponents =
                    b.path("/offers/{id}").buildAndExpand(offer.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
