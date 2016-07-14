package lv.ctco.controllers;

import io.restassured.RestAssured;
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
    public void postRequestTest() {
        User user = new User();
        user.setName("John");
        user.setSurname("The first");
        user.setEmail("john@john.com");
        user.setPassword("1234");
        user.setId(10000);

        Request request = new Request();
        request.setUser(user);

        given().
                body(request).
                        when().contentType("application/json").post("/requests/users/1").then().statusCode(201);


        /*
        Headers header = given().contentType("application/json").body(user).when().post("/users").getHeaders();

        Request request = new Request();

        given().contentType("application/json")
                .body(request).when()
                .post(header.getValue("Location") + "r/requests/").then()
                .statusCode(CREATED);
*/
    }

    @Test
    public void getAllRequestsTestOk() {
        get("/requests").then().statusCode(200);

    }




}