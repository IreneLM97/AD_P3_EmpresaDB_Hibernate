package repositories.empleado;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.EmpleadoException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;
import models.Proyecto;
import repositories.departamento.DaoDepartamentoImpl;

/**
 * Clase que implementa los métodos necesarios para las operaciones CRUD de Empleado.
 */
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
	    	throw new EmpleadoException("No se encontró empleado con ID: " + id);
	    } finally {
	        hb.close();
	    }
	}

	@Override
	public List<Empleado> findAll() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
        List<Empleado> list = query.getResultList();
        hb.close();
        return list;
	}

	@Override
	public Boolean save(Empleado entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();

        try {
        	Departamento departamento = entity.getDepartamento();
	        if (departamento != null) {
	            // Actualizamos el departamento que tuviese a ese jefe como jefe para setearlo a null
	            if (!nullificarJefeDepartamento(departamento, entity, hb)) {
	                return false;
	            }
	        }
	        
	        // Guardar o actualizar el empleado
	        hb.getTransaction().begin();
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

	@Override
	public Boolean addToProyecto(Empleado entity, Proyecto proyecto) {
		logger.info("addToProyecto()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        
        try {
            hb.getTransaction().begin();
            entity = hb.getManager().find(Empleado.class, entity.getId());
            proyecto = hb.getManager().find(Proyecto.class, proyecto.getId());
            
            entity.getProyectos().add(proyecto);
            proyecto.getEmpleados().add(entity);
            
            hb.getManager().merge(entity);
            hb.getManager().merge(proyecto);
            
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new EmpleadoException("Error al añadir empleado con ID: " + entity.getId() + " a proyecto con ID: " + proyecto.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

	@Override
	public Boolean deleteFromProyecto(Empleado entity, Proyecto proyecto) {
		logger.info("deleteFromProyecto()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        
        try {
            hb.getTransaction().begin();
            entity = hb.getManager().find(Empleado.class, entity.getId());
            proyecto = hb.getManager().find(Proyecto.class, proyecto.getId());
            
            entity.getProyectos().remove(proyecto);
            proyecto.getEmpleados().remove(entity);
            
            hb.getManager().merge(entity);
            hb.getManager().merge(proyecto);
            
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new EmpleadoException("Error al eliminar empleado con ID: " + entity.getId() + " de proyecto con ID: " + proyecto.getId() + " - " + e.getMessage());
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
	        // Buscamos al departamento en el contexto de persistencia para tener una instancia gestionada
	        Departamento departamentoPersistido = hb.getManager().find(Departamento.class, departamento.getId());
	        if (departamentoPersistido != null) {
	            // Buscamos el departamento que tuviese como jefe a ese jefe
	            TypedQuery<Departamento> query = hb.getManager().createQuery(
	                    "SELECT d FROM departamento d WHERE d.jefe.id = :jefe_id AND d.id != :dpto_id", Departamento.class);
	            query.setParameter("jefe_id", jefe.getId());
	            query.setParameter("dpto_id", departamentoPersistido.getId());
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
