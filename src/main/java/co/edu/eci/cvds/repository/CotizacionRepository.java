package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    public List<Cotizacion> findByIden(Long id);
}
