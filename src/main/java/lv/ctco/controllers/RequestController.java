package lv.ctco.controllers;


import lv.ctco.entities.Request;
import lv.ctco.entities.User;
import lv.ctco.repository.RequestRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "rides/requests")
public class RequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> requestsFindAll() {
        requestRepository.findAll();
        return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "/{ID}", method = RequestMethod.GET)
    public ResponseEntity<?> requestById(@PathVariable("ID") int id) {
        if (requestRepository.exists(id)) {
            return new ResponseEntity<>(requestRepository.findOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(requestRepository.findOne(id), HttpStatus.NOT_FOUND);
        }
    }


    @Transactional
    @RequestMapping(path = "/bypassenger/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> requestByPassengerName(@PathVariable("passengerName") int id) {
        if (userRepository.exists(id)) {
            User user = userRepository.findOne(id);
            List<Request> requestList = requestRepository.getRequestsByPassenger(user);
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userRepository.findOne(id), HttpStatus.NOT_FOUND);
        }
    }



    @Transactional
    @RequestMapping(path = "/{ID}", method = RequestMethod.POST)
    public ResponseEntity<?> requestAdd(@PathVariable("ID") int id,@RequestBody Request request) {
        User user = userRepository.findOne(id);
        request.setUser(user);
        requestRepository.save(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "/{ID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> requestDeleteById(@PathVariable("ID") int id) {
        if (requestRepository.exists(id)) {
            requestRepository.delete(id);
            return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.NOT_FOUND);
        }
    }
}
