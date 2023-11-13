package models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity(name = "departamento")
@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM departamento d")
public class Departamento {
	
	@Id
	private UUID id = UUID.randomUUID();
	private String nombre;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "jefe", nullable = true)
	private Empleado jefe;
	
	@OneToMany(mappedBy = "departamento", orphanRemoval = false, cascade = CascadeType.ALL)
    private Set<Empleado> empleados = new HashSet<>();
	
	public Departamento(UUID id) {
		setId(id);
	}
	
	public Departamento(String nombre) {
		setNombre(nombre);
	}

	public Departamento(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	public Departamento(String nombre, Empleado jefe) {
		setNombre(nombre);
		setJefe(jefe);
	}
	
	public Departamento(UUID id, String nombre, Empleado jefe) {
		setId(id);
		setNombre(nombre);
		setJefe(jefe);
	}
	
	// TODO MOSTRAR LISTA DE EMPLEADOS ASOCIADOS A ESE DEPARTAMENTO
	@Override
	public String toString() {
	    String format = "[ %-36s ][ %-20s ][ %-55s ]";
	    String jefeInfo = (jefe != null) ? jefe.getId() + " | " + jefe.getNombre() : "N/A";
		return String.format(format, this.id.toString(), this.nombre, jefeInfo);
	}
}