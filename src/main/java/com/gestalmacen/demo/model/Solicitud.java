package com.gestalmacen.demo.model;

import java.time.LocalDateTime;

public class Solicitud {
private Long id;
    private Long empresaId;  // Aislamiento por bodega
    private Long usuarioId;  // ¿Quién hizo el movimiento?
    private Long productoId; // ¿Qué producto se movió?
    private Long almacenId;  // ¿En qué almacén ocurrió?
    
    private String tipoSolicitud; // Ej: "ENTRADA", "SALIDA", "AJUSTE", "TRASLADO"
    private Double cantidad;      // Cuánto entró o salió
    private LocalDateTime fechaSolicitud; // Fecha y hora exacta del movimiento
    private String motivo;        // Ej: "Venta del día", "Mercadería vencida", "Llegada de proveedor"
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío (Obligatorio para Spring/JPA)
    public Solicitud() {
    }

    // Constructor con todos los parámetros
    public Solicitud(Long id, Long empresaId, Long usuarioId, Long productoId, 
                     Long almacenId, String tipoSolicitud, Double cantidad, 
                     LocalDateTime fechaSolicitud, String motivo, 
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.empresaId = empresaId;
        this.usuarioId = usuarioId;
        this.productoId = productoId;
        this.almacenId = almacenId;
        this.tipoSolicitud = tipoSolicitud;
        this.cantidad = cantidad;
        this.fechaSolicitud = fechaSolicitud;
        this.motivo = motivo;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Long almacenId) {
        this.almacenId = almacenId;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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