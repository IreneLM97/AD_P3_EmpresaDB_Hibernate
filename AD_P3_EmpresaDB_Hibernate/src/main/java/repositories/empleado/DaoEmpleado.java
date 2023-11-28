package repositories.empleado;

import java.util.UUID;

import models.Empleado;
import models.Proyecto;
import repositories.DaoInterface;

public interface DaoEmpleado extends DaoInterface<Empleado> {

	/**
	 * Método para obtener un registro de la tabla Empleado dado su id.
	 * 
	 * @param id identificador del empleado que se quiere buscar
	 * @return registro de Empleado encontrado; null si no lo encuentra
	 */
	Empleado getEmpleadoById(UUID id);
	
	/**
	 * Método para añadir un empleado a un proyecto.
	 * 
	 * @param e empleado que se quiere añadir
	 * @param p proyecto al que se quiere añadir
	 * @return true si se ha podido añadir, false en caso contrario
	 */
	Boolean addToProyecto(Empleado e, Proyecto p);
	
	/**
	 * Método para eliminar un empleado de un proyecto.
	 * 
	 * @param e empleado que se quiere eliminar
	 * @param p proyecto del que se quiere eliminar
	 * @return true si se ha podido eliminar, false en caso contrario
	 */
	Boolean deleteFromProyecto(Empleado e, Proyecto p);
}
