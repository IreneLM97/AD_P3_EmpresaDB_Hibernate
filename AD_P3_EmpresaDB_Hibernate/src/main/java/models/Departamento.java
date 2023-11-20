package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "departamento")
@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM departamento d")
public class Departamento {
	
	@Id
    private UUID id;
    private String nombre;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "jefe_id", nullable = true)
    private Empleado jefe;

    @OneToMany(mappedBy = "departamento", orphanRemoval = false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Empleado> empleados = new HashSet<>();
    
    // Constructor privado para la clase Departamento que toma un Builder
    private Departamento(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.jefe = builder.jefe;
        this.empleados = builder.empleados;
    }
	
	@PreRemove
	public void nullificarEmpleados() {
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
//		return Objects.hash(id, nombre, jefe, empleados);
	}
	
	@Override
	public String toString() {
	    String format = "[ %-36s ][ %-20s ][ %-55s ][ %-20s ]";
	    String jefeInfo = (jefe != null) ? jefe.getId() + " | " + jefe.getNombre() : "N/A";
	    
	    List<String> empleadosList = this.empleados.stream()
	            .map(empleado -> empleado.getNombre())
	            .collect(Collectors.toList());
	    String empleadosInfo = String.join(", ", empleadosList);
	    
		return String.format(format, this.id.toString(), this.nombre, jefeInfo, empleadosInfo);
	}
	
	/**
	 * Clase Builder para crear instancias de Departamento.
	 */
	public static class Builder {
	    private UUID id = UUID.randomUUID(); // inicializamos UUID
	    private String nombre;
	    private Empleado jefe;
	    private Set<Empleado> empleados = new HashSet<>();

	    /**
         * Establece el ID para el Departamento que se está construyendo.
         * 
         * @param id ID a establecer para el Departamento
         * @return Instancia del Builder
         */
	    public Builder id(UUID id) {
	        this.id = id; // Asigna el UUID proporcionado
	        return this;
	    }

	    /**
         * Establece el nombre para el Departamento que se está construyendo.
         * 
         * @param nombre Nombre a establecer para el Departamento
         * @return Instancia del Builder
         */
	    public Builder nombre(String nombre) {
	        this.nombre = nombre;
	        return this;
	    }

	    /**
         * Establece el jefe para el Departamento que se está construyendo.
         * 
         * @param jefe Jefe a establecer para el Departamento
         * @return Instancia del Builder
         */
	    public Builder jefe(Empleado jefe) {
	        this.jefe = jefe;
	        return this;
	    }

	    /**
         * Establece los empleados para el Departamento que se está construyendo.
         * 
         * @param empleados Empleados a establecer para el Departamento
         * @return Instancia del Builder
         */
	    public Builder empleados(Set<Empleado> empleados) {
	        this.empleados = empleados;
	        return this;
	    }

	    /**
         * Construye el Departamento con los atributos configurados.
         * 
         * @return Instancia construida de Departamento
         */
	    public Departamento build() {
	        return new Departamento(this); 
	    }
	}
}