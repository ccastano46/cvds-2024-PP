package co.edu.eci.cvds.controller;

import co.edu.eci.cvds.service.mecanicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/mecanica")
public class MecanicaController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @PutMapping("")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        if (producto.isPresent()) {
            Producto productoExistente = producto.get();
            productoExistente.setNombre(productoDetalles.getNombre());
            productoExistente.setDescripcionBreve(productoDetalles.getDescripcionBreve());
            productoExistente.setDescripcionTecnica(productoDetalles.getDescripcionTecnica());
            productoExistente.setCategoria(productoDetalles.getCategoria());
            productoExistente.setImagen(productoDetalles.getImagen());
            productoExistente.setValor(productoDetalles.getValor());
            productoExistente.setMoneda(productoDetalles.getMoneda());
            productoExistente.setDescuento(productoDetalles.getDescuento());
            productoExistente.setImpuesto(productoDetalles.getImpuesto());
            Producto actualizado = productoService.guardar(productoExistente);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

