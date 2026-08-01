package productosconsola;

import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.*;

public class ProductosConsolaAplicacion {
	public static void main(String[] args) {
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:productosconsola.db");
				Scanner sc = new Scanner(System.in)) {
			// Variables de todo el programa
			int opcion;
//			PreparedStatement pst = null;
//			ResultSet rs = null;

			do {
				// System.out.println("====");
				// System.out.println("MENÚ");
				System.out.println();
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
				System.out.print("Selecciona una opción: ");
				opcion = Integer.parseInt(sc.nextLine());
				switch (opcion) {
				case 1:{
					System.out.print("""
							
							LISTADO
							
							""");
					System.out.printf("%2s %-20s %12s\n", "ID", "Producto","Precio");
					System.out.printf("%2s %-20s %12s\n", "--", "--------","------");

					// conexion de base de Datos
					try (PreparedStatement pst = con.prepareStatement("SELECT * FROM productos");
							ResultSet rs = pst.executeQuery()) {
						while(rs.next()) { //De uno en uno mientras haya carga que procesar
							System.out.printf("%2d %-20s %10.2f €\n", rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio").setScale(2));
						}

					} catch (SQLException e) {
						System.out.println("Error al hacer el Listado");
					}

					break;
				}
				case 2:{
					System.out.print("""
							
							BUSCAR POR ID
							
							""");
					System.out.print("Dime el ID: ");
					System.out.println();
					Long id = Long.parseLong(sc.nextLine());

					// conexion de base de Datos
					try (PreparedStatement pst = con.prepareStatement("SELECT * FROM productos WHERE id=?")) {
						
						pst.setLong(1, id);
						
//						ResultSet rs = pst.executeQuery();
//						
//						while(rs.next()) { //De uno en uno mientras haya carga que procesar
//							System.out.printf("%2d %-20s %10.2f €\n", rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio").setScale(2));
//						}
						
						try (ResultSet rs = pst.executeQuery()) {
							
							if (rs.next()) { 
								System.out.printf("%6s: %s\n", "Id", rs.getLong("id"));
								System.out.printf("%6s: %s\n", "Nombre", rs.getString("nombre"));
								System.out.printf("%6s: %s\n", "Precio", rs.getBigDecimal("precio"));
							} else {
								System.out.println("No se ha encontrado el id " + id);
							}
						} 

					} catch (SQLException e) {
						System.out.println("Error al buscar el producto");
					}

					break;
				}
				case 3:{
					System.out.print("""
							
							NUEVO PRODUCTO
							
							""");
					
					System.out.print("Nombre: ");
					String nombre = sc.nextLine();
	
					System.out.print("Precio: ");
					BigDecimal precio = new BigDecimal(sc.nextLine());
					
					// conexion de base de Datos
					try (PreparedStatement pst = con.prepareStatement("INSERT INTO productos (nombre, precio) VALUES (?, ?)")) {
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
					break;
				}
				case 4: {
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
					try (PreparedStatement pst = con.prepareStatement("UPDATE productos SET nombre=?, precio=? WHERE id=?")) {
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
					break;
				}
				case 5: {
					System.out.print("""
							
							ELIMINAR PRODUCTO
							
							""");
					
					System.out.print("Id: ");
					Long id = Long.parseLong(sc.nextLine());
					
					// conexion de base de Datos
					try (PreparedStatement pst = con.prepareStatement("DELETE FROM productos WHERE id=?")) {
				
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
					break;
				}
				// TODO: Hacer las demás opciones
				case 0:
					System.out.println("Gracias por usar el programa");
					break;
				default:
					System.out.println("Opción no reconocida");
				}
			} while (opcion != 0);

		} catch (NumberFormatException | SQLException e) {
			System.out.println("Error no controlado en la app");
			System.out.println(e.getMessage());
		}
	}
}
