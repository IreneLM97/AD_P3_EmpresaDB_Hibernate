package models;

import java.time.LocalDate;
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
@Entity(name = "empleado")
@NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM empleado e")
@NamedQuery(name = "Empleado.findAllWithDepartamentoInfo", query = "SELECT e, d.id, d.nombre FROM empleado e LEFT JOIN e.departamento d")
public class Empleado {

	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	private String nombre;
	private Double salario;
	private LocalDate nacido;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "departamento_id", nullable = true)
	private Departamento departamento;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "empleado_proyecto", joinColumns = @JoinColumn(name = "empleado_id"), inverseJoinColumns = @JoinColumn(name = "proyecto_id"))
	@Builder.Default
	private Set<Proyecto> proyectos = new HashSet<>();

	/** Método que se ejecuta antes de eliminar un registro de Empleado */
	@PreRemove
	public void eliminarDependencias() {
		// eliminamos dependencias con relación con departamento
		if (this.departamento != null) {
			departamento.getEmpleados().remove(this); // lo eliminamos de la lista de empleados de ese departamento
		
			if(departamento.getJefe().getId() == this.id) { // comprobamos si es jefe del departamento
				departamento.setJefe(null);
			}
			
			this.setDepartamento(null);  // seteamos su departamento a null
		}

		// eliminamos dependencias con relación con proyecto
		this.proyectos.stream().forEach(proyecto -> proyecto.getEmpleados().remove(this));
		this.proyectos.clear();
	}

	@Override
	public boolean equals(Object obj) {
		Empleado empleado = (Empleado) obj;
		return this.id.equals(empleado.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		result = prime * result + ((nacido == null) ? 0 : nacido.hashCode());
		return result;
	}

	@Override
	public String toString() {
		String format = "[ %-36s ][ %-20s ][ %-8s ][ %-12s ][ %-55s ][ %-55s ]";
		String departamentoInfo = (departamento != null) ? departamento.getId() + " | " + departamento.getNombre()
				: "N/A";

		List<String> proyectosList = this.proyectos.stream().map(proyecto -> proyecto.getNombre())
				.collect(Collectors.toList());
		String proyectosInfo = String.join(", ", proyectosList);

		return String.format(format, this.id.toString(), this.nombre, salario, nacido, departamentoInfo, proyectosInfo);
	}
}
