package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.ID.VehiculoID;
import co.edu.eci.cvds.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, VehiculoID> {
    public List<Vehiculo> findByMarcaAndModelAndYear(String marca, String model, String year);
}
