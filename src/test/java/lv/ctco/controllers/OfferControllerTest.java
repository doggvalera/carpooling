package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.CarPoolingApplication;
import lv.ctco.entities.Offer;
import lv.ctco.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class OfferControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetNotFound() {
        get("/something").then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetAllOK() {
        get(OFFER_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetOneNotFound() {
        get(OFFER_PATH + "/-1").then().statusCode(NOT_FOUND);
    }

//    @Test
//    public void testGetOneOK() {
//        User user = new User();
//        user.setName("name");
//        user.setSurname("surname");
//        user.setPassword("password");
//        user.setEmail("mail");
//
//        Headers headersU = given().contentType(JSON).body(user).when().post(USER_PATH).getHeaders();
//
//        Offer offer = new Offer();
//        offer.setUser(user);
//        offer.setPassengersAmount(3);
//
//        Headers headerO = given().contentType(JSON).body(offer).when().post(OFFER_PATH + "/" + user.getId()).getHeaders();
//
//        get(headerO.getValue("Location")).then().statusCode(OK);
//    }

    @Test
    public void testGetOneNotFount() {
        get(OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetOneOK() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setPassword("password");
        user.setEmail("mail");
        Offer offer = new Offer();
        offer.setUser(user);
        offer.setPassengersAmount(3);

        get(OFFER_PATH + BY_DRIVER_PATH + "/" + user.getId()).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testPostNotFound() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setPassword("password");
        user.setEmail("mail");
        user.setId(1);
        Offer offer = new Offer();
        offer.setUser(user);
        offer.setPassengersAmount(3);

        given().contentType(JSON).body(offer).when().post(OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);

    }

}
