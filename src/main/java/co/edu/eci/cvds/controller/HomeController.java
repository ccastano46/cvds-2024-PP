package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.service.CategoriaService;
import co.edu.eci.cvds.service.CotizacionService;
import co.edu.eci.cvds.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/LincolnLines")
public class HomeController {
    private final VehiculoService vehiculoService;
    private final CategoriaService categoriaService;

    @Autowired
    public HomeController(VehiculoService vehiculoService, CategoriaService categoriaService) {
        this.vehiculoService = vehiculoService;
        this.categoriaService = categoriaService;

    }

    @GetMapping("")
    public String home(Model model) {
        Set<String> marcas = vehiculoService.getMarcas();
        model.addAttribute("marcas", marcas);
        return "index";
    }


    @GetMapping("/agendamiento")
    public String agendamiento(Model model) {
        return "Agendamiento";
    }

    @GetMapping("/cotizacionFinal")
    public String cotizacion(Model model) {
        return "/lista/cotizacionFinal";
    }

    @GetMapping("/productos/{categoria}")
    public String obten(Model model, @PathVariable String categoria) {
        Set<Producto> productos = categoriaService.listaProductos(categoria);
        model.addAttribute("productos",productos);
        return "Categorias";
    }









}
