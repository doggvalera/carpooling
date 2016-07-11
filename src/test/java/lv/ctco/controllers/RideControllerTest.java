package lv.ctco.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import static io.restassured.RestAssured.get;

public class RideControllerTest {
    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();


    @Test
    public void testShowAllRides() throws Exception {
        get("/ride").then().statusCode(OK);
    }

    @Test
    public void testShowRidesByDriver() throws Exception {

    }

    @Test
    public void testShowRidesPassenger() throws Exception {

    }
}