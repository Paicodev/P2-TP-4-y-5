package src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO implements OperacionesDAO {

    @Override
    public void insertar(Empleado emp) {
        String sql = "INSERT INTO empleados (nombre, departamento) VALUES (?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emp.getNombre());
            pstmt.setString(2, emp.getDepartamento());
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente vía DAO.");
            
        } catch (SQLException e) {
            System.out.println("Error al insertar (DAO): " + e.getMessage());
        }
    }

    @Override
    public void modificar(Empleado emp) {
        String sql = "UPDATE empleados SET nombre = ?, departamento = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emp.getNombre());
            pstmt.setString(2, emp.getDepartamento());
            pstmt.setInt(3, emp.getId());
            pstmt.executeUpdate();
            System.out.println("Empleado modificado correctamente vía DAO.");
            
        } catch (SQLException e) {
            System.out.println("Error al modificar (DAO): " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente vía DAO.");
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar (DAO): " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Empleado> consultarTodos() {
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setId(rs.getInt("id"));
                emp.setNombre(rs.getString("nombre"));
                emp.setDepartamento(rs.getString("departamento"));
                listaEmpleados.add(emp);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar (DAO): " + e.getMessage());
        }
        
        return listaEmpleados;
    }
}