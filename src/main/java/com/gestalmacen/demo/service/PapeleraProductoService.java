package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.PapeleraProducto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PapeleraProductoService {
    private List<PapeleraProducto> papelera;
    private Long contadorId = 1L;

    public PapeleraProductoService() {
        papelera = new ArrayList<>();
        
        // Un dato de prueba: Alguien borró un paquete de galletas de la Bodega 1
        papelera.add(new PapeleraProducto(contadorId++, 1L, 99L, "775000111222", 
                                          "Galletas Oreo", 1L, 1.50, 
                                          LocalDateTime.now(), 2L)); // 2L sería el ID del usuario que lo borró
    }

    /**
     * 1. Registrar un producto en la papelera (Llamado cuando se inactiva un producto)
     */
    public PapeleraProducto enviarAPapelera(PapeleraProducto registroPapelera) {
        registroPapelera.setId(contadorId++);
        registroPapelera.setFechaEliminacion(LocalDateTime.now());
        
        papelera.add(registroPapelera);
        return registroPapelera;
    }

    /**
     * 2. Listar todos los productos eliminados de una empresa
     */
    public List<PapeleraProducto> listarPapeleraPorEmpresa(Long empresaId) {
        List<PapeleraProducto> listaFiltrada = new ArrayList<>();
        
        for (PapeleraProducto p : papelera) {
            if (p.getEmpresaId().equals(empresaId)) {
                listaFiltrada.add(p);
            }
        }
        return listaFiltrada;
    }

    /**
     * 3. Restaurar producto (Simplemente lo sacamos de esta lista de eliminados)
     * El controlador se encargará de avisarle a ProductoService que lo reactive.
     */
    public boolean restaurarProducto(Long idPapelera, Long empresaId) {
        for (int i = 0; i < papelera.size(); i++) {
            PapeleraProducto p = papelera.get(i);
            
            if (p.getId().equals(idPapelera) && p.getEmpresaId().equals(empresaId)) {
                papelera.remove(i); // Lo sacamos de la papelera
                return true;
            }
        }
        return false;
    }

    /**
     * 4. Eliminar un producto de forma permanente
     */
    public boolean eliminarDefinitivamente(Long idPapelera, Long empresaId) {
        // En este diseño en memoria, restaurar y eliminar definitivamente 
        // hacen lo mismo (quitarlo de la lista). La diferencia estará en el Controller.
        for (int i = 0; i < papelera.size(); i++) {
            PapeleraProducto p = papelera.get(i);
            
            if (p.getId().equals(idPapelera) && p.getEmpresaId().equals(empresaId)) {
                papelera.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * 5. Vaciar toda la papelera de una empresa
     */
    public void vaciarPapelera(Long empresaId) {
        // Usamos removeIf (una función moderna de Java) para borrar 
        // masivamente todos los que cumplan la condición.
        papelera.removeIf(p -> p.getEmpresaId().equals(empresaId));
    }
}  
