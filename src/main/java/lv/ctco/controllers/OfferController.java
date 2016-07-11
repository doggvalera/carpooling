package lv.ctco.controllers;

import lv.ctco.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/rides/offer")
public class OfferController {

    @Autowired
    OfferRepository offerRepository;

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
        offerRepository.findOne(id);

//        List<Offer> driversByID = new ArrayList<>();
//        for (int i = 0; i < offerRepository.findAll().size(); i++) {
//            if (offerRepository.findAll().get(i).getId() == id) {
//                driversByID.add(offerRepository.findOne(i));
//            }
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
