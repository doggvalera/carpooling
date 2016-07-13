package lv.ctco.controllers;


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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(path = "/requests",method = RequestMethod.GET)
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
    @RequestMapping(path = "/users/{userId}/requests", method = RequestMethod.GET)
    public ResponseEntity<?> getRequestsByUserId(@PathVariable("userId") int id) {
        if (userRepository.exists(id)) {
            User user = userRepository.findOne(id);
            List<Request> requestList = requestRepository.selectRequestsByPassenger(user);
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/users/{userId}/requests", method = RequestMethod.POST)
    public ResponseEntity<?> postRequestForPassenger(@PathVariable("userId") int userId, @RequestBody Request request,
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
    @RequestMapping(path = "/users/{userId}/requests/{requestId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRequestById(@PathVariable("userId") int userId, @PathVariable("requestId") int requestId) {

        if (userRepository.exists(userId)) {
            User user = userRepository.findOne(userId);
            List<Request> requestList = requestRepository.selectRequestsByPassenger(user);
            Request request = requestList.stream().filter((s) -> s.getId() == requestId).findFirst().get();
            requestList.remove(request);
            requestRepository.save(requestList);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
