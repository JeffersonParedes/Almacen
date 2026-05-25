package com.gestalmacen.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "papelera_producto")

public class PapeleraProducto {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(name = "producto_id_original", nullable = false)
    private Long productoIdOriginal;

    @Column(name = "codigo_barras", length = 50)
    private String codigoBarras;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "precio", nullable = false)
    private Double precio = 0.0;

    @Column(name = "fecha_eliminacion", nullable = false, updatable = false)
    private LocalDateTime fechaEliminacion;

    @Column(name = "usuario_id_eliminador", nullable = false)
    private Long usuarioIdEliminador;

    @PrePersist
    protected void onCreate() {
        this.fechaEliminacion = LocalDateTime.now();
    }

    public PapeleraProducto() {}

    // ¡Tus Getters y Setters aquí!
    
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
