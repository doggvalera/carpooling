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
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class OfferControllerTest {

    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final String OFFER_PATH = "/offers";
    public static final String JSON = "application/json";

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
    public void testGetOneNotFount() {
        get(OFFER_PATH + "/-1").then().statusCode(NOT_FOUND);
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

        get(OFFER_PATH + "/bydriver/" + user.getId()).then().statusCode(OK);
    }
}
