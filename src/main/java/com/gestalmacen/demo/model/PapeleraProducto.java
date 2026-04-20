package com.gestalmacen.demo.model;

import java.time.LocalDateTime; 

public class PapeleraProducto {

private Long id;
    private Long empresaId; // Aisla la papelera por bodega
    private Long productoIdOriginal; // El ID que tenía en la tabla Producto antes de morir
    
    private String codigoBarras;
    private String nombre;
    private Long categoriaId;
    private Double precio;
    
    private LocalDateTime fechaEliminacion; // Cuándo se borró (funciona como el createdAt)
    private Long usuarioIdEliminador; // ¿Qué usuario apretó el botón de eliminar?

    // Constructor vacío (Obligatorio para Spring/JPA)
    public PapeleraProducto() {
    }

    // Constructor con todos los parámetros
    public PapeleraProducto(Long id, Long empresaId, Long productoIdOriginal, 
                            String codigoBarras, String nombre, Long categoriaId, 
                            Double precio, LocalDateTime fechaEliminacion, 
                            Long usuarioIdEliminador) {
        this.id = id;
        this.empresaId = empresaId;
        this.productoIdOriginal = productoIdOriginal;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.categoriaId = categoriaId;
        this.precio = precio;
        this.fechaEliminacion = fechaEliminacion;
        this.usuarioIdEliminador = usuarioIdEliminador;
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

    public Long getProductoIdOriginal() {
        return productoIdOriginal;
    }

    public void setProductoIdOriginal(Long productoIdOriginal) {
        this.productoIdOriginal = productoIdOriginal;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public Long getUsuarioIdEliminador() {
        return usuarioIdEliminador;
    }

    public void setUsuarioIdEliminador(Long usuarioIdEliminador) {
        this.usuarioIdEliminador = usuarioIdEliminador;
    }

}  
 