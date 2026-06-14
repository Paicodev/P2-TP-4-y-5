package src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDAO implements OperacionesDAO {

    public ArrayList<Departamento> obtenerDepartamentos() {
        ArrayList<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM departamentos";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Departamento(rs.getInt("id_depto"), rs.getString("nombre_depto")));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener departamentos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void insertar(Empleado emp) {
        String sql = "INSERT INTO empleados (nombre, id_depto, ruta_foto) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emp.getNombre());
            pstmt.setInt(2, emp.getIdDepto());
            pstmt.setString(3, emp.getRutaFoto());
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente vía DAO.");
            
        } catch (SQLException e) {
            System.out.println("Error al insertar (DAO): " + e.getMessage());
        }
    }

    @Override
    public void modificar(Empleado emp) {
        String sql = "UPDATE empleados SET nombre = ?, id_depto = ?, ruta_foto = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emp.getNombre());
            pstmt.setInt(2, emp.getIdDepto());
            pstmt.setString(3, emp.getRutaFoto());
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
                emp.setIdDepto(rs.getInt("id_depto"));
                emp.setRutaFoto(rs.getString("ruta_foto"));
                listaEmpleados.add(emp);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar (DAO): " + e.getMessage());
        }
        
        return listaEmpleados;
    }
}