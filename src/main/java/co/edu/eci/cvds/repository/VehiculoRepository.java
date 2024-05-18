package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.id.VehiculoID;
import co.edu.eci.cvds.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, VehiculoID> {
    List<Vehiculo> findByMarca(String marca);
    List<Vehiculo> findByMarcaAndModel(String marca, String model);
}