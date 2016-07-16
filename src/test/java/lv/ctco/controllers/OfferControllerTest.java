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

import static io.restassured.RestAssured.*;
import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8092")

public class OfferControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8092;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.authentication = preemptive().basic("admin@a.a", "admin");
    }

    @Test
    public void testGetAllOffersOK() throws Exception{
        get(OFFER_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetOfferByIDFailed() throws Exception{
        get(OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetOfferByIDOK() throws Exception{
        Offer offer = new Offer();

        Headers headersRequest = given().contentType(JSON).body(offer).when().post(USER_PATH + OFFER_PATH).getHeaders();

        get(headersRequest.getValue("Location")).then().statusCode(OK);
    }

    @Test
    public void testDeleteOfferByIDFailed() throws Exception{
        delete(OFFER_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testDeleteOfferByIDOK() throws Exception{
        Offer offer = new Offer();

        Headers headersRequest = given().contentType(JSON).body(offer).when().post(USER_PATH + OFFER_PATH).getHeaders();

        delete(headersRequest.getValue("Location")).then().statusCode(OK);
    }

}
