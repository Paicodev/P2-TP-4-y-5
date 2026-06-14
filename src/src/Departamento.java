package src;

public class Departamento {
    private int idDepto;
    private String nombreDepto;

    public Departamento(int idDepto, String nombreDepto) {
        this.idDepto = idDepto;
        this.nombreDepto = nombreDepto;
    }

    public int getIdDepto() { return idDepto; }
    public String getNombreDepto() { return nombreDepto; }

    // el ComboBox mostrará este texto en lugar del espacio en memoria del objeto.
    @Override
    public String toString() {
        return nombreDepto;
    }
}
