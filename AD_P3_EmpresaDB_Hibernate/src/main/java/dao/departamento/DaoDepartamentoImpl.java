 package dao.departamento;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.DepartamentoException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

/**
 * Clase que implementa los métodos necesarios para las operaciones CRUD de Departamento.
 */
public class DaoDepartamentoImpl implements DaoDepartamento {
	private final Logger logger = Logger.getLogger(DaoDepartamentoImpl.class.getName());
	
	@Override
	public Departamento getDepartamentoById(UUID id) {
	    logger.info("getDepartamentoById()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    try {
	        return hb.getManager().find(Departamento.class, id);
	    } catch (Exception e) {
	    	throw new DepartamentoException("No se encontró departamento con ID: " + id);
	    } finally {
	        hb.close();
	    }
	}
	
	@Override
	public List<Departamento> findAll() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
        List<Departamento> list = query.getResultList();
        hb.close();
        return list;
	}

	@Override
	public Boolean save(Departamento entity) {
	    logger.info("save()");
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();

	    try {
	        Empleado jefe = entity.getJefe();
	        if (jefe != null) {
	            // Actualizamos el departamento que tuviese a ese jefe como jefe para setearlo a null
	            if (!nullificarJefeDepartamento(entity, jefe, hb)) {
	                return false;
	            }
	            
	            // Actualizar la tabla de empleados para que el jefe pertenezca al nuevo departamento
	            jefe.setDepartamento(entity);
	            hb.getTransaction().begin();
	            hb.getManager().merge(jefe);
	            hb.getTransaction().commit();
	        }
	        
	        // Guardar o actualizar el departamento
	        hb.getTransaction().begin();
	        hb.getManager().merge(entity);
	        hb.getTransaction().commit();
	        return true;
	     } catch (Exception e) {
	    	 if (hb.getTransaction().isActive()) {
	    		 hb.getTransaction().rollback();
	    	 }
	         return false;
	    } finally {
	        hb.close();
	    }
	}

	@Override
	public Boolean delete(Departamento entity) {
		logger.info("delete()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            // Ojo que borrar implica que estemos en la misma sesión y nos puede dar problemas, por eso lo recuperamos otra vez
            entity = hb.getManager().find(Departamento.class, entity.getId());
            hb.getManager().remove(entity);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new DepartamentoException("Error al eliminar departamento con ID: " + entity.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}
	
	/**
	 * Método privado para actualizar a null el jefe en el departamento que tenga a jefe como jefe y no sean el departamento dado.
	 * 
	 * @param departamento 
	 * @param jefe
	 * @param hb hibernate manager
	 * @return true si se puede actualizar, false en caso contrario
	 */
	private boolean nullificarJefeDepartamento(Departamento departamento, Empleado jefe, HibernateManager hb) {
		// Nueva transacción para la lógica específica de actualizar jefe de departamento
	    hb.getTransaction().begin();

	    try {
	        // Buscamos al jefe en el contexto de persistencia para tener una instancia gestionada
	        Empleado jefePersistido = hb.getManager().find(Empleado.class, jefe.getId());
	        if (jefePersistido != null) {
	            // Buscamos el departamento que tuviese como jefe a ese jefe
	            TypedQuery<Departamento> query = hb.getManager().createQuery(
	                    "SELECT d FROM departamento d WHERE d.jefe.id = :jefe_id AND d.id != :dpto_id", Departamento.class);
	            query.setParameter("jefe_id", jefePersistido.getId());
	            query.setParameter("dpto_id", departamento.getId());
	            query.getSingleResult().setJefe(null);
	        }

	        // Commit de la transacción específica
	        hb.getTransaction().commit();
	        return true;
	    } catch (NoResultException e) {
	    	return true;
	    } catch (Exception e) {
	        logger.warning("Problemas al actualizar jefe del departamento a null " + e.getMessage());
	        return false;
	    } finally {
	    	if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	    }
	}
}
