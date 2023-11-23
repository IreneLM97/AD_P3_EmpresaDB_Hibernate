package controllers;

import models.Departamento;
import models.Empleado;
import models.Proyecto;
import dao.departamento.DaoDepartamento;
import dao.empleado.DaoEmpleado;
import dao.proyecto.DaoProyecto;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Clase que será el controlador de la Empresa.
 */
public class EmpresaController {
	// Logger para mostrar mensajes informativos
    private final Logger logger = Logger.getLogger(EmpresaController.class.getName());

    // Mis dependencias
    private final DaoDepartamento departamentoDao;
    private final DaoEmpleado empleadoDao;
    private final DaoProyecto proyectoDao;
    
    public EmpresaController(DaoDepartamento departamentoDao, DaoEmpleado empleadoDao, DaoProyecto proyectoDao) {
        this.departamentoDao = departamentoDao;
        this.empleadoDao = empleadoDao;
        this.proyectoDao = proyectoDao;
    }
    
 // ======================================| DEPARTAMENTO |====================================== 
    /**
     * Obtiene un departamento dado su ID.
     *
     * @param id
     * @return
     */
    public Departamento getDepartamentoById(UUID id) {
        logger.info("Obteniendo departamento por ID: " + id);
        return departamentoDao.getDepartamentoById(id);
    }
    
    /**
     * Obtiene la lista de departamentos.
     * 
     * @return
     */
    public List<Departamento> getDepartamentos() {
        logger.info("Obteniendo departamentos");
        return departamentoDao.findAll();
    }

    /**
     * Crea un departamento dado.
     * 
     * @param departamento
     * @return true si se puede crear, false en caso contrario
     */
    public Boolean createDepartamento(Departamento departamento) {
        logger.info("Creando departamento");
        return departamentoDao.save(departamento);
    }

    /**
     * Actualiza un departamento dado.
     * 
     * @param departamento
     * @return true si se puede actualizar, false en caso contrario
     */
    public Boolean updateDepartamento(Departamento departamento) {
        logger.info("Actualizando departamento con ID: " + departamento.getId());
        return departamentoDao.save(departamento);
    }

    /**
     * Elimina un departamento dado.
     * 
     * @param departamento
     * @return true si se puede eliminar, false en caso contrario
     */
    public Boolean deleteDepartamento(Departamento departamento) {
        logger.info("Eliminando departamento con ID: " + departamento.getId());
        return departamentoDao.delete(departamento);
    }
    // ==========================================================================================

    // ======================================| EMPLEADO |========================================
    /**
     * Obtiene un empleado dado su ID.
     * 
     * @param id
     * @return
     */
    public Empleado getEmpleadoById(UUID id) {
        logger.info("Obteniendo empleado por ID: " + id);
        return empleadoDao.getEmpleadoById(id);
    }

    /**
     * Obtiene la lista de empleados.
     * 
     * @return
     */
    public List<Empleado> getEmpleados() {
        logger.info("Obteniendo empleados");
        return empleadoDao.findAll();
    }

    /**
     * Crea un empleado dado.
     * 
     * @param empleado
     * @return true si se puede crear, false en caso contrario
     */
    public Boolean createEmpleado(Empleado empleado) {
        logger.info("Creando empleado");
        return empleadoDao.save(empleado);
    }

    /**
     * Actualiza un empleado dado.
     * 
     * @param empleado
     * @return true si se puede actualizar, false en caso contrario
     */
    public Boolean updateEmpleado(Empleado empleado) {
        logger.info("Actualizando empleado con ID: " + empleado.getId());
        return empleadoDao.save(empleado);
    }
    
    /**
     * Añade un empleado a un proyecto.
     * 
     * @param empleado
     * @param proyecto
     * @return true si se puede añadir, false en caso contrario
     */
    public Boolean addToProyecto(Empleado empleado, Proyecto proyecto) {
    	logger.info("Añadiendo empleado con ID: " + empleado.getId() + " a proyecto con ID: " + proyecto.getId());
        return empleadoDao.addToProyecto(empleado, proyecto);
    }
    
    /**
     * Elimina un empleado de un proyecto.
     * 
     * @param empleado
     * @param proyecto
     * @return true si se puede eliminar, false en caso contrario
     */
    public Boolean deleteFromProyecto(Empleado empleado, Proyecto proyecto) {
    	logger.info("Eliminando empleado con ID: " + empleado.getId() + " de proyecto con ID: " + proyecto.getId());
        return empleadoDao.deleteFromProyecto(empleado, proyecto);
    }

    /**
     * Elimina un empleado dado.
     * 
     * @param empleado
     * @return true si se puede eliminar, false en caso contrario
     */
    public Boolean deleteEmpleado(Empleado empleado) {
        logger.info("Eliminando empleado con ID: " + empleado.getId());
        return empleadoDao.delete(empleado);
    }
 // ==========================================================================================

 // ======================================| PROYECTO |========================================
    /**
     * Obtiene un proyecto dado su ID.
     * 
     * @param id
     * @return
     */
    public Proyecto getProyectoById(UUID id) {
        logger.info("Obteniendo empleado por ID: " + id);
        return proyectoDao.getProyectoById(id);
    }
    
    /**
     * Obtiene la lista de proyectos.
     * 
     * @return
     */
    public List<Proyecto> getProyectos() {
        logger.info("Obteniendo proyectos");
        return proyectoDao.findAll();
    }

    /**
     * Crea un proyecto dado.
     * 
     * @param proyecto
     * @return true si se puede crear, false en caso contrario
     */
    public Boolean createProyecto(Proyecto proyecto) {
        logger.info("Creando proyecto");
        return proyectoDao.save(proyecto);
    }

    /**
     * Actualiza un proyecto dado.
     * 
     * @param proyecto
     * @return true si se puede actualizar, false en caso contrario
     */
    public Boolean updateProyecto(Proyecto proyecto) {
        logger.info("Actualizando proyecto con ID: " + proyecto.getId());
        return proyectoDao.save(proyecto);
    }

    /**
     * Elimina un proyecto dado.
     * 
     * @param proyecto
     * @return true si se puede eliminar, false en caso contrario
     */
    public Boolean deleteProyecto(Proyecto proyecto) {
        logger.info("Eliminando proyecto con ID: " + proyecto.getId());
        return proyectoDao.delete(proyecto);
    }
// ==========================================================================================
}