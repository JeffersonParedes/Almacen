package com.gestalmacen.demo.dto.request;

public class InventarioRequestDTO {

    private Long productoId;
    private Long almacenId;
    private Double stockActual; // <-- ¡Este era el que faltaba!
    private Double stockMinimo;

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

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
}