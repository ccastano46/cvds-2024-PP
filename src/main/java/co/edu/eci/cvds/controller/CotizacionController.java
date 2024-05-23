package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.exception.LincolnLinesException;
import co.edu.eci.cvds.model.Cliente;
import co.edu.eci.cvds.service.ClienteService;
import co.edu.eci.cvds.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.LocalTime;



@Controller
@RequestMapping("/paginaCotizaciones")
public class CotizacionController {
    private final CotizacionService cotizacionService;
    private final ClienteService clienteService;

    @Autowired
    public CotizacionController(CotizacionService cotizacionService, ClienteService clienteService) {
        this.cotizacionService = cotizacionService;
        this.clienteService = clienteService;

    }

    @PostMapping("/registarAgendamiento")
    public String agendar(@RequestParam("nombre") String nombre,
                          @RequestParam("apellido") String apellido,
                          @RequestParam("celular") String celular,
                          @RequestParam("correo") String correo,
                          @RequestParam("ciudad") String ciudad,
                          @RequestParam("direccion") String direccion,
                          @RequestParam("cotizacion") String cotizacion,
                          @RequestParam("dia") String dia,
                          @RequestParam("mes") String mes,
                          @RequestParam("ano") String ano,
                          @RequestParam("hora") LocalTime hora) {
        int anoActual = Integer.parseInt(ano);
        int mesActual = CotizacionService.identificarMes(mes);
        int diaActual = Integer.parseInt(dia);
        int horaActual = hora.getHour();
        int minutoActual = hora.getMinute();
        String mensaje;
        Cliente cliente = new Cliente(nombre,apellido,celular,correo);
        clienteService.agregarCliente(cliente);
        try {
            cotizacionService.agendarCita(LocalDateTime.of(anoActual,mesActual,diaActual,horaActual,minutoActual)
                    ,ciudad,direccion,Integer.parseInt(cotizacion),cliente);
            mensaje = "Felicidades " + nombre + " " +apellido + ", queremos informarle que su cita para el "
                    + dia + "/" + mes + "/" + ano + " a las " + hora + " fue agendada exitosamente";
        } catch (LincolnLinesException e) {
            mensaje = e.getMessage();
        }

        return "redirect:/LincolnLines/agendamiento?cotizacion=" + cotizacion + "&respuesta=" + mensaje;
    }

}