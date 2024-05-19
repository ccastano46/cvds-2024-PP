package co.edu.eci.cvds;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.Categoria;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.model.Cotizacion;
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.service.CategoriaService;
import co.edu.eci.cvds.service.ClienteService;
import co.edu.eci.cvds.service.CotizacionService;
import co.edu.eci.cvds.service.LoginService;
import co.edu.eci.cvds.service.ProductoService;
import co.edu.eci.cvds.service.VehiculoService;


@SpringBootTest
class SpringApplicationTests {

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    private CotizacionService cotizacionService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LoginService loginService;

    @Test
    void shouldAgregarProducto() {
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();


        Producto producto1;
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            producto1 = new Producto("celular",4800000f,"COP");
            producto1.setDescripcionBreve("Prueba de agregacion");
            producto1.setDescuento(0.25f);
            productoService.agregarProducto(producto1);
            producto1 = productoService.buscarProductoPorNombre("Computador");
            categoriaService.agregarProducto("Electronica",producto1);
            producto1 = productoService.buscarProductoPorNombre("CelUlar");
            categoriaService.agregarProducto("Electronica",producto1);
            producto1 = productoService.buscarProductoPorNombre("Casco");
            categoriaService.agregarProducto("Mecanica",producto1);
            categoriaService.agregarProducto("Sports",producto1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        Producto celular = productoService.buscarProductoPorNombre("Celular");
        Producto computaDor = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        Categoria electronica = categoriaService.buscarCategoria("electronica");
        Categoria sports = categoriaService.buscarCategoria("sports");
        Categoria mecanica = categoriaService.buscarCategoria("mecanica");
        assertEquals(2, categoriaService.listaProductos("Electronica").size());
        assertEquals(1, categoriaService.listaProductos("Mecanica").size());
        assertEquals(1, categoriaService.listaProductos("Sports").size());
        assertTrue(categoriaService.listaProductos("Electronica").contains(celular));
        assertTrue(categoriaService.listaProductos("Mecanica").contains(casco));
        assertTrue(categoriaService.listaProductos("Sports").contains(casco));
        assertTrue(categoriaService.listaProductos("Electronica").contains(computaDor));
        assertTrue(productoService.buscarProductos().contains(casco));
        assertTrue(productoService.buscarProductos().contains(celular));
        assertTrue(productoService.buscarProductos().contains(computaDor));
        assertTrue(categoriaService.listarCategorias().contains(electronica));
        assertTrue(categoriaService.listarCategorias().contains(mecanica));
        assertTrue(categoriaService.listarCategorias().contains(sports));
        assertEquals(3,productoService.buscarProductos().size());
        assertEquals(3,categoriaService.listarCategorias().size());
        assertTrue(productoService.conocerCategorias("Celular").contains(categoriaService.buscarCategoria("Electronica")));
        assertTrue(productoService.conocerCategorias("Computador").contains(categoriaService.buscarCategoria("Electronica")));
        assertEquals(2,productoService.conocerCategorias("casco").size());
        assertTrue(productoService.conocerCategorias("Casco").contains(categoriaService.buscarCategoria("Mecanica")));
        assertTrue(productoService.conocerCategorias("cAscO").contains(categoriaService.buscarCategoria("Sports")));


    }


    @Test
    void shouldAgregarVehiculo(){
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        vehiculoService.agregarVehiculo(new Vehiculo("Suzuki","Swfit","2012"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        List<Vehiculo> vehiculos = vehiculoService.getVehiculos();
        assertEquals(2,vehiculos.size());
        assertEquals("SUZUKI",vehiculoService.getVehiculo("Suzuki","swfit").getMarca());
        assertTrue(vehiculos.contains(new Vehiculo("Suzuki","Swfit","2012")));
        assertTrue(vehiculos.contains(new Vehiculo("Volkswagen","Gol","2001")));
    }

    @Test
    void shouldAsociarProductoVehiculo(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        Producto producto1;
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            producto1 = new Producto("celular",4800000f,"COP");
            producto1.setDescripcionBreve("Prueba de agregacion");
            producto1.setDescuento(0.25f);
            productoService.agregarProducto(producto1);
            //Asociar productos con categorias
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica", productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports", productoService.buscarProductoPorNombre("Casco"));
            //Asociar Vehiculos con productos
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","supra",productoService.buscarProductoPorNombre("Casco"));
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        Producto computador = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        assertEquals(2,vehiculoService.listraProductosOptimos("volkswagen","gol").size());
        assertEquals(1,vehiculoService.listraProductosOptimos("volkswagen","suprA").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(computador));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(casco));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","supra").contains(casco));
    }

    @Test
    void shouldNotAsociarVehiculo(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador", 700f, "USD"));
        }catch (LincolnLinesException e){
            fail("Lanzo excepcion");
        }
        //Asociar Vehiculos con productos
        try{
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.PRODUCTO_SIN_CATEGORIA,e.getMessage());
        }
    }

    @Test
    void shouldAgregarACarrito(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            //Asociar productos con categorias
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Mecanica", productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports", productoService.buscarProductoPorNombre("Casco"));
            //Asociar Vehiculos con productos
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","supra",productoService.buscarProductoPorNombre("Casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("computador"));
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        Vehiculo vehiculo = vehiculoService.getVehiculo("volkswagen","gol");
        Producto computador = productoService.buscarProductoPorNombre("ComputaDor");
        Producto casco = productoService.buscarProductoPorNombre("casco");
        assertEquals(3,cotizacionService.verCarrito(cotizacion.getIden()).size());
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).contains(casco));
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).contains(computador));
        assertEquals(2,cotizacionService.contadorProducto(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden()));
        assertEquals(1,cotizacionService.contadorProducto(productoService.buscarProductoPorNombre("computador"),cotizacion.getIden()));
        assertEquals(vehiculo,cotizacionService.getVehiculo(cotizacion.getIden()));
        assertEquals(2,vehiculoService.listraProductosOptimos("volkswagen","gol").size());
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(computador));
        assertTrue(vehiculoService.listraProductosOptimos("volkswagen","gol").contains(casco));
        assertEquals(Cotizacion.EN_PROCESO,cotizacionService.conocerEstado(cotizacion.getIden()));
    }

    @Test
    void shouldNotAgregarCarrito(){
        //Limpieza de tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        try{
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
        }catch (LincolnLinesException e){
            fail(e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
           fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.PRODUCTO_NO_COMPATIBLE, e.getMessage());
        }

        try {
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,-1);
            fail("No lanzo excepcion");
        } catch (LincolnLinesException e) {
            assertEquals(LincolnLinesException.DATOS_FALTANTES, e.getMessage());
        }
        Cotizacion cotizacion = new Cotizacion();
        try {
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("computador"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            Cliente cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
            cotizacionService.agendarCita(LocalDateTime.of(2050,11,11,10,0),null,null,cotizacion.getIden(),cliente);
        } catch (LincolnLinesException e) {
            fail("lanzo excepcion");
        }

        try{

            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.NO_ACCIONES,e.getMessage());

        }

        try {
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("computador"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),-1);

        } catch (LincolnLinesException e) {
          fail("lanzo excepcion");
        }

        try{
            cotizacionService.actualizarEstado(cotizacion.getIden(),Cotizacion.FINALIZADO);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.NO_ACCIONES,e.getMessage());
        }
    }

    @Test
    void shouldQuitarDelCarrito(){
        //Limpieza de tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),null,cotizacion.getIden());
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden());
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden());
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("cOmpuTador"),cotizacion.getIden());
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        assertTrue(cotizacionService.verCarrito(cotizacion.getIden()).isEmpty());
        assertEquals(Cotizacion.ELIMINADO,cotizacionService.conocerEstado(cotizacion.getIden()));
    }

    @Test
    void shouldNotQuitarDelCarrito(){
        //Limpieza de tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),null,cotizacion.getIden());

        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }


        try {
            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            Cliente cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
            cotizacionService.agendarCita(LocalDateTime.of(2050,11,11,10,0),null,null,cotizacion.getIden(),cliente);
        } catch (LincolnLinesException e) {
            fail("lanzo excepcion");
        }

        try{
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden());
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.NO_ACCIONES,e.getMessage());

        }

        try {
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
        } catch (LincolnLinesException e) {
            fail("lanzo excepcion");
        }

        try{
            cotizacionService.actualizarEstado(cotizacion.getIden(),Cotizacion.FINALIZADO);
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion.getIden());
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.NO_ACCIONES,e.getMessage());
        }
    }


    @Test
    void shouldCalcularTotalCarrito(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();

        //Agregar Categorias
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));

        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Supra","2001"));
        Cotizacion cotizacion = new Cotizacion();
        try {
            //Agregar productos
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            //Asociar productos con categorias
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Mecanica", productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports", productoService.buscarProductoPorNombre("Casco"));
            //Asociar Vehiculos con productos
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","supra",productoService.buscarProductoPorNombre("Casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("cascO"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("computador"));
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        float calculado = cotizacionService.calcularTotalCarrito(cotizacion.getIden());
        float tasaError = Math.abs(8373740519f-calculado)/8373740519f;
        assertTrue(tasaError <= 0.05);
    }

    @Test
    void shouldCalcularSinDescuento(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Producto producto;
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Celular"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion1 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("celular"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        float calculado = cotizacionService.totalSinDescuento(cotizacion.getIden());
        float verdadero = 8322933656.0f + 2676366.00f;
        float tasaError = Math.abs(verdadero-calculado)/verdadero;
        assertTrue(tasaError <= 0.05);
        assertEquals(4800000f,cotizacionService.totalSinDescuento(cotizacion1.getIden()));
    }

    @Test
    void shouldCalcularDescuento(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Producto producto;
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Celular"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion1 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("celular"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        float calculado = cotizacionService.calcularDescuentoTotal(cotizacion.getIden());
        float verdadero = 6658346924.8f;
        float tasaError = Math.abs(verdadero-calculado)/verdadero;
        assertTrue(tasaError <= 0.05);
        assertEquals(0,cotizacionService.calcularDescuentoTotal(cotizacion1.getIden()));

    }

    @Test
    void shouldCalcularImpuesto(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Producto producto;
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Celular"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion1 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("celular"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        float calculado = cotizacionService.calcularImpuestoTotal(cotizacion.getIden());
        float verdadero = 348048.61f + 532649309.06f;
        float tasaError = Math.abs(verdadero-calculado)/verdadero;
        assertTrue(tasaError <= 0.05);
        assertEquals(0,cotizacionService.calcularImpuestoTotal(cotizacion1.getIden()));
    }
    @Test
    void shouldCalcularTotal(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Producto producto;
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("Celular"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion1 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("celular"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        float calculado = cotizacionService.calcularFinal(cotizacion.getIden());
        float verdadero = 8373740519f - 6709090009.60f + 536727200.77f + 353415.79f;
        float tasaError = Math.abs(verdadero-calculado)/verdadero;
        long id= cotizacion.getIden();
        float total = cotizacionService.totalSinDescuento(id) - cotizacionService.calcularDescuentoTotal(id) + cotizacionService.calcularImpuestoTotal(id);
        assertTrue(tasaError <= 0.05);
        assertEquals(4800000f,cotizacionService.calcularFinal(cotizacion1.getIden()));
        assertEquals(calculado,total);
    }

    @Test
    void shouldAgendarCita(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Producto producto;
        Cotizacion cotizacion2 = new Cotizacion();
        Cliente cliente;

        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion2 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
            cotizacionService.agendarCita(LocalDateTime.of(2040,12,31,9,0),null,null,cotizacion.getIden(),cliente);
            cotizacionService.agendarCita(LocalDateTime.of(2040,12,31,11,35),"Bogota","Calle 156 #9-24",cotizacion2.getIden(),cliente);

        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        cotizacion = cotizacionService.encontrarCotizacion(cotizacion.getIden());
        cotizacion2 = cotizacionService.encontrarCotizacion(cotizacion2.getIden());
        assertEquals(3,cotizacionService.listarCotizaciones().size());
        assertEquals(2,cotizacionService.cotizacionesAgendadas().size());
        assertTrue(cotizacionService.cotizacionesAgendadas().contains(cotizacion));
        assertTrue(cotizacionService.cotizacionesAgendadas().contains(cotizacion2));
        assertEquals("Calle 156 #9-24",cotizacionService.direccionRegocida(cotizacion2.getIden()));

    }


    @Test
    void shoulNodAgendarCita(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Producto producto;
        Cliente cliente = new Cliente();

        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);

            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        //Cita faltante
        try{
            cotizacionService.agendarCita(null,null,null,cotizacion.getIden(),cliente);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
            assertTrue(cotizacionService.cotizacionesAgendadas().isEmpty());
        }
        //Cliente faltante
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,10,30),null,null,cotizacion.getIden(),null);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
        }
        //Entrega faltante
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,10,30),"Bogota",null,cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
        }

        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,10,30),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.DATOS_FALTANTES,e.getMessage());
        }
        //Hora antes de las 8
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,5,30),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }
        //Hora despues de las 3pm
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,31,15,1),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }

        //No se agenda con una hora de diferencia

        try{
            cotizacionService.agendarCita(LocalDateTime.now().plusMinutes(30),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }


    }

    @Test
    void shouldNotAgendar2(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Codigo
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        categoriaService.agregarCategoria(new Categoria("Mecanica"));
        categoriaService.agregarCategoria(new Categoria("Sports"));
        vehiculoService.agregarVehiculo(new Vehiculo("Volkswagen","Gol","2001"));
        Cotizacion cotizacion = new Cotizacion();
        Producto producto;
        Cotizacion cotizacion2 = new Cotizacion();
        Cotizacion cotizacion3 = new Cotizacion();
        Cliente cliente = new Cliente();
        Cotizacion cotizacion4 = new Cotizacion();
        try {
            producto = new Producto("Computador",700f,"USD");
            producto.setImpuesto(0.13f);
            productoService.agregarProducto(producto);
            producto = new Producto("Casco",1000000f,"EUR");
            producto.setDescuento(0.8f);
            producto.setImpuesto(0.32f);
            productoService.agregarProducto(producto);
            productoService.agregarProducto(new Producto("celular",4800000f,"COP"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("Computador"));
            categoriaService.agregarProducto("Electronica",productoService.buscarProductoPorNombre("CelUlar"));
            categoriaService.agregarProducto("Mecanica",productoService.buscarProductoPorNombre("Casco"));
            categoriaService.agregarProducto("Sports",productoService.buscarProductoPorNombre("Casco"));
            vehiculoService.agregarProducto("volkswagen","gOL",productoService.buscarProductoPorNombre("Computador"));
            vehiculoService.agregarProducto("volkswagen","gol",productoService.buscarProductoPorNombre("casco"));
            cotizacion = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),null,cotizacion.getIden());
            cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("computador"),vehiculoService.getVehiculo("volkswagen","gol"),cotizacion.getIden());
            cotizacion2 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacion3 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);
            cotizacion4 = cotizacionService.agregarAlCarrito(productoService.buscarProductoPorNombre("casco"),vehiculoService.getVehiculo("volkswagen","gol"),-1);

            clienteService.agregarCliente(new Cliente("Camilo", "Castaño Quintanilla", "3183074075",null));
            cliente = clienteService.getCLiente("CamiLO","castaño Quintanilla");
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }

        //Cotizacion Finalizada
        try{
            cotizacionService.actualizarEstado(cotizacion4.getIden(),Cotizacion.FINALIZADO);
            cotizacionService.agendarCita(LocalDateTime.of(2050,12,5,10,0),null,null,cotizacion4.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.NO_ACCIONES,e.getMessage());
        }

        //Se agendo para ahorita
        try{
            cotizacionService.agendarCita(LocalDateTime.now(),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }
        //Se agendo para fecha anterior
        try{
            cotizacionService.agendarCita(LocalDateTime.of(2024,5,13,10,1),null,"Calle de prueba",cotizacion.getIden(),cliente);
            fail("No Lanzo Excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }
        //Paso de dos horas entre citas
        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,13,10,1),"null","Calle de prueba",cotizacion.getIden(),cliente);
        }catch (LincolnLinesException e){
            fail(e.getMessage());
        }

        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,13,11,1),null,"Calle de prueba",cotizacion2.getIden(),cliente);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FECHA_NO_DISPONIBLE,e.getMessage());
        }


        //Cotizacion ya agendada
        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,10,10,1),null,"Calle de prueba",cotizacion.getIden(),cliente);

        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.COTIZACION_AGENDADA,e.getMessage());
        }

        //Carrito Vacio
        try {
            cotizacionService.quitarDelCarrito(productoService.buscarProductoPorNombre("casco"),cotizacion3.getIden());
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        try{
            cotizacionService.agendarCita(LocalDateTime.of(20250,5,10,10,1),null,"Calle de prueba",cotizacion3.getIden(),cliente);
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.CARRITO_VACIO,e.getMessage());
        }
    }

    @Test
    void shouldSegregarVehiculo(){
        //Limpiar Tablas
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        //Agregar Vehiculos
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA","PRIUS","1997"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA","SUPRA","1978"));
        vehiculoService.agregarVehiculo(new Vehiculo("BMW", "X3", "2011"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA","PRIUS","1997"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA","SUPRA","1978"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA","CROWN","1955"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "ECONOLINE E350", "2000"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "F150", "1975"));
        vehiculoService.agregarVehiculo(new Vehiculo("ASTON MARTIN", "DBS", "2008"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "ESCORT", "1993"));
        vehiculoService.agregarVehiculo(new Vehiculo("GMC", "2500 CLUB COUPE", "1997"));
        vehiculoService.agregarVehiculo(new Vehiculo("DODGE", "DURANGO", "1999"));
        vehiculoService.agregarVehiculo(new Vehiculo("DODGE", "NEON", "2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("BMW", "X3", "2011"));
        vehiculoService.agregarVehiculo(new Vehiculo("BMW", "745", "2002"));
        vehiculoService.agregarVehiculo(new Vehiculo("NISSAN", "MAXIMA", "1997"));
        vehiculoService.agregarVehiculo(new Vehiculo("CADILLAC", "STS-V", "2006"));
        vehiculoService.agregarVehiculo(new Vehiculo("JEEP", "WRANGLER", "1994"));
        vehiculoService.agregarVehiculo(new Vehiculo("CHEVROLET", "TRAVERSE", "2012"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "E150", "2006"));
        vehiculoService.agregarVehiculo(new Vehiculo("MAZDA", "MX-6", "1993"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "F-SERIES", "1991"));
        vehiculoService.agregarVehiculo(new Vehiculo("MITSUBISHI", "ECLIPSE", "2001"));
        vehiculoService.agregarVehiculo(new Vehiculo("STUDEBAKER", "AVANTI", "1963"));
        vehiculoService.agregarVehiculo(new Vehiculo("MAZDA", "MIATA MX-5", "1996"));
        vehiculoService.agregarVehiculo(new Vehiculo("BMW", "760", "2003"));
        vehiculoService.agregarVehiculo(new Vehiculo("GMC", "SIERRA 3500", "2002"));
        vehiculoService.agregarVehiculo(new Vehiculo("MERCEDES-BENZ", "S-CLASS", "2010"));
        vehiculoService.agregarVehiculo(new Vehiculo("PONTIAC", "DAEWOO KALOS", "2005"));
        vehiculoService.agregarVehiculo(new Vehiculo("ACURA", "LEGEND", "1992"));
        vehiculoService.agregarVehiculo(new Vehiculo("VOLKSWAGEN", "GTI", "1987"));
        vehiculoService.agregarVehiculo(new Vehiculo("GMC", "VANDURA 3500", "1994"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA", "CAMRY", "1992"));
        vehiculoService.agregarVehiculo(new Vehiculo("PONTIAC", "FIREBIRD", "1995"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA", "AVALON", "2000"));
        vehiculoService.agregarVehiculo(new Vehiculo("SUZUKI", "FORENZA", "2004"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "FIESTA", "2011"));
        vehiculoService.agregarVehiculo(new Vehiculo("CHEVROLET", "SILVERADO", "2003"));
        vehiculoService.agregarVehiculo(new Vehiculo("MITSUBISHI", "CHALLENGER", "1997"));
        vehiculoService.agregarVehiculo(new Vehiculo("BENTLEY", "CONTINENTAL GT", "2010"));
        vehiculoService.agregarVehiculo(new Vehiculo("CHEVROLET", "SUBURBAN 2500", "2012"));
        vehiculoService.agregarVehiculo(new Vehiculo("TOYOTA", "TUNDRA", "2008"));
        vehiculoService.agregarVehiculo(new Vehiculo("KIA", "AMANTI", "2009"));
        vehiculoService.agregarVehiculo(new Vehiculo("GMC", "SAVANA", "2011"));
        vehiculoService.agregarVehiculo(new Vehiculo("CHRYSLER", "CIRRUS", "1999"));
        vehiculoService.agregarVehiculo(new Vehiculo("FORD", "EXPLORER", "1994"));
        vehiculoService.agregarVehiculo(new Vehiculo("MAZDA", "MILLENIA", "1996"));
        Set<String> marcas = vehiculoService.getMarcas();
        assertTrue(marcas.contains("MAZDA"));
        assertTrue(marcas.contains("TOYOTA"));
        assertTrue(marcas.contains("CHEVROLET"));
        assertTrue(marcas.contains("MITSUBISHI"));
        assertEquals(6, vehiculoService.getModelosMarca("TOYOTA").size());
        assertTrue(vehiculoService.getModelosMarca("TOYOTA").contains(new Vehiculo("TOYOTA", "TUNDRA", "2008")));
        assertTrue(vehiculoService.getModelosMarca("TOYOTA").contains(new Vehiculo("TOYOTA","CROWN","1955")));
        assertEquals(2, vehiculoService.getModelosMarca("DODGE").size());
        assertTrue(vehiculoService.getModelosMarca("DODGE").contains(new Vehiculo("DODGE", "DURANGO", "1999")));
        assertTrue(vehiculoService.getModelosMarca("DODGE").contains(new Vehiculo("DODGE", "NEON", "2001")));
        boolean mismaMarca = true;
        for(Vehiculo v: vehiculoService.getModelosMarca("TOYOTA")){
            if(!v.getMarca().equals("TOYOTA")){
                mismaMarca = false;
                break;
            }
        }
        assertTrue(mismaMarca);

        for(Vehiculo v: vehiculoService.getModelosMarca("DODGE")){
            if(!v.getMarca().equals("DODGE")){
                mismaMarca = false;
                break;
            }
        }
        assertTrue(mismaMarca);
    }

    @Test
    void shouldBeEquals(){
        Vehiculo vehiculo = new Vehiculo();
        Vehiculo vehiculo1 = new Vehiculo();
        Producto producto = new Producto();
        Producto producto1 = new Producto();
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Cliente cliente1 = new Cliente();
        Cliente cliente = new Cliente();
        Categoria categoria = new Categoria();
        Categoria categoria1 = new Categoria();

        Vehiculo vehiculo2 = new Vehiculo();
        Vehiculo vehiculo3 = new Vehiculo();
        Producto producto2 = new Producto();
        Producto producto3 = new Producto();
        Cotizacion cotizacion2 = new Cotizacion();
        Cotizacion cotizacion3 = new Cotizacion();
        Cliente cliente2 = new Cliente();
        Cliente cliente3 = new Cliente();
        Categoria categoria2 = new Categoria();
        Categoria categoria3 = new Categoria();
        try{
            vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
            vehiculo1 = new Vehiculo("Toyota", "Corolla", "2022");
            producto = new Producto("Producto1",200f,"COP");
            producto1 = new Producto("Producto1",200f,"COP");
            producto.setImpuesto(0.15f);
            producto.setImagen("...");
            producto1.setImpuesto(0.15f);
            producto1.setImagen("...");
            cotizacion = new Cotizacion();
            cotizacion1 = new Cotizacion();
            cliente = new Cliente("Camilo","Castano","3183074075","ccc");
            cliente1 = new Cliente("Camilo","Castano","3183074075","ccc");
            categoria = new Categoria("hola");
            categoria1 = new Categoria("hola");
            categoria.agregarProducto(producto);
            categoria1.agregarProducto(producto1);
        }catch(LincolnLinesException e){
            fail("Lanzo excepcion");
        }
        assertEquals(vehiculo, vehiculo1);
        assertEquals(producto, producto1);
        assertEquals(cotizacion, cotizacion1);
        assertEquals(cliente, cliente1);
        assertEquals(categoria,categoria1);
        assertEquals(vehiculo3, vehiculo2);
        assertEquals(producto3, producto2);
        assertEquals(cotizacion3, cotizacion2);
        assertEquals(cliente3, cliente2);
        assertEquals(categoria3,categoria2);
    }

    @Test
    void shouldNotBeEquals(){
        Vehiculo vehiculo = new Vehiculo();
        Vehiculo vehiculo1 = new Vehiculo();
        Producto producto = new Producto();
        Producto producto1 = new Producto();
        Cotizacion cotizacion = new Cotizacion();
        Cotizacion cotizacion1 = new Cotizacion();
        Cliente cliente1 = new Cliente();
        Cliente cliente = new Cliente();
        Categoria categoria = new Categoria();
        Categoria categoria1 = new Categoria();

        try{
            vehiculo = new Vehiculo("Toyota", "Corolla", "2022");
            vehiculo1 = new Vehiculo("Toyot", "Corolla", "2022");
            producto = new Producto("Producto",200f,"COP");
            producto1 = new Producto("Producto1",200f,"COP");
            producto.setImpuesto(0.15f);
            producto.setImagen("...");
            producto1.setImpuesto(0.15f);
            cotizacion = new Cotizacion(vehiculo);
            cotizacion1 = new Cotizacion();
            cliente = new Cliente("Cmilo","Castano","3183074075","ccc");
            cliente1 = new Cliente("Camilo","Castano","3183074075","ccc");
            categoria = new Categoria("hola");
            categoria1 = new Categoria("hola");
            categoria.agregarProducto(producto);
        }catch(LincolnLinesException e){
            fail("Lanzo excepcion");
        }
        assertNotEquals(vehiculo, vehiculo1);
        assertNotEquals(producto, producto1);
        assertNotEquals(cotizacion, cotizacion1);
        assertNotEquals(cliente, cliente1);
        assertNotEquals(categoria,categoria1);
        assertNotEquals(producto,vehiculo);
        assertNotEquals(vehiculo,producto);
        assertNotEquals(cotizacion,cliente);
        assertNotEquals(cliente,cotizacion);
        assertNotEquals(categoria,cliente);
        assertNotEquals(null, producto);
        assertNotEquals(null, vehiculo);
        assertNotEquals(null, cotizacion);
        assertNotEquals(null, cliente);
        assertNotEquals(null, categoria);
    }

    @Test
    void shouldActualizarProducto(){
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        Producto producto1;
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            producto1 = new Producto("celular",4800000f,"COP");
            producto1.setDescripcionBreve("Prueba de agregacion");
            producto1.setDescuento(0.25f);
            productoService.agregarProducto(producto1);
            producto1.setDescripcionBreve("Esto es una prueba de descripcion breve");
            producto1.setDescripcionTecnica("Esto es una prueba de descripcion tecnica");
            producto1.setMoneda("MXN");
            producto1.setValor(5f);
            categoriaService.agregarProducto("Electronica",producto1);
            productoService.actualizarProducto(producto1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        producto1 = productoService.buscarProductoPorNombre("Celular");
        assertEquals("Esto es una prueba de descripcion breve",producto1.getDescripcionBreve());
        assertEquals("Esto es una prueba de descripcion tecnica",producto1.getDescripcionTecnica());
        assertEquals("MXN",producto1.getMoneda());
        assertTrue(categoriaService.listarProductos("Electronica").contains(producto1));
    }


    @Test
    void shouldNotActualizarProducto(){
        categoriaService.limpiarTabla();
        cotizacionService.limpiarTabla();
        clienteService.limpiarTabla();
        vehiculoService.limpiarTabla();
        productoService.limpiarTabla();
        Producto producto1 = new Producto();
        categoriaService.agregarCategoria(new Categoria("Electronica"));
        try {
            productoService.agregarProducto(new Producto("Computador",700f,"USD"));
            productoService.agregarProducto(new Producto("Casco",1000000f,"EUR"));
            producto1 = new Producto("celular",4800000f,"COP");
            producto1.setDescripcionBreve("Prueba de agregacion");
            producto1.setDescuento(0.25f);

            productoService.agregarProducto(producto1);
            categoriaService.agregarProducto("Electronica",producto1);
        } catch (LincolnLinesException e) {
            fail(e.getMessage());
        }
        try{
            producto1 = new Producto(producto1.getNombre(),-5000f,"COP",0.5f,0);
            producto1.setDescripcionBreve("Esto es una prueba de descripcion breve");
            producto1.setDescripcionTecnica("Esto es una prueba de descripcion tecnica");
            producto1.setMoneda("MXN");
            productoService.actualizarProducto(producto1);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.VALOR_NEGATIVO,e.getMessage());
        }



        try{
            producto1 = new Producto(producto1.getNombre(),5000f,"COP",-0.5f,0);
            producto1.setDescripcionBreve("Esto es una prueba de descripcion breve");
            producto1.setDescripcionTecnica("Esto es una prueba de descripcion tecnica");
            producto1.setMoneda("MXN");
            productoService.actualizarProducto(producto1);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.VALOR_NEGATIVO,e.getMessage());
        }

        try{
            producto1 = new Producto(producto1.getNombre(),5000f,"COP",0.5f,-0.3f);
            producto1.setDescripcionBreve("Esto es una prueba de descripcion breve");
            producto1.setDescripcionTecnica("Esto es una prueba de descripcion tecnica");
            producto1.setMoneda("MXN");
            productoService.actualizarProducto(producto1);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.VALOR_NEGATIVO,e.getMessage());
        }

        try{
            producto1 = new Producto(producto1.getNombre(),5000f,"COP",1.5f,0);
            producto1.setDescripcionBreve("Esto es una prueba de descripcion breve");
            producto1.setDescripcionTecnica("Esto es una prueba de descripcion tecnica");
            producto1.setMoneda("MXN");
            productoService.actualizarProducto(producto1);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FUERA_RANGO,e.getMessage());
        }

        try{
            producto1 = new Producto(producto1.getNombre(),5000f,"COP",0.5f,5);
            producto1.setDescripcionBreve("Esto es una prueba de descripcion breve");
            producto1.setDescripcionTecnica("Esto es una prueba de descripcion tecnica");
            producto1.setMoneda("MXN");
            productoService.actualizarProducto(producto1);
            fail("No lanzo excepcion");
        }catch (LincolnLinesException e){
            assertEquals(LincolnLinesException.FUERA_RANGO,e.getMessage());
        }
        producto1 = productoService.buscarProductoPorNombre(producto1.getNombre());
        assertNotEquals("Esto es una prueba de descripcion tecnica", producto1.getDescripcionTecnica());
        assertNotEquals("Esto es una prueba de descripcion breve",producto1.getDescripcionBreve());
        assertNotEquals("MXN",producto1.getMoneda());
        assertEquals(0.25f,producto1.getDescuento());
    }

    @Test
    void testAuthenticateValidCredentials() {
        String username = "admin";
        String password = "password123";

        // Verifica que el método authenticate devuelva true para credenciales válidas
        assertTrue(loginService.createAdminAccount(username, password));
        assertTrue(loginService.authenticate(username, password));
        assertFalse(loginService.authenticate(username, "wrongpassword"));
    }




}

