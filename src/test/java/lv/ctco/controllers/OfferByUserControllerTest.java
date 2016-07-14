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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class OfferByUserControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testPostOfferNotFound() throws Exception {
        Offer offer = new Offer();
        offer.setPassengersAmount(3);

        given().contentType(JSON).body(offer).when().post(USER_PATH + BAD_ID + OFFER_PATH).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testPostOfferOK() throws Exception {
        User user = StandartBuilder.buildUser();
        Headers headersUser = given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).getHeaders();

        Offer offer = new Offer();
        offer.setPassengersAmount(3);

        given().contentType(JSON).body(offer).when().post(headersUser.getValue("Location") + OFFER_PATH).then().statusCode(CREATED);

    }

    @Test
    public void testUpdateOfferByIdOK() throws Exception {
        User user = StandartBuilder.buildUser();
        Headers headersUser = given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).getHeaders();

        Offer offer = new Offer();
        offer.setPassengersAmount(3);

        Headers headersOffer = given().contentType(JSON).body(offer).when().post(headersUser.getValue("Location") + OFFER_PATH).getHeaders();

        Offer offer2 = new Offer();
        offer.setUser(user);
        offer.setPassengersAmount(5);

        String[] paths = headersOffer.getValue("Location").split("/");
        given().contentType(JSON).body(offer2).when().put(headersUser.getValue("Location") + "/" + paths[paths.length-2] + "/" + paths[paths.length-1]).then().statusCode(OK);
    }

    @Test
    public void testUpdateOfferByIdFailed() throws Exception {
        User user = StandartBuilder.buildUser();
        Headers headersUser = given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).getHeaders();

        Offer offer = new Offer();
        offer.setUser(user);
        offer.setPassengersAmount(3);

        Headers headersOffer = given().contentType(JSON).body(offer).when().post(headersUser.getValue("Location") + OFFER_PATH).getHeaders();

        Offer offer2 = new Offer();
        offer.setUser(user);
        offer.setPassengersAmount(5);

        given().contentType(JSON).body(offer2).when().put(USER_PATH + BAD_ID + OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
        given().contentType(JSON).body(offer2).when().put(headersUser.getValue("Location") + OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }
}