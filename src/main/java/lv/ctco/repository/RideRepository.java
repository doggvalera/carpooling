package lv.ctco.repository;

import lv.ctco.Entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Integer> {

//    @Query("select r from Ride r where r.offer.user = ?1")
//    List<Ride> getByDriver(User driver);

//    @Query("select r from Ride r where r.requestList.user = ?1")
//    List<Ride> getByPassenger(User driver);
}
