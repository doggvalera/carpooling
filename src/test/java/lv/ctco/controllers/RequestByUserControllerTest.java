package lv.ctco.controllers;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import lv.ctco.CarPoolingApplication;
import lv.ctco.entities.Request;
import lv.ctco.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static lv.ctco.Consts.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class RequestByUserControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.authentication = preemptive().basic("admin@a.a", "admin");
    }

    @Test
    public void testGetRequestForUserOK() throws Exception {
        User user = StandartBuilder.buildUser();
        given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).then().statusCode(CREATED);

        Request request = new Request();
        request.setUser(user);

        given().contentType(JSON).body(request).when().post(USER_PATH + REQUEST_PATH).then().statusCode(CREATED);
        get(USER_PATH + REQUEST_PATH).then().statusCode(OK);
    }


    @Test
    public void testPostRequestForUserOK() throws Exception {
        User user = StandartBuilder.buildUser();
        given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).then().statusCode(CREATED);

        Request request = new Request();
        request.setUser(user);

        given().contentType(JSON).body(request).when().post(USER_PATH + REQUEST_PATH).then().statusCode(CREATED);
    }
}