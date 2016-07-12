package lv.ctco.repository;

import lv.ctco.entities.Ride;
import lv.ctco.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Integer> {

    @Query("select r from Ride r where r.offer.user = ?1")
    List<Ride> getByDriver(User driver);

    @Query("select r from Ride r join r.requestList q where q.user = ?1")
    List<Ride> getByPassenger(User driver);
}
