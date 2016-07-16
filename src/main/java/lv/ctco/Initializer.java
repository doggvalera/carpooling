package lv.ctco;

import lv.ctco.entities.*;
import lv.ctco.repository.OfferRepository;
import lv.ctco.repository.RequestRepository;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    OfferRepository offerRepository;


    @Transactional
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        List<UserRoles> userRoles = new ArrayList<>();
        UserRoles userRole = new UserRoles();
        userRole.setRole("ADMIN");

        User user = new User();
        user.setName("admin");
        user.setEmail("admin@a.a");
        user.setPassword("admin");
        user.setSurname("admin");
        userRoles.add(userRole);
        user.setUserRoles(userRoles);

        userRepository.save(user);

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
        request.setUser(user);
        requestRepository.save(request);

        Offer offer = new Offer();
        offer.setCoordinateTo(coordinate);
        offer.setCoordinateFrom(coordinate);
        offer.setCarDescription("Ferrarry");
        offer.setDate(dateTimeRange);
        offer.setPassengersAmount(3);
        offer.setUser(user);
        offerRepository.save(offer);
    }
}