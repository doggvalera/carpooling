package lv.ctco;

import lv.ctco.entities.User;
import lv.ctco.entities.UserRoles;
import lv.ctco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    UserRepository userRepository;


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
    }
}