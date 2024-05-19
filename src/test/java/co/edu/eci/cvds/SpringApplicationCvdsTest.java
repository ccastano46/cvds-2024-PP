package co.edu.eci.cvds;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Login;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.CotizacionRepository;
import co.edu.eci.cvds.repository.LoginRepository;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import co.edu.eci.cvds.service.CotizacionSerrvice;
import co.edu.eci.cvds.service.LoginService;
import co.edu.eci.cvds.service.ProductoService;
import co.edu.eci.cvds.service.VehiculoService;



@SpringBootTest
class SpringApplicationTests {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @InjectMocks
    private ProductoService productoService;



    @Mock
    private CotizacionRepository cotizacionRepository;

    @InjectMocks
    private CotizacionSerrvice cotizacionSerrvice;

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private LoginService loginService;





    @Test
    void shouldLLenarTablas(){
        Vehiculo vehiculo = new Vehiculo("Suzuki","i-40","2023");
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo vehiculoGuardado = vehiculoService.agregarVehiculo(vehiculo);
        assertEquals(vehiculo, vehiculoGuardado);
        assertEquals(1,vehiculoService.getVehiculos().size());
        vehiculoGuardado = vehiculoRepository.findByMarcaAndModelAndYearVehicle(vehiculo.getMarca(),vehiculo.getModel(),vehiculo.getYearVehicle()).get(0);
        assertEquals(vehiculoService.getVehiculo("Suzuki","i-40","2023"), vehiculoGuardado);

        Producto producto = new Producto("Motor1","Motor",(float)15.2,"US");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
        Producto productoGuardado = productoService.agregarProducto(producto);
        assertEquals(producto, productoGuardado);
        assertEquals(1,productoService.buscarProductos().size());
        productoGuardado = productoRepository.findByNombre("Motor1").get(0);
        assertEquals(productoService.buscarProductoPorNombre("Motor1"),productoGuardado);
    }

    /**
     * Prueba para verificar el método equals de Producto.
     */
    @Test
    void testEquals() {
        Producto producto1 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto2 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto3 = new Producto("nombre2", "categoria2", 200.0f, "EUR");

        assertEquals(producto1, producto2);
        assertNotEquals(producto1, producto3);
        assertNotEquals(producto1, new Object());
    }



