package co.edu.eci.cvds.controller;


import co.edu.eci.cvds.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/cotizaciones")
public class CotizacionRestController {
    private final CotizacionService cotizacionService;

    @Autowired
    public CotizacionRestController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;

    }

    @GetMapping("/horasDisponibles/{ano}/{mes}/{dia}")
    public List<LocalTime> horasDisponibles(@PathVariable("ano") String ano, @PathVariable("mes") String mes, @PathVariable("dia") String dia) {
        return cotizacionService.horasDisponibles(ano, mes, dia);
    }

}