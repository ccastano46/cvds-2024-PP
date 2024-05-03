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

    public void agendar(Cotizacion cotizacion){
        Cotizacion currentCotizacion = encontrarCotizacion(cotizacion.getIden());
        if(!currentCotizacion.getEstado().equals("ELIMINADO") && !(cotizacion.getCita() == null
                        || cotizacion.getCiudadRecogida() == null
                        || cotizacion.getDireccionRecogida() == null
                        || cotizacion.getCliente() == null))
        {
            currentCotizacion.setCita(cotizacion.getCita());
            currentCotizacion.setCiudadRecogida(cotizacion.getCiudadRecogida());
            currentCotizacion.setDireccionRecogida(cotizacion.getDireccionRecogida());
            currentCotizacion.setCliente(cotizacion.getCliente());
        }

    }

    public Cotizacion agregarAlCarritoPrimeraVez(Producto producto){
        //creacion cotizacion y carrito
        return null;
    }

    public void agregarAlCarritoNVez(Producto producto, Cotizacion cotizacion){

    }

    public void quitarDelCarrito(Producto producto, Cotizacion cotizacion){
        //Si se quitan todos los productos, su estado cambia a eliminado
    }

    public float calcularTotalCarrito(){
        return 0;
    }
}
