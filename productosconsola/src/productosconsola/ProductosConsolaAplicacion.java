package productosconsola;

import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.*;

public class ProductosConsolaAplicacion {
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
//			PreparedStatement pst = null;
//			ResultSet rs = null;

			do {
				// System.out.println("====");
				// System.out.println("MENÚ");
				
				mostrarMenu();
				
				opcion = pedirOpcion();
				
				procesarOpcion(opcion);
				
			} while (opcion != 0);

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
		int opcion;
		System.out.print("Selecciona una opción: ");
		opcion = Integer.parseInt(sc.nextLine());
		return opcion;
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
		System.out.printf(FORMATO_CABECERAS, "ID", "Producto","Precio");
		System.out.printf(FORMATO_CABECERAS, "--", "--------","------");

		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_SELECT);
				ResultSet rs = pst.executeQuery()) {
			while(rs.next()) { //De uno en uno mientras haya carga que procesar
				System.out.printf(FORMATO_LINEA, rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio").setScale(2));
			}

		} catch (SQLException e) {
			System.out.println("Error al hacer el Listado");
		}
	}

	private static void buscarID() {
		System.out.print("""
				
				BUSCAR POR ID
				
				""");
		System.out.print("Dime el ID: ");
		System.out.println();
		Long id = Long.parseLong(sc.nextLine());

		// conexion de base de Datos
		try (PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID)) {
			
			pst.setLong(1, id);
			
//						ResultSet rs = pst.executeQuery();
//						
//						while(rs.next()) { //De uno en uno mientras haya carga que procesar
//							System.out.printf("%2d %-20s %10.2f €\n", rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio").setScale(2));
//						}
			
			try (ResultSet rs = pst.executeQuery()) {
				
				if (rs.next()) { 
					System.out.printf(FORMATO_REGISTRO, "Id", rs.getLong("id"));
					System.out.printf(FORMATO_REGISTRO, "Nombre", rs.getString("nombre"));
					System.out.printf(FORMATO_REGISTRO, "Precio", rs.getBigDecimal("precio"));
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
		
		System.out.print("Nombre: ");
		String nombre = sc.nextLine();

		System.out.print("Precio: ");
		BigDecimal precio = new BigDecimal(sc.nextLine());
		
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
		
		System.out.print("Id: ");
		Long id = Long.parseLong(sc.nextLine());

		System.out.print("Nombre: ");
		String nombre = sc.nextLine();

		System.out.print("Precio: ");
		BigDecimal precio = new BigDecimal(sc.nextLine());
		
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
		
		System.out.print("Id: ");
		Long id = Long.parseLong(sc.nextLine());
		
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
}
