package models;

import java.time.LocalDate;
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
    private UUID id;
    private String nombre;
    private Double salario;
    private LocalDate nacido;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "departamento_id", nullable = true)
    private Departamento departamento;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "empleado_proyecto",
        joinColumns = @JoinColumn(name = "empleado_id"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectos = new HashSet<>();
	
    // Constructor privado para la clase Empleado que toma un Builder
    private Empleado(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.salario = builder.salario;
        this.nacido = builder.nacido;
        this.departamento = builder.departamento;
        this.proyectos = builder.proyectos;
    }
	
	@Override
	public boolean equals(Object obj) {
		Empleado empleado = (Empleado) obj;
        return id.equals(empleado.id);
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
//		return Objects.hash(id, nombre, salario, nacido, departamento, proyectos);
	}
	
	@Override
	public String toString() {
		String format = "[ %-36s ][ %-20s ][ %-8s ][ %-12s ][ %-55s ]";
		String departamentoInfo = (departamento != null) ? departamento.getId() + " | " + departamento.getNombre() : "N/A";
	    return String.format(format, this.id.toString(), this.nombre, salario, nacido, departamentoInfo);
	}
	
	/**
     * Clase Builder para crear instancias de Empleado.
     */
    public static class Builder {
        private UUID id = UUID.randomUUID(); // inicializamos UUID
        private String nombre;
        private Double salario;
        private LocalDate nacido;
        private Departamento departamento;
        private Set<Proyecto> proyectos = new HashSet<>();

        /**
         * Establece el ID para el Empleado que se está construyendo.
         * 
         * @param id ID a establecer para el Empleado
         * @return Instancia del Builder
         */
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Establece el nombre para el Empleado que se está construyendo.
         * 
         * @param nombre Nombre a establecer para el Empleado
         * @return Instancia del Builder
         */
        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        /**
         * Establece el salario para el Empleado que se está construyendo.
         * 
         * @param salario Salario a establecer para el Empleado
         * @return Instancia del Builder
         */
        public Builder salario(Double salario) {
            this.salario = salario;
            return this;
        }

        /**
         * Establece la fecha de nacimiento para el Empleado que se está construyendo.
         * 
         * @param nacido Fecha de nacimiento a establecer para el Empleado
         * @return Instancia del Builder
         */
        public Builder nacido(LocalDate nacido) {
            this.nacido = nacido;
            return this;
        }

        /**
         * Establece el departamento para el Empleado que se está construyendo.
         * 
         * @param departamento Departamento a establecer para el Empleado
         * @return Instancia del Builder
         */
        public Builder departamento(Departamento departamento) {
            this.departamento = departamento;
            return this;
        }

        /**
         * Establece los proyectos para el Empleado que se está construyendo.
         * 
         * @param proyectos Proyectos a establecer para el Empleado.
         * @return Instancia del Builder
         */
        public Builder proyectos(Set<Proyecto> proyectos) {
            this.proyectos = proyectos;
            return this;
        }

        /**
         * Construye el Empleado con los atributos configurados.
         * 
         * @return Instancia construida de Empleado
         */
        public Empleado build() {
            return new Empleado(this);
        }
    }
}


