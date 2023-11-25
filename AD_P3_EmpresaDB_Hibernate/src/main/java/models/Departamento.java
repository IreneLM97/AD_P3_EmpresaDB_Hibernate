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
@Entity(name = "departamento")
@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM departamento d")
public class Departamento {

	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	private String nombre;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "jefe_id", nullable = true)
	private Empleado jefe;

	@OneToMany(mappedBy = "departamento", orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@Builder.Default
	private Set<Empleado> empleados = new HashSet<>();

	/** Método que se ejecuta antes de eliminar un registro de Departamento */
	@PreRemove
	public void eliminarDependencias() {
		empleados.forEach(empleado -> empleado.setDepartamento(null));
	}

	@Override
	public boolean equals(Object obj) {
		Departamento departamento = (Departamento) obj;
		return this.id.equals(departamento.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public String toString() {
		String format = "[ %-36s ][ %-20s ][ %-55s ][ %-20s ]";
		String jefeInfo = (jefe != null) ? jefe.getId() + " | " + jefe.getNombre() : "N/A";

		List<String> empleadosList = this.empleados.stream().map(empleado -> empleado.getNombre())
				.collect(Collectors.toList());
		String empleadosInfo = String.join(", ", empleadosList);

		return String.format(format, this.id.toString(), this.nombre, jefeInfo, empleadosInfo);
	}
}