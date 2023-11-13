package dao;

import java.util.List;

/**
 * Interfaz con los métodos necesarios para realizar operaciones CRUD sobre la base de datos.
 * 
 * @param <T> tipo de objeto genérico (representa Empleado o Departamento)
 */
public interface DaoInterface<T> {
	/**
	 * Método para listar los registros de tabla.
	 * 
	 * @return 
	 */
	List<T> listar();
	
	/**
	 * Método para guardar un registro en la tabla.
	 * 
	 * @param t registro que se quiere guardar
	 * @return true si se ha guardado correctamente, false en caso contrario
	 */
	Boolean save(T entity);
	
	/**
	 * Método para eliminar un registro de la tabla.
	 * 
	 * @param t registro que se quiere eliminar
	 * @return true si se ha podido guardar, false en caso contrario
	 */
	Boolean delete(T entity);
}
