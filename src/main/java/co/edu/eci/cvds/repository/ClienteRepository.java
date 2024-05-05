package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    public List<Cliente> findByCorreo(String correo);
}
