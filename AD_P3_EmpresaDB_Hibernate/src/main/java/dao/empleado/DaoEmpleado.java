package dao.empleado;

import java.util.UUID;

import dao.DaoInterface;
import models.Empleado;

public interface DaoEmpleado extends DaoInterface<Empleado> {

	/**
	 * Método para obtener un registro de la tabla Empleado dado su id.
	 * 
	 * @param id identificador del empleado que se quiere buscar
	 * @return registro de Empleado encontrado; null si no lo encuentra
	 */
	Empleado getEmpleadoById(UUID id);

}
