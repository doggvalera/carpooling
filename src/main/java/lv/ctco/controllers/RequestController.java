package lv.ctco.controllers;


import lv.ctco.entities.Request;
import lv.ctco.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
<<<<<<< HEAD
@RequestMapping(path = "/requests")
=======
@RequestMapping(path = "/rides/requests")
>>>>>>> 848f94c3d521f76240a2b9374bbf7af86a078cdd
public class RequestController {

    @Autowired
    RequestRepository requestRepository;

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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> studentsPost(@RequestBody Request request) {
        requestRepository.save(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = "/{ID}", method = RequestMethod.DELETE)
    public ResponseEntity<?> studentDeleteById(@PathVariable("ID") int id) {
        if (requestRepository.exists(id)) {
            requestRepository.delete(id);
            return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(requestRepository.findAll(), HttpStatus.NOT_FOUND);
        }
    }
}
