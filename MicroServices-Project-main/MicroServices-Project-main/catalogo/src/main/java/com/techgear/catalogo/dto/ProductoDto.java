package com.techgear.catalogo.dto;

public class ProductoDto {

    public Integer id;
    public String nombre;
    public double precio;
    public String descripcion;
    public String categoria; // "MANGA", "FIGURAS", etc.

    public ProductoDto() {
    }

    public ProductoDto(Integer id, String nombre, double precio, String descripcion, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }
}
