package co.edu.eci.cvds.controller;



import co.edu.eci.cvds.service.VehiculoService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




import java.util.Set;

@Controller
@RequestMapping(value = "/LincolnLines")
public class HomeController {
    private final VehiculoService vehiculoService;


    @Autowired
    public HomeController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;

    }

    @GetMapping("")
    public String home(Model model) {
        Set<String> marcas = vehiculoService.getMarcas();
        model.addAttribute("marcas", marcas);
        return "index";
    }


    @GetMapping("/agendamiento")
    public String agendamiento(@RequestParam("cotizacion") String cotizacion,@RequestParam(value = "respuesta", required = false) String respuesta,Model model) {
        model.addAttribute("cotizacion", cotizacion);
        model.addAttribute("mensajeRespuesta",respuesta);
        return "/cotizacion/Agendamiento";
    }



    @GetMapping("/respuestaAgendamiento")
    public String respuestaAgendamiento() {
        return "/agendamiento/ventanaEmergente";
    }

    @GetMapping("/cotizacionFinal")
    public String cotizacion() {
        return "/lista/cotizacionFinal";
    }








}
