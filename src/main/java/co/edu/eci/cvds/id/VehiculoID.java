package co.edu.eci.cvds.id;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class VehiculoID implements Serializable {
    @Getter @Setter
    private String marca;
    @Getter @Setter
    private String model;
}
