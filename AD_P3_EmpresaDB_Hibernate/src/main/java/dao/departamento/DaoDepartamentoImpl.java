package dao.departamento;

import java.util.List;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.DepartamentoException;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

public class DaoDepartamentoImpl implements DaoDepartamento{
	private final Logger logger = Logger.getLogger(DaoDepartamentoImpl.class.getName());
	
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

	// TODO CONTROLAR QUE AL METER UN JEFE QUE FUESE JEFE DE OTRO DEPARTAMENTO SE ACTUALICE EN EL ANTIGUO
	@Override
	public Boolean save(Departamento entity) {
		logger.info("save()");
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        
        try {
            // Comprobamos si ha introducido un jefe el usuario
            Empleado jefe = null;
            if (entity.getJefe().getId() != null) {  // si se ha introducido jefe
            	// Buscamos ese empleado en la base de datos
                jefe = hb.getManager().find(Empleado.class, entity.getJefe().getId());
            }

            // Hacer merge del departamento (o persistir si es nuevo)
            entity.setJefe(jefe);  // le asociamos el jefe completo
            Departamento mergedDepartamento = hb.getManager().merge(entity);

            // Actualizar el campo departamento del Empleado asociado si existe
            if (mergedDepartamento.getJefe() != null) {
                Empleado jefeActualizado = mergedDepartamento.getJefe();
                jefeActualizado.setDepartamento(mergedDepartamento);
                hb.getManager().merge(jefeActualizado);  // Merge del Empleado
            }

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
            throw new DepartamentoException("Error al eliminar tenista con uuid: " + entity.getId() + " - " + e.getMessage());
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
