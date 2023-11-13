package controllers;

import models.Departamento;
import models.Empleado;
import models.Proyecto;
import dao.departamento.DaoDepartamento;
import dao.empleado.DaoEmpleado;
import dao.proyecto.DaoProyecto;

import java.util.List;
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
    public List<Departamento> getDepartamentos() {
        logger.info("Obteniendo Departamento");
        return departamentoDao.listar();
    }

    public Boolean createDepartamento(Departamento departamento) {
        logger.info("Creando Departamento");
        return departamentoDao.save(departamento);
    }

    public Boolean updateDepartamento(Departamento departamento) {
        logger.info("Actualizando Departamento con uuid: " + departamento.getId());
        return departamentoDao.save(departamento);
    }

    public Boolean deleteDepartamento(Departamento departamento) {
        logger.info("Eliminando Departamento con uuid: " + departamento.getId());
        return departamentoDao.delete(departamento);
    }
    // ==========================================================================================

    // ======================================| EMPLEADO |======================================   
    public List<Empleado> getEmpleados() {
        logger.info("Obteniendo Empleados");
        return empleadoDao.listar();
    }

    public Boolean createEmpleado(Empleado empleado) {
        logger.info("Creando Empleados");
        return empleadoDao.save(empleado);
    }

    public Boolean updateEmpleado(Empleado empleado) {
        logger.info("Actualizando Empleados con uuid: " + empleado.getId());
        return empleadoDao.save(empleado);
    }

    public Boolean deleteEmpleado(Empleado empleado) {
        logger.info("Eliminando Empleados con uuid: " + empleado.getId());
        return empleadoDao.delete(empleado);
    }
 // ==========================================================================================

 // ======================================| PROYECTO |======================================
    public List<Proyecto> getProyectos() {
        logger.info("Obteniendo Proyectos");
        return proyectoDao.listar();
    }

    public Boolean createProyecto(Proyecto proyecto) {
        logger.info("Creando Raqueta");
        return proyectoDao.save(proyecto);
    }

    public Boolean updateProyecto(Proyecto proyecto) {
        logger.info("Actualizando Raqueta con uuid: " + proyecto.getId());
        return proyectoDao.save(proyecto);
    }

    public Boolean deleteProyecto(Proyecto proyecto) {
        logger.info("Eliminando Raqueta con uuid: " + proyecto.getId());
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