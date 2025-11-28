package com.techgear.catalogo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techgear.catalogo.dto.ProductoDto;
import com.techgear.catalogo.model.Producto;
import com.techgear.catalogo.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoApiController {

    private final ProductoService productoService;

    // Inyección por constructor (Spring se encarga)
    public ProductoApiController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /api/productos
    @GetMapping
    public List<ProductoDto> listarProductos() {
        return productoService.getProductos()
                .stream()
                .map(this::toDto)
                .toList();
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerPorId(@PathVariable int id) {
        Producto producto = productoService.getProducto(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(producto));
    }

    // Conversión de entidad JPA -> DTO para Android
    private ProductoDto toDto(Producto producto) {
        String nombreCategoria = null;
        if (producto.getCategoria() != null) {
            nombreCategoria = producto.getCategoria().getNombre();
        }

        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                nombreCategoria
        );
    }
}
