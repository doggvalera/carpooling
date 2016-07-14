package lv.ctco.controllers;

import com.jayway.restassured.internal.http.HTTPBuilder;
import lv.ctco.HeaderBuilder;
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

    @RequestMapping(path = "/bydriver/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOffersByDriverID(@PathVariable("id") int id) {
        if (userRepository.exists(id)) {
            User user = userRepository.findOne(id);
            List<Offer> offerList = offerRepository.getByDriverID(user);
            return new ResponseEntity<>(offerList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Transactional
    @RequestMapping(path = "/{uid}", method = RequestMethod.POST)
    public ResponseEntity<?> postOffer(@PathVariable("uid") int id, @RequestBody Offer offer, UriComponentsBuilder b) {

        User user = userRepository.findOne(id);
        if (userRepository.exists(id)) {
            offer.setUser(user);
            offerRepository.save(offer);

            HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, OFFER_PATH + "/{id}", offer.getId());

            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @RequestMapping(path = "/{id}/{uId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOfferById(@PathVariable("id") int id, @PathVariable("id") int uId, @RequestBody Offer offer) {
        if (offerRepository.exists(id)) {
            Offer editedOffer = offerRepository.findOne(id);
            editedOffer.setCarDescription(offer.getCarDescription());
            editedOffer.setCoordinateFrom(offer.getCoordinateFrom());
            editedOffer.setCoordinateTo(offer.getCoordinateTo());
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

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOfferById(@PathVariable("id") int id) {
        offerRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
