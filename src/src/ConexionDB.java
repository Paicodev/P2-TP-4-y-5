package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionDB {
 // Configuración de la URL de conexión, usuario y contraseña
 private static final String URL = "jdbc:mysql://localhost:3306/empresa_db";
 private static final String USER = "root";
 private static final String PASSWORD = ""; // Sustituye por tu clave real
 public static Connection conectar() {
 Connection conexion = null;
 try {
 // Estableciendo la conexión utilizando DriverManager
 conexion = DriverManager.getConnection(URL, USER, PASSWORD);
 System.out.println("¡Conexión a la base de datos establecida con éxito!");
 } catch (SQLException e) {
 System.out.println("Error al conectar con la base de datos: " + e.getMessage());
 }
 return conexion;
 }
}
