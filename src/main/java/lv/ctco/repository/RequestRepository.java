package lv.ctco.repository;

import lv.ctco.entities.Request;
import lv.ctco.entities.Ride;
import lv.ctco.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("select r from Request r where r.user = ?1")
    List<Request> getRequestsByPassenger(User user);


}
