package co.edu.eci.cvds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import co.edu.eci.cvds.config.BasicAuthInterceptor;
import co.edu.eci.cvds.config.WebConfig;
import co.edu.eci.cvds.controller.CarritoController;
import co.edu.eci.cvds.controller.ConfigurationController;
import co.edu.eci.cvds.exceptions.LincolnLinesException;
import co.edu.eci.cvds.model.CarritoDeCompras;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.EstadoCotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.CarritoRepository;
import co.edu.eci.cvds.service.CarritoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.aspectj.lang.annotation.Before;
import org.junit.*;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

/*import org.junit.jupiter.api.Test;*/

@SpringBootTest
class SpringApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}


    @Test
    public void testSuma() {
        // Arrange (Preparación)
        int a = 5;
        int b = 3;
        Calculadora calculadora = new Calculadora();

        // Act (Acción)
        int resultado = calculadora.sumar(a, b);

        // Assert (Aserción)
        assertEquals(8, resultado);
    }

    @Test
    public void testBorrarElemento() {
        // Arrange (Preparación)
        ListaCompras lista = new ListaCompras(); // Crear una instancia de ListaCompras
        lista.agregarElemento("pieza1"); // Agregar elementos a la lista
        lista.agregarElemento("pieza2");
        lista.agregarElemento("pieza3");

        // Act (Acción)
        lista.borrarElemento("pieza2"); // Borrar un elemento de la lista

        // Assert (Aserción)
        List<String> listaEsperada = new ArrayList<>(); // Crear la lista esperada después del borrado
        listaEsperada.add("pieza1");
        listaEsperada.add("pieza3");
        assertEquals(listaEsperada, lista.obtenerLista()); // Verificar que la lista actual sea igual a la lista esperada
    }

    @Test
    public void testBorrarElementoNoExistente() {
        // Arrange (Preparación)
        ListaCompras lista = new ListaCompras(); // Crear una instancia de ListaCompras
        lista.agregarElemento("pieza1"); // Agregar elementos a la lista
        lista.agregarElemento("pieza2");
        lista.agregarElemento("pieza3");

        // Act (Acción)
        lista.borrarElemento("Manzanas"); // Intentar borrar un elemento que no está en la lista

        // Assert (Aserción)
        List<String> listaEsperada = new ArrayList<>(); // La lista esperada es la misma que antes del intento de borrado
        listaEsperada.add("pieza1");
        listaEsperada.add("pieza2");
        listaEsperada.add("pieza3");
        assertEquals(listaEsperada, lista.obtenerLista()); // Verificar que la lista actual no ha cambiado
    }

    @Test
    public void testAgregarElemento() {
        // Arrange (Preparación)
        ListaCompras lista = new ListaCompras();
        String elemento1 = "Leche";
        String elemento2 = "Pan";
        String elemento3 = "Huevos";

        // Act (Acción)
        lista.agregarElemento(elemento1);
        lista.agregarElemento(elemento2);
        lista.agregarElemento(elemento3);

        // Assert (Aserción)
        assertTrue(lista.contieneElemento(elemento1)); // Verificar que el elemento 1 esté en la lista
        assertTrue(lista.contieneElemento(elemento2)); // Verificar que el elemento 2 esté en la lista
        assertTrue(lista.contieneElemento(elemento3)); // Verificar que el elemento 3 esté en la lista

    }

    @Test
    public void testCalcularPrecio() {
        // Arrange (Preparación)
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.agregarItem("Aceite", 5.0); // Item con precio unitario de $5
        cotizacion.agregarItem("Filtro de aceite", 10.0); // Item con precio unitario de $10
        cotizacion.agregarItem("Llantas", 100.0); // Item con precio unitario de $100

        CalculadoraPrecios calculadora = new CalculadoraPrecios();

        // Act (Acción)
        double precioCalculado = calculadora.calcularPrecio(cotizacion);

        // Assert (Aserción)
        assertEquals(115.0, precioCalculado, 0.001); // Verificar que el precio calculado sea $115 (5 + 10 + 100)
    }

    @Test
    public void testValidarCantidadNegativa() {
        // Arrange (Preparación)
        int cantidadProductos = -5; // Cantidad de productos negativa

        ValidadorCantidadProductos validador = new ValidadorCantidadProductos();

        // Act (Acción) y Assert (Aserción)
        try {
            validador.validarCantidad(cantidadProductos);
            fail("Se esperaba una excepción");
        } catch (IllegalArgumentException e) {
            // Verificar el mensaje de la excepción
            String mensajeEsperado = "La cantidad de productos no puede ser negativa";
            assertEquals(mensajeEsperado, e.getMessage());
        }
    }


    @Test
    public void testAgregarProducto() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto = new Producto("Pieza1", 2.5f); // Creamos un producto

        // Act (Acción)
        carrito.agregarProducto(producto); // Agregamos el producto al carrito

        // Assert (Aserción)
        assertEquals(1, carrito.getProductos().size()); // Verificamos que el producto se haya agregado al carrito
        assertEquals(producto, carrito.getProductos().get(0)); // Verificamos que el producto agregado sea el mismo
    }

    @Test
    public void testQuitarProducto() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto = new Producto("Pieza2", 1.5f); // Creamos un producto
        carrito.agregarProducto(producto); // Agregamos el producto al carrito

        // Act (Acción)
        carrito.quitarProducto(producto); // Quitamos el producto del carrito

        // Assert (Aserción)
        assertEquals(0, carrito.getProductos().size()); // Verificamos que el producto se haya quitado del carrito
    }

    @Test
    public void testCalcularSubtotal() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto1 = new Producto("Pieza1", 2.5f);
        Producto producto2 = new Producto("Pieza2", 1.5f);
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);

        // Act (Acción)
        float subtotal = carrito.calcularSubtotal(); // Calculamos el subtotal del carrito

        // Assert (Aserción)
        assertEquals(4.0f, subtotal, 0.001); // Verificamos que el subtotal sea el esperado
    }

    @Test
    public void testCalcularTotal() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto1 = new Producto("Pieza1", 2.5f);
        Producto producto2 = new Producto("Pieza2", 1.5f);
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);
        float tasaImpuesto = 10.0f; // Tasa de impuesto del 10%

        // Act (Acción)
        float total = carrito.calcularTotal(tasaImpuesto); // Calculamos el total del carrito

        // Assert (Aserción)
        assertEquals(4.4f, total, 0.001); // Verificamos que el total sea el esperado (subtotal + impuestos)
    }

    @Test
    public void testConstructorCarritoDeCompras() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();

        // Act (Acción)
        List<Producto> productos = carrito.getProductos();

        // Assert (Aserción)
        assertEquals(0, productos.size()); // Verificar que la lista de productos esté inicialmente vacía
    }

    @Test
    public void testAgregarProducto() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto1 = new Producto("pieza1", 2.5f);
        Producto producto2 = new Producto("pieza2", 1.5f);

        // Act (Acción)
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);

        // Assert (Aserción)
        List<Producto> productos = carrito.getProductos();
        assertEquals(2, productos.size()); // Verificar que se hayan agregado dos productos
        assertEquals(producto1, productos.get(0)); // Verificar que el primer producto agregado sea correcto
        assertEquals(producto2, productos.get(1)); // Verificar que el segundo producto agregado sea correcto
    }

    @Test
    public void testCalcularSubtotal() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto1 = new Producto("pieza1", 2.5f);
        Producto producto2 = new Producto("pieza2", 1.5f);
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);

        // Act (Acción)
        float subtotal = carrito.calcularSubtotal();

        // Assert (Aserción)
        assertEquals(4.0f, subtotal, 0.001); // Verificar que el subtotal sea el esperado (2.5 + 1.5)
    }

    @Test
    public void testCalcularImpuestos() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto1 = new Producto("pieza1", 2.5f);
        Producto producto2 = new Producto("pieza2", 1.5f);
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);
        float tasaImpuesto = 10.0f; // Tasa de impuesto del 10%

        // Act (Acción)
        float impuestos = carrito.calcularImpuestos(tasaImpuesto);

        // Assert (Aserción)
        assertEquals(0.4f, impuestos, 0.001); // Verificar que los impuestos sean el 10% del subtotal (4 * 0.1)
    }

    @Test
    public void testCalcularTotalConImpuesto() {
        // Arrange (Preparación)
        CarritoDeCompras carrito = new CarritoDeCompras();
        Producto producto1 = new Producto("Leche", 2.5f);
        Producto producto2 = new Producto("Pan", 1.5f);
        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);
        float tasaImpuesto = 10.0f; // Tasa de impuesto del 10%

        // Act (Acción)
        float total = carrito.calcularTotal(tasaImpuesto);

        // Assert (Aserción)
        assertEquals(4.4f, total, 0.001); // Verificar que el total sea el subtotal + impuestos (4 + 0.4)
    }

    @Test
    public void testCrearCliente() {
        // Arrange (Preparación)
        long id = 123456;
        String nombre = "Juan Perez";
        String direccion = "Calle 123, Ciudad";
        String correo = "juan@example.com";
        String telefono = "1234567890";

        // Act (Acción)
        Cliente cliente = new Cliente(id, nombre, direccion, correo, telefono);

        // Assert (Aserción)
        assertEquals(id, cliente.getId());
        assertEquals(nombre, cliente.getNombre());
        assertEquals(direccion, cliente.getDireccion());
        assertEquals(correo, cliente.getCorreoElectronico());
        assertEquals(telefono, cliente.getTelefono()); // Verificar la creación de un cliente:
    }

    // Verificar la modificación de los atributos de un cliente
    @Test
    public void testModificarCliente() {
        // Arrange (Preparación)
        Cliente cliente = new Cliente(1, "Pedro Gomez", "Carrera 45", "pedro@example.com", "987654321");

        // Act (Acción)
        cliente.setNombre("Pedro Rodriguez");
        cliente.setDireccion("Calle 67");
        cliente.setCorreoElectronico("pedro.rodriguez@example.com");
        cliente.setTelefono("123456789");

        // Assert (Aserción)
        assertEquals("Pedro Rodriguez", cliente.getNombre());
        assertEquals("Calle 67", cliente.getDireccion());
        assertEquals("pedro.rodriguez@example.com", cliente.getCorreoElectronico());
        assertEquals("123456789", cliente.getTelefono());
    }

    @Test
    public void testGetId() {
        // Arrange (Preparación)
        long idEsperado = 123456;
        Cliente cliente = new Cliente(idEsperado, "Nombre", "Dirección", "correo@example.com", "1234567890");

        // Act (Acción)
        long idObtenido = cliente.getId();

        // Assert (Aserción)
        assertEquals(idEsperado, idObtenido);
    }

    @Test
    public void testSetId() {
        // Arrange (Preparación)
        Cliente cliente = new Cliente(0, "Nombre", "Dirección", "correo@example.com", "1234567890");
        long idEsperado = 123456;

        // Act (Acción)
        cliente.setId(idEsperado);
        long idObtenido = cliente.getId();

        // Assert (Aserción)
        assertEquals(idEsperado, idObtenido);
    }

    @Test
    public void testGetNombre() {
        // Arrange (Preparación)
        String nombreEsperado = "Juan Perez";
        Cliente cliente = new Cliente(0, nombreEsperado, "Dirección", "correo@example.com", "1234567890");

        // Act (Acción)
        String nombreObtenido = cliente.getNombre();

        // Assert (Aserción)
        assertEquals(nombreEsperado, nombreObtenido);
    }

    @Test
    public void testSetNombre() {
        // Arrange (Preparación)
        Cliente cliente = new Cliente(0, "Nombre", "Dirección", "correo@example.com", "1234567890");
        String nombreEsperado = "Juan Perez";

        // Act (Acción)
        cliente.setNombre(nombreEsperado);
        String nombreObtenido = cliente.getNombre();

        // Assert (Aserción)
        assertEquals(nombreEsperado, nombreObtenido);
    }

       @Test
    public void testGetIdCotizacion() {
        // Arrange (Preparación)
        String idEsperado = "123456";
        Cotizacion cotizacion = new Cotizacion(idEsperado, LocalDateTime.now(), new Cliente(1, "Cliente", "Dirección", "correo@example.com", "1234567890"), new ArrayList<>(), 0, 0, 0, EstadoCotizacion.PENDIENTE);

        // Act (Acción)
        String idObtenido = cotizacion.getId();

        // Assert (Aserción)
        assertEquals(idEsperado, idObtenido);
    }

    @Test
    public void testSetIdCotizacion() {
        // Arrange (Preparación)
        Cotizacion cotizacion = new Cotizacion("", LocalDateTime.now(), new Cliente(1, "Cliente", "Dirección", "correo@example.com", "1234567890"), new ArrayList<>(), 0, 0, 0, EstadoCotizacion.PENDIENTE);
        String idEsperado = "123456";

        // Act (Acción)
        cotizacion.setId(idEsperado);
        String idObtenido = cotizacion.getId();

        // Assert (Aserción)
        assertEquals(idEsperado, idObtenido);
    }

    @Test(expected = LincolnLinesException.class)
    public void testSetValorConValorNegativo() throws LincolnLinesException {
        Producto producto = new Producto();
        producto.setValor(-50); // Intentar establecer un valor negativo debe lanzar una excepción
    
    @Test(expected = LincolnLinesException.class)
    public void testSetImpuestoConImpuestoNegativo() throws LincolnLinesException {
        Producto producto = new Producto();
        producto.setImpuesto(-10); // Intentar establecer un impuesto negativo debe lanzar una excepción
    }

    @Test(expected = LincolnLinesException.class)
    public void testSetDescuentoConDescuentoNegativo() throws LincolnLinesException {
        Producto producto = new Producto();
        producto.setDescuento(-15); // Intentar establecer un descuento negativo debe lanzar una excepción
    }

    // Este test verifica que el método hashCode genere un valor hash consistente para objetos con los mismos atributos
    @Test
    public void testHashCode() {
        // Se crea el primer objeto Producto con ciertos valores para sus atributos
        Producto producto1 = new Producto();
        producto1.setNombre("Aceite");
        producto1.setDescripcionBreve("Aceite de motor");
        producto1.setDescripcionTecnica("Lubricante para vehículos");
        producto1.setImagen("aceite.jpg");
        producto1.setValor(50);
        producto1.setMoneda("USD");
        producto1.setDescuento(0);
        producto1.setImpuesto(5);

        // Se crea el segundo objeto Producto con los mismos valores para sus atributos
        Producto producto2 = new Producto();
        producto2.setNombre("Aceite");
        producto2.setDescripcionBreve("Aceite de motor");
        producto2.setDescripcionTecnica("Lubricante para vehículos");
        producto2.setImagen("aceite.jpg");
        producto2.setValor(50);
        producto2.setMoneda("USD");
        producto2.setDescuento(0);
        producto2.setImpuesto(5);

        // Se verifica que el valor de hashCode para ambos objetos sea igual
        assertEquals(producto1.hashCode(), producto2.hashCode());
    }

    @Test
    public void testEquals() {
        // Crear un objeto Producto con ciertos valores para sus atributos
        Producto producto1 = new Producto();
        producto1.setNombre("Aceite");
        producto1.setDescripcionBreve("Aceite de motor");
        producto1.setDescripcionTecnica("Lubricante para vehículos");
        producto1.setCategoria("Aceites y lubricantes");
        producto1.setImagen("aceite.jpg");
        producto1.setValor(50);
        producto1.setMoneda("USD");
        producto1.setDescuento(0);
        producto1.setImpuesto(5);

        // Crear otro objeto Producto con los mismos valores para sus atributos
        Producto producto2 = new Producto();
        producto2.setNombre("Aceite");
        producto2.setDescripcionBreve("Aceite de motor");
        producto2.setDescripcionTecnica("Lubricante para vehículos");
        producto2.setCategoria("Aceites y lubricantes");
        producto2.setImagen("aceite.jpg");
        producto2.setValor(50);
        producto2.setMoneda("USD");
        producto2.setDescuento(0);
        producto2.setImpuesto(5);

        // Verificar que los objetos producto1 y producto2 sean considerados iguales
        assertTrue(producto1.equals(producto2));
    }


    @Test
    public void testConstructorVehiculo() {
        // Define los valores esperados para la marca, modelo y año
        String marcaEsperada = "Toyota";
        String modeloEsperado = "Corolla";
        String yearEsperado = "2022";

        // Crea un nuevo objeto Vehiculo utilizando el constructor con parámetros
        Vehiculo vehiculo = new Vehiculo(marcaEsperada, modeloEsperado, yearEsperado);

        // Verifica que los valores asignados al vehículo coincidan con los esperados
        assertEquals(marcaEsperada, vehiculo.getMarca());
        assertEquals(modeloEsperado, vehiculo.getModel());
        assertEquals(yearEsperado, vehiculo.getYear());
    }

    @Test
    public void testConstructorVehiculoSinArgumentos() {
        Vehiculo vehiculo = new Vehiculo();

        // Verificar que los atributos del objeto creado estén inicializados correctamente
        assertNull(vehiculo.getMarca());
        assertNull(vehiculo.getModel());
        assertNull(vehiculo.getYear());
    }

    @Test
    public void testHashCode() {
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");

        // Verificar que el valor de hashCode sea igual para objetos iguales
        assertEquals(vehiculo1.hashCode(), vehiculo2.hashCode());
    }

   @Test
    public void testEquals() {
        // Crear dos vehículos con los mismos valores para marca, modelo y año
        Vehiculo vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
        Vehiculo vehiculo2 = new Vehiculo("Toyota", "Corolla", "2022");

        // Crear un vehículo con valores diferentes para probar la desigualdad
        Vehiculo vehiculoDiferente = new Vehiculo("Honda", "Civic", "2021");

        // Verificar que vehiculo1 sea igual a vehiculo2
        assertTrue(vehiculo1.equals(vehiculo2));

        // Verificar que vehiculo1 no sea igual a vehiculoDiferente
        assertFalse(vehiculo1.equals(vehiculoDiferente));

        // Verificar que un vehículo no sea igual a null
        assertFalse(vehiculo1.equals(null));

        // Verificar que un vehículo no sea igual a un objeto de otra clase
        assertFalse(vehiculo1.equals("Toyota Corolla 2022"));
    }

        @Mock
    private CarritoDeCompras carritoDeComprasMock;

    private CarritoRepository carritoRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carritoRepository = new CarritoRepository();
        carritoRepository.setCarritoDeCompras(carritoDeComprasMock);
    }

    @Test
    public void testAgregarProducto() {
        // Arrange
        String productoId = "1";

        // Simular el carrito de compras con un mapa vacío de productos
        Map<String, Integer> productosEnCarrito = new HashMap<>();
        when(carritoDeComprasMock.getProductosEnCarrito()).thenReturn(productosEnCarrito);

        // Act
        carritoRepository.agregarProducto(productoId);

        // Assert
        assertEquals(1, productosEnCarrito.size());
        assertEquals(Integer.valueOf(1), productosEnCarrito.get(productoId));
    }

    @Test
    public void testAgregarProductoExistente() {
        // Arrange
        String productoId = "1";

        // Simular el carrito de compras con un producto existente
        Map<String, Integer> productosEnCarrito = new HashMap<>();
        productosEnCarrito.put(productoId, 1);
        when(carritoDeComprasMock.getProductosEnCarrito()).thenReturn(productosEnCarrito);

        // Act
        carritoRepository.agregarProducto(productoId);

        // Assert
        assertEquals(1, productosEnCarrito.size());
        assertEquals(Integer.valueOf(2), productosEnCarrito.get(productoId));
    }

    private CarritoRepository carritoRepository;
    private Map<String, Integer> productosEnCarrito;

    @Before
    public void setUp() {
        carritoRepository = new CarritoRepository();
        productosEnCarrito = new HashMap<>();
        carritoRepository.setProductosEnCarrito(productosEnCarrito);
    }

    @Test
    public void testQuitarProductoExistenteUnaUnidad() {
        // Arrange
        String productoId = "1";
        productosEnCarrito.put(productoId, 1);

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(0, productosEnCarrito.size());
    }

    @Test
    public void testQuitarProductoExistenteMasDeUnaUnidad() {
        // Arrange
        String productoId = "2";
        int cantidadInicial = 3;
        productosEnCarrito.put(productoId, cantidadInicial);

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(cantidadInicial - 1, productosEnCarrito.get(productoId).intValue());
    }

    @Test
    public void testQuitarProductoInexistente() {
        // Arrange
        String productoId = "3";

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(0, productosEnCarrito.size());
    }

    @Test
    public void testQuitarProductoExistenteCeroUnidades() {
        // Arrange
        String productoId = "4";
        productosEnCarrito.put(productoId, 0);

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(0, productosEnCarrito.size());
    }

    private CarritoRepository carritoRepository;
    private Map<String, Integer> productosEnCarrito;

    @Before
    public void setUp() {
        carritoRepository = new CarritoRepository();
        productosEnCarrito = new HashMap<>();
        carritoRepository.setProductosEnCarrito(productosEnCarrito);
    }

    @Test
    public void testAgregarProductoNuevo() {
        // Arrange
        String productoId = "1";

        // Act
        carritoRepository.agregarProducto(productoId);

        // Assert
        assertEquals(1, productosEnCarrito.size());
        assertEquals(Integer.valueOf(1), productosEnCarrito.get(productoId));
    }

    @Test
    public void testAgregarProductoExistente() {
        // Arrange
        String productoId = "2";
        productosEnCarrito.put(productoId, 2); // Producto existente con cantidad 2

        // Act
        carritoRepository.agregarProducto(productoId);

        // Assert
        assertEquals(1, productosEnCarrito.size());
        assertEquals(Integer.valueOf(3), productosEnCarrito.get(productoId));
    }

    @Test
    public void testAgregarVariosProductos() {
        // Arrange
        String productoId1 = "3";
        String productoId2 = "4";

        // Act
        carritoRepository.agregarProducto(productoId1);
        carritoRepository.agregarProducto(productoId2);

        // Assert
        assertEquals(2, productosEnCarrito.size());
        assertEquals(Integer.valueOf(1), productosEnCarrito.get(productoId1));
        assertEquals(Integer.valueOf(1), productosEnCarrito.get(productoId2));
    }

    private CarritoRepository carritoRepository;
    private Map<String, Integer> productosEnCarrito;

    @Before
    public void setUp() {
        carritoRepository = new CarritoRepository();
        productosEnCarrito = new HashMap<>();
        carritoRepository.setProductosEnCarrito(productosEnCarrito);
    }

    @Test
    public void testQuitarProductoExistenteUnaUnidad() {
        // Arrange
        String productoId = "1";
        productosEnCarrito.put(productoId, 1);

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(0, productosEnCarrito.size());
    }

    @Test
    public void testQuitarProductoExistenteMasDeUnaUnidad() {
        // Arrange
        String productoId = "2";
        int cantidadInicial = 3;
        productosEnCarrito.put(productoId, cantidadInicial);

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(cantidadInicial - 1, productosEnCarrito.get(productoId).intValue());
    }

    @Test
    public void testQuitarProductoInexistente() {
        // Arrange
        String productoId = "3";

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(0, productosEnCarrito.size());
    }

    @Test
    public void testQuitarProductoExistenteCeroUnidades() {
        // Arrange
        String productoId = "4";
        productosEnCarrito.put(productoId, 0);

        // Act
        carritoRepository.quitarProducto(productoId);

        // Assert
        assertEquals(0, productosEnCarrito.size());
    }

    private CarritoRepository carritoRepository;
    private CarritoDeCompras carritoDeCompras;

    @Before
    public void setUp() {
        carritoRepository = new CarritoRepository();
        carritoDeCompras = new CarritoDeCompras();
        // Establecer un valor específico para el subtotal en el carrito de compras
        carritoDeCompras.setSubtotal(100.0f);
    }

    @Test
    public void testCalcularSubtotal() {
        // Arrange
        carritoRepository.setCarritoDeCompras(carritoDeCompras);

        // Act
        float subtotalCalculado = carritoRepository.calcularSubtotal();

        // Assert
        assertEquals(100.0f, subtotalCalculado, 0.0f); // Verificar que el subtotal calculado sea igual al establecido
    }

        @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObtenerSubtotal() {
        // Arrange
        float subtotalEsperado = 150.0f;
        when(carritoService.calcularSubtotal()).thenReturn(subtotalEsperado);

        // Act
        float subtotalObtenido = carritoController.obtenerSubtotal();

        // Assert
        assertEquals(subtotalEsperado, subtotalObtenido, 0.0f); // Verificar que el subtotal obtenido sea igual al esperado
    }

    @Mock
    private CarritoService carritoService; // Mock del servicio

    @InjectMocks
    private CarritoController carritoController; // Controlador a probar

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Inicializar los mocks
    }

    @Test
    public void testObtenerSubtotal() {
        // Valor esperado para el subtotal
        float subtotalEsperado = 100.0f;

        // Configuración del mock para el servicio
        when(carritoService.calcularSubtotal()).thenReturn(subtotalEsperado);

        // Llamada al método del controlador que se va a probar
        float subtotalObtenido = carritoController.obtenerSubtotal();

        // Verificación de que el resultado sea el esperado
        assertEquals(subtotalEsperado, subtotalObtenido, 0.0f);
    }

        @Mock
    private ConfigurationService configurationService; // Mock del servicio

    @InjectMocks
    private ConfigurationController configurationController; // Controlador a probar

    @Mock
    private Model model; // Mock de Spring Model

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Inicializar los mocks
    }

    @Test
    public void testExample() {
        // Valor esperado para el premio
        String premioEsperado = "50% de descuento";

        // Configuración del mock para el servicio
        when(configurationService.getPremio()).thenReturn(premioEsperado);

        // Llamada al método del controlador que se va a probar
        String viewName = configurationController.example(model);

        // Verificación de que se llamó al método getPremio en el servicio
        // y se agregó el atributo "premio" al Model con el valor esperado
        assertEquals("example", viewName);
        assertEquals(premioEsperado, model.getAttribute("premio"));
    }

    @Test
    public void testExampleApi() {
        // Mock del controlador
        ConfigurationController configurationController = new ConfigurationController(mock(ConfigurationService.class));

        // Llamada al método del controlador que se va a probar
        String result = configurationController.exampleApi();

        // Verificación de que el resultado es el esperado
        assertEquals("example-api", result);
    }

    @Mock
    private CarritoService carritoService; // Mock del servicio

    @InjectMocks
    private CarritoController carritoController; // Controlador a probar

    @Test
    public void testAgregarProductoAlCarrito() {
        // Inicializar los mocks
        MockitoAnnotations.initMocks(this);

        // Producto ID de ejemplo
        String productoId = "ABC123";

        // Llamada al método del controlador que se va a probar
        carritoController.agregarProductoAlCarrito(productoId);

        // Verificación de que se llamó al método agregarProducto en el servicio
        verify(carritoService).agregarProducto(productoId);
    }

        @Mock
    private InterceptorRegistry registry; // Mock del registro de interceptores

    @Mock
    private BasicAuthInterceptor basicAuthInterceptor; // Mock del interceptor de autenticación básica

    @InjectMocks
    private WebConfig webConfig; // Objeto a probar

    @Test
    public void testAddInterceptors() {
        // Inicializar los mocks y configurar el interceptor en WebConfig
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(webConfig, "basicAuthInterceptor", basicAuthInterceptor);

        // Llamada al método a probar
        webConfig.addInterceptors(registry);

        // Verificación de que se agrega el interceptor con las rutas adecuadas
        verify(registry).addInterceptor(basicAuthInterceptor)
                .addPathPatterns("/login/protected/**")
                .excludePathPatterns("/static/**");
    }

    @Autowired
    private WebConfig webConfig;

    @Test
    public void testWebConfigAutowired() {
        assertNotNull(webConfig); // Verificar que la instancia de WebConfig se haya autowired correctamente
        assertNotNull(webConfig.getBasicAuthInterceptor()); // Verificar que el BasicAuthInterceptor también se haya autowired correctamente
    }

}
