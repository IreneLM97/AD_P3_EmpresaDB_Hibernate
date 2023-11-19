package dao.proyecto;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.ProyectoException;
import jakarta.persistence.TypedQuery;
import models.Proyecto;

public class DaoProyectoImpl implements DaoProyecto{
	private final Logger logger = Logger.getLogger(DaoProyectoImpl.class.getName());
	
	@Override
	public Proyecto getProyectoById(UUID id) {
	    logger.info("getProyectoById()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    try {
	        return hb.getManager().find(Proyecto.class, id);
	    } catch (Exception e) {
	    	throw new ProyectoException("No se encontró proyecto con ID: " + id);
	    } finally {
	        hb.close();
	    }
	}
	
	@Override
	public List<Proyecto> findAll() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Proyecto> query = hb.getManager().createNamedQuery("Proyecto.findAll", Proyecto.class);
        List<Proyecto> list = query.getResultList();
        hb.close();
        return list;
	}
	
	// TODO NO SE SI HAY QUE HACER ALGO CON EMPLEADO
	@Override
	public Boolean save(Proyecto entity) {
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
            throw new ProyectoException("Error al guardar proyecto con ID: " + entity.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

	@Override
	public Boolean delete(Proyecto entity) {
		logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
            entity = hb.getManager().find(Proyecto.class, entity.getId());
            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new DepartamentoException("Error al eliminar proyecto con ID: " + entity.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
}
