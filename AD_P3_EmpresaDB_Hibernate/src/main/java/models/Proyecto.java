package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "proyecto")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT d FROM proyecto d")
public class Proyecto {

	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	private String nombre;

	@ManyToMany(mappedBy = "proyectos", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@Builder.Default
	private Set<Empleado> empleados = new HashSet<>();

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
}
