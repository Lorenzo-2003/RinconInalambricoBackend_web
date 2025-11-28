package com.techgear.catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techgear.catalogo.model.Producto;
import com.techgear.catalogo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos(){
        return productoRepository.findAll();
    }

    public Producto getProducto(int id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto saveProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Producto producto){
        Producto updproducto = getProducto(producto.getId());
        if (updproducto==null) {
            return null;
        }
        updproducto.setNombre(producto.getNombre());
        updproducto.setPrecio(producto.getPrecio());
        updproducto.setStock(producto.getStock());
        updproducto.setCategoria(producto.getCategoria());
        
        return productoRepository.save(updproducto);
    }

    public void deleteProducto(Integer id){
        productoRepository.deleteById(id);
    }     
}
