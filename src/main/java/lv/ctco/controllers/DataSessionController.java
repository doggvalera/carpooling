//package lv.ctco.controllers;
//
//import lv.ctco.repository.RequestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//
///**
// * Created by alexander.kairis on 7/12/2016.
// */
//@RestController
//@RequestMapping(path = "/date")
//public class DataSessionController {
//
//    @Autowired
//    RequestRepository requestRepository;
//
//    @Transactional
//    @RequestMapping(path = "/{id}/date", method = RequestMethod.POST)
//    public ResponseEntity<?> addDate(@PathVariable("id") int id,
//                                     @RequestBody LocalDateTime date) {
//        if (requestRepository.exists(id)){
//            requestRepository.findOne(id).getDate().setEarliestDeparture(date);
//            requestRepository.findOne(id).getDate().setLatestDeparture(date);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}
