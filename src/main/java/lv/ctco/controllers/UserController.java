package lv.ctco.controllers;

import lv.ctco.HeaderBuilder;
import lv.ctco.entities.User;
import lv.ctco.entities.UserCredentials;
import lv.ctco.entities.UserRoles;
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
import java.util.Arrays;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static lv.ctco.Consts.*;

@RestController
@RequestMapping(USER_PATH)
public class UserController {

    @Autowired
    UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;


    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> userFindAll() {
        userRepository.findAll();
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(path = WITHOUT_INPUT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> userAddWithoutParams(@RequestBody User user, UriComponentsBuilder b) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        HttpHeaders responseHeaders = HeaderBuilder.buildHeader(b, USER_PATH + "/{id}", user.getId());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> userAdd(@RequestParam String name,
                                     String surname,
                                     String email,
                                     String password) {
        if (userRepository.findUserByEmail(email) == null) {
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(password);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User with this email already exists", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/api/reg",method = RequestMethod.POST)
    public ResponseEntity<?> userAdd(@RequestBody  User user) {
        if (userRepository.findUserByEmail(user.getEmail()) == null) {
            UserRoles userRoles = new UserRoles();
            userRoles.setRole("USER");
            user.setUserRoles(Arrays.asList(userRoles));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
