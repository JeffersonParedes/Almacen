package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Solicitud;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudService {
private List<Solicitud> solicitudes;
    private Long contadorId = 1L;
    
    // Inyectamos el servicio de inventario para poder mandarle órdenes
    private final InventarioService inventarioService;

    // Spring Boot automáticamente nos pasará el InventarioService al construir esta clase
    public SolicitudService(InventarioService inventarioService) {
        //this.inventarios = new ArrayList<>(); 
        this.solicitudes = new ArrayList<>();
        this.inventarioService = inventarioService;
        
        // Un dato de prueba para el historial
        solicitudes.add(new Solicitud(contadorId++, 1L, 1L, 2L, 1L, 
                                      "ENTRADA", 24.0, LocalDateTime.now(), 
                                      "Llegada de camión repartidor", 
                                      LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * 1. Registrar una Entrada de Mercadería
     */
    public Solicitud registrarEntrada(Solicitud nuevaEntrada) {
        // Configuramos la solicitud
        nuevaEntrada.setId(contadorId++);
        nuevaEntrada.setTipoSolicitud("ENTRADA");
        nuevaEntrada.setFechaSolicitud(LocalDateTime.now());
        nuevaEntrada.setCreatedAt(LocalDateTime.now());
        nuevaEntrada.setUpdatedAt(LocalDateTime.now());

        // ¡LA MAGIA OCURRE AQUÍ! Le decimos al Inventario que sume el stock
        inventarioService.sumarStock(
            nuevaEntrada.getProductoId(), 
            nuevaEntrada.getAlmacenId(), 
            nuevaEntrada.getEmpresaId(), 
            nuevaEntrada.getCantidad()
        );

        // Si todo salió bien, guardamos el historial
        solicitudes.add(nuevaEntrada);
        return nuevaEntrada;
    }

    /**
     * 2. Registrar una Salida (Venta, merma o retiro)
     */
    public Solicitud registrarSalida(Solicitud nuevaSalida) {
        // Configuramos la solicitud
        nuevaSalida.setId(contadorId++);
        nuevaSalida.setTipoSolicitud("SALIDA");
        nuevaSalida.setFechaSolicitud(LocalDateTime.now());
        nuevaSalida.setCreatedAt(LocalDateTime.now());
        nuevaSalida.setUpdatedAt(LocalDateTime.now());

        // ¡LA MAGIA OCURRE AQUÍ! Le decimos al Inventario que reste el stock.
        // Si no hay stock suficiente, el InventarioService lanzará un error 
        // y el código se detendrá aquí, evitando guardar una solicitud falsa.
        inventarioService.restarStock(
            nuevaSalida.getProductoId(), 
            nuevaSalida.getAlmacenId(), 
            nuevaSalida.getEmpresaId(), 
            nuevaSalida.getCantidad()
        );

        // Si la resta fue exitosa, guardamos la evidencia en el historial
        solicitudes.add(nuevaSalida);
        return nuevaSalida;
    }

    /**
     * 3. Listar todo el historial de movimientos de una bodega
     */
    public List<Solicitud> listarHistorialPorEmpresa(Long empresaId) {
        List<Solicitud> listaFiltrada = new ArrayList<>();
        
        for (Solicitud s : solicitudes) {
            if (s.getEmpresaId().equals(empresaId)) {
                listaFiltrada.add(s);
            }
        }
        return listaFiltrada;
    }     
}  
 