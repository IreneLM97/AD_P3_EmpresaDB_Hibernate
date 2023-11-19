package dao.departamento;

import java.util.UUID;

import dao.DaoInterface;
import models.Departamento;

public interface DaoDepartamento extends DaoInterface<Departamento> {

	Departamento getDepartamentoById(UUID id);

}
