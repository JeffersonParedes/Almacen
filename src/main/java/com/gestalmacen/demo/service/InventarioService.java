package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Inventario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioService {
    private List<Inventario> inventarios;
    private Long contadorId = 1L;

    public InventarioService() {
        inventarios = new ArrayList<>();
        
        // Datos de prueba (Empresa 1)
        // Arroz (Producto 1) en la Tienda (Almacén 1) -> Hay 50 kilos (Stock mínimo 10)
        inventarios.add(new Inventario(contadorId++, 1L, 1L, 1L, 50.0, 10.0, 
                                       LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()));
        
        // Arroz (Producto 1) en el Depósito (Almacén 2) -> Hay 200 kilos (Stock mínimo 50)
        inventarios.add(new Inventario(contadorId++, 1L, 1L, 2L, 200.0, 50.0, 
                                       LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()));
                                       
        // Inka Kola (Producto 2) en la Tienda (Almacén 1) -> Hay 24 botellas (Stock mínimo 12)
        inventarios.add(new Inventario(contadorId++, 1L, 2L, 1L, 24.0, 12.0, 
                                       LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * 1. Consultar el stock específico de un producto en un almacén concreto
     */
    public Inventario obtenerInventarioEspecifico(Long productoId, Long almacenId, Long empresaId) {
        for (Inventario inv : inventarios) {
            if (inv.getProductoId().equals(productoId) && 
                inv.getAlmacenId().equals(almacenId) && 
                inv.getEmpresaId().equals(empresaId)) {
                return inv;
            }
        }
        return null; // Significa que este producto nunca ha entrado a ese almacén
    }

    /**
     * 2. Consultar Stock Global (Suma todo lo que hay en la empresa de ese producto)
     */
    public Double consultarStockGlobal(Long productoId, Long empresaId) {
        Double stockTotal = 0.0;
        for (Inventario inv : inventarios) {
            if (inv.getProductoId().equals(productoId) && inv.getEmpresaId().equals(empresaId)) {
                stockTotal += inv.getStockActual();
            }
        }
        return stockTotal;
    }

    /**
     * 3. Sumar Stock (Llamado por Solicitud de Entrada)
     */
    public void sumarStock(Long productoId, Long almacenId, Long empresaId, Double cantidad) {
        Inventario inv = obtenerInventarioEspecifico(productoId, almacenId, empresaId);
        
        if (inv != null) {
            // Si ya existe el registro, le sumamos la cantidad
            inv.setStockActual(inv.getStockActual() + cantidad);
            inv.setUltimaActualizacion(LocalDateTime.now());
        } else {
            // Si el producto nunca había estado en este almacén, creamos el registro desde cero
            Inventario nuevoInv = new Inventario(contadorId++, empresaId, productoId, almacenId, 
                                                 cantidad, 5.0, // Stock mínimo por defecto
                                                 LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
            inventarios.add(nuevoInv);
        }
    }

    /**
     * 4. Restar Stock (Llamado por Solicitud de Salida o Venta)
     */
    public boolean restarStock(Long productoId, Long almacenId, Long empresaId, Double cantidad) {
        Inventario inv = obtenerInventarioEspecifico(productoId, almacenId, empresaId);
        
        if (inv != null) {
            // Validamos que haya stock suficiente para restar
            if (inv.getStockActual() >= cantidad) {
                inv.setStockActual(inv.getStockActual() - cantidad);
                inv.setUltimaActualizacion(LocalDateTime.now());
                return true; // Operación exitosa
            } else {
                throw new IllegalArgumentException("Error: Stock insuficiente en el almacén seleccionado.");
            }
        }
        throw new IllegalArgumentException("Error: El producto no existe en este almacén.");
    }  
}
