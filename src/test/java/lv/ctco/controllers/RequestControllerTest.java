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
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CarPoolingApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8090")

public class RequestControllerTest {

    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();

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
    public void testRequestsFindAll() throws Exception {

        User user = new User();
        user.setName("TestName");
        user.setSurname("TestSurname");
        Request request = new Request();
        request.setUser(user);
        Headers header = given().contentType("application/json").body(request).when().post("/rides/requests").getHeaders();
        get(header.getValue("Location")).then().body("name", equalTo("TestName"));
    }
}