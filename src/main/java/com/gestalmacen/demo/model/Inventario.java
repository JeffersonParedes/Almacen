package com.gestalmacen.demo.model;

import java.time.LocalDateTime;

public class Inventario {
private Long id;
    private Long empresaId;  // Aislamiento por bodega
    private Long productoId; // ¿Qué producto es?
    private Long almacenId;  // ¿Dónde está guardado?
    
    private Double stockActual; // Cantidad real física
    private Double stockMinimo; // Alerta para saber cuándo volver a comprar
    
    private LocalDateTime ultimaActualizacion; // Cuándo fue la última vez que se movió
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor vacío (Obligatorio para Spring/JPA)
    public Inventario() {
    }

    // Constructor con todos los parámetros
    public Inventario(Long id, Long empresaId, Long productoId, Long almacenId, 
                      Double stockActual, Double stockMinimo, 
                      LocalDateTime ultimaActualizacion, 
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.empresaId = empresaId;
        this.productoId = productoId;
        this.almacenId = almacenId;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.ultimaActualizacion = ultimaActualizacion;
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

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }

    public Double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
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
  