package lv.ctco.controllers;

import lv.ctco.HeaderBuilder;
import lv.ctco.LoginContext;
import lv.ctco.entities.*;

import lv.ctco.repository.OfferRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(path = USER_PATH + OFFER_PATH)
public class OfferByUserController {

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginContext loginContext;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOffersForUser() {
        User user = loginContext.getCurrentUser();
        if (user != null) {
            List<Offer> offerList = offerRepository.getByDriver(user);
            return new ResponseEntity<>(offerList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Transactional
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> postOffer(@RequestBody Offer offer, UriComponentsBuilder b) {
        User user = loginContext.getCurrentUser();
        if (user != null) {
            offer.setUser(user);
            offer.setRequests(new ArrayList<>());
            offerRepository.save(offer);

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, OFFER_PATH + "/{id}",user.getId(), offer.getId());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> postOfferForUser(@RequestParam String earliestDeparture,
                                              String latestDeparture,
                                              String latitudeFrom,
                                              String longitudeFrom,
                                              String latitudeTo,
                                              String longitudeTo,
                                              String car,
                                              UriComponentsBuilder b) {
        User user = loginContext.getCurrentUser();
        if (user != null) {
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

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, OFFER_PATH + "/{id}", offer.getId());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOfferById(@PathVariable("id") int id, @RequestBody Offer offer) {
        if (offerRepository.exists(id)) {
            Offer editedOffer = offerRepository.findOne(id);
            editedOffer.setCarDescription(offer.getCarDescription());
            editedOffer.setCoordinateTo(offer.getCoordinateTo());
            editedOffer.setCoordinateFrom(offer.getCoordinateFrom());
            editedOffer.setDate(offer.getDate());
            editedOffer.setPassengersAmount(offer.getPassengersAmount());
            User user = loginContext.getCurrentUser();
            editedOffer.setUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
