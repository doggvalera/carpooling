package lv.ctco.controllers;

import lv.ctco.entities.User;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> userFindAll() {
        userRepository.findAll();
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> userAdd(@RequestBody User user, UriComponentsBuilder b) {
        UriComponents uriComponents = b.path("/users" + "/{id}").buildAndExpand(user.getId());

        userRepository.save(user);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }



}
