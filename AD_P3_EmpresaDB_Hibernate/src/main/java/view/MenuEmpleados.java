package view;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import IO.IO;
import constantes.color.Colores;
import controllers.EmpresaController;
import models.Departamento;
import models.Empleado;

public class MenuEmpleados {
	public static void mostrarMenu(EmpresaController controller) {

		List<String> opciones = List.of(
				"\n =======|MENU EMPLEADOS|========\n", 
				"| 1.- Listar Empleados	        |\n",
				"| 2.- Agregar Empleado	        |\n",
				"| 3.- Modificar Empleado 	|\n",
				"| 4.- Eliminar Empleado	     	|\n",
				"| 5.- Volver al menu principal  |\n", 
				" ===============================\n");

		while (true) {
			opciones.stream().forEach(System.out::print);
			IO.print("\nIntroduce tu elección: ");
			switch (IO.readInt()) {
				case 1:
					listarEmpleados(controller);
					break;
				case 2:
					insertEmpleado(controller);
					break;
				case 3:
					updateEmpleado(controller);
					break;
				case 4:
					deleteEmpleado(controller);
					break;
				case 5:
					MenuPrincipal.main(null);
					break;
				default:
					IO.println(Colores.ROJO + "Opción no válida" + Colores.RESET);
			}
		}
	}

	private static void listarEmpleados(EmpresaController controller) {
		// Obtenemos todos los empleados
		List<Empleado> empleados = controller.getEmpleados().stream()
                .sorted(Comparator.comparing(Empleado::getNombre))
                .collect(Collectors.toList());
		// Mostramos todos los empleados
		String format = "[ %-36s ][ %-20s ][ %-8s ][ %-12s ][ %-55s ]";
		System.out.println(String.format(format, "ID", "NOMBRE", "SALARIO", "NACIDO", "DEPARTAMENTO"));
        empleados.forEach(System.out::println);
	}

	private static void insertEmpleado(EmpresaController controller) {
		// Obtenemos los datos del empleado que se quiere insertar
		String nombre = IO.readString("Nombre ? ");
		Double salario = IO.readDouble("Salario? ");
		LocalDate nacido = IO.readLocalDate("Nacido ? ");
		UUID departamento = IO.readUUIDOptional("Departamento ? ");

		// Comprobamos si existe el departamento
		if (departamento != null && controller.getEmpleadoById(departamento) == null) {
			System.out.println(Colores.ROJO + "No se ha podido insertar el empleado, el departamento con ID: " + departamento + " no existe en la tabla EMPLEADOS" + Colores.ROJO + Colores.RESET);
			return;
		}
				
		// Creamos el empleado y lo insertamos
		Empleado empleado = new Empleado(nombre, salario, nacido);
		if(departamento != null) empleado.setDepartamento(controller.getDepartamentoById(departamento));
						
		// Comprobamos si se ha insertado el registro y damos feedback
		IO.println(controller.createEmpleado(empleado) ? "Insertado correctamente" :
				Colores.ROJO 
				+ "No se ha podido insertar el empleado" 
				+ Colores.RESET);
	}

	private static void updateEmpleado(EmpresaController controller) {
		// Obtenemos empleado que se quiere actualizar
	    UUID id = IO.readUUID("ID ? ");
	    Empleado empleado = controller.getEmpleadoById(id);

	    // Comprobamos si existe ese empleado
	    if (empleado != null) {
	        String nombre = IO.readStringOptional("Nombre ? ");
	        nombre = (nombre.isEmpty()) ? empleado.getNombre() : nombre;
	        
	        Double salario = IO.readDoubleOptional("Salario ? ");
	        salario = (salario == null) ? empleado.getSalario() : salario;
	        
	        LocalDate nacido = IO.readLocalDateOptional("Nacido ? ");
	        nacido = (nacido == null) ? empleado.getNacido() : nacido;

	        UUID departamento = IO.readUUIDOptional("Departamento ? ");
	        
	        // Comprobamos si existe el departamento
			if (departamento != null && controller.getDepartamentoById(departamento) == null) {
				System.out.println(Colores.ROJO + "No se ha podido actualizar el empleado, el departamento con ID: " + departamento + " no existe en la tabla EMPLEADOS" + Colores.ROJO + Colores.RESET);
				return;
			}

	        // Realiza la actualización del departamento
	        boolean actualizado = controller.updateEmpleado(new Empleado(nombre, salario, nacido, controller.getDepartamentoById(departamento)));

	        IO.println(actualizado ? "Actualizado correctamente" : Colores.ROJO +
	                "\nInformación no válida\n" +
	                "Asegúrese de:\n" +
	                "- Haber rellenado al menos 1 campo\n" +
	                "- Que el ID del jefe exista en la tabla empleado" +
	                Colores.RESET);
	    } else {
	        IO.println(Colores.ROJO + "No se ha encontrado un departamento con el ID introducido" + Colores.RESET);
	    }
	}

	private static void deleteEmpleado(EmpresaController controller) {
		UUID id = IO.readUUID("ID ? ");
		Empleado empleado = new Empleado(id);
		
		IO.println(controller.deleteEmpleado(empleado) ? "Eliminado correctamente" :
			Colores.ROJO 
			+ "No se ha encontrado un Empleado con el ID introducido" 
			+ Colores.RESET);
	}
}
