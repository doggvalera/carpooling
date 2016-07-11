package lv.ctco.repository;

import lv.ctco.Entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RidesRepository extends JpaRepository<Ride, Integer> {
}
