package co.edu.eci.cvds;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import co.edu.eci.cvds.service.ClienteService;
import co.edu.eci.cvds.service.ConfigurationService;
import co.edu.eci.cvds.service.CotizacionSerrvice;
import co.edu.eci.cvds.service.ProductoService;
import co.edu.eci.cvds.service.VehiculoService;
import co.edu.eci.cvds.model.Configuration;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.repository.ClienteRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class serviceTest {

    /**
     * Prueba para verificar el método equals de Producto.
     */
    @Test
    public void testEquals() {
        Producto producto1 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto2 = new Producto("nombre1", "categoria1", 100.0f, "USD");
        Producto producto3 = new Producto("nombre2", "categoria2", 200.0f, "EUR");

        assertTrue(producto1.equals(producto2));
        assertFalse(producto1.equals(producto3));
        assertFalse(producto1.equals(new Object()));
    }

    /**
     * Prueba para verificar el método equals de la clase Cliente.
     *
     * @param correo1 Correo electrónico para el primer cliente.
     * @param nombre1 Nombre para el primer cliente.
     * @param primerApellido1 Primer apellido para el primer cliente.
     * @param segundoApellido1 Segundo apellido para el primer cliente.
     * @param celular1 Número de celular para el primer cliente.
     */
    @Test
    public void testEquals(
            String correo1, String nombre1, String primerApellido1,
            String segundoApellido1, String celular1) {

        // Crear dos objetos Cliente con los mismos atributos
        Cliente cliente1 = new Cliente(correo1, nombre1, primerApellido1, segundoApellido1, celular1);
        Cliente cliente2 = new Cliente(correo1, nombre1, primerApellido1, segundoApellido1, celular1);

        // Crear un objeto Cliente con diferentes atributos
        Cliente cliente3 = new Cliente("correo2@example.com", "Pedro", "Lopez", null, "0987654321");

        // Verificar que un objeto es igual a sí mismo
        assertTrue(cliente1.equals(cliente1));

        // Verificar que dos objetos con los mismos atributos son iguales
        assertTrue(cliente1.equals(cliente2));

        // Verificar que dos objetos con diferentes atributos no son iguales
        assertFalse(cliente1.equals(cliente3));
    }

    @Test
    public void testEqualsAndHashCode() {
        Cliente cliente1 = new Cliente("correo1@example.com", "Juan", "Perez", "Gomez", "1234567890");
        Cliente cliente2 = new Cliente("correo1@example.com", "Juan", "Perez", "Gomez", "1234567890");
        Cliente cliente3 = new Cliente("correo2@example.com", "Pedro", "Lopez", null, "0987654321");

        // Comprobación de igualdad entre cliente1 y cliente2
        Assertions.assertEquals(cliente1, cliente2);
        Assertions.assertEquals(cliente1.hashCode(), cliente2.hashCode());

        // Comprobación de desigualdad entre cliente1 y cliente3
        Assertions.assertNotEquals(cliente1, cliente3);
        Assertions.assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    /**
     * Prueba para obtener un cliente por su correo.
     */
    public void testGetCliente() {
        // Crear un cliente de ejemplo y guardarlo en la base de datos
        Cliente clienteEjemplo = new Cliente("correo@ejemplo.com", "John", "Doe", null, "123456789");
        clienteRepository.save(clienteEjemplo);

        // Obtener el cliente usando el servicio
        Cliente clienteObtenido = clienteService.getCLiente("correo@ejemplo.com");

        // Verificar que el cliente obtenido coincide con el cliente de ejemplo
        Assertions.assertEquals(clienteEjemplo, clienteObtenido);
    }
   
    private ConfigurationService configurationService;

    /**
     * Prueba para agregar una configuración.
     */
    @Test
    public void testAddConfiguration() {
        Configuration configuration = new Configuration("key", "value");
        Configuration addedConfiguration = configurationService.addConfiguration(configuration);
        Assertions.assertEquals(configuration, addedConfiguration);
    }

    /**
     * Prueba para obtener una configuración por su propiedad.
     */
    @Test
    public void testGetConfiguration() {
        Configuration configuration = new Configuration("key", "value");
        configurationService.addConfiguration(configuration);
        Configuration retrievedConfiguration = configurationService.getConfiguration("key");
        Assertions.assertEquals(configuration, retrievedConfiguration);
    }

    /**
     * Prueba para obtener todas las configuraciones.
     */
    @Test
    public void testGetAllConfigurations() {
        Configuration config1 = new Configuration("key1", "value1");
        Configuration config2 = new Configuration("key2", "value2");
        configurationService.addConfiguration(config1);
        configurationService.addConfiguration(config2);

        List<Configuration> allConfigurations = configurationService.getAllConfigurations();

        Assertions.assertEquals(2, allConfigurations.size());
        Assertions.assertTrue(allConfigurations.contains(config1));
        Assertions.assertTrue(allConfigurations.contains(config2));
    }

    /**
     * Prueba para actualizar una configuración.
     */
    @Test
    public void testUpdateConfiguration() {
        Configuration configuration = new Configuration("key", "value");
        configurationService.addConfiguration(configuration);

        Configuration updatedConfiguration = new Configuration("key", "updatedValue");
        configurationService.updateConfiguration(updatedConfiguration);

        Configuration retrievedConfiguration = configurationService.getConfiguration("key");
        Assertions.assertEquals("updatedValue", retrievedConfiguration.getValor());
    }

    /**
     * Prueba para eliminar una configuración.
     */
    @Test
    public void testDeleteConfiguration() {
        Configuration configuration = new Configuration("key", "value");
        configurationService.addConfiguration(configuration);

        configurationService.deleteConfiguration("key");

        Configuration retrievedConfiguration = configurationService.getConfiguration("key");
        Assertions.assertNull(retrievedConfiguration);
    }

    /**
     * Prueba para obtener el valor de una propiedad específica (en este caso, "premio").
     */
    @Test
    public void testGetPremio() {
        Configuration premioConfiguration = new Configuration("premio", "10");
        configurationService.addConfiguration(premioConfiguration);

        String premio = configurationService.getPremio();

        Assertions.assertEquals("10", premio);
    }

    private CotizacionSerrvice cotizacionService;

    @Test
    public void testListarCotizaciones() {
        Cotizacion cotizacion1 = new Cotizacion();
        Cotizacion cotizacion2 = new Cotizacion();
        cotizacionService.agregarAlCarritoPrimeraVez(new Producto("Producto 1", "Categoria 1", 100.0f, "USD"));
        cotizacionService.agregarAlCarritoPrimeraVez(new Producto("Producto 2", "Categoria 2", 200.0f, "USD"));
        List<Cotizacion> cotizaciones = cotizacionService.listarCotizaciones();
        Assertions.assertEquals(2, cotizaciones.size());
    }
  
    /**
     * Prueba para encontrar una cotización por su ID.
     */
    @Test
    public void testEncontrarCotizacion() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacionService.agregarAlCarritoPrimeraVez(new Producto("Producto 1", "Categoria 1", 100.0f, "USD"));
        Long id = cotizacion.getIden();
        Cotizacion foundCotizacion = cotizacionService.encontrarCotizacion(id);
        Assertions.assertEquals(cotizacion, foundCotizacion);
    }

    /**
     * Prueba para agregar un producto al carrito por primera vez.
     */    
    @Test
    public void testAgregarAlCarritoPrimeraVez() {
        Producto producto = new Producto("Producto 1", "Categoria 1", 100.0f, "USD");
        Cotizacion cotizacion = cotizacionService.agregarAlCarritoPrimeraVez(producto);
        Assertions.assertTrue(cotizacion.getProductosCotizacion().contains(producto));
    }

    /**
     * Prueba para agregar un producto al carrito más de una vez.
     */
    @Test
    public void testAgregarAlCarritoNVez() {
        Producto producto = new Producto("Producto 1", "Categoria 1", 100.0f, "USD");
        Cotizacion cotizacion = new Cotizacion();
        cotizacionService.agregarAlCarritoNVez(producto, cotizacion);
        Assertions.assertTrue(cotizacion.getProductosCotizacion().contains(producto));
    }

    /**
     * Prueba para quitar un producto del carrito.
     */   
    @Test
    public void testQuitarDelCarrito() {
        Producto producto = new Producto("Producto 1", "Categoria 1", 100.0f, "USD");
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.agregarProductoAlCarrito(producto);
        cotizacionService.quitarDelCarrito(producto, cotizacion);
        Assertions.assertFalse(cotizacion.getProductosCotizacion().contains(producto));
    }

    /**
     * Prueba para calcular el total del carrito.
     */
    @Test
    public void testCalcularTotalCarrito() {
        Producto producto1 = new Producto("Producto 1", "Categoria 1", 100.0f, "USD");
        Producto producto2 = new Producto("Producto 2", "Categoria 2", 200.0f, "USD");
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.agregarProductoAlCarrito(producto1);
        cotizacion.agregarProductoAlCarrito(producto2);
        float total = cotizacionService.calcularTotalCarrito(cotizacion);
        Assertions.assertEquals(300, total);
    }

    /**
     * Prueba para agregar un vehículo.
     */
    @Test
    public void testAgregarVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo addedVehiculo = vehiculoService.agregarVehiculo(vehiculo);
        Assertions.assertEquals(vehiculo, addedVehiculo);
    }

    /**
     * Prueba para obtener un vehículo por marca, modelo y año.
     */
    @Test
    public void testGetVehiculo() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        Assertions.assertEquals(vehiculo, retrievedVehiculo);
    }

    /**
     * Prueba para agregar un producto a un vehículo y luego verificar su existencia.
     */
    @Test
    public void testAgregarProducto() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Producto producto = new Producto("Llantas", "Accesorio", 100.0f, "USD");  // Ajuste al constructor
        vehiculoService.agregarVehiculo(vehiculo);
        vehiculoService.agregarProducto("Toyota", "Corolla", "2022", producto);
        Vehiculo updatedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        Assertions.assertTrue(updatedVehiculo.getProductosVehiculo().contains(producto));
    }

    /**
     * Prueba para obtener la lista de vehículos.
     */
    @Test
    public void testGetVehiculos() {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Honda", "Civic", "2023");
        vehiculoService.agregarVehiculo(vehiculo1);
        vehiculoService.agregarVehiculo(vehiculo2);
        List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
        Assertions.assertEquals(2, vehiculos.size());
        Assertions.assertTrue(vehiculos.contains(vehiculo1));
        Assertions.assertTrue(vehiculos.contains(vehiculo2));
    }

  
    private ProductoService productoService;

    /**
    * Prueba para buscar un producto por su nombre.
    */
    @Test
    public void testBuscarProductoPorNombre() {
        Producto producto = new Producto("Celular", "Electrónica", 800.0f, "USD");
        productoService.agregarProducto(producto);

        Producto foundProducto = productoService.buscarProductoPorNombre("Celular");

        Assertions.assertEquals(producto, foundProducto);
    }

    /**
     * Prueba para buscar un producto por su nombre.
     */
    @Test
    public void testBuscarProductos() {
        Producto producto1 = new Producto("Mouse", "Computación", 20.0f, "USD");
        Producto producto2 = new Producto("Teclado", "Computación", 40.0f, "USD");
        productoService.agregarProducto(producto1);
        productoService.agregarProducto(producto2);

        List<Producto> productos = productoService.buscarProductos();

        Assertions.assertEquals(2, productos.size());
        Assertions.assertTrue(productos.contains(producto1));
        Assertions.assertTrue(productos.contains(producto2));
    }

    /**
    * Prueba para obtener la lista de productos.
    */
    @Test
    public void testActualizarProducto() {
        Producto producto = new Producto("Mouse", "Computación", 20.0f, "USD");
        productoService.agregarProducto(producto);

        Producto updatedProducto = new Producto("Mouse", "Computación", 25.0f, "USD");
        productoService.actualizarProducto(updatedProducto);

        Producto foundProducto = productoService.buscarProductoPorNombre("Mouse");
        Assertions.assertEquals(25.0f, foundProducto.getValor());
    }

    
    @Test
    public void testBorrarProducto() {
        Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
        productoService.agregarProducto(producto);

        productoService.borrarProducto("Impresora");

        Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
        Assertions.assertNull(foundProducto);
    }

    private VehiculoService vehiculoService;

    /**
     * Prueba para borrar un producto.
     */
    @Test
    public void testBorrarProductoService() {
        Producto producto = new Producto("Impresora", "Oficina", 200.0f, "USD");
        productoService.agregarProducto(producto);

        productoService.borrarProducto("Impresora");

        Producto foundProducto = productoService.buscarProductoPorNombre("Impresora");
        Assertions.assertNull(foundProducto);
    }

    /**
     * Prueba para agregar un vehículo utilizando el servicio.
     */
    @Test
    public void testAgregarVehiculoService() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo addedVehiculo = vehiculoService.agregarVehiculo(vehiculo);
        Assertions.assertEquals(vehiculo, addedVehiculo);
    }

    /**
     * Prueba para obtener un vehículo utilizando el servicio.
     */
    @Test
    public void testGetVehiculoService() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        Vehiculo retrievedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        Assertions.assertEquals(vehiculo, retrievedVehiculo);
    }

    /**
     * Prueba para agregar un producto utilizando el servicio de vehículos.
     * Se verifica que el producto agregado se encuentre en la lista de productos del vehículo actualizado.
     */
    @Test
    public void testAgregarProductoService() {
        Vehiculo vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
        vehiculoService.agregarVehiculo(vehiculo);
        Producto producto = new Producto("Llantas", "Accesorio", 100.0f, "USD");
        vehiculoService.agregarProducto("Toyota", "Corolla", "2022", producto);
        Vehiculo updatedVehiculo = vehiculoService.getVehiculo("Toyota", "Corolla", "2022");
        
        List<Producto> productosVehiculo = updatedVehiculo.getProductosVehiculo();
        boolean productoEncontrado = false;
        for (Producto p : productosVehiculo) {
            if (p.getNombre().equals(producto.getNombre())) {
                productoEncontrado = true;
                break;
            }
        }
        
        Assertions.assertTrue(productoEncontrado);
    }    

    /**
     * Prueba para obtener la lista de vehículos utilizando el servicio.
     * Se verifica que la lista obtenida contenga los vehículos agregados previamente.
     */
    @Test
    public void testGetVehiculosService() {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Honda", "Civic", "2023");
        vehiculoService.agregarVehiculo(vehiculo1);
        vehiculoService.agregarVehiculo(vehiculo2);
        List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
        Assertions.assertEquals(2, vehiculos.size());
        Assertions.assertTrue(vehiculos.contains(vehiculo1));
        Assertions.assertTrue(vehiculos.contains(vehiculo2));
    }

}
