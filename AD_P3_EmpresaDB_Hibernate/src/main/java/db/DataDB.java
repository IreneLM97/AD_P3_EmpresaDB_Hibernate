package db;

import java.util.List;

import models.Departamento;
import models.Empleado;
import models.Proyecto;

/**
 * Clase para inicializar la base de datos con algunos registros.
 */
public final class DataDB {
    public static List<Departamento> getDepartamentoInit() {
        return List.of(
        		Departamento.builder().nombre("Informatica").build(),
                Departamento.builder().nombre("Administracion").build(),
                Departamento.builder().nombre("Recursos Humanos").build()
        );
    }
    
    public static List<Empleado> getEmpleadoInit() {
        return List.of(
            Empleado.builder().nombre("Pedro Picapiedra").salario(3500.0).build(),
            Empleado.builder().nombre("Pablo Almansa").salario(4000.0).build(),
            Empleado.builder().nombre("Alfonso Gutierrez").salario(2500.0).build(),
            Empleado.builder().nombre("Almudena Garcia").salario(5000.0).build(),
            Empleado.builder().nombre("Sara Dominguez").build(),
            Empleado.builder().nombre("Homer Simpson").salario(4750.0).build(),
            Empleado.builder().nombre("Kianu Reeves").salario(1500.0).build(),
            Empleado.builder().nombre("Alfonso X").salario(2350.25).build(),
            Empleado.builder().nombre("Felipe VI").build(),
            Empleado.builder().nombre("Isabel Martinez").salario(6475.5).build()
        );
    }
    
    public static List<Proyecto> getProyectoInit() {
        return List.of(
        	Proyecto.builder().nombre("Evalaneca").build(),
        	Proyecto.builder().nombre("OrganismoGestion").build()
        );
    }
}