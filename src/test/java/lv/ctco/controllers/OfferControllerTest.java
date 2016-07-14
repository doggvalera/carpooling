package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.CarPoolingApplication;
import lv.ctco.entities.Offer;
import lv.ctco.entities.Request;
import lv.ctco.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class OfferControllerTest {

    @Test
    public void testGetAllOffersOK() throws Exception{
        get(OFFER_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetOfferByIDFailed() throws Exception{
        get(OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetRequestByIDOK() throws Exception{
        User user = StandartBuilder.buildUser();
        Headers headersUser = given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).getHeaders();

        Offer offer = new Offer();
        offer.setUser(user);

        Headers headersRequest = given().contentType(JSON).body(offer).when().post(headersUser.getValue("Location") + OFFER_PATH).getHeaders();

        get(headersRequest.getValue("Location")).then().statusCode(OK);
    }

    @Test
    public void testDeleteRequestByIDFailed() throws Exception{
        delete(OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testDeleteRequestByIDOK() throws Exception{
        User user = StandartBuilder.buildUser();
        Headers headersUser = given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).getHeaders();

        Offer offer = new Offer();
        offer.setUser(user);

        Headers headersRequest = given().contentType(JSON).body(offer).when().post(headersUser.getValue("Location") + OFFER_PATH).getHeaders();

        delete(headersRequest.getValue("Location")).then().statusCode(OK);
    }

}
