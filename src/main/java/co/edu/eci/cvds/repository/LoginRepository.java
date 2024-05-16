package co.edu.eci.cvds.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.eci.cvds.model.Login;



@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
    boolean existsByUsername(String username);
}
