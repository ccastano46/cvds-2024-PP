package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.service.mecanicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping(value = "/mecanica1")
public class mecanicaController {
    private final mecanicaService mecanicaService;

    @Autowired
    public mecanicaController(mecanicaService mecanicaService) {
        this.mecanicaService = mecanicaService;

    }

    @GetMapping("")
    public String mecanica(Model model) {
        Set<img th:src="producto.img">
        Set<String> marcas = mecanicaService.getMarcas();
        model.addAttribute("marcas", marcas);
        return "index";
    }






}
