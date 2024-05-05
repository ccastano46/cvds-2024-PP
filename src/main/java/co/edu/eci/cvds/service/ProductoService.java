package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto agregarProducto(Producto producto) {
        try{
            if(!(producto.getValor() < 0 || producto.getImpuesto() < 0 || producto.getDescuento() < 0)) return productoRepository.save(producto);
            else  return null;
        }catch(NullPointerException e){
            return null;
        }
    }

    public Producto buscarProductoPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre).get(0);
    }

    public List<Producto> buscarProductos() {
        return productoRepository.findAll();
    }

    public Producto actualizarProducto(Producto producto) {
        try{
            if(!(producto.getValor() < 0 || producto.getImpuesto() < 0 || producto.getDescuento() < 0)){
                Producto productoExistente = this.buscarProductoPorNombre(producto.getNombre());
                productoExistente.setNombre(producto.getNombre());
                productoExistente.setDescripcionBreve(producto.getDescripcionBreve());
                productoExistente.setDescripcionTecnica(producto.getDescripcionTecnica());
                productoExistente.setCategoria(producto.getCategoria());
                productoExistente.setImagen(producto.getImagen());
                productoExistente.setValor(producto.getValor());
                productoExistente.setMoneda(producto.getMoneda());
                productoExistente.setImpuesto(producto.getImpuesto());
                productoExistente.setDescuento(producto.getDescuento());
                productoRepository.save(productoExistente);
            }
            return null;
        }catch(NullPointerException e){
            return null;
        }

    }

    public void borrarProducto(String nombre) {
        productoRepository.deleteById(nombre);
    }

}