    @Test
    void shouldAgregarAlCarrito() {
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto1);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        List<Producto> carrito = cotizacionSerrvice.verCarrito(cotizacion.getIden());
        assertEquals(2, carrito.size());
        assertTrue(carrito.contains(producto) && carrito.contains(producto1));
    }
    @Test
    void shouldNotAgregarCarrito(){
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto2", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        List<Producto> carrito = cotizacionSerrvice.verCarrito(cotizacion.getIden());
        assertEquals(1, carrito.size());
        assertTrue(carrito.contains(producto) && !carrito.contains(producto1));
    }

    @Test
    void shouldCalcularTotalCarrito(){
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",500000f,"COP");
        Producto producto1 = new Producto("Producto", "Categoria",200f,"USD");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto1);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        Money calculado = cotizacionSerrvice.calcularTotalCarrito(cotizacion);
        assertEquals(Monetary.getCurrency("COP"),calculado.getCurrency());
        float tasaError = (779600f-calculado.getNumber().floatValue())/779600f;
        assertTrue(tasaError <= 0.15);
    }

    @Test
    void shouldAgendar(){
        Cotizacion cotizaion1 = new Cotizacion(new Vehiculo("Suzuki","Swift","2014"));
        Cotizacion cotizaion2 = new Cotizacion(new Vehiculo("Mercedes","Sedan","2022"));
        assertTrue(cotizacionSerrvice.agendarCita(LocalDateTime.of(2024, 5, 10, 8, 0), "Bogota", "Calle 159 #7-74", cotizaion1));
        when(cotizacionRepository.findByCita()).thenReturn(List.of(cotizaion1));
        assertTrue(cotizacionSerrvice.agendarCita(LocalDateTime.of(2024,5,10,15,0),"Bogota","Calle 66 #11-50",cotizaion2));
        when(cotizacionRepository.findByCita()).thenReturn(List.of(cotizaion1,cotizaion2));
        assertEquals(2,cotizacionSerrvice.cotizacionesAgendadas().size());

    }

    @Test
    void shouldCalcularTotal(){
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",500000f,"COP");
        Producto producto1 = new Producto("Producto", "Categoria",5,"USD");
        Producto producto3 = new Producto("Producto2", "Categoria1",50,"EUR");
        producto.setDescuento(0.8f);
        producto.setImpuesto(0.2f);
        producto1.setDescuento(0.1f);
        producto1.setImpuesto(0.6f);
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto3);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        cotizacionSerrvice.agregarAlCarritoNVez(producto3,cotizacion);
        float calculado = cotizacionSerrvice.cotizacionTotal(cotizacion);
        float esperado = 728799.79f + 101947.09f + 30514.26f;
        assertTrue(0.15 <= (esperado-calculado)/esperado);
        /*
        Carrito 728'799,79
        Descuento 101947,09
        Impuesto 30'514.26
         */



    }





    /**
     * Prueba para quitar un producto del carrito.
     */
    @Test
    void testQuitarDelCarrito() {
        Vehiculo vehiculo = new Vehiculo("TOYOTAF","PRIUS","2005");
        Cotizacion cotizacion = new Cotizacion(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);
        Producto producto =  new Producto("Producto1","Categoria",200f,"US");
        Producto producto1 = new Producto("Producto", "Categoria",200f,"US");
        vehiculoService.agregarVehiculo(vehiculo);
        productoService.agregarProducto(producto);
        productoService.agregarProducto(producto1);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto);
        vehiculoService.agregarProducto("TOYOTAF","PRIUS","2005",producto1);
        cotizacion = cotizacionSerrvice.agregarAlCarritoPrimeraVez(producto,vehiculo);
        when(cotizacionRepository.findByIden(anyLong())).thenReturn(List.of(cotizacion));
        cotizacionSerrvice.agregarAlCarritoNVez(producto1,cotizacion);
        cotizacionSerrvice.quitarDelCarrito(producto,cotizacion);
        assertEquals(1,cotizacionSerrvice.verCarrito(cotizacion.getIden()).size());
        assertFalse(cotizacionSerrvice.verCarrito(cotizacion.getIden()).contains(producto));
        assertTrue(cotizacionSerrvice.verCarrito(cotizacion.getIden()).contains(producto1));
    }

    /**
     * Prueba para agregar un vehículo.
     */
    @Test
    void testAgregarVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
        Vehiculo addedVehiculo = vehiculoService.agregarVehiculo(vehiculo);
        assertEquals(vehiculo, addedVehiculo);
    }

    /**
     * Prueba para obtener un vehículo por marca, modelo y año.
     */
    @Test
    void testGetVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        assertEquals(vehiculo, retrievedVehiculo);
    }

    /**
     * Prueba para obtener la lista de vehículos.
     */
    @Test
    void testGetVehiculos() {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Honda", "Civic", "2023");
        vehiculoService.agregarVehiculo(vehiculo1);
        vehiculoService.agregarVehiculo(vehiculo2);
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo1,vehiculo2));
        List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
        assertEquals(2, vehiculos.size());
        assertTrue(vehiculos.contains(vehiculo1));
        assertTrue(vehiculos.contains(vehiculo2));
    }


    /**
     * Prueba para buscar un producto por su nombre.
     */
    @Test
    void testBuscarProductos() {
        Producto producto1 = new Producto("Mouse", "Computación", 20.0f, "USD");
        Producto producto2 = new Producto("Teclado", "Computación", 40.0f, "USD");
        productoService.agregarProducto(producto1);
        productoService.agregarProducto(producto2);
        when(productoRepository.findAll()).thenReturn(List.of(producto1,producto2));
        List<Producto> productos = productoService.buscarProductos();

        assertEquals(2, productos.size());
        assertTrue(productos.contains(producto1));
        assertTrue(productos.contains(producto2));
    }

    /**
     * Prueba para actualizar producto.
     */
    @Test
    void testActualizarProducto() {
        Producto producto = new Producto("Mouse", "Computación", 20.0f, "USD");
        productoService.agregarProducto(producto);
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
        Producto updatedProducto = new Producto("Mouse", "Computación", 25.0f, "USD");
        productoService.actualizarProducto(updatedProducto);
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(updatedProducto));
        Producto foundProducto = productoService.buscarProductoPorNombre("Mouse");
        assertEquals(25.0f, foundProducto.getValor());
    }


    @Test
    void testBorrarProducto() {

        Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        productoService.agregarProducto(producto);

        productoService.borrarProducto("Impresora");

        Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
        assertNull(foundProducto);
    }

    /**
     * Prueba para borrar un producto.
     */
    @Test
    void testBorrarProductoService() {
        Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
        productoService.agregarProducto(producto);

        productoService.borrarProducto("Impresora");

        Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
        assertNull(foundProducto);
    }

    /**
     * Prueba para obtener un vehículo utilizando el servicio.
     */
    @Test
    void testGetVehiculoService() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        when(vehiculoRepository.findByMarcaAndModelAndYearVehicle(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        assertEquals(vehiculo, retrievedVehiculo);
    }

    @Test
    void shouldBeEquals(){
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        Producto producto = new Producto("Producto1","Categoria",200f,"COP");
        Producto producto1 = new Producto("Producto1","Categoria",200f,"COP");
        producto.setImpuesto(0.15f);
        producto.setImagen("...");
        producto1.setImpuesto(0.15f);
        producto1.setImagen("...");
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        Cliente cliente = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        Cliente cliente1 = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        assertEquals(vehiculo, vehiculo2);
        assertEquals(producto, producto1);
        assertEquals(cotizacion, cotizacion2);
        assertEquals(cliente, cliente1);
    }

    @Test
    void shouldNotBeEquals(){
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculo2.setModel("Cambio");
        Producto producto = new Producto("Producto1","Categoria",200f,"COP");
        Producto producto1 = new Producto("Producto1","Categoria",200f,"COP");
        producto.setImpuesto(0.15f);
        producto.setImagen("...");
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        cotizacion2.agendar(null,"Bogota",null);
        Cliente cliente = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        Cliente cliente1 = new Cliente("castanocamilo522@gmail.com","Camilo","Casta",null,"3183074075");
        cliente.setSegundoApellido("Quintanilla");
        assertNotEquals(vehiculo, vehiculo2);
        assertNotEquals(producto, producto1);
        assertNotEquals(cotizacion, cotizacion2);
        assertNotEquals(cliente, cliente1);
    }
    @Test
    void testAuthenticateValidCredentials() {
        String username = "admin";
        String password = "password123";
        when(loginRepository.existsByUsername(username, password)).thenReturn(true);

        // Verifica que el método authenticate devuelva true para credenciales válidas
        assertTrue(loginService.authenticate(username, password));
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        String username = "admin";
        String password = "wrongpassword";
        when(loginRepository.existsByUsername(username, password)).thenReturn(false);

        // Verifica que el método authenticate devuelva false para credenciales inválidas
        assertFalse(loginService.authenticate(username, password));
    }

    @Test
    void testCreateAdminAccountSuccess() {
        String username = "newadmin";
        String password = "adminpass";

        when(loginRepository.existsByUsername(username, password)).thenReturn(false);

        // Verifica que el método createAdminAccount devuelva true si la cuenta no existe
        assertTrue(loginService.createAdminAccount(username, password));

        // Verifica que se haya llamado al método save del repositorio
        verify(loginRepository, times(1)).save(any(Login.class));
    }

    @Test
    void testCreateAdminAccountFailure() {
        String username = "existingadmin";
        String password = "adminpass";

        when(loginRepository.existsByUsername(username, password)).thenReturn(true);

        // Verifica que el método createAdminAccount devuelva false si la cuenta ya existe
        assertFalse(loginService.createAdminAccount(username, password));

        // Verifica que no se haya llamado al método save del repositorio
        verify(loginRepository, times(0)).save(any(Login.class));
    }
}
