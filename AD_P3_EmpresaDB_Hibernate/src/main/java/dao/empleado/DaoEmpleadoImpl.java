package dao.empleado;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import dao.departamento.DaoDepartamentoImpl;
import db.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.EmpleadoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

public class DaoEmpleadoImpl implements DaoEmpleado {
	private final Logger logger = Logger.getLogger(DaoDepartamentoImpl.class.getName());
	
	@Override
	public Empleado getEmpleadoById(UUID id) {
	    logger.info("getEmpleadoById()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    try {
	        return hb.getManager().find(Empleado.class, id);
	    } catch (Exception e) {
	    	logger.warning("No se encontró empleado con ID: " + id);
	    	return null;
	    } finally {
	        hb.close();
	    }
	}

	@Override
	public List<Empleado> listar() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
        List<Empleado> list = query.getResultList();
        hb.close();
        return list;
	}

	// TODO AL ACTUALIZAR DUPLICA
	@Override
	public Boolean save(Empleado entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();

        try {
            hb.getManager().merge(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            throw new EmpleadoException("Error al guardar empleado con ID: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

	@Override
	public Boolean delete(Empleado entity) {
		logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
            entity = hb.getManager().find(Empleado.class, entity.getId());
            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new EmpleadoException("Error al eliminar empleado con ID: " + entity.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

}
