package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PreRemove;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "proyecto")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT d FROM proyecto d")
public class Proyecto {

	@Id
	private UUID id;
	private String nombre;

	@ManyToMany(mappedBy = "proyectos", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Set<Empleado> empleados = new HashSet<>();

	/** Constructor privado para la clase Proyecto que toma un Builder */
	private Proyecto(Builder builder) {
		this.id = builder.id;
		this.nombre = builder.nombre;
		this.empleados = builder.empleados;
	}

	/** Método que se ejecuta antes de eliminar un registro de Proyecto */
	@PreRemove
	public void eliminarDependencias() {
		this.empleados.stream().forEach(empleado -> empleado.getProyectos().remove(this));
		this.empleados.clear();
	}

	@Override
	public boolean equals(Object obj) {
		Proyecto proyecto = (Proyecto) obj;
		return this.id.equals(proyecto.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((empleados == null) ? 0 : empleados.hashCode());
		return result;
	}

	@Override
	public String toString() {
		String format = "[ %-36s ][ %-20s ][ %-55s ]";

		List<String> empleadosList = this.empleados.stream().map(empleado -> empleado.getNombre())
				.collect(Collectors.toList());
		String empleadosInfo = String.join(", ", empleadosList);

		return String.format(format, this.id.toString(), this.nombre, empleadosInfo);
	}

	/**
	 * Clase Builder para crear instancias de Proyecto.
	 */
	public static class Builder {
		private UUID id = UUID.randomUUID(); // inicializamos UUID
		private String nombre;
		private Set<Empleado> empleados = new HashSet<>();

		/**
		 * Establece el ID para el Proyecto que se está construyendo.
		 * 
		 * @param id ID a establecer para el Proyecto
		 * @return Instancia del Builder
		 */
		public Builder id(UUID id) {
			this.id = id;
			return this;
		}

		/**
		 * Establece el nombre para el Proyecto que se está construyendo.
		 * 
		 * @param nombre Nombre a establecer para el Proyecto
		 * @return Instancia del Builder
		 */
		public Builder nombre(String nombre) {
			this.nombre = nombre;
			return this;
		}

		/**
		 * Establece los empleados para el Proyecto que se está construyendo.
		 * 
		 * @param empleados Empleados a establecer para el Proyecto
		 * @return Instancia del Builder
		 */
		public Builder empleados(Set<Empleado> empleados) {
			this.empleados = empleados;
			return this;
		}

		/**
		 * Construye el Proyecto con los atributos configurados.
		 * 
		 * @return Instancia construida de Proyecto
		 */
		public Proyecto build() {
			return new Proyecto(this);
		}
	}
}
