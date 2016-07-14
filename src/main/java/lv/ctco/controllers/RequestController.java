package lv.ctco.controllers;


import lv.ctco.entities.Coordinate;
import lv.ctco.entities.DateTimeRange;
import lv.ctco.entities.Request;
import lv.ctco.entities.User;
import lv.ctco.repository.RequestRepository;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/requests")

public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllRequests() {
        requestRepository.findAll();
        return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "/{requestId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestById(@PathVariable("requestId") int id) {
        if (requestRepository.exists(id)) {
            return new ResponseEntity<>(requestRepository.findOne(id), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/users/{userId2}", method = RequestMethod.GET)

    public ResponseEntity<?> getRequestsByUserId(@PathVariable("userId2") int id) {
        if (userRepository.exists(id)) {
            User user = userRepository.findOne(id);
            List<Request> requestList = requestRepository.selectRequestsByPassenger(user);
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Transactional
    @RequestMapping(path = "/users/{userId2}", method = RequestMethod.POST)
    public ResponseEntity<?> postRequestForUser(@PathVariable("userId2") int userId, @RequestBody Request request,
                                                     UriComponentsBuilder b) {
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            request.setUser(user);
            requestRepository.save(request);

            UriComponents uriComponents =
                    b.path("/requests/{id}").buildAndExpand(request.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Transactional
    @RequestMapping(path = "/{requestId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRequestById(@PathVariable("requestId") int requestId) {
        if ( requestRepository.findOne(requestId) == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        requestRepository.delete(requestId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RequestMapping(path = "/users/{userId2}", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> postRequestForUser(@PathVariable("userId2") String userId2, @RequestParam String earliestDeparture,
                                                String latestDeparture,
                                                String latitudeFrom,
                                                String longitudeFrom,
                                                String latitudeTo,
                                                String longitudeTo,
                                                String radius,
                                                UriComponentsBuilder b) {
        int userId = Integer.parseInt(userId2);
        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            Request request = new Request();
            request.setUser(user);


            Coordinate coordinateFrom = new Coordinate();
            Coordinate coordinateTo = new Coordinate();

            coordinateFrom.setLatitude(Double.parseDouble(latitudeFrom));
            coordinateFrom.setLongitude(Double.parseDouble(longitudeFrom));

            coordinateTo.setLatitude(Double.parseDouble(latitudeTo));
            coordinateTo.setLongitude(Double.parseDouble(longitudeTo));

            request.setCoordinateFrom(coordinateFrom);
            request.setCoordinateTo(coordinateTo);

            request.setRadius(Double.parseDouble(radius));

            //e.g. 1986-04-08 12:30

            DateTimeRange dateTimeRange = new DateTimeRange();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime earliestDateTime = LocalDateTime.parse(earliestDeparture, formatter);
            LocalDateTime latestDateTime = LocalDateTime.parse(latestDeparture, formatter);

            dateTimeRange.setEarliestDeparture(earliestDateTime);
            dateTimeRange.setLatestDeparture(latestDateTime);

            request.setDate(dateTimeRange);
            requestRepository.save(request);

            UriComponents uriComponents =
                    b.path("/requests/{id}").buildAndExpand(request.getId());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
