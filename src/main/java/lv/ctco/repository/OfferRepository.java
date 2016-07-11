package lv.ctco.repository;


import lv.ctco.Entities.Offer;
import lv.ctco.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Integer> {
    @Query("select o from Offer o where o.user = ?1")
    List<Offer> getByDriverID(User driver);
}
