package lv.ctco.controllers;


import lv.ctco.HeaderBuilder;
import lv.ctco.LoginContext;
import lv.ctco.entities.*;
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

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(REQUEST_PATH)

public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginContext loginContext;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllRequests() {
        requestRepository.findAll();
        return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = USER_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getRequestsForUser() {
        User user = loginContext.getCurrentUser();
        if (user != null) {
            List<Request> requestList = requestRepository.selectRequestsByPassenger(user);
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{requestId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestById(@PathVariable("requestId") int id) {
        if (requestRepository.exists(id)) {
            return new ResponseEntity<>(requestRepository.findOne(id), HttpStatus.OK);
        }
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

}
