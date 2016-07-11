package lv.ctco.repository;

import lv.ctco.Entities.Ride;
import lv.ctco.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Integer> {

    @Query("select r from Ride r where r.offer.user = ?1")
    List<Ride> getByDriver(User driver);

    @Query("select r from Ride r where r.request.user = ?1")
    List<Ride> getByPassenger(User driver);

}
