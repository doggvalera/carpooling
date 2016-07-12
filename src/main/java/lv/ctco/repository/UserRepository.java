package lv.ctco.repository;

import lv.ctco.entities.Ride;
import lv.ctco.entities.User;
import lv.ctco.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    List<User> findUserByEmail(String email, String password);
}
