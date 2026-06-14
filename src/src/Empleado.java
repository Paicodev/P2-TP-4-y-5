package src;

public class Empleado {
    private int id;
    private String nombre;
    private int idDepto;
    private String rutaFoto;

    // Constructor vacío
    public Empleado() {
    }

    // Constructor completo para leer o actualizar
    public Empleado(int id, String nombre, int idDepto, String rutaFoto) {
        this.id = id;
        this.nombre = nombre;
        this.idDepto = idDepto;
        this.rutaFoto = rutaFoto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getIdDepto() { return idDepto; }
    public void setIdDepto(int idDepto) { this.idDepto = idDepto; }
    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }
}