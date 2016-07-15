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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static io.restassured.RestAssured.*;

import static lv.ctco.Consts.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")
public class RequestControllerTest {

    @Before
    public void before() {
        RestAssured.port = 8090;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.authentication = preemptive().basic("admin@a.a", "admin");
    }

    @Test
    public void testGetAllRequestsOK() throws Exception{
        get(REQUEST_PATH).then().statusCode(OK);
    }

    @Test
    public void testGetRequestByIDFailed() throws Exception{
        get(REQUEST_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testGetRequestByIDOK() throws Exception{
        User user = StandartBuilder.buildUser();
        given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).then().statusCode(CREATED);

        Request request = new Request();
        request.setUser(user);

        Headers headersRequest = given().contentType(JSON).body(request).when().post(USER_PATH + REQUEST_PATH).getHeaders();

        get(headersRequest.getValue("Location")).then().statusCode(OK);
    }

    @Test
    public void testDeleteRequestByIDFailed() throws Exception{
        delete(REQUEST_PATH + BAD_ID).then().statusCode(NOT_FOUND);
    }

    @Test
    public void testDeleteRequestByIDOK() throws Exception{
        User user = StandartBuilder.buildUser();
        given().contentType(JSON).body(user).when().post(USER_PATH + WITHOUT_INPUT_PATH).then().statusCode(CREATED);

        Request request = new Request();
        request.setUser(user);

        Headers headersRequest = given().contentType(JSON).body(request).when().post(USER_PATH + REQUEST_PATH).getHeaders();

        delete(headersRequest.getValue("Location")).then().statusCode(OK);
    }

}