package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Almacen;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlmacenService {
    private List<Almacen> almacenes;
    private Long contadorId = 1L;

    public AlmacenService() {
        almacenes = new ArrayList<>();
        
        // Datos de prueba para la "Bodega Central" (empresaId = 1)
        almacenes.add(new Almacen(contadorId++, 1L, "Tienda Principal (Vitrina)", 
                                  "Av. Pacífico Mz. A Lt. 1", "ACTIVO", 
                                  LocalDateTime.now(), LocalDateTime.now()));
                                  
        almacenes.add(new Almacen(contadorId++, 1L, "Depósito Trasero", 
                                  "Av. Pacífico Mz. A Lt. 1 (Parte posterior)", "ACTIVO", 
                                  LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * 1. Registrar un nuevo almacén / ubicación
     */
    public Almacen registrarAlmacen(Almacen nuevoAlmacen) {
        nuevoAlmacen.setId(contadorId++);
        nuevoAlmacen.setEstado("ACTIVO");
        nuevoAlmacen.setCreatedAt(LocalDateTime.now());
        nuevoAlmacen.setUpdatedAt(LocalDateTime.now());
        
        almacenes.add(nuevoAlmacen);
        return nuevoAlmacen;
    }

    /**
     * 2. Listar almacenes (Aislamiento por Empresa)
     */
    public List<Almacen> listarAlmacenesPorEmpresa(Long empresaId) {
        List<Almacen> listaFiltrada = new ArrayList<>();
        
        for (Almacen a : almacenes) {
            // Filtramos por empresa y excluimos los que estén inactivos
            if (a.getEmpresaId().equals(empresaId) && !a.getEstado().equals("INACTIVO")) {
                listaFiltrada.add(a);
            }
        }
        return listaFiltrada;
    }

    /**
     * 3. Obtener un almacén específico
     */
    public Almacen obtenerAlmacen(Long id, Long empresaId) {
        for (Almacen a : almacenes) {
            if (a.getId().equals(id) && a.getEmpresaId().equals(empresaId)) {
                return a;
            }
        }
        return null;
    }

    /**
     * 4. Actualizar datos del almacén
     */
    public Almacen actualizarAlmacen(Long id, Long empresaId, String nuevoNombre, String nuevaDireccion) {
        Almacen almacen = obtenerAlmacen(id, empresaId);
        
        if (almacen != null) {
            almacen.setNombre(nuevoNombre);
            almacen.setDireccion(nuevaDireccion);
            almacen.setUpdatedAt(LocalDateTime.now());
        }
        
        return almacen;
    }

    /**
     * 5. Cambiar estado (INACTIVO o EN_MANTENIMIENTO)
     */
    public Almacen cambiarEstado(Long id, Long empresaId, String nuevoEstado) {
        Almacen almacen = obtenerAlmacen(id, empresaId);
        
        if (almacen != null) {
            almacen.setEstado(nuevoEstado);
            almacen.setUpdatedAt(LocalDateTime.now());
        }
        
        return almacen;
    } 
}
