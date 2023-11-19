package models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "empleado")
@NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM empleado e")
@NamedQuery(name = "Empleado.findAllWithDepartamentoInfo", query = "SELECT e, d.id, d.nombre FROM empleado e LEFT JOIN e.departamento d")
public class Empleado {
	
	@Id
    private UUID id = UUID.randomUUID();
    private String nombre;
    private Double salario;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id", nullable = true)
    private Departamento departamento;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "empleado_proyecto",
        joinColumns = @JoinColumn(name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectos = new HashSet<>();
	
	public Empleado(UUID id, String nombre, Double salario, Departamento departamento) {
		setId(id);
		setNombre(nombre);
		setSalario(salario);
		setDepartamento(departamento);
	}
	
	public Empleado(String nombre, Double salario, Departamento departamento) {
		setNombre(nombre);
		setSalario(salario);
		setDepartamento(departamento);
	}
	
	public Empleado(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	public Empleado(UUID id, Departamento departamento) {
		setId(id);
		setDepartamento(departamento);
	}
	
	public Empleado(UUID id) {
		setId(id);
	}
	
	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null) ? 0 : id.hashCode());
	    result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
	    result = prime * result + ((salario == null) ? 0 : salario.hashCode());
	    // No incluir la referencia a proyectos y departamento en el cálculo del hash
	    return result;
	}
	
	@Override
	public String toString() {
		String format = "[ %-36s ][ %-20s ][ %-8s ][ %-55s ]";
		String salarioStr = (salario != null) ? Double.toString(this.salario) : "N/A";
		String departamentoInfo = (departamento != null) ? departamento.getId() + " | " + departamento.getNombre() : "N/A";
	    return String.format(format, this.id.toString(), this.nombre, salarioStr, departamentoInfo);
	}
}
