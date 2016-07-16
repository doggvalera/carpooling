package lv.ctco.controllers;

import lv.ctco.entities.*;

import java.time.LocalDateTime;
import java.time.Month;

public class StandartBuilder {
    private static int mailid = 0;

    public static User buildUser() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setPassword("password");
        user.setEmail("mail" + mailid++);

        return user;
    }

    public static Request buildRequest() {

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(100);
        coordinate.setLongitude(200);

        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setEarliestDeparture(LocalDateTime.of(2016, Month.APRIL,12,12,00));
        dateTimeRange.setLatestDeparture(LocalDateTime.of(2016, Month.APRIL,12,12,30));

        Request request = new Request();
        request.setCoordinateFrom(coordinate);
        request.setCoordinateTo(coordinate);
        request.setRadius(100);
        request.setDate(dateTimeRange);
        request.setUser(buildUser());

        return request;
    }

    public static Offer buildOffer() {

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(100);
        coordinate.setLongitude(200);
        
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setEarliestDeparture(LocalDateTime.of(2016, Month.APRIL,12,12,00));
        dateTimeRange.setLatestDeparture(LocalDateTime.of(2016, Month.APRIL,12,12,30));

        Offer offer = new Offer();
        offer.setCoordinateTo(coordinate);
        offer.setCoordinateFrom(coordinate);
        offer.setCarDescription("Ferrarry");
        offer.setDate(dateTimeRange);
        offer.setPassengersAmount(3);
        offer.setUser(buildUser());
        return offer;
    }
}
