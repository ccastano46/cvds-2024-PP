package co.edu.eci.cvds.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Getter private long iden;

    @Column(name = "estado", length = 10, nullable = false)
    @Getter @Setter private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion", nullable = false, updatable = false)
    @Getter private Timestamp fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cita")
    @Getter @Setter private Timestamp cita;

    @Column(name = "ciudadRecogida", length = 50)
    @Getter @Setter private String ciudadRecogida;
    @Column(name = "direccionRecogida", length = 50)
    @Getter @Setter private String direccionRecogida;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Carrito",
            joinColumns = @JoinColumn(name = "cotizacion"),
            inverseJoinColumns = @JoinColumn(name = "producto", referencedColumnName = "nombre")
    )
    @Getter private List<Producto> productosCotizacion;

    @ManyToOne
    @JoinColumn(name = "correoCliente", referencedColumnName = "correo")
    @Getter @Setter private Cliente cliente;




    public Cotizacion() {
        this.estado = "CREADO";
        Date date = new Date();
        this.fechaCreacion = new Timestamp(date.getTime());
        this.productosCotizacion = new ArrayList<>();
    }

    public Cotizacion(Timestamp cita, String ciudadRecogida, String direccionRecogida, Cliente cliente){
        Date date = new Date();
        this.estado = "CREADO";
        this.fechaCreacion = new Timestamp(date.getTime());
        this.cita = cita;
        this.ciudadRecogida = ciudadRecogida;
        this.direccionRecogida = direccionRecogida;
        this.cliente = cliente;
        this.productosCotizacion = new ArrayList<>();
    }

    public void agregarProductoAlCarrito(Producto producto){
        productosCotizacion.add(producto);
    }

    public void eliminarProductoDelCarrito(Producto producto){
        productosCotizacion.remove(producto);
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (iden ^ (iden >>> 32));
        result = prime * result + ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
        result = prime * result + ((cita == null) ? 0 : cita.hashCode());
        result = prime * result + ((ciudadRecogida == null) ? 0 : ciudadRecogida.hashCode());
        result = prime * result + ((direccionRecogida == null) ? 0 : direccionRecogida.hashCode());
        result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        try{
            Cotizacion cotizacion = (Cotizacion) obj;
            return cotizacion.getIden() == this.iden;
        }catch(Exception e){
            return false;
        }

    }
}