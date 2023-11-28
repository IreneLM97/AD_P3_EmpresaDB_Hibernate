package repositories.proyecto;

import java.util.UUID;

import models.Proyecto;
import repositories.DaoInterface;

public interface DaoProyecto extends DaoInterface<Proyecto> {

	/**
	 * Método para obtener un registro de la tabla Proyecto dado su id.
	 * 
	 * @param id identificador del proyecto que se quiere buscar
	 * @return registro de Proyecto encontrado; null si no lo encuentra
	 */
	Proyecto getProyectoById(UUID id);
	
}
