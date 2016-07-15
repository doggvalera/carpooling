package lv.ctco.controllers;

import lv.ctco.HeaderBuilder;
import lv.ctco.RequestOfferMatcher;
import lv.ctco.entities.Offer;
import lv.ctco.entities.Request;
import lv.ctco.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(path = REQUEST_PATH + "/{rid}" + OFFER_PATH)
public class OfferByRequestController {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestOfferMatcher requestOfferMatcher;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOffersByRequest(@PathVariable("rid") int id) {
        Request request = requestRepository.findOne(id);
        List<Offer> offers = requestOfferMatcher.findOffersByRequest(request);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

}
