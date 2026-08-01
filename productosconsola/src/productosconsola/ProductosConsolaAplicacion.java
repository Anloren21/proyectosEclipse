package productosconsola;

import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.*;

public class ProductosConsolaAplicacion {
	private static final int OPCION_SALIR = 0;

	//	Refactorizaciones
	private static final String JDBC_URL = "jdbc:sqlite:productosconsola.db";

	private static final String FORMATO_CABECERAS = "%2s %-20s %12s\n";
	private static final String FORMATO_REGISTRO = "%6s: %s\n";
	private static final String FORMATO_LINEA = "%2d %-20s %10.2f €\n";

	private static final String SQL_SELECT = "SELECT * FROM productos";
	private static final String SQL_SELECT_ID = "SELECT * FROM productos WHERE id=?";

	private static final String SQL_INSERT = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
	private static final String SQL_UPDATE_ID = "UPDATE productos SET nombre=?, precio=? WHERE id=?";
	private static final String SQL_DELETE_ID = "DELETE FROM productos WHERE id=?";

	private static final Scanner sc = new Scanner(System.in);
	private static Connection con = null;
	
//	Programa principal
	public static void main(String[] args) {
		try  {
			con = DriverManager.getConnection(JDBC_URL);
			
			int opcion;

			do {
				mostrarMenu();
				opcion = pedirOpcion();
				procesarOpcion(opcion);
				
			} while (opcion != OPCION_SALIR);

		} catch (NumberFormatException | SQLException e) {
			System.out.println("Error no controlado en la app");
			System.out.println(e.getMessage());
		} finally {
			sc.close();
			
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.out.println("Ha habido un error al cerrar la conexion");
				}
			}
		}
	}

	private static void mostrarMenu() {
		System.out.println("""
				====
				MENÚ
				====
	
				1. Listado de productos
				2. Buscar por id
	
				3. Añadir producto
				4. Modificar producto
				5. Borrar producto
	
				0. Salir
				""");
	}

	private static int pedirOpcion() {
		return pedirInt("Selecciona una opcion: ");
	}

	private static void procesarOpcion(int opcion) {
		switch (opcion) {

		case 1:
			listado();

			break;
		case 2:
			buscarID();

			break;
		case 3:
			insertar();
			break;
		case 4: 
			modificar();
			break;
		
		case 5:  
			borrar();
			break;
		case 0:
			System.out.println("Gracias por usar el programa");
			break;
		default:
			System.out.println("Opción no reconocida");
		}
	}

	private static void listado() {
		System.out.print("""
				
				LISTADO
				
				""");

		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_SELECT); ResultSet rs = pst.executeQuery()) {
			mostrarListado(rs);
		} catch (SQLException e) {
			System.out.println("Error al hacer el Listado");
		}
	}

	private static void buscarID() {
		System.out.print("""
				
				BUSCAR POR ID
				
				""");
		
		Long id = pedirLong("Dime el ID: ");
		System.out.println();

		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID)) {
			
			pst.setLong(1, id);
			
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) { 
					mostrarRegistro(rs);
				} else {
					System.out.println("No se ha encontrado el id " + id);
				}
			} 
		} catch (SQLException e) {
			System.out.println("Error al buscar el producto");
		}
	}

	private static void insertar() {
		System.out.print("""
				
				NUEVO PRODUCTO
				
				""");
		
		String nombre = pedirString("Nombre");
		BigDecimal precio = pedirBigDecimal("Precio");
		
		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_INSERT)) {
			pst.setString(1, nombre);
			pst.setBigDecimal(2, precio);
			
			//Ejecutar un cambio dentro de lo que ahi dentro de la base
			int numeroRegistrosModificados = pst.executeUpdate();
			
			if (numeroRegistrosModificados == 1) {
				System.out.println("Inserción correcta");
			} else {
				System.out.println("Se han modificado " + numeroRegistrosModificados);							
			}
		} catch (SQLException e) {
			System.out.println("Error al hacer el añadir ");
		}
	}

	private static void modificar() {
		System.out.print("""
				
				MODIFICAR PRODUCTO
				
				""");
		
		Long id = pedirLong("Id: ");
		String nombre = pedirString("Nombre: ");
		BigDecimal precio = pedirBigDecimal("Precio: ");
		
		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_UPDATE_ID)) {
			pst.setString(1, nombre);
			pst.setBigDecimal(2, precio);
			pst.setLong(3, id);
			
			//Ejecutar un cambio dentro de lo que ahi dentro de la base
			int numeroRegistrosModificados = pst.executeUpdate();
			
			if (numeroRegistrosModificados == 1) {
				System.out.println("Modificación correcta");
			} else {
				System.out.println("Se han modificado " + numeroRegistrosModificados);							
			}
		} catch (SQLException e) {
			System.out.println("Error al modificar el producto ");
		}
	}

	private static void borrar() {
		System.out.print("""
				
				ELIMINAR PRODUCTO
				
				""");
		
		Long id = pedirLong("Id: ");
		
		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_DELETE_ID)) {

			pst.setLong(1, id);
			
			//Ejecutar un cambio dentro de lo que ahi dentro de la base
			int numeroRegistrosModificados = pst.executeUpdate();
			
			if (numeroRegistrosModificados == 1) {
				System.out.println("Borrado correcta");
			} else {
				System.out.println("Se han modificado " + numeroRegistrosModificados);							
			}
		} catch (SQLException e) {
			System.out.println("Error al eliminar el producto");
		}
	}

	private static void mostrarListado(ResultSet rs) throws SQLException {
		mostrarCabeceras();
		
		while(rs.next()) { //De uno en uno mientras haya carga que procesar
			mostrasLinea(rs);
		}
	}

	private static void mostrarCabeceras() {
		System.out.printf(FORMATO_CABECERAS, "ID", "Producto","Precio");
		System.out.printf(FORMATO_CABECERAS, "--", "--------","------");
	}

	private static void mostrasLinea(ResultSet rs) throws SQLException {
		System.out.printf(FORMATO_LINEA, rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio").setScale(2));
	}

	private static void mostrarRegistro(ResultSet rs) throws SQLException {
		System.out.printf(FORMATO_REGISTRO, "Id", rs.getLong("id"));
		System.out.printf(FORMATO_REGISTRO, "Nombre", rs.getString("nombre"));
		System.out.printf(FORMATO_REGISTRO, "Precio", rs.getBigDecimal("precio"));
	}

	private static String pedirString(String mensaje) {
		System.out.print(mensaje + ": ");
		return sc.next();
	}
	
	private static Long pedirLong(String mensaje) {
		return Long.parseLong(pedirString(mensaje));
	}
	
	private static BigDecimal pedirBigDecimal(String mensaje) {
		return new BigDecimal(pedirString(mensaje));
	}
	
	private static int pedirInt(String mensaje) {
		return Integer.parseInt(pedirString(mensaje));
	}
}
