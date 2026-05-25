package com.gestalmacen.demo.dto.request;

public class UsuarioRequestDTO {
    
    private String usuario;
    private String contrasena; // El cliente SÍ nos envía la contraseña para registrarse
    private String nombreCompleto;
    private String rol;

    // Getters y Setters
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
