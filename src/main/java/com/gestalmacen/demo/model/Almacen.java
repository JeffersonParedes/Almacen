package com.gestalmacen.demo.model;

import java.time.LocalDateTime; 

public class Almacen {
    private Long id;
    private Long empresaId; // Vincula este almacén a una bodega específica
    private String nombre; // Ej: "Tienda Principal", "Depósito Trasero"
    private String direccion; // Útil si la bodega tiene un local y un depósito en otra calle
    private String estado; // Ej: "ACTIVO", "EN_MANTENIMIENTO", "INACTIVO"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío (Obligatorio para Spring/JPA)
    public Almacen() {
    }

    // Constructor con todos los parámetros
    public Almacen(Long id, Long empresaId, String nombre, String direccion, 
                   String estado, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.empresaId = empresaId;
        this.nombre = nombre;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
 