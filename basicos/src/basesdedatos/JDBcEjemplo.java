package basesdedatos;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class JDBcEjemplo {	
	private static final String URL = "jdbc:sqlite:basicos.db";
	
	private static final String SQL_SELECT = "SELECT * FROM productos"; //Órdenes al conductor
	private static final String SQL_SELECT_ID = "SELECT * FROM productos WHERE id=?";
	private static final String SQL_INSERT = "INSERT INTO productos (nombre, precio) VALUES (?,?)\r\n";
	private static final String SQL_UPDATE = "UPDATE productos SET nombre=?, precio=? WHERE id=?\r\n";
	private static final String SQL_DELETE = "DELETE FROM productos WHERE id=?\r\n";
	
	private static Connection con;
	
	public static void main(String[] args) throws SQLException {
		//Scanner sc = new Scanner(System.in);
		
		con = DriverManager.getConnection(URL);  //Carretera
		//ResultSet rs;
		listado("SELECT");
		
		//System.out.print("Dime el id: ");
		Long id = Long.parseLong("2");//sc.nextLine()
		
		PreparedStatement pst = con.prepareStatement(SQL_SELECT_ID);
		pst.setLong(1, id);
		
		ResultSet rs = pst.executeQuery(); //Cargamento
		
		while (rs.next()) { //De uno en uno mientras haya carga que procesar
			System.out.printf("%2s %-10s %5s\n", rs.getString("id"), rs.getString("nombre"), rs.getString("precio"));
		} 
		
		rs.close();
		pst.close();
		
		//Insertar un nuevo registro
		pst = con.prepareStatement(SQL_INSERT);
		
		pst.setString(1, "NUEVO");
		pst.setBigDecimal(2, new BigDecimal("1234.12"));
		
		pst.executeUpdate();
		pst.close();
		
		listado("INSERT");
		
		//Modificar un nuevo registro
		pst = con.prepareStatement(SQL_UPDATE);
		
		pst.setString(1, "MODIFICADO");
		pst.setBigDecimal(2, new BigDecimal("4321.12"));
		pst.setLong(3, 5);
		
		pst.executeUpdate();
		pst.close();
		
		listado("UPDATE");
		
		//Eliminar un nuevo registro
		pst = con.prepareStatement(SQL_DELETE);
		
		pst.setLong(1, 5);
		
		pst.executeUpdate();
		pst.close();
		
		listado("DELETE");
		
		resetearId();
		
		con.close();
	}

	private static void listado(String titulo) throws SQLException {
		System.out.println("--------------------------");
		System.out.println(titulo);
		System.out.println("--------------------------");
		System.out.println();
		
		Statement st = con.createStatement(); //Camión
		ResultSet rs = st.executeQuery(SQL_SELECT); //Cargamento
		
		while(rs.next()) { //De uno en uno mientras haya carga que procesar
			System.out.printf("%2d %-20s %10.2f €\n", rs.getLong("id"), rs.getString("nombre"), rs.getBigDecimal("precio").setScale(2));
		}
		
		rs.close();
		st.close();
		
		System.out.println();
		System.out.println("FIN " + titulo);
		System.out.println("--------------------------");
		System.out.println();
	}
	
	private static void resetearId() throws SQLException {
		Statement st = con.createStatement(); //Camión
		st.executeUpdate("UPDATE sqlite_sequence SET seq=4 WHERE name='productos'"); //Cargamento
		
	}
}
