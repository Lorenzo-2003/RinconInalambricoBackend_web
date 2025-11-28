package com.techgear.catalogo.repository;

import com.techgear.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; 

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Buscar productos según el nombre de la categoría
    List<Producto> findByCategoriaNombreIgnoreCase(String nombre);

}
