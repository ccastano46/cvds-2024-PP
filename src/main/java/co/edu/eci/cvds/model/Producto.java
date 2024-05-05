package co.edu.eci.cvds.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @Column(name = "nombre", length = 50, nullable = false)
    @Getter @Setter private String nombre;
    @Column(name = "descripcionBreve", length = 150)
    @Getter @Setter private String descripcionBreve;
    @Column(name = "descripcionTecnica", length = 250)
    @Getter @Setter private String descripcionTecnica;
    @Column(name = "categoria", length = 20, nullable = false)
    @Getter @Setter private String categoria;
    @Column(name = "imagen", length = 100)
    @Getter @Setter private String imagen;
    @Column(name = "valor", nullable = false)
    @Getter @Setter private float valor;
    @Column(name = "moneda", length = 10, nullable = false)
    @Getter @Setter private String moneda;
    @Column(name = "descuento",nullable = false)
    @Getter @Setter private float descuento;
    @Column(name = "impuesto",nullable = false)
    @Getter @Setter private float impuesto;
    @ManyToMany(mappedBy = "productosVehiculo")
    @Getter private List<Vehiculo> vehiculos;
    @ManyToMany(mappedBy = "productosCotizacion")
    @Getter private List<Cotizacion> cotizaciones;


    public Producto() {
        this.vehiculos = new ArrayList<>();
        this.cotizaciones = new ArrayList<>();
    }
    public Producto(String nombre,String categoria, float valor, String moneda) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.valor = valor;
        this.moneda = moneda;
        this.descuento = 0;
        this.impuesto = 0;
        this.vehiculos = new ArrayList<>();
        this.cotizaciones = new ArrayList<>();
    }
    public void agregarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }
    public void agregarCotizacion(Cotizacion cotizacion) {
        this.cotizaciones.add(cotizacion);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((descripcionBreve == null) ? 0 : descripcionBreve.hashCode());
        result = prime * result + ((descripcionTecnica == null) ? 0 : descripcionTecnica.hashCode());
        result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
        result = prime * result + Float.floatToIntBits(valor);
        result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
        result = prime * result + Float.floatToIntBits(descuento);
        result = prime * result + Float.floatToIntBits(impuesto);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        try {
            Producto producto = (Producto) obj;

            return this.nombre.equals(producto.getNombre()) &&
                    this.categoria.equals(producto.getCategoria())
                    && this.valor == producto.getValor()
                    && this.moneda.equals(producto.getMoneda())
                    && this.hashCode() == producto.hashCode();
        } catch (Exception e) {
            return false;
        }
    }
    
}
