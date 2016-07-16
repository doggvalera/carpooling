package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.CarPoolingApplication;
import lv.ctco.entities.Offer;
import lv.ctco.entities.Request;
import lv.ctco.entities.User;
import lv.ctco.repository.OfferRepository;
import lv.ctco.repository.RequestRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.jayway.restassured.RestAssured.put;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class RequestByOfferControllerTest {

    @Autowired
    OfferRepository offerRepository;
    @Autowired
    RequestRepository requestRepository;

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.authentication = preemptive().basic("admin@a.a", "admin");
    }

    @Test
    public void testGetRequestByOffer() throws Exception {
        Offer offer = StandartBuilder.buildOffer();
        Headers headersRequest = given().contentType(JSON).body(offer).when().post(USER_PATH + OFFER_PATH).getHeaders();
        get(headersRequest.getValue("Location") + REQUEST_PATH).then().statusCode(OK);

    }

   /* @Test
    public void testPostRequestForOffer() throws Exception {
        Request request = StandartBuilder.buildRequest();
        Offer offer = StandartBuilder.buildOffer();
        Headers headersOffer = given().contentType(JSON).body(offer).when().post(USER_PATH + OFFER_PATH).getHeaders();
        Headers headersRequest = given().contentType(JSON).body(request).when().post(USER_PATH + REQUEST_PATH).getHeaders();
        String[] headerParts = headersRequest.getValue("Location").split("/");
        given().contentType(JSON).body("").put(OFFER_PATH + "/" + headersOffer.getValue("Location") + "/" + REQUEST_PATH + "/" + headerParts[headerParts.length - 1]).then().statusCode(OK);
    }*/

}
