package db;

import java.util.List;

import models.Departamento;
import models.Empleado;
import models.Proyecto;

public final class DataDB {
    public static List<Departamento> getDepartamentoInit() {
        return List.of(
        		new Departamento("Informatica"),
                new Departamento("Administracion"),
                new Departamento("Recursos Humanos")
        );
    }
    
    public static List<Empleado> getEmpleadoInit() {
        return List.of(
        		new Empleado("Pedro Picapiedra", 3500.0, null),
        		new Empleado("Pablo Almansa", 4000.0, null),
        		new Empleado("Alfonso Gutierrez", 2500.0, null),
        		new Empleado("Almudena Garcia", 5000.0, null),
        		new Empleado("Sara Dominguez", null, null),
        		new Empleado("Homer Simpson", 4750.0, null),
        		new Empleado("Kianu Reeves", 1500.0, null),
        		new Empleado("Alfonso X", 2350.25, null),
        		new Empleado("Felipe VI", null, null),
        		new Empleado("Isabel Martinez", 6475.5, null)
        	
        );
    }
    
    public static List<Proyecto> getProyectoInit() {
        return List.of(
        		new Proyecto("Evalaneca"),
                new Proyecto("OrganismoGestion")
        );
    }
}