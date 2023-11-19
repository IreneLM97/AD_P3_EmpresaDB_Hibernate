 package dao.departamento;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.DepartamentoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

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
	    	logger.warning("No se encontró departamento con ID: " + id);
	    	return null;
	    } finally {
	        hb.close();
	    }
	}
	
	@Override
	public List<Departamento> listar() {
		logger.info("findAll()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
        List<Departamento> list = query.getResultList();
        hb.close();
        return list;
	}

	// TODO CONTROLAR QUE AL METER UN JEFE QUE FUESE JEFE DE OTRO DEPARTAMENTO SE ACTUALICE EN EL ANTIGUO Y FUNCIONE
	@Override
	public Boolean save(Departamento entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        
        try {
        	Empleado jefe = entity.getJefe();

            // Si hay un jefe asociado al departamento que intentamos guardar
            if (jefe != null) {
            	// Buscar al jefe en el contexto de persistencia para tener una instancia gestionada
                Empleado jefePersistido = hb.getManager().find(Empleado.class, jefe.getId());
                if (jefePersistido != null) {
                    // Buscar los departamentos actuales del nuevo jefe y establecer su jefe a null
                    TypedQuery<Departamento> query = hb.getManager().createQuery(
                            "SELECT d FROM departamento d WHERE d.jefe.id = :jefe_id", Departamento.class);
                    query.setParameter("jefe_id", jefePersistido.getId());
                    query.getResultList().stream().forEach(dept -> {
                        if (!dept.getId().equals(entity.getId())) {
                            dept.setJefe(null);
                            hb.getManager().merge(dept);
                        }
                    });
                }
            }

            // Guardar o actualizar el departamento
            hb.getManager().merge(entity);
            
            hb.getTransaction().commit();
            hb.close();
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
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

}



// Comprobamos si ha introducido un jefe el usuario
//Empleado jefe = null;
//if (entity.getJefe().getId() != null) {  // si se ha introducido jefe
//	// Buscamos ese empleado en la base de datos
//  jefe = hb.getManager().find(Empleado.class, entity.getJefe().getId());
//}

// Hacer merge del departamento (o persistir si es nuevo)
//entity.setJefe(jefe);  // le asociamos el jefe completo
//Departamento mergedDepartamento = hb.getManager().merge(entity);

// Actualizar el campo departamento del Empleado asociado si existe
//if (mergedDepartamento.getJefe() != null) {
//  Empleado jefeActualizado = mergedDepartamento.getJefe();
//  jefeActualizado.setDepartamento(mergedDepartamento);
//  hb.getManager().merge(jefeActualizado);  // Merge del Empleado
//}


/*
Empleado jefe = entity.getJefe();

// Si hay un jefe y ya está asociado a otro departamento, eliminamos la relación
if (jefe != null) {
    Departamento departamentoJefe = jefe.getDepartamento();
    if (departamentoJefe != null && !departamentoJefe.getId().equals(entity.getId())) {
        departamentoJefe.setJefe(null);
        hb.getManager().merge(departamentoJefe);
    }

    // Seteamos el nuevo departamento al empleado (nuevo jefe)
    jefe.setDepartamento(entity);
    hb.getManager().merge(jefe);
}

// Seteamos el jefe al departamento que estamos guardando
entity.setJefe(jefe);

// Guardamos o actualizamos el departamento
hb.getManager().merge(entity);
*/

