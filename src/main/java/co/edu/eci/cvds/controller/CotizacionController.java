package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/cotizaciones")
public class CotizacionController {
    private final CotizacionService cotizacionService;
    @Autowired
    public CotizacionController( CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @GetMapping("/horasDisponibles/{ano}/{mes}/{dia}")
    public List<LocalTime> horasDisponibles(@PathVariable("ano") String ano, @PathVariable("mes") String mes, @PathVariable("dia") String dia) {
        return cotizacionService.horasDisponibles(ano, mes, dia);
    }
}