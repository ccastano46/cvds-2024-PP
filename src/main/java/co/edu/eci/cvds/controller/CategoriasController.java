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
@RequestMapping(value = "/categorias")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String verProductosYServicios(Model model) {
        model.addAttribute("producto", productService.getAllProducts());
        return "products-services/ver-productos-y-servicios";
    }

    @GetMapping("/productos")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/productos")
    @ResponseBody
    public List<Product> exampleProducts(@RequestBody Product product) {
        productService.addProduct(product);
        return productService.getAllProducts();
    }

    @GetMapping("/carrito")
    //public String listarServicios() {
       //return "products-services/Shopping-cart";

    


}