package com.gestalmacen.demo.dto.request;

public class CategoriaRequestDTO {

    private String nombre;
    private String descripcion;

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
