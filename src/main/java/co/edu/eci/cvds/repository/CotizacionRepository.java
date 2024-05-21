package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    public List<Cotizacion> findByIden(Long id);
    @Query(value = "SELECT c FROM Cotizacion c WHERE c.cita IS NOT NULL ")
    public List<Cotizacion> findByCita();
}