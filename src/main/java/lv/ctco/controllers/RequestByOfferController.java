package lv.ctco.controllers;

import lv.ctco.HeaderBuilder;
import lv.ctco.RequestOfferMatcher;
import lv.ctco.entities.Offer;
import lv.ctco.entities.Request;
import lv.ctco.repository.OfferRepository;
import lv.ctco.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static lv.ctco.Consts.OFFER_PATH;
import static lv.ctco.Consts.REQUEST_PATH;
    @RestController
    @RequestMapping(path = OFFER_PATH + "/{oid}" + REQUEST_PATH)
    public class RequestByOfferController {
        @Autowired
        OfferRepository offerRepository;
        @Autowired
        RequestRepository requestRepository;
        @Autowired
        RequestOfferMatcher requestOfferMatcher;

        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<?> getRequestByOffer(@PathVariable("oid") int id) {
            Offer offer = offerRepository.findOne(id);
            List<Request> requests = offer.getRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        }

        @Transactional
        @RequestMapping(path = "/{rid}", method = RequestMethod.PUT)
        public ResponseEntity<?> postRequestForOffer(@PathVariable("rid") int rid,
                                                    @PathVariable("oid") int id) {
            Offer offer = offerRepository.findOne(id);
            Request request = requestRepository.getOne(rid);
            if(offer != null){
                offer.add(request);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
