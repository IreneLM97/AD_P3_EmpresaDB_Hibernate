package dao.proyecto;

import java.util.UUID;

import dao.DaoInterface;
import models.Proyecto;

public interface DaoProyecto extends DaoInterface<Proyecto> {

	Proyecto getProyectoById(UUID id);
	
}
