package dao.departamento;

import java.util.UUID;

import dao.DaoInterface;
import models.Departamento;

public interface DaoDepartamento extends DaoInterface<Departamento> {

	/**
	 * Método para obtener un registro de la tabla Departamento dado su id.
	 * 
	 * @param id identificador del departamento que se quiere buscar
	 * @return registro de Departamento encontrado; null si no lo encuentra
	 */
	Departamento getDepartamentoById(UUID id);

}
