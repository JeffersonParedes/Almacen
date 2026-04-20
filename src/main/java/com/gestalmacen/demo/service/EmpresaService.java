package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Empresa;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {
    // Simulamos la tabla de la base de datos
    private List<Empresa> empresas;
    private Long contadorId = 1L; // Simulamos el AUTO_INCREMENT

    public EmpresaService() {
        empresas = new ArrayList<>();
        // Agregamos una bodega por defecto para tener datos con qué probar
        empresas.add(new Empresa(
            contadorId++, 
            "20123456789", 
            "Bodega Central Nuevo Chimbote", 
            "Av. Pacifico Mz. A Lt. 1", 
            "987654321", 
            LocalDate.now(), 
            "ACTIVO", 
            LocalDateTime.now(), 
            LocalDateTime.now()
        ));
    }

    /**
     * 1. Registrar Nueva Empresa (Onboarding)
     */
    public Empresa registrarNuevaEmpresa(Empresa nuevaEmpresa) {
        // Validamos el RUC de forma sencilla
        if (nuevaEmpresa.getRuc() == null || nuevaEmpresa.getRuc().length() != 11) {
            throw new IllegalArgumentException("El RUC debe tener 11 dígitos.");
        }

        // Asignamos los datos automáticos
        nuevaEmpresa.setId(contadorId++);
        nuevaEmpresa.setFechaSuscripcion(LocalDate.now());
        nuevaEmpresa.setEstado("ACTIVO");
        nuevaEmpresa.setCreatedAt(LocalDateTime.now());
        nuevaEmpresa.setUpdatedAt(LocalDateTime.now());

        // Guardamos en nuestra "Base de datos en memoria"
        empresas.add(nuevaEmpresa);
        
        return nuevaEmpresa;
    }

    /**
     * 2. Obtener Datos de una Empresa
     */
    public Empresa obtenerDatosEmpresa(Long id) {
        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
                return empresa;
            }
        }
        return null; // El Controller deberá manejar este null (ej. lanzando un Error 404)
    }

    /**
     * 3. Listar todas las empresas (Útil para un super-administrador del SaaS)
     */
    public List<Empresa> listarTodasLasEmpresas() {
        return empresas;
    }

    /**
     * 4. Actualizar Datos Generales (Mudanza, nuevo teléfono)
     */
    public Empresa actualizarDatosGenerales(Long id, String nuevaDireccion, String nuevoTelefono) {
        Empresa empresa = obtenerDatosEmpresa(id);
        
        if (empresa != null) {
            // Solo actualizamos lo permitido
            empresa.setDireccionPrincipal(nuevaDireccion);
            empresa.setTelefonoContacto(nuevoTelefono);
            empresa.setUpdatedAt(LocalDateTime.now());
        }
        
        return empresa;
    }

    /**
     * 5. Cambiar Estado (Suspender por falta de pago, o reactivar)
     */
    public Empresa cambiarEstado(Long id, String nuevoEstado) {
        Empresa empresa = obtenerDatosEmpresa(id);
        
        if (empresa != null) {
            empresa.setEstado(nuevoEstado);
            empresa.setUpdatedAt(LocalDateTime.now());
        }
        
        return empresa;
    }
}  
