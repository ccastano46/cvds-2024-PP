package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    public List<Producto> findByNombre(String nombre);
}
