package lv.ctco.repository;

import lv.ctco.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


}
