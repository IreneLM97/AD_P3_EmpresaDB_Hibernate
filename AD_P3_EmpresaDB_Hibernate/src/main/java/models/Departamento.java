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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "jefe_id", nullable = true)
    private Empleado jefe;

    @OneToMany(mappedBy = "departamento", orphanRemoval = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Empleado> empleados = new HashSet<>();
	
    // TODO INTENTAR OPTIMIZAR CONSTRUCTORES CON BUILDER
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
	
	@PreRemove
	public void nullificarEmpleados() {
		empleados.forEach(empleado -> empleado.setDepartamento(null));
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
	    // No incluir la referencia a empleados en el cálculo del hash
	    return result;
	}
	
	// TODO MOSTRAR LISTA DE EMPLEADOS ASOCIADOS A ESE DEPARTAMENTO
	@Override
	public String toString() {
	    String format = "[ %-36s ][ %-20s ][ %-55s ]";
	    String jefeInfo = (jefe != null) ? jefe.getId() + " | " + jefe.getNombre() : "N/A";
		return String.format(format, this.id.toString(), this.nombre, jefeInfo);
	}
}