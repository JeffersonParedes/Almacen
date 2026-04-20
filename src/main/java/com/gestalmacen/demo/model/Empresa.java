package com.gestalmacen.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Empresa {
    private Long id;
    private String ruc;
    private String razonSocial; // Ej: "Inversiones Don Pepe S.A.C." o "Bodega Las Flores"
    private String direccionPrincipal;
    private String telefonoContacto;
    private LocalDate fechaSuscripcion; 
    private String estado; // Ej: "ACTIVO", "SUSPENDIDO", "INACTIVO"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío (Obligatorio para Spring/JPA)
    public Empresa() {
    }

    // Constructor con todos los parámetros
    public Empresa(Long id, String ruc, String razonSocial, String direccionPrincipal, 
                   String telefonoContacto, LocalDate fechaSuscripcion, String estado, 
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.direccionPrincipal = direccionPrincipal;
        this.telefonoContacto = telefonoContacto;
        this.fechaSuscripcion = fechaSuscripcion;
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = direccionPrincipal;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public LocalDate getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(LocalDate fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
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
