package co.edu.eci.cvds.model;

import co.edu.eci.cvds.SpringApplicationCvds;
import co.edu.eci.cvds.exception.LincolnLinesException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase - Entidad Producto
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */


@Entity
@Table(name = "Productos")
public class Producto {
    @Id
    @Column(name = "nombre", length = 100, nullable = false, updatable = false)
    @Getter private String nombre;
    @Column(name = "descripcionBreve", length = 150)
    @Getter @Setter private String descripcionBreve;
    @Column(name = "descripcionTecnica", length = 250)
    @Getter @Setter private String descripcionTecnica;
    @Column(name = "imagen", length = 50)
    @Getter @Setter private String imagen;
    @Column(name = "valor", nullable = false)
    @Getter  private float valor;
    @Column(name = "moneda", length = 10, nullable = false)
    @Getter @Setter private String moneda;
    @Column(name = "descuento",nullable = false)
    @Getter private float descuento;
    @Column(name = "impuesto",nullable = false)
    @Getter  private float impuesto;
    @ManyToMany(mappedBy = "productosCategoria", fetch = FetchType.EAGER)
    @Getter private Set<Categoria> categorias;
    @ManyToMany(mappedBy = "productosVehiculo", fetch = FetchType.EAGER)
    @Getter private Set<Vehiculo> vehiculos;



    public Producto() {
        this.categorias = new HashSet<>();
        this.vehiculos = new HashSet<>();
    }

    /**
     * Constructor de la clase producto
     * @param nombre, nombre del producto
     * @param valor, valor de dicho producto
     * @param moneda, tipo de divisa
     */
    public Producto(String nombre,float valor, String moneda) throws LincolnLinesException {
        this.setValor(valor);
        this.nombre = nombre.toUpperCase();
        this.moneda = moneda;
        this.descuento = 0;
        this.impuesto = 0;
        this.categorias = new HashSet<>();
        this.vehiculos = new HashSet<>();
    }

    public Producto(String nombre, float valor, String moneda, float impuesto, float descuento){
        this.nombre = nombre;
        this.valor = valor;
        this.moneda = moneda;
        this.impuesto = impuesto;
        this.descuento = descuento;
        this.categorias = new HashSet<>();
        this.vehiculos = new HashSet<>();
    }





    /**
     * Setter de valor
     * @param valor, nuevo valor del producto
     * @throws LincolnLinesException VALOR_NEGATIVO si el nuevo valor es negativo
     */
    public void setValor(float valor) throws LincolnLinesException {
        if(valor < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        this.valor = valor;
    }

    /**
     * Setter de descuento
     * @param descuento, nuevo descuento del producto
     * @throws LincolnLinesException VALOR_NEGATIVO si el nuevo descuento es negativo
     * FUERA_RANGO si el nuevo descuento > 1
     */

    public void setDescuento(float descuento) throws LincolnLinesException {
        if(descuento < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        if(descuento > 1) throw new LincolnLinesException(LincolnLinesException.FUERA_RANGO);
        this.descuento = descuento;
    }

    /**
     * Setter de impuesto
     * @param impuesto, nuevo impuesto del producto
     * @throws LincolnLinesException VALOR_NEGATIVO si el nuevo impuesto es negativo
     * FUERA_RANGO si el nuevo impuesto > 1
     */

    public void setImpuesto(float impuesto) throws LincolnLinesException {
        if(impuesto < 0) throw new LincolnLinesException(LincolnLinesException.VALOR_NEGATIVO);
        if(impuesto > 1) throw new LincolnLinesException(LincolnLinesException.FUERA_RANGO);
        this.impuesto = impuesto;
    }

    protected float getValorFinal(){
        return this.valor - (this.valor * this.descuento);
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
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Producto producto = (Producto) obj;
        return (this.nombre == null ? producto.getNombre() == null :this.nombre.equals(producto.getNombre())) &&
                (this.descripcionBreve == null ? producto.getDescripcionBreve() == null : this.descripcionBreve.equals(producto.getDescripcionBreve())) &&
                (this.descripcionTecnica == null ? producto.getDescripcionTecnica() == null : this.descripcionTecnica.equals(producto.getDescripcionTecnica())) &&
                (this.valor == 0 ? producto.getValor() == 0 : this.valor == producto.getValor()) &&
                (this.moneda == null ? producto.getMoneda() == null : this.moneda.equals(producto.getMoneda())) &&
                (this.impuesto == 0 ? producto.getImpuesto() == 0 : this.impuesto == producto.getImpuesto()) &&
                (this.descuento == 0 ? producto.getDescuento() == 0 : this.descuento == producto.getDescuento());
    }

    /**
     * Agrega una categoria al conjunto de categorias
     * @param categoria a la que pertence el producto
     */
    protected void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

    /**
     * Agrega un vehiculo al conjunto de vehiculos
     * @param vehiculo
     */
    protected void agregarVehiculo(Vehiculo vehiculo){
        this.vehiculos.add(vehiculo);
    }





}