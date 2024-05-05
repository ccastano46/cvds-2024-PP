package co.edu.eci.cvds.service;

import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionSerrvice {
    private final CotizacionRepository cotizacionRepository;


    @Autowired
    public CotizacionSerrvice(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    public List<Cotizacion> listarCotizaciones(){
        return cotizacionRepository.findAll();
    }

    public Cotizacion encontrarCotizacion(Long id){
        return cotizacionRepository.findByIden(id).get(0);

    }

    public Cotizacion agregarAlCarritoPrimeraVez(Producto producto){
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.agregarProductoAlCarrito(producto);
        cotizacionRepository.save(cotizacion); 
        return cotizacion;
    }

    public void agregarAlCarritoNVez(Producto producto, Cotizacion cotizacion){
        cotizacion.agregarProductoAlCarrito(producto);
        cotizacionRepository.save(cotizacion);
    }

    public void quitarDelCarrito(Producto producto, Cotizacion cotizacion){
        cotizacion.eliminarProductoDelCarrito(producto);
        if (cotizacion.getProductosCotizacion().isEmpty()) {
        cotizacion.setEstado("ELIMINADO"); 
    }
    cotizacionRepository.save(cotizacion);
    }

    public float calcularTotalCarrito(Cotizacion cotizacion){
        float total = 0;
        for (Producto producto : cotizacion.getProductosCotizacion()) {
        total += producto.getValor(); 
        }
    return total;
    }
}
