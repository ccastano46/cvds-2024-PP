package co.edu.eci.cvds.ID;
import co.edu.eci.cvds.model.Vehiculo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class VehiculoID implements Serializable {
    @Getter @Setter
    private String marca;
    @Getter @Setter
    private String model;
    @Getter @Setter
    private String year;




}
