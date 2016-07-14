package lv.ctco;

import org.springframework.http.HttpStatus;

public class Consts {
    public static final int OK = HttpStatus.OK.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int CREATED = HttpStatus.CREATED.value();

    public static final String JSON = "application/json";

    public static final String BAD_ID = "/-1";
    public static final String OFFER_PATH = "/offers";
    public static final String USER_PATH = "/users";
    public static final String REQUEST_PATH = "/request";
    public static final String RIDE_PATH = "/rides";
    public static final String BY_DRIVER_PATH = "/bydriver";
    public static final String BY_PASSANGER_PATH = "/bypassanger";
}
