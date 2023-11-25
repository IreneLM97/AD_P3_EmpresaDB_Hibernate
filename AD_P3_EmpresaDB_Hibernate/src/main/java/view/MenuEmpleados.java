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
import models.Proyecto;

/**
 * Clase para mostrar menú de Empleados.
 */
public class MenuEmpleados {
	public static void mostrarMenu(EmpresaController controller) {

		List<String> opciones = List.of(
				"\n =========|MENU EMPLEADOS|==========\n", 
				"| 1.- Listar Empleados	            |\n",
				"| 2.- Agregar Empleado	            |\n",
				"| 3.- Modificar Empleado 	    |\n",
				"| 4.- Añadir Empleado a Proyecto    |\n",
				"| 5.- Eliminar Empleado de Proyecto |\n",
				"| 6.- Eliminar Empleado	            |\n",
				"| 7.- Volver al menu principal      |\n", 
				" ===================================\n");

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
					addToProyecto(controller);
					break;
				case 5:
					deleteFromProyecto(controller);
					break;
				case 6:
					deleteEmpleado(controller);
					break;
				case 7:
					MenuPrincipal.main(null);
					break;
				default:
					IO.println(Colores.ROJO + "Opción no válida" + Colores.RESET);
			}
		}
	}

	/**
	 * Método para listar los empleados al usuario.
	 * 
	 * @param controller
	 */
	private static void listarEmpleados(EmpresaController controller) {
		// Obtenemos todos los empleados
		List<Empleado> empleados = controller.getEmpleados().stream()
                .sorted(Comparator.comparing(Empleado::getNombre))
                .collect(Collectors.toList());
		
		// Mostramos todos los empleados y sus proyectos
		String format = "[ %-36s ][ %-20s ][ %-8s ][ %-12s ][ %-55s ][ %-55s ]";
		System.out.println(String.format(format, "ID", "NOMBRE", "SALARIO", "NACIDO", "DEPARTAMENTO", "PROYECTOS"));
        empleados.forEach(System.out::println);
	}

	/**
	 * Método para solicitar campos de un empleado e insertarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void insertEmpleado(EmpresaController controller) {
		// Obtenemos los datos del empleado que se quiere insertar
		String nombre = IO.readString("Nombre ? ");
		Double salario = IO.readDouble("Salario? ");
		LocalDate nacido = IO.readLocalDate("Nacido ? ");
		UUID departamento = IO.readUUIDOptional("Departamento ? ");

		// Comprobamos si existe el departamento
		if (departamento != null && controller.getDepartamentoById(departamento) == null) {
			System.out.println(Colores.ROJO + "No se ha podido insertar el empleado, el departamento con ID: " + departamento + " no existe en la tabla DEPARTAMENTOS" + Colores.ROJO + Colores.RESET);
			return;
		}
				
		// Creamos el empleado
		Empleado empleado = Empleado.builder().nombre(nombre).salario(salario).nacido(nacido).build();
		if(departamento != null) empleado.setDepartamento(controller.getDepartamentoById(departamento));
						
		// Comprobamos si se ha insertado el registro y damos feedback
		IO.println(controller.createEmpleado(empleado) ? "Insertado correctamente" :
				Colores.ROJO + "No se ha podido insertar el empleado" + Colores.RESET);
	}

	/**
	 * Método para solicitar campos de un empleado y actualizarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void updateEmpleado(EmpresaController controller) {
		// Obtenemos empleado que se quiere actualizar
	    UUID id = IO.readUUID("ID ? ");
	    Empleado empleado = controller.getEmpleadoById(id);

	    // Comprobamos si existe ese empleado y pedimos el resto de datos
	    if (empleado != null) {
	        String nombre = IO.readStringOptional("Nombre ? ");
	        nombre = (nombre.isEmpty()) ? empleado.getNombre() : nombre;
	        
	        Double salario = IO.readDoubleOptional("Salario ? ");
	        salario = (salario == null) ? empleado.getSalario() : salario;
	        
	        LocalDate nacido = IO.readLocalDateOptional("Nacido ? ");
	        nacido = (nacido == null) ? empleado.getNacido() : nacido;

	        UUID departamento_id = IO.readUUIDOptional("Departamento ? ");
	        // Comprobamos si existe el departamento
			if (departamento_id != null && controller.getDepartamentoById(departamento_id) == null) {
				System.out.println(Colores.ROJO + "No se ha podido actualizar el empleado, el departamento con ID: " + departamento_id + " no existe en la tabla DEPARTAMENTOS" + Colores.ROJO + Colores.RESET);
				return;
			}
			// Establecemos el departamento del empleado
			Departamento departamento = (departamento_id == null) ? empleado.getDepartamento() : controller.getDepartamentoById(departamento_id);

	        // Actualizamos el empleado
			empleado.setNombre(nombre);
			empleado.setSalario(salario);
			empleado.setNacido(nacido);
			empleado.setDepartamento(departamento);
			
			IO.println(controller.updateEmpleado(empleado) ? "Actualizado correctamente" : 
	        	Colores.ROJO + "No se ha podido actualizar el empleado" + Colores.RESET);
	    } else {
	        IO.println(Colores.ROJO + "No se ha encontrado un empleado con el ID introducido" + Colores.RESET);
	    }
	}
	
	/**
	 * Método para añadir un empleado a un proyecto.
	 * 
	 * @param controller
	 */
	private static void addToProyecto(EmpresaController controller) {
		// Obtenemos empleado que se quiere añadir al proyecto
	    UUID id_empleado = IO.readUUID("ID del Empleado ? ");
	    Empleado empleado = controller.getEmpleadoById(id_empleado);
	    
	    // Obtenemos el proyecto al que se quiere añadir el empleado
	    UUID id_proyecto = IO.readUUID("ID del Proyecto ? ");
	    Proyecto proyecto = controller.getProyectoById(id_proyecto);
	    
	    // Comprobamos si existen empleado y proyecto
	    if(empleado == null || proyecto == null) {
	    	IO.println(Colores.ROJO + "No se puede realizar la operación porque no existe empleado o proyecto" + Colores.RESET);
	    	return;
	    }
	    
	    // Comprobamos si el empleado ya está en ese proyecto
	    if(empleado.getProyectos().contains(proyecto)) {
	    	IO.println(Colores.ROJO + "No se puede añadir porque el empleado ya está en ese proyecto" + Colores.RESET);
	    	return;
	    }
	    
	    // Realizamos la operación
	    IO.println(controller.addToProyecto(empleado, proyecto) ? "Se ha añadido correctamente el empleado al proyecto" : 
        	Colores.ROJO + "No se ha podido añadir el empleado al proyecto" + Colores.RESET);
	}
	
	/**
	 * Método para eliminar un empleado de un proyecto.
	 * 
	 * @param controller
	 */
	private static void deleteFromProyecto(EmpresaController controller) {
		// Obtenemos empleado que se quiere eliminar del proyecto
	    UUID id_empleado = IO.readUUID("ID del Empleado ? ");
	    Empleado empleado = controller.getEmpleadoById(id_empleado);
	    
	    // Obtenemos el proyecto del que se quiere eliminar el empleado
	    UUID id_proyecto = IO.readUUID("ID del Proyecto ? ");
	    Proyecto proyecto = controller.getProyectoById(id_proyecto);
	    
	    // Comprobamos si existen empleado y proyecto
	    if(empleado == null || proyecto == null) {
	    	IO.println(Colores.ROJO + "No se puede realizar la operación porque no existe empleado o proyecto" + Colores.RESET);
	    	return;
	    }
	    
	    // Comprobamos si el empleado no está en ese proyecto
	    if(!empleado.getProyectos().contains(proyecto)) {
	    	IO.println(Colores.ROJO + "No se puede eliminar porque el empleado no está en ese proyecto" + Colores.RESET);
	    	return;
	    }
	    
	    // Realizamos la operación
	    IO.println(controller.deleteFromProyecto(empleado, proyecto) ? "Se ha eliminado correctamente el empleado del proyecto" : 
        	Colores.ROJO + "No se ha podido eliminar el empleado del proyecto" + Colores.RESET);
	}

	/**
	 * Método para solicitar un empleado y eliminarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void deleteEmpleado(EmpresaController controller) {
		// Obtenemos el empleado a eliminar
		UUID id = IO.readUUID("ID ? ");
		Empleado empleado = controller.getEmpleadoById(id);
		
		// empleado existe
	    if (empleado != null) {  
	        boolean eliminado = controller.deleteEmpleado(empleado);
	        IO.println(eliminado ? "Empleado eliminado correctamente" :
	                Colores.ROJO + "No se ha podido eliminar el empleado" + Colores.RESET);
	        
	    // empleado no existe   
	    } else {  
	        IO.println(Colores.ROJO + "No se ha encontrado un empleado con el ID introducido" + Colores.RESET);
	    }
	}
}
