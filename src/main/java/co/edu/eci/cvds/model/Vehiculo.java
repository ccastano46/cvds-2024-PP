package co.edu.eci.cvds.model;
import co.edu.eci.cvds.SpringApplicationCvds;
import co.edu.eci.cvds.exception.LincolnLinesException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import co.edu.eci.cvds.id.VehiculoID;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Clase - Entidad Vehiculo
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Entity
@Table(name = "Vehiculos")
@IdClass(VehiculoID.class)

public class Vehiculo {
    @Id
    @Column(name = "marca", length = 20, nullable = false)
    @Getter @Setter private String marca;
    @Id
    @Column(name = "modelo", length = 20, nullable = false)
    @Getter @Setter private String model;
    @Column(name = "yearVehicle", length = 4, nullable = false)
    @Getter @Setter private String yearVehicle;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProductosPorVehiculo",
            joinColumns = {
                    @JoinColumn(name = "marca"),
                    @JoinColumn(name = "modelo")
            },
            inverseJoinColumns = @JoinColumn(name = "nombreProducto", referencedColumnName = "nombre")
    )
    @Getter private Set<Producto> productosVehiculo;



    /**
     * Constructor de Vehiculo
     * @param marca, marca del vehiculo
     * @param model, modelo del vehiculo
     * @param year, año en el que se estreno el vehiculo
     */
    public Vehiculo(String marca, String model, String year) {
        this.marca = marca.toUpperCase();
        this.model = model.toUpperCase();
        this.yearVehicle = year;
        this.productosVehiculo = new HashSet<>();

    }

    public Vehiculo() {

        this.productosVehiculo = new HashSet<>();
    }


    /**
     * Asocia un producto con el vehiculo
     * @param producto, producto apto para el vehiculo
     */
    public void anadirProducto(Producto producto) throws LincolnLinesException {
        if(producto.getCategorias().isEmpty()) throw new LincolnLinesException(LincolnLinesException.PRODUCTO_SIN_CATEGORIA);
        producto.agregarVehiculo(this);
        productosVehiculo.add(producto);

    }

    public void limpiarProductos(){
        productosVehiculo.clear();
    }

    /**
     * Funcion que indica si un producto es apto para el vehiculo
     * @param producto, producto que se desea saber si es apto para el vehiculo.
     * @return true si el producto pertenece al conjunto de productos y false, de lo contrario
     */
    public boolean productoApto(Producto producto){
        return this.productosVehiculo.contains(producto);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + ((yearVehicle == null) ? 0 : yearVehicle.hashCode());
        result = prime * result + ((productosVehiculo == null) ? 0 : productosVehiculo.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) obj;
        return (this.marca == null ? vehiculo.getMarca() == null :this.marca.equals(vehiculo.getMarca())) &&
                (this.model == null ? vehiculo.getModel() == null : this.model.equals(vehiculo.getModel())) &&
                (this.yearVehicle == null ? vehiculo.getYearVehicle() == null : this.yearVehicle.equals(vehiculo.getYearVehicle())) &&
                this.productosVehiculo.equals(vehiculo.getProductosVehiculo());

    }

}


