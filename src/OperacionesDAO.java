import java.util.ArrayList;

public interface OperacionesDAO {
    void insertar(Empleado emp);
    void modificar(Empleado emp);
    void eliminar(int id);
    ArrayList<Empleado> consultarTodos();
}