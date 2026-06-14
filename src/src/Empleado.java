package src;

public class Empleado {
    private int id;
    private String nombre;
    private String departamento;

    // Constructor vacío
    public Empleado() {
    }

    // Constructor para insertar (sin ID, ya que es autoincremental)
    public Empleado(String nombre, String departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // Constructor completo para leer o actualizar
    public Empleado(int id, String nombre, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}