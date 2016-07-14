package lv.ctco.controllers;

import lv.ctco.HeaderBuilder;
import lv.ctco.LoginContext;
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
import java.util.List;

import static lv.ctco.Consts.OFFER_PATH;
import static lv.ctco.Consts.REQUEST_PATH;
import static lv.ctco.Consts.USER_PATH;

@RestController
@RequestMapping(USER_PATH + REQUEST_PATH)
public class RequestByUserController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginContext loginContext;

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postRequestForUser(@RequestBody Request request,
                                                UriComponentsBuilder b) {
        User user = loginContext.getCurrentUser();
        if (user != null) {
            request.setUser(user);
            requestRepository.save(request);

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, REQUEST_PATH + "/{id}", request.getId());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRequestsForUser() {
        User user = loginContext.getCurrentUser();
        if (user != null) {
            List<Request> requestList = requestRepository.selectRequestsByPassenger(user);
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> postRequestForUser(@RequestParam String earliestDeparture,
                                                String latestDeparture,
                                                String latitudeFrom,
                                                String longitudeFrom,
                                                String latitudeTo,
                                                String longitudeTo,
                                                String radius,
                                                UriComponentsBuilder b) {
        User user = loginContext.getCurrentUser();
        if (user != null) {
            Request request = new Request();
            request.setUser(user);

            Coordinate coordinateFrom = buildCoordinate(latitudeFrom, longitudeFrom);
            Coordinate coordinateTo = buildCoordinate(latitudeTo, longitudeTo);

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

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, REQUEST_PATH + "/{id}", request.getId());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Coordinate buildCoordinate(String latitude, String longitude) {
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(Double.parseDouble(latitude));
        coordinate.setLongitude(Double.parseDouble(longitude));
        return coordinate;
    }
}
