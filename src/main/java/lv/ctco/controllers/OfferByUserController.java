package lv.ctco.controllers;

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
import org.springframework.web.util.UriComponentsBuilder;

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
