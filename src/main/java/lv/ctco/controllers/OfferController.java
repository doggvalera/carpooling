package lv.ctco.controllers;

import com.jayway.restassured.internal.http.HTTPBuilder;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
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
    @Autowired
    LoginContext loginContext;

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

    @RequestMapping(path = USER_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getOffersForUser() {
        User user = loginContext.getCurrentUser();
        if (user != null) {
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



}
