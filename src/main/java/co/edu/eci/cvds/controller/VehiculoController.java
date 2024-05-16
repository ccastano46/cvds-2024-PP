package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.model.Vehiculo;
import co.edu.eci.cvds.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    private final VehiculoService vehiculoService;
    @Autowired
    public VehiculoController(VehiculoService vehiculoService) {
            this.vehiculoService = vehiculoService;
    }

    @GetMapping("/modelos/{marca}")
    public List<Vehiculo> actualizarModelos(@PathVariable("marca") String marca) {
        return vehiculoService.getModelosMarca(marca);
    }

    @GetMapping("/anos/{marca}/{modelo}")
    public int[] actualizarAnos(@PathVariable("marca") String marca, @PathVariable("modelo") String modelo){
        return vehiculoService.getMinMaxYear(marca,modelo);
    }
}
