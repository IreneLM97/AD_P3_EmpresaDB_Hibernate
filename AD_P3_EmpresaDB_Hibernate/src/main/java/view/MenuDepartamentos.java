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

public class MenuDepartamentos {
	public static void mostrarMenu(EmpresaController controller) {

		List<String> opciones = List.of("\n ======|MENU DEPARTAMENTOS|=====\n", "| 1.- Listar Departamentos	|\n",
				"| 2.- Agregar Departamento	|\n", "| 3.- Modificar Departamento    |\n",
				"| 4.- Eliminar Departamento     |\n", "| 5.- Volver al menu principal  |\n",
				" ===============================\n");

		while (true) {
			opciones.stream().forEach(System.out::print);
			IO.print("\nIntroduce tu elección: ");
			switch (IO.readInt()) {
			case 1:
				listarDepartamentos(controller);
				break;
			case 2:
				insertDepartamento(controller);
				break;
			case 3:
				updateDepartamento(controller);
				break;
			case 4:
				deleteDepartamento(controller);
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
	 * Método para listar los departamentos al usuario.
	 * 
	 * @param controller
	 */
	private static void listarDepartamentos(EmpresaController controller) {
	    // Obtenemos todos los departamentos
	    List<Departamento> departamentos = controller.getDepartamentos().stream()
	            .sorted(Comparator.comparing(Departamento::getNombre))
	            .collect(Collectors.toList());

	    // Mostramos todos los departamentos y sus empleados
	    String format = "[ %-36s ][ %-20s ][ %-55s ][ %-20s ]";
	    System.out.println(String.format(format, "ID", "NOMBRE", "JEFE", "EMPLEADOS"));

	    departamentos.forEach(departamento -> {
	    	UUID jefeId = (departamento.getJefe() != null) ? departamento.getJefe().getId() : null;
	        // Obtener la lista de empleados del departamento
	        List<String> empleados = departamento.getEmpleados().stream()
	                .map(empleado -> controller.getEmpleadoById(empleado.getId()).getNombre())
	                .collect(Collectors.toList());
	        System.out.println(String.format(format,
	                departamento.getId(),
	                departamento.getNombre(),
	                jefeId,
	                String.join(", ", empleados)));
	    });
	}

	/**
	 * Método para solicitar campos de un departamento e insertarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void insertDepartamento(EmpresaController controller) {
		// Obtenemos los datos del departamento que se quiere insertar
		String nombre = IO.readString("Nombre ? ");
		UUID jefe = IO.readUUIDOptional("Jefe ? ");

		// Comprobamos si existe el jefe
		if (jefe != null && controller.getEmpleadoById(jefe) == null) {
			System.out.println(Colores.ROJO + "No se ha podido insertar el departamento, el jefe con ID: " + jefe + " no existe en la tabla EMPLEADOS" + Colores.ROJO + Colores.RESET);
			return;
		}
		
		// Creamos el departamento 
		Departamento departamento = new Departamento.Builder().nombre(nombre).build();
		if(jefe != null) departamento.setJefe(controller.getEmpleadoById(jefe));
				
		// Comprobamos si se ha insertado el registro y damos feedback
		IO.println(controller.createDepartamento(departamento) ? "Insertado correctamente" :
				Colores.ROJO 
				+ "No se ha podido insertar el departamento" 
				+ Colores.RESET);
	}

	/**
	 * Método para solicitar campos de un departamento y actualizarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void updateDepartamento(EmpresaController controller) {
		// Obtenemos departamento que se quiere actualizar
	    UUID id = IO.readUUID("ID ? ");
	    Departamento departamento = controller.getDepartamentoById(id);

	    // Comprobamos si existe ese departamento y pedimos el resto de datos
	    if (departamento != null) {
	    	// Establecemos el nombre del departamento
	        String nombre = IO.readStringOptional("Nombre ? ");
	        nombre = (nombre.isEmpty()) ? departamento.getNombre() : nombre;

	        UUID jefe_id = IO.readUUIDOptional("Jefe ? ");
	        // Comprobamos si existe el jefe
			if (jefe_id != null && controller.getEmpleadoById(jefe_id) == null) {
				System.out.println(Colores.ROJO + "No se ha podido actualizar el departamento, el jefe con ID: " + jefe_id + " no existe en la tabla EMPLEADOS" + Colores.ROJO + Colores.RESET);
				return;
			}
			// Establecemos el jefe del departamento
			Empleado jefe = (jefe_id == null) ? departamento.getJefe() : controller.getEmpleadoById(jefe_id);

	        // Actualizamos el departamento
	        departamento.setNombre(nombre);
	        departamento.setJefe(jefe);
	        
	        IO.println(controller.updateDepartamento(departamento) ? "Actualizado correctamente" : 
	        	Colores.ROJO + "No se ha podido actualizar el departamento" + Colores.RESET);
	    } else {
	        IO.println(Colores.ROJO + "No se ha encontrado un departamento con el ID introducido" + Colores.RESET);
	    }
	}

	/**
	 * Método para solicitar un departamento y eliminarlo en la base de datos.
	 * 
	 * @param controller
	 */
	private static void deleteDepartamento(EmpresaController controller) {
		// Obtenemos el departamento a eliminar
	    UUID id = IO.readUUID("ID ? ");
	    Departamento departamento = controller.getDepartamentoById(id);
	    
	    // departamento existe
	    if (departamento != null) {  
	        boolean eliminado = controller.deleteDepartamento(departamento);
	        IO.println(eliminado ? "Departamento eliminado correctamente" :
	                Colores.ROJO + "No se ha podido eliminar el departamento" + Colores.RESET);
	        
	    // departamento no existe   
	    } else {  
	        IO.println(Colores.ROJO + "No se ha encontrado un departamento con el ID introducido" + Colores.RESET);
	    }
	}
}