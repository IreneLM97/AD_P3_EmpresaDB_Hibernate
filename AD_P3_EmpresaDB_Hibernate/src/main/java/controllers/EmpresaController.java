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
    public Departamento getDepartamentoById(UUID id) {
        logger.info("Obteniendo departamento por ID: " + id);
        return departamentoDao.getDepartamentoById(id);
    }
    
    public List<Departamento> getDepartamentos() {
        logger.info("Obteniendo departamentos");
        return departamentoDao.findAll();
    }

    public Boolean createDepartamento(Departamento departamento) {
        logger.info("Creando departamento");
        return departamentoDao.save(departamento);
    }

    public Boolean updateDepartamento(Departamento departamento) {
        logger.info("Actualizando departamento con ID: " + departamento.getId());
        return departamentoDao.save(departamento);
    }

    public Boolean deleteDepartamento(Departamento departamento) {
        logger.info("Eliminando departamento con ID: " + departamento.getId());
        return departamentoDao.delete(departamento);
    }
    // ==========================================================================================

    // ======================================| EMPLEADO |======================================   
    public Empleado getEmpleadoById(UUID id) {
        logger.info("Obteniendo empleado por ID: " + id);
        return empleadoDao.getEmpleadoById(id);
    }

    public List<Empleado> getEmpleados() {
        logger.info("Obteniendo empleados");
        return empleadoDao.findAll();
    }

    public Boolean createEmpleado(Empleado empleado) {
        logger.info("Creando empleado");
        return empleadoDao.save(empleado);
    }

    public Boolean updateEmpleado(Empleado empleado) {
        logger.info("Actualizando empleado con ID: " + empleado.getId());
        return empleadoDao.save(empleado);
    }

    public Boolean deleteEmpleado(Empleado empleado) {
        logger.info("Eliminando empleado con ID: " + empleado.getId());
        return empleadoDao.delete(empleado);
    }
 // ==========================================================================================

 // ======================================| PROYECTO |======================================
    public Proyecto getProyectoById(UUID id) {
        logger.info("Obteniendo empleado por ID: " + id);
        return proyectoDao.getProyectoById(id);
    }
    
    public List<Proyecto> getProyectos() {
        logger.info("Obteniendo proyectos");
        return proyectoDao.findAll();
    }

    public Boolean createProyecto(Proyecto proyecto) {
        logger.info("Creando proyecto");
        return proyectoDao.save(proyecto);
    }

    public Boolean updateProyecto(Proyecto proyecto) {
        logger.info("Actualizando proyecto con ID: " + proyecto.getId());
        return proyectoDao.save(proyecto);
    }

    public Boolean deleteProyecto(Proyecto proyecto) {
        logger.info("Eliminando proyecto con ID: " + proyecto.getId());
        return proyectoDao.delete(proyecto);
    }
// ==========================================================================================

}


//public Optional<Tenista> getTenistaById(UUID uuid) {
//logger.info("Obteniendo Tenista con uuid: " + uuid);
//return tenistasRepository.findById(uuid);
//}

//public Optional<Raqueta> getRaquetaById(UUID uuid) {
//logger.info("Obteniendo Raqueta con uuid: " + uuid);
//return raquetasRepository.findById(uuid);
//}

//public Optional<Tenista> getTenistaById(UUID uuid) {
//logger.info("Obteniendo Tenista con uuid: " + uuid);
//return tenistasRepository.findById(uuid);
//}