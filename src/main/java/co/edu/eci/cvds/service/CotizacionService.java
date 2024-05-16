package co.edu.eci.cvds.service;


import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import java.util.List;



/**
 * Clase Service de cotizacion
 * @author Equipo Pixel Pulse
 * 10/05/2024
 */
@Service
public class CotizacionService {
    private final CotizacionRepository cotizacionRepository;


    @Autowired
    public CotizacionService(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    /**
     * Función que devuelve lista de cotizaciones en la Base de Datos
     * @return lista de cotizaciones que esta en la base de datos.
     */

    public List<Cotizacion> listarCotizaciones(){
        return cotizacionRepository.findAll();
    }

    /**
     *Funcion que indica cotizaciones con citas agendadas
     * @return lista de las cotizaciones que ya fueron agendadas.
     */
    public List<Cotizacion> cotizacionesAgendadas(){
        return cotizacionRepository.findByCita();
    }

    /**
     * Función que devuelve la información de una cotización que se desea encontrar
     * @param id, identificador de la cotización que se desea encontrar
     * @return Cotizacion con el id especificado.
     */
    public Cotizacion encontrarCotizacion(long id){
        return cotizacionRepository.findByIden(id).get(0);

    }

    /**
     * Funcion que agrega un producto a la lista de productos de cotizaciones
     * @param producto, producto a agregar al carrito.
     * @param vehiculo, vehiculo para el cual se hace la cotizacion
     * @param cotizacion, Id de la cotizacion en cuestion
     * @return cotizacion especificada.
     * @throws LincolnLinesException DATOS_FALTANTES si tanto vehiculo como cotizacion son null
     * VEHICULO_NO_COMPATIBLE si el vehiculo ingresado es diferente al de la cotizacion
     */
    public Cotizacion agregarAlCarrito(Producto producto, Vehiculo vehiculo, long cotizacion) throws LincolnLinesException {
        Cotizacion cotizacionEncontrada;
        if(cotizacion == -1 && vehiculo == null) throw new LincolnLinesException(LincolnLinesException.DATOS_FALTANTES);
        else if(cotizacion == -1) cotizacionEncontrada = cotizacionRepository.save(new Cotizacion(vehiculo));
        else cotizacionEncontrada = this.encontrarCotizacion(cotizacion);
        cotizacionEncontrada.agregarProductoAlCarrito(producto);
        return cotizacionRepository.save(cotizacionEncontrada);
    }


    /**
     * Metodo que quita producto del carrito
     * @param producto, producto a quitar del carrito
     * @param cotizacionId identificador de la cotizacion de la cual se va a quitar el producto
     */
    public void quitarDelCarrito(Producto producto, long cotizacionId) throws LincolnLinesException{
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        cotizacion.eliminarProductoDelCarrito(producto);
        cotizacionRepository.save(cotizacion);
    }


    /**
     * Funcion que investiga los productos que estan incluidos en el carrito de una cotizacion en especifico
     * @param cotizacionId, identificador del cotizador  a avegiruar
     * @return lita de productos
     */
    public List<Producto> verCarrito(Long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.getProductosCotizacion();

    }

    /**
     * indica el Subtotal de los productos agregados al carrito de cierta cotizacion
     * @param cotizacionId, identificador de la cotizacion en la que se agregaron los productos
     * @return float del subtotal del carrito
     */
    public float calcularTotalCarrito(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularSubtotal();
    }

    /**
     * Calcula el descuento toal para la cotizacion
     * @param cotizacionId, identificador de la cotizacion en la que se agregaron los productos
     * @return descuento total de la cotizacion
     */
    public float calcularDescuentoTotal(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularDescuento().getNumber().floatValue();
    }

    /**
     * Calcula el Impuesto toal para la cotizacion
     * @param cotizacionId, identificador de la cotizacion en la que se agregaron los productos
     * @return impuesto total de la cotizacion
     */
    public float calcularImpuestoTotal(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularImpuesto().getNumber().floatValue();
    }

    /**
     * Calcula el total de la cotizacion sin tener en cuenta el descuento
     * @param cotizacionId, identificador de la cotizacion
     * @return total sin descuento
     */
    public float totalSinDescuento(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularSinDescuento().getNumber().floatValue();
    }

    /**
     * Entrega la cotizacion final de una cotizacion, teniendo en cuenta subtotal, descuento e impuestos
     * @param cotizacionId, identificador de la cotizacion.
     * @return total a pagar.
     */
    public float calcularFinal(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.calcularFinal();
    }


    /**
     * Metodo para separar cita a una cotizacion especifica
     * @param cita, fecha y hora en la que se va a realizar la cita
     * @param ciudad, ciudad donde dse va a recoger el vehiculo
     * @param direccion, direccion donde se va a recoger el vehiculo
     * @param cotizacion identificacion de la cotizacion a agendar
     * @param cliente, cliente que agenda la cita
     * @throws LincolnLinesException FECHA_NO_DISPONIBLE si la fecha que se desea agendar ya esta tomada.
     * DATOS_FALTANTES si no se proporciono una fecha para agendar
     */
    public void agendarCita(LocalDateTime cita, String ciudad, String direccion, long cotizacion, Cliente cliente) throws LincolnLinesException {
        if(cita == null) throw new LincolnLinesException(LincolnLinesException.DATOS_FALTANTES);
        if(!fechaDisponible(cita)) throw new LincolnLinesException(LincolnLinesException.FECHA_NO_DISPONIBLE);
        Cotizacion cotizacionEncontrada = this.encontrarCotizacion(cotizacion);
        cotizacionEncontrada.agendar(cita,ciudad,direccion,cliente);
        cotizacionRepository.save(cotizacionEncontrada);
    }

    /**
     * Funcion que se encarga de verificar que una fecha este disponible
     * @param fechaEsperada, fecha la cul se va a comparar con lods demas
     * @return boolean que inidca si se va a poder agendar cita
     */

    private boolean fechaDisponible(LocalDateTime fechaEsperada){
        LocalDate fechaBase;
        Duration entreCitas;
        Duration nowVCita = Duration.between(LocalDateTime.now(),fechaEsperada);
        LocalTime horaInicio = LocalTime.of(8,0);
        LocalTime horaFin = LocalTime.of(15,0);
        if(fechaEsperada.isEqual(LocalDateTime.now()) || fechaEsperada.isBefore(LocalDateTime.now())
                || fechaEsperada.toLocalTime().isBefore(horaInicio) || fechaEsperada.toLocalTime().isAfter(horaFin)
                || nowVCita.getSeconds() < 3600) return false;
        for(Cotizacion c: this.cotizacionesAgendadas()){
            fechaBase = c.getCita().toLocalDate();
            entreCitas = Duration.between(c.getCita(),fechaEsperada);
            if(fechaBase.isEqual(fechaEsperada.toLocalDate()) && entreCitas.getSeconds() < 7200) return false;
        }
        return true;
    }

    public void limpiarTabla(){
        for(Cotizacion cotizacion : this.listarCotizaciones()){
            cotizacion.limpiarCotizacion();
        }
        cotizacionRepository.deleteAllInBatch();
    }

    /**
     * Funcion que indica cuantas veces se encuentra un producto en el carrito
     * @param producto, producto que se desea analizar
     * @param cotizacion, carrito que se desea revisar
     * @return numero de veces que se encuentra el producto en el carrito
     */
    public int contadorProducto(Producto producto,long cotizacion){
        int contador = 0;
        List<Producto> carrito = this.verCarrito(cotizacion);
        for (Producto value : carrito) {
            if (value.equals(producto)) contador++;
        }
        return contador;
    }

    /**
     * Indica el vehiculo para el cual fue realizado la cotizacion
     * @param cotizacion, identificador de la cotizacion de la cual se desea conocer el vehiculo
     * @return vehiculo
     */
    public Vehiculo getVehiculo(long cotizacion){
        Cotizacion encontrada = this.encontrarCotizacion(cotizacion);
        return encontrada.getVehiculo();
    }

    /**
     * Indica la direccion de recogida de la cotizacion,
     * @param cotizacionId, identificador de la cotizacion
     * @return direccion en la que se va a recoger el vehiculo
     */
    public String direccionRegocida(long cotizacionId){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        return cotizacion.getDireccionRecogida();
    }

    /**
     * Indica el estado de una cotizacion
     * @param idCotizacion, identificador de la cotizaciom
     * @return estado de la cotizacion
     */
    public String conocerEstado(long idCotizacion){
        Cotizacion cotizacion = this.encontrarCotizacion(idCotizacion);
        return cotizacion.getEstado();
    }

    public void actualizarEstado(long cotizacionId, String nuevoEstado){
        Cotizacion cotizacion = this.encontrarCotizacion(cotizacionId);
        cotizacion.setEstado(nuevoEstado);
        cotizacionRepository.save(cotizacion);
    }


}