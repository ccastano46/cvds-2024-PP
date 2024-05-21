package co.edu.eci.cvds.controller;
 
import co.edu.eci.cvds.model.Producto;
import co.edu.eci.cvds.service.CotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import java.util.List;
 
@Controller
@RequestMapping(value = "/Lincoln")
public class CotizacionController {
 
    private final CotizacionService cotizacionService;
 
    @Autowired
    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }
 
    /**
     * Método que maneja las solicitudes GET para la ruta "/cotizacionFinal".
     * Calcula los valores de subtotal, impuestos y total para una cotización específica
     * y los agrega al modelo para su visualización en la vista "cotizacionFinal".
     *
     * @param model       Objeto Model de Spring para agregar atributos.
     * @param cotizacionId Identificador de la cotización.
     * @return La vista "cotizacionFinal" que mostrará los valores calculados y la lista de productos.
     */
    @GetMapping("/cotizacionFinal")
    public String cotizacionFinal(Model model, @RequestParam(name = "cotizacionId") Long cotizacionId) {

            List<Producto> productos = cotizacionService.verCarrito(cotizacionId);
            model.addAttribute("productos", productos);
 
            float subtotal = cotizacionService.totalSinDescuento(cotizacionId);
            float descuento = cotizacionService.calcularDescuentoTotal(cotizacionId); // Calcular descuento total
            float impuestos = cotizacionService.calcularImpuestoTotal(cotizacionId);
            float total = cotizacionService.calcularFinal(cotizacionId);
 
            model.addAttribute("Subtotal", subtotal);
            model.addAttribute("Descuento", descuento); // Agregar descuento total al modelo
            model.addAttribute("Impuestos", impuestos);
            model.addAttribute("Total", total);
  
        return "cotizacionFinal";
    }
}
