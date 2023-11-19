package dao.empleado;

import java.util.UUID;

import dao.DaoInterface;
import models.Empleado;

public interface DaoEmpleado extends DaoInterface<Empleado> {

	Empleado getEmpleadoById(UUID id);

}
