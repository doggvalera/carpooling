package lv.ctco.controllers;

import lv.ctco.entities.User;
import lv.ctco.entities.UserCredentials;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> userFindAll() {
        userRepository.findAll();
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> userAdd(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserCredentials userCredentials) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        //userRepository.save(user);

        
        List<User> userByEmail = userRepository.findUserByEmail(userCredentials.getEmail(), userCredentials.getPassword());
        return new ResponseEntity<>(userByEmail.size() != 0, HttpStatus.OK);
    }



}
