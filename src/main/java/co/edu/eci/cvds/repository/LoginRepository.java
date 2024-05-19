package co.edu.eci.cvds.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.eci.cvds.model.Login;

import java.util.List;


@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
    boolean existsByUsernameAndPassword(String username,String password);
    boolean existsByUsername(String username);
    List<Login> findByUsername(String username);
}
