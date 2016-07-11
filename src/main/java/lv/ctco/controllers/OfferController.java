package lv.ctco.controllers;

import lv.ctco.entities.Offer;
import lv.ctco.entities.User;
import lv.ctco.repository.OfferRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rides/offer")
public class OfferController {

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOffers() {
        offerRepository.findAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOffersById(@PathVariable("id") int id) {
        offerRepository.findOne(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/bydriver/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOffersByDriverID(@PathVariable("id") int id) {
        User user = userRepository.findOne(id);
        List<Offer> offerList = offerRepository.getByDriverID(user);
        return new ResponseEntity<>(offerList, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postOffer(@RequestBody Offer offer) {
        offerRepository.save(offer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOfferById(@PathVariable("id") int id) {
        offerRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
