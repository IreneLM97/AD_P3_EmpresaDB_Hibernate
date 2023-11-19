package db;

import java.util.List;

import models.Departamento;
import models.Empleado;
import models.Proyecto;

public final class DataDB {
    public static List<Departamento> getDepartamentoInit() {
        return List.of(
        		new Departamento.Builder().nombre("Informatica").build(),
                new Departamento.Builder().nombre("Administracion").build(),
                new Departamento.Builder().nombre("Recursos Humanos").build()
        );
    }
    
    public static List<Empleado> getEmpleadoInit() {
        return List.of(
            new Empleado.Builder().nombre("Pedro Picapiedra").salario(3500.0).build(),
            new Empleado.Builder().nombre("Pablo Almansa").salario(4000.0).build(),
            new Empleado.Builder().nombre("Alfonso Gutierrez").salario(2500.0).build(),
            new Empleado.Builder().nombre("Almudena Garcia").salario(5000.0).build(),
            new Empleado.Builder().nombre("Sara Dominguez").build(),
            new Empleado.Builder().nombre("Homer Simpson").salario(4750.0).build(),
            new Empleado.Builder().nombre("Kianu Reeves").salario(1500.0).build(),
            new Empleado.Builder().nombre("Alfonso X").salario(2350.25).build(),
            new Empleado.Builder().nombre("Felipe VI").build(),
            new Empleado.Builder().nombre("Isabel Martinez").salario(6475.5).build()
        );
    }
    
    public static List<Proyecto> getProyectoInit() {
        return List.of(
        	new Proyecto.Builder().nombre("Evalaneca").build(),
        	new Proyecto.Builder().nombre("OrganismoGestion").build()
        );
    }
}