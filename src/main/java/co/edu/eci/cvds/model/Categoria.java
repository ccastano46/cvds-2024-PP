package co.edu.eci.cvds.model;

import co.edu.eci.cvds.SpringApplicationCvds;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;

import java.util.Set;

/**
 * Clase - Entidad Categoria
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */

@Entity
@Table(name = "Categorias")
public class Categoria {

    @Id
    @Column(name = "nombre", length = 30, nullable = false)
    @Getter private String nombre;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ProductosPorCategoria",
            joinColumns = {
                    @JoinColumn(name = "categoria", referencedColumnName = "nombre")
            },
            inverseJoinColumns = @JoinColumn(name = "producto", referencedColumnName = "nombre")
    )
    @Getter private Set<Producto> productosCategoria;

    public Categoria(){
        this.productosCategoria = new HashSet<>();
    }

    /**
     * Constructor de Categoria
     * @param nombre, nombre de la categoria
     */
    public Categoria(String nombre){
        this.nombre = nombre.toUpperCase();
        this.productosCategoria = new HashSet<>();
    }

    /**
     * Metodo que agrega un producto a la lista de prodcutos
     * @param producto a agregar
     */
    public void agregarProducto(Producto producto){
        producto.agregarCategoria(this);
        this.productosCategoria.add((producto));
    }


    /**
     * Indica si un producto pertenece a la categoria
     * @param producto, producto que se desea conocer
     * @return true, si el producto esta dentro del conjunto de productos, false sino.
     */
    public boolean contieneProducto(Producto producto){
        return productosCategoria.contains(producto);
    }

    public void limpiarProductos(){
        this.productosCategoria.clear();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((productosCategoria == null) ? 0 : productosCategoria.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return (this.nombre == null ? categoria.getNombre() == null : this.nombre.equals(categoria.getNombre()))
                && this.productosCategoria.equals(categoria.getProductosCategoria());

    }





}