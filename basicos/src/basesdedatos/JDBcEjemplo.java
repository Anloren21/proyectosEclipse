package basesdedatos;

import java.sql.*;

public class JDBcEjemplo {
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:sqlite:E:\\FORMACION JAVA\\git\\Java-Eclipse\\basicos.db";
		
		String sqlSelect = "SELECT * FROM productos";
		
		Connection con = DriverManager.getConnection(url);  //Carretera
		Statement st = con.createStatement(); //Camión
		ResultSet rs = st.executeQuery(sqlSelect); //Cargamento
		
		while(rs.next()) { //De uno en uno mientras haya carga que procesar
			System.out.printf("%s %s %s\n", rs.getString("id"), rs.getString("nombre"), rs.getString("precio"));
		}
		
	}

}
