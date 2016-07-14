package lv.ctco.controllers;

import lv.ctco.HeaderBuilder;
import lv.ctco.entities.Coordinate;
import lv.ctco.entities.DateTimeRange;
import lv.ctco.entities.Offer;
import lv.ctco.entities.User;
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

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(path = USER_PATH + "/{uid}" + OFFER_PATH)
public class OfferByUserController {

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> postOffer(@PathVariable("uid") int id, @RequestBody Offer offer, UriComponentsBuilder b) {

        User user = userRepository.findOne(id);
        if (userRepository.exists(id)) {
            offer.setUser(user);
            offerRepository.save(offer);

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, USER_PATH + "/{uid}" + OFFER_PATH + "/{id}",user.getId(), offer.getId());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/without", method = RequestMethod.POST)
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

    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> postOfferForUser(@PathVariable("uid") String userId2, @RequestParam String earliestDeparture,
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

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, OFFER_PATH + "/{id}", offer.getId());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOfferById(@PathVariable("id") int id, @PathVariable("uid") int uId, @RequestBody Offer offer) {
        if (offerRepository.exists(id)) {
            Offer editedOffer = offerRepository.findOne(id);
            editedOffer.setCarDescription(offer.getCarDescription());
            editedOffer.setCoordinateTo(offer.getCoordinateTo());
            editedOffer.setCoordinateFrom(offer.getCoordinateFrom());
            editedOffer.setDate(offer.getDate());
            editedOffer.setDelayTime(offer.getDelayTime());
            editedOffer.setPassengersAmount(offer.getPassengersAmount());
            editedOffer.setTime(offer.getTime());
            User user = userRepository.findOne(uId);
            editedOffer.setUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
