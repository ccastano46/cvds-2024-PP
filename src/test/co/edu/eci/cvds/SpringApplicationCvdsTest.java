package co.edu.eci.cvds;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.repository.ProductoRepository;
import co.edu.eci.cvds.repository.VehiculoRepository;
import co.edu.eci.cvds.service.ProductoService;
import co.edu.eci.cvds.service.VehiculoService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/*import org.junit.jupiter.api.Test;*/

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

    @Test
    void shouldLLenarTablas(){
        Vehiculo vehiculo = new Vehiculo("Suzuki","i-40","2023");
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));
        when(vehiculoRepository.findByMarcaAndModelAndYear(anyString(),anyString(),anyString())).thenReturn(List.of(vehiculo));
        Vehiculo vehiculoGuardado = vehiculoService.agregarVehiculo(vehiculo);
        assertEquals(vehiculo, vehiculoGuardado);
        assertEquals(vehiculoService.getVehiculos().size(),1);
        vehiculoGuardado = vehiculoRepository.findByMarcaAndModelAndYear(vehiculo.getMarca(),vehiculo.getModel(),vehiculo.getYear()).get(0);
        assertEquals(vehiculoService.getVehiculo("Suzuki","i-40","2023"), vehiculoGuardado);

        Producto producto = new Producto("Motor1","Motor",(float)15.2,"US");
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoRepository.findByNombre(anyString())).thenReturn(List.of(producto));
        Producto productoGuardado = productoService.agregarProducto(producto);
        assertEquals(producto, productoGuardado);
        assertEquals(productoService.buscarProductos().size(),1);
        productoGuardado = productoRepository.findByNombre("Motor1").get(0);
        assertEquals(productoService.buscarProductoPorNombre("Motor1"),productoGuardado);


    }

}
