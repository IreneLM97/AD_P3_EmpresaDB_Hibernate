package models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "proyecto")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT d FROM proyecto d")
public class Proyecto {

	@Id
	private UUID id = UUID.randomUUID();
	private String nombre;
	
	public Proyecto(UUID id) {
		setId(id);
	}
	
	public Proyecto(String nombre) {
		setNombre(nombre);
	}

	public Proyecto(UUID id, String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	@Override
	public String toString() {
	    String format = "[ %-36s ][ %-20s ]";
		return String.format(format, this.id.toString(), this.nombre);
	}
}
