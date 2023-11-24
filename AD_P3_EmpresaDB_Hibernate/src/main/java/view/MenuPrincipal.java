package view;

import java.util.List;
import java.util.function.Consumer;

import IO.IO;
import constantes.color.Colores;
import controllers.EmpresaController;
import dao.departamento.DaoDepartamentoImpl;
import dao.empleado.DaoEmpleadoImpl;
import dao.proyecto.DaoProyectoImpl;
import db.DataDB;
import db.HibernateManager;

import java.util.logging.Logger;

/**
 * Clase para mostrar menú principal.
 */
@SuppressWarnings("unused")
public class MenuPrincipal {

	static Logger logger = Logger.getLogger(MenuPrincipal.class.getName());

	// Creamos nuestro controlador y le añadimos y le inyectamos las dependencias
	public static EmpresaController controller = new EmpresaController(
			new DaoDepartamentoImpl(), 
			new DaoEmpleadoImpl(),
			new DaoProyectoImpl());

	public static void main(String[] args) {
		// Iniciamos la base de datos
		initDataBase();

		List<String> opciones = List.of(
				"\n ========|MENU PRINCIPAL|========\n", 
				"| 1.- Gestionar Departamentos	 |\n",
				"| 2.- Gestionar Empleados        |\n", 
				"| 3.- Gestionar Proyectos        |\n",
				"| 4.- Salir			 |\n", 
				" ================================\n");

		while (true) {
			opciones.stream().forEach(System.out::print);
			switch (IO.readInt("\nIntroduce tu elección: ")) {
			case 1: // menú departamentos
				MenuDepartamentos.mostrarMenu(controller);
				break;
			case 2: // menú empleados
				MenuEmpleados.mostrarMenu(controller);
				break;
			case 3: // salir del menú
				MenuProyectos.mostrarMenu(controller);
				break;
			case 4: // salir del menú
				System.exit(1);
				break;
			default:
				IO.println(Colores.ROJO + "Opción no válida" + Colores.RESET);
			}
		}
	}

	/**
	 * Método para iniciar la base de datos.
	 */
	private static void initDataBase() {
		HibernateManager hb = HibernateManager.getInstance();

		// ==|COMENTAR O DESCOMENTAR PARA INSERTAR DATOS POR DEFECTO AL ARRANCAR LA
		// APLICACION==|
//        clearAndInitializeEntities(controller.getDepartamentos(), controller::deleteDepartamento, DataDB.getDepartamentoInit(), controller::createDepartamento);
//        clearAndInitializeEntities(controller.getEmpleados(), controller::deleteEmpleado, DataDB.getEmpleadoInit(), controller::createEmpleado);
//        clearAndInitializeEntities(controller.getProyectos(), controller::deleteProyecto, DataDB.getProyectoInit(), controller::createProyecto);

		hb.open();
		hb.close();
	}

	/**
	 * Método para vaciar e iniciar la base de datos.
	 * 
	 * @param <T>
	 * @param entities
	 * @param deleteFunction
	 * @param initData
	 * @param createFunction
	 */
	private static <T> void clearAndInitializeEntities(List<T> entities, Consumer<T> deleteFunction, List<T> initData,
			Consumer<T> createFunction) {
		entities.stream().forEach(deleteFunction);
		initData.forEach(createFunction);
	}
}
