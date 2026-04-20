package com.gestalmacen.demo.model;

import java.time.LocalDateTime;

public class Categoria {
   private Long id;
    private Long empresaId; // Llave foránea para aislar las categorías por bodega
    private String nombre; // Ej: "Bebidas", "Snacks"
    private String descripcion;
    private String estado; // Ej: "ACTIVO", "INACTIVO"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío (Obligatorio para Spring/JPA)
    public Categoria() {
    }

    // Constructor con todos los parámetros
    public Categoria(Long id, Long empresaId, String nombre, String descripcion, 
                     String estado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.empresaId = empresaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ==========================================
    // Getters y Setters
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }   
}
