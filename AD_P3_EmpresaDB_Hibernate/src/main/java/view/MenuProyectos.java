package view;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import IO.IO;
import constantes.color.Colores;
import controllers.EmpresaController;
import models.Proyecto;

public class MenuProyectos {
	public static void mostrarMenu(EmpresaController controller) {

		List<String> opciones = List.of("\n ======|MENU DEPARTAMENTOS|=====\n", 
										"| 1.- Listar Proyecto		|\n",
										"| 2.- Agregar Proyecto		|\n", 
										"| 3.- Modificar Proyecto    	|\n",
										"| 4.- Eliminar Proyecto     	|\n", 
										"| 5.- Volver al menu principal  |\n",
										" ===============================\n");

		while (true) {
			opciones.stream().forEach(System.out::print);
			IO.print("\nIntroduce tu elección: ");
			switch (IO.readInt()) {
			case 1:
				listarProyectos(controller);
				break;
			case 2:
				insertProyecto(controller);
				break;
			case 3:
				updateProyecto(controller);
				break;
			case 4:
				deleteProyecto(controller);
				break;
			case 5:
				MenuPrincipal.main(null);
				break;
			default:
				IO.println(Colores.ROJO + "Opción no válida" + Colores.RESET);
			}
		}
	}

	/**
	 * Método para listar los proyectos al usuario.
	 * 
	 * @param controller
	 */
	private static void listarProyectos(EmpresaController controller) {
		// Obtenemos todos los departamentos
		List<Proyecto> proyectos = controller.getProyectos().stream()
                .sorted(Comparator.comparing(Proyecto::getNombre))
                .collect(Collectors.toList());
		// Mostramos todos los departamentos
		String format = "[ %-36s ][ %-20s ]";
		System.out.println(String.format(format, "ID", "NOMBRE"));
        proyectos.forEach(System.out::println);
	}

	/**
	 * Método para solicitar campos de un proyecto e insertarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void insertProyecto(EmpresaController controller) {
		String nombre = IO.readString("Nombre ? ");

		Proyecto proyecto = new Proyecto(nombre);
				
		// Comprobamos si se ha insertado el registro y damos feedback
		IO.println(controller.createProyecto(proyecto) ? "Insertado correctamente" :
				Colores.ROJO 
				+ "No se ha encontrado un proyecto con el ID introducido" 
				+ Colores.RESET);
	}

	private static void updateProyecto(EmpresaController controller) {
		// Obtenemos los datos del proyecto que se quiere modificar
				IO.print("ID ? ");
				UUID id = IO.readUUID();
				IO.print("Nombre ? ");
				String nombre = IO.readStringOptional();

				// Creamos el proyecto y lo modificamos
				Proyecto proyecto = new Proyecto(id, nombre);
				IO.println(controller.updateProyecto(proyecto) ? "Actualizado correctamente"
						: Colores.ROJO 
						+ "\nRegistro no encontrado o Información no válida\n" 
						+ "Asegúrese de:\n"
						+ "- Haber rellenado al menos 1 campo\n"
						+ "- Que el ID del proyecto a modificar exista en la tabla proyecto\n"
						+ "- Que el ID del empleado exista en la tabla empleado" 
						+ Colores.RESET);
	}

	private static void deleteProyecto(EmpresaController controller) {
		UUID id = IO.readUUID("ID ? ");
		Proyecto proyecto = new Proyecto(id);
		
		IO.println(controller.deleteProyecto(proyecto) ? "Eliminado correctamente" :
			Colores.ROJO 
			+ "No se ha encontrado un Proyecto con el ID introducido" 
			+ Colores.RESET);
	}
}