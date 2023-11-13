package view;

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
		String format = "[ %-36s ][ %-20s ][ %-8s ][ %-55s ]";
		System.out.println(String.format(format, "ID", "NOMBRE", "SALARIO", "DEPARTAMENTO"));
        empleados.forEach(System.out::println);
	}

	private static void insertEmpleado(EmpresaController controller) {
		// Obtenemos los datos del departamento que se quiere insertar
				String nombre = IO.readString("Nombre ? ");
				Double salario = IO.readDoubleOptional("Salario?: ");
				UUID departamento = IO.readUUIDOptional("Departamento ? ");

				// Creamos el departamento y lo insertamos
				Empleado empleado = new Empleado(nombre, salario, new Departamento(departamento));
						
				// Comprobamos si se ha insertado el registro y damos feedback
				IO.println(controller.createEmpleado(empleado) ? "Insertado correctamente" :
						Colores.ROJO 
						+ "No se ha encontrado un empleado con el ID introducido" 
						+ Colores.RESET);
	}

	private static void updateEmpleado(EmpresaController controller) {
		// Obtenemos los datos del departamento que se quiere modificar
		IO.print("ID ? ");
		UUID id = IO.readUUID();
		IO.print("Nombre ? ");
		String nombre = IO.readStringOptional();
		IO.print("Salario ? ");
		Double salario = IO.readDoubleOptional();
		IO.print("Departamento ? ");
		UUID departamento = IO.readUUIDOptional();

		// Creamos el departamento y lo modificamos
		Empleado empleado = new Empleado(id, nombre, salario, new Departamento(departamento));
		IO.println(controller.updateEmpleado(empleado) ? "Actualizado correctamente"
				: Colores.ROJO 
				+ "\nRegistro no encontrado o Información no válida\n" 
				+ "Asegúrese de:\n"
				+ "- Haber rellenado al menos 1 campo\n"
				+ "- Que el ID del empleado a modificar exista en la tabla empleado\n"
				+ "- Que el ID del departamento exista en la tabla departamento" 
				+ Colores.RESET);
	}

	private static void deleteEmpleado(EmpresaController controller) {
		UUID id = IO.readUUID("ID:  ? ");
		Empleado empleado = new Empleado(id);
		
		IO.println(controller.deleteEmpleado(empleado) ? "Eliminado correctamente" :
			Colores.ROJO 
			+ "No se ha encontrado un Empleado con el ID introducido" 
			+ Colores.RESET);
	}
}
