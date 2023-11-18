package dao.departamento;

import java.util.UUID;

import dao.DaoInterface;
import models.Departamento;
import models.Empleado;

public interface DaoDepartamento extends DaoInterface<Departamento>{

	Departamento getDepartamentoById(UUID id);

}
