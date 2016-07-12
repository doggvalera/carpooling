package lv.ctco.controllers;

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

@RestController

@RequestMapping(path = "/offers")
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
    public ResponseEntity<?> getOffersById(@PathVariable("id") int id) {
        if (offerRepository.exists(id)) {
            return new ResponseEntity<>(offerRepository.findOne(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/bydriver/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOffersByDriverID(@PathVariable("id") int id) {
        if (offerRepository.exists(id)) {
            User user = userRepository.findOne(id);
            List<Offer> offerList = offerRepository.getByDriverID(user);
            return new ResponseEntity<>(offerList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Transactional
    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> postOffer(@PathVariable("id") int id, @RequestBody Offer offer) {


        User user = userRepository.findOne(id);
        if (user.getId()==id) {
            offer.setUser(user);
            offerRepository.save(offer);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOfferById(@PathVariable("id") int id, @RequestBody Offer offer) {
        if (offerRepository.exists(id)) {
            Offer editedOffer = offerRepository.findOne(id);
            editedOffer.setCarDescription(offer.getCarDescription());
            editedOffer.setCoordinate(offer.getCoordinate());
            editedOffer.setDate(offer.getDate());
            editedOffer.setDelayTime(offer.getDelayTime());
            editedOffer.setPassengersAmount(offer.getPassengersAmount());
            editedOffer.setTime(offer.getTime());
            editedOffer.setUser(offer.getUser());
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
