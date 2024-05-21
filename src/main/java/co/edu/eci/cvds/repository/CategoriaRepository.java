package co.edu.eci.cvds.repository;


import co.edu.eci.cvds.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {
     List<Categoria> findByNombre(String nombre);

}