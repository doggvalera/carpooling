package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lv.ctco.CarPoolingApplication;
import lv.ctco.RequestOfferMatcher;
import lv.ctco.entities.*;
import lv.ctco.repository.OfferRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.preemptive;
import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class RequestOfferMatcherTest {

    @Autowired
    RequestOfferMatcher requestOfferMatcher;
    @Autowired
    OfferRepository offerRepository;

    DateTimeRange dateTimeRange = new DateTimeRange() {{
        setEarliestDeparture(LocalDateTime.of(2000, Month.NOVEMBER, 23, 11, 23));
        setLatestDeparture(LocalDateTime.of(2000, Month.NOVEMBER, 23, 13, 23));
    }};
    DateTimeRange dateTimeRange2 = new DateTimeRange() {{
        setEarliestDeparture(LocalDateTime.of(2000, Month.NOVEMBER, 23, 12, 0));
        setLatestDeparture(LocalDateTime.of(2000, Month.NOVEMBER, 23, 12, 59));
    }};
    DateTimeRange dateTimeRange3 = new DateTimeRange() {{
        setEarliestDeparture(LocalDateTime.of(2001, Month.NOVEMBER, 23, 12, 0));
        setLatestDeparture(LocalDateTime.of(2001, Month.NOVEMBER, 23, 12, 59));
    }};
    Coordinate coordinateTo = new Coordinate(){{
        setLatitude(200);
        setLatitude(200);
    }};
    Coordinate coordinateFrom = new Coordinate(){{
        setLatitude(100);
        setLatitude(100);
    }};

    Request request = new Request() {{
        setDate(dateTimeRange);
        setCoordinateTo(coordinateTo);
        setCoordinateFrom(coordinateFrom);
        setRadius(10);
    }};

    Offer offer = new Offer(){{
        setCoordinateTo(coordinateTo);
        setCoordinateFrom(coordinateFrom);
        setCarDescription("bla bla bla");
        setDate(dateTimeRange2);
        setPassengersAmount(2);
        setRequests(new ArrayList<>());
    }};

    Offer offer1 = new Offer(){{
        setCoordinateTo(coordinateTo);
        setCoordinateFrom(coordinateFrom);
        setCarDescription("bla bla bla");
        setDate(dateTimeRange2);
        setPassengersAmount(0);
        setRequests(new ArrayList<>());
    }};

    Offer offer2 = new Offer(){{
        setCoordinateTo(coordinateFrom);
        setCoordinateFrom(coordinateTo);
        setCarDescription("bla bla bla");
        setDate(dateTimeRange2);
        setPassengersAmount(2);
        setRequests(new ArrayList<>());
    }};

    Offer offer3 = new Offer(){{
        setCoordinateTo(coordinateTo);
        setCoordinateFrom(coordinateFrom);
        setCarDescription("bla bla bla");
        setDate(dateTimeRange3);
        setPassengersAmount(2);
        setRequests(new ArrayList<>());
    }};


    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.authentication = preemptive().basic("admin@a.a", "admin");
    }

    @Test
    public void testFindOfferByRequest() throws Exception {
        offerRepository.deleteAll();
        given().contentType(JSON).body(offer).when().post(USER_PATH + OFFER_PATH).then().statusCode(CREATED);
        given().contentType(JSON).body(offer1).when().post(USER_PATH + OFFER_PATH).then().statusCode(CREATED);
        given().contentType(JSON).body(offer2).when().post(USER_PATH + OFFER_PATH).then().statusCode(CREATED);
        given().contentType(JSON).body(offer3).when().post(USER_PATH + OFFER_PATH).then().statusCode(CREATED);

        given().contentType(JSON).body(request).when().post(USER_PATH + REQUEST_PATH).then().statusCode(CREATED);

        List<Offer> offers = requestOfferMatcher.findOffersByRequest(request);

        assertEquals(1, offers.size());
        assertEquals(offer.getCarDescription(), offers.get(0).getCarDescription());
        assertEquals(offer.getPassengersAmount(), offers.get(0).getPassengersAmount());
        assertEquals(offer.getCoordinateFrom().getLatitude(), offers.get(0).getCoordinateFrom().getLatitude(), 0.0000001);
        assertEquals(offer.getCoordinateFrom().getLongitude(), offers.get(0).getCoordinateFrom().getLongitude(), 0.0000001);
        assertEquals(offer.getCoordinateTo().getLatitude(), offers.get(0).getCoordinateTo().getLatitude(), 0.0000001);
        assertEquals(offer.getCoordinateTo().getLongitude(), offers.get(0).getCoordinateTo().getLongitude(), 0.0000001);

    }


}
