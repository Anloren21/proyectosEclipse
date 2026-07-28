package basesdedatos;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class JDBcEjemplo {
	public static void main(String[] args) throws SQLException {
		//Scanner sc = new Scanner(System.in);
		
		String url = "jdbc:sqlite:E:\\FORMACION JAVA\\git\\Java-Eclipse\\basicos\\basicos.db";
		
		String sqlSelect = "SELECT * FROM productos"; //Órdenes al conductor
		String sqlSelectId = "SELECT * FROM productos WHERE id=?";
		String sqlInsert = "INSERT INTO productos (nombre, precio) VALUES (?,?)\r\n";
		String sqlUpdate = "UPDATE productos SET nombre=?, precio=? WHERE id=?\r\n";
		String sqlDelete = "DELETE FROM productos WHERE id=?\r\n";
		
		Connection con = DriverManager.getConnection(url);  //Carretera
		//ResultSet rs;
		listado("SELECT", con, sqlSelect);
		
		//System.out.print("Dime el id: ");
		Long id = Long.parseLong("2");//sc.nextLine()
		
		PreparedStatement pst = con.prepareStatement(sqlSelectId);
		pst.setLong(1, id);
		
		ResultSet rs = pst.executeQuery(); //Cargamento
		
		while (rs.next()) { //De uno en uno mientras haya carga que procesar
			System.out.printf("%2s %-10s %5s\n", rs.getString("id"), rs.getString("nombre"), rs.getString("precio"));
		} 
		
		//Insertar un nuevo registro
		pst = con.prepareStatement(sqlInsert);
		
		pst.setString(1, "NUEVO");
		pst.setBigDecimal(2, new BigDecimal("1234.12"));
		
		pst.executeUpdate();
		listado("INSERT", con, sqlSelect);
		
		//Modificar un nuevo registro
		pst = con.prepareStatement(sqlUpdate);
		
		pst.setString(1, "MODIFICADO");
		pst.setBigDecimal(2, new BigDecimal("4321.12"));
		pst.setLong(3, 5);
		
		pst.executeUpdate();
		listado("UPDATE", con, sqlSelect);
		
		//Eliminar un nuevo registro
		pst = con.prepareStatement(sqlDelete);
		
		pst.setLong(1, 5);
		
		pst.executeUpdate();
		
		listado("DELETE", con, sqlSelect);
		
		resetearId(con);
	}

	private static void listado(String titulo, Connection con, String sqlSelect) throws SQLException {
		System.out.println("--------------------------");
		System.out.println(titulo);
		System.out.println("--------------------------");
		System.out.println();
		
		Statement st = con.createStatement(); //Camión
		ResultSet rs = st.executeQuery(sqlSelect); //Cargamento
		
		while(rs.next()) { //De uno en uno mientras haya carga que procesar
			System.out.printf("%2s %-10s %5s\n", rs.getString("id"), rs.getString("nombre"), rs.getString("precio"));
		}
		
		System.out.println();
	}
	
	private static void resetearId(Connection con) throws SQLException {
		Statement st = con.createStatement(); //Camión
		st.executeUpdate("UPDATE sqlite_sequence SET seq=4 WHERE name='productos'"); //Cargamento
		
	}
}
