package IO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.UUID;

/**
 * <p>
 * Clase estática para leer de teclado con comprobación de tipo de datos y escribir en pantalla.
 * </p>
 * 
 * <p>
 * Los tipos de dato que maneja son:
 * </p>
 * 
 * <ul>
 * <li>entero (int)
 * <li>decimal (double)
 * <li>caracter (char)
 * <li>byte
 * <li>short
 * <li>int
 * <li>long
 * <li>float
 * <li>double
 * <li>boolean (true, false)
 * <li>char
 * <li>String (admite cadena vacía)
 * <li>String (no admite cadena vacía)
 * <li>UUID
 * <li>UUID (opcional)   
 * <li>LocalDate
 * <li>LocalDate (opcional)
 * </ul>
 *
 */
public class IO {

	private static Scanner sc = new Scanner(System.in);

	/**
	 * Constructor
	 */
	IO() {
		sc.useDelimiter("\n");
	}

	/**
	 * Muestra un objeto
	 * 
	 * @param o objeto
	 */
	static public void print(Object o) {
		System.out.print(o);
	}

	/**
	 * Muestra un objeto y salta de línea
	 * 
	 * @param o objeto
	 */
	static public void println(Object o) {
		System.out.println(o);
	}

	/**
	 * Lee un valor de tipo byte
	 * 
	 * @return
	 */
	static public byte readByte() {
		while (true) {
			try {
				return Byte.parseByte(sc.nextLine());
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo byte ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo byte mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public byte readByte(String mensaje) {
		IO.print(mensaje);
		return readByte();
	}

	/**
	 * Lee un valor de tipo short
	 * 
	 * @return
	 */
	static public short readShort() {
		while (true) {
			try {
				return Short.parseShort(sc.nextLine());
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo short ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo short mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public short readShort(String mensaje) {
		IO.print(mensaje);
		return readShort();
	}

	/**
	 * Lee un valor de tipo int
	 * 
	 * @return
	 */
	static public int readInt() {
		while (true) {
			try {
				return Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo int ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo int mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public int readInt(String mensaje) {
		IO.print(mensaje);
		return readInt();
	}

	/**
	 * Lee un valor de tipo long
	 * 
	 * @return
	 */
	static public long readLong() {
		while (true) {
			try {
				return Long.parseLong(sc.nextLine());
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo long ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo long mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public long readLong(String mensaje) {
		IO.print(mensaje);
		return readLong();
	}

	/**
	 * Lee un valor de tipo float
	 * 
	 * @return
	 */
	static public float readFloat() {
		while (true) {
			try {
				return Float.parseFloat(sc.nextLine());
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo float ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo float mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public float readFloat(String mensaje) {
		IO.print(mensaje);
		return readFloat();
	}

	/**
	 * Lee un valor de tipo double
	 * 
	 * @return
	 */
	static public Double readDouble() {
		while (true) {
			try {
				return Double.parseDouble(IO.readString());
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo double ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo double mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public Double readDouble(String mensaje) {
		IO.print(mensaje);
		return readDouble();
	}
	
	/**
	 * Lee un valor de tipo double que puede ser opcional
	 * 
	 * @return
	 */
	static public Double readDoubleOptional() {
		while (true) {
			try {
				String lectura = IO.readStringOptional();
				return (lectura.equals("null") || lectura.isEmpty()) ? null : Double.parseDouble(lectura);
			} catch (Exception e) {
				System.err.print("ERROR: No es de tipo double ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo double que puede ser opcional mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public Double readDoubleOptional(String mensaje) {
		IO.print(mensaje);
		return readDoubleOptional();
	}

	/**
	 * Lee un valor de tipo boolean
	 * 
	 * @return
	 */
	static public boolean readBoolean() {
		while (true) {
			String s = sc.nextLine();
			if (s.equals("true")) return true;
			if (s.equals("false")) return false;
			System.err.print("ERROR: No es de tipo boolean (true o false) ? ");
		}
	}
	
	/**
	 * Lee un valor de tipo boolean mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public boolean readBoolean(String mensaje) {
		IO.print(mensaje);
		return readBoolean();
	}

	/**
	 * Lee un valor de tipo char
	 * 
	 * @return
	 */
	static public char readChar() {
		while (true) {
			String s = sc.nextLine();
			if (s.length() == 1) {
				return s.toCharArray()[0];
			}
			System.err.print("ERROR: No es de tipo char ? ");
		}
	}
	
	/**
	 * Lee un valor de tipo char mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public char readChar(String mensaje) {
		IO.print(mensaje);
		return readChar();
	}
	
	/**
	 * Lee un valor de tipo String (no admite cadena vacía)
	 * 
	 * @return
	 */
	static public String readString() {
		while (true) {
			String lectura = sc.nextLine().trim();
			if(lectura.isEmpty()) {
				System.err.print("ERROR: este campo es obligatorio ? ");
			} else {
				return lectura;
			}
		}
	}
	
	/**
	 * Lee un valor de tipo String (no admite cadena vacía) mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public String readString(String mensaje) {
		IO.print(mensaje);
		return readString();
	}
	
	/**
	 * Lee un valor de tipo String (admite cadena vacía)
	 * 
	 * @return
	 */
	static public String readStringOptional() {
		return sc.nextLine().trim();
	}
	
	/**
	 * Lee un valor de tipo String (admite cadena vacía) mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public String readStringOptional(String mensaje) {
		IO.print(mensaje);
		return readStringOptional();
	}
	
	/**
	 * Lee un valor de tipo UUID
	 * 
	 * @return
	 */
	static public UUID readUUID() {
		while (true) {
			try {
				return java.util.UUID.fromString(IO.readString());
			} catch (Exception e) {
				System.err.print("ERROR: no es de tipo UUID [ej: " + new UUID(0, 0) + "] ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo UUID mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public UUID readUUID(String mensaje) {
		IO.print(mensaje);
		return readUUID();
	}
	
	/**
	 * Lee un valor de tipo UUID que puede ser opcional
	 * @return
	 */
	static public UUID readUUIDOptional() {
		while (true) {
			try {
				String lectura = IO.readStringOptional();
				return (lectura.equals("null") || lectura.isEmpty()) ? null : java.util.UUID.fromString(lectura);
			} catch (Exception e) {
				System.err.print("ERROR: no es de tipo UUID [ej: " + new UUID(0, 0) + "] ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo UUID que puede ser opcional mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public UUID readUUIDOptional(String mensaje) {
		IO.print(mensaje);
		return readUUIDOptional();
	}
	
	/**
	 * Lee un valor de tipo LocalDate
	 * 
	 * @return
	 */
	static public LocalDate readLocalDate() {
		while (true) {
			try {
				return LocalDate.parse(IO.readString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			} catch (Exception e) {
				System.err.print("ERROR: no es de tipo LocalDate [formato dd-mm-aaaa] ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo LocalDate mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public LocalDate readLocalDate(String mensaje) {
		IO.print(mensaje);
		return readLocalDate();
	}
	
	/**
	 * Lee un valor de tipo LocalDate que puede ser opcional
	 * 
	 * @return
	 */
	static public LocalDate readLocalDateOptional() {
		while (true) {
			try {
				String lectura = IO.readStringOptional();
				return (lectura.equals("null") || lectura.isEmpty()) ? null : LocalDate.parse(lectura, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			} catch (Exception e) {
				System.err.print("ERROR: no es de tipo LocalDate [formato dd-mm-aaaa] ? ");
			}
		}
	}
	
	/**
	 * Lee un valor de tipo LocalDate que puede ser opcional mostrando un mensaje al usuario
	 * 
	 * @param mensaje que se muestra al usuario
	 * @return
	 */
	static public LocalDate readLocalDateOptional(String mensaje) {
		IO.print(mensaje);
		return readLocalDateOptional();
	}	
	
}
