package co.edu.eci.cvds.service;


import co.edu.eci.cvds.model.Categoria;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;

    }

    /**
     * Metodo que guarda una categoria en la base de datos
     * @param categoria registratada
     */
    public void agregarCategoria(Categoria categoria){
        categoriaRepository.save(categoria);
    }

    /**
     * Metodo que indica las categorias en la base de datos
     * @return
     */
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }

    /**
     * Indica la informacion acerca una categoria en especifico
     * @param nombre, nombre de la categoria
     * @return, categoria en la BD
     */
    public Categoria buscarCategoria(String nombre){
        return categoriaRepository.findByNombre(nombre.toUpperCase()).get(0);
    }

    /**
     * Conjunto de productos pertenecientes a una categoria
     * @param categoria, categoria de la cual que desea conocer los productos
     * @return Conjunto de productos pertenecientes a dicha categoria
     */
    public Set<Producto> listarProductos(String categoria){
        Categoria currentCategoria= this.buscarCategoria(categoria);
        return currentCategoria.getProductosCategoria();
    }

    /**
     * Agrega un producto al conjunto de productos
     * @param categoria, nombre de la categoria a la cual se desea agregar el producto
     * @param producto, producto que se desea agregar
     * @return categoria registrada den la base de datos
     */
    public Categoria agregarProducto(String categoria, Producto producto){
        Categoria categoriaEncontrada = this.buscarCategoria(categoria);
        categoriaEncontrada.agregarProducto(producto);
        return categoriaRepository.save(categoriaEncontrada);
    }

    /**
     * Indica los productos pertenecientes a una categoria
     * @param categoria, categoria que se desea averiguar
     * @return conjunto de categorias.
     */

    public Set<Producto> listaProductos(String categoria){
        Categoria categoriaEncontrada = this.buscarCategoria(categoria);
        return categoriaEncontrada.getProductosCategoria();
    }

    public void limpiarTabla(){
        for(Categoria c:this.listarCategorias()){
            c.limpiarProductos();
        }
        categoriaRepository.deleteAllInBatch();
    }





}