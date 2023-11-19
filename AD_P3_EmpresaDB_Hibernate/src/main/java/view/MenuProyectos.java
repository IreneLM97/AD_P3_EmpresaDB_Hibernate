package view;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import IO.IO;
import constantes.color.Colores;
import controllers.EmpresaController;
import models.Proyecto;

// TODO AÑADIR OPCIONES DE MODIFICAR EMPLEADOS O ETC
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
		// Obtenemos todos los proyectos
		List<Proyecto> proyectos = controller.getProyectos().stream()
                .sorted(Comparator.comparing(Proyecto::getNombre))
                .collect(Collectors.toList());
		// Mostramos todos los proyectos y sus empleados
		String format = "[ %-36s ][ %-20s ][ %-55s ]";
		System.out.println(String.format(format, "ID", "NOMBRE", "EMPLEADOS"));
        proyectos.forEach(System.out::println);
	}

	/**
	 * Método para solicitar campos de un proyecto e insertarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void insertProyecto(EmpresaController controller) {
		// Obtenemos los datos del proyecto que se quiere insertar
		String nombre = IO.readString("Nombre ? ");

		// Creamos el proyecto
		Proyecto proyecto = new Proyecto.Builder().nombre(nombre).build();
				
		// Comprobamos si se ha insertado el registro y damos feedback
		IO.println(controller.createProyecto(proyecto) ? "Insertado correctamente" :
				Colores.ROJO 
				+ "No se ha encontrado un proyecto con el ID introducido" 
				+ Colores.RESET);
	}

	/**
	 * Método para solicitar campos de un proyecto y actualizarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void updateProyecto(EmpresaController controller) {
		// Obtenemos proyecto que se quiere actualizar
		UUID id = IO.readUUID("ID ? ");
		Proyecto proyecto = controller.getProyectoById(id);
		
		// Comprobamos si existe ese proyecto y pedimos el resto de datos
		if(proyecto != null) {
			String nombre = IO.readStringOptional("Nombre ? ");
			nombre = (nombre.isEmpty()) ? proyecto.getNombre() : nombre;

			// Actualizamos el proyecto
			proyecto.setNombre(nombre);

	        IO.println(controller.updateProyecto(proyecto) ? "Actualizado correctamente" : 
	        	Colores.ROJO + "No se ha podido actualizar el proyecto" + Colores.RESET);
	    } else {
	        IO.println(Colores.ROJO + "No se ha encontrado un proyecto con el ID introducido" + Colores.RESET);
	    }
	}

	/**
	 * Método para solicitar un proyecto y eliminarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void deleteProyecto(EmpresaController controller) {
		// Obtenemos el proyecto a eliminar
		UUID id = IO.readUUID("ID ? ");
		Proyecto proyecto = controller.getProyectoById(id);
		
		// proyecto existe
	    if (proyecto != null) {  
	        boolean eliminado = controller.deleteProyecto(proyecto);
	        IO.println(eliminado ? "Proyecto eliminado correctamente" :
	                Colores.ROJO + "No se ha podido eliminar el proyecto" + Colores.RESET);
	        
	    // proyecto no existe   
	    } else {  
	        IO.println(Colores.ROJO + "No se ha encontrado un proyecto con el ID introducido" + Colores.RESET);
	    }
	}
}