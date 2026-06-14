package src.views;

import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import src.*;

public class VistaEmpleados extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre;
    private JComboBox<Departamento> comboDepartamento;
    private JButton btnModificar, btnEliminar, btnGuardar, btnBuscarFoto;
    private String rutaFotoActual = "";
    private JLabel lblFoto;

    private EmpleadoDAO dao;

    public VistaEmpleados() {
        dao = new EmpleadoDAO();
        
        //Configuración básica de la ventana
        setTitle("Gestión de Empleados");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Izquierdo: Formulario con cajas de texto y botones ---
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // Bloqueamos el ID porque es autoincremental y no debe editarse a mano
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Departamento:"));
        comboDepartamento = new JComboBox<>();
        cargarDepartamentosComboBox();
        panelFormulario.add(comboDepartamento);

        btnBuscarFoto = new JButton("Buscar Foto");
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(100, 100));
        lblFoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelFormulario.add(btnBuscarFoto);
        panelFormulario.add(lblFoto);

        
        btnGuardar = new JButton("Nuevo");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        JPanel panelBotones1 = new JPanel(new FlowLayout());
        panelBotones1.add(btnGuardar);
        
        JPanel panelBotones2 = new JPanel(new FlowLayout());
        panelBotones2.add(btnEliminar);

        panelFormulario.add(panelBotones1);
        panelFormulario.add(panelBotones2);

        add(panelFormulario, BorderLayout.WEST);

        // --- Panel Derecho: JTable y JScrollPane ---
        String[] columnas = {"ID", "Nombre", "ID Depto", "Ruta Foto"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // --- Cargar datos iniciales al abrir la ventana ---
        cargarTabla();

        // --- Eventos de la Interfaz ---
        
        // Evento de Selección (MouseListener) y Transferencia de Datos
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener la fila seleccionada 
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    // Extraer datos y pasarlos a los JTextField
                    txtId.setText(tabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                    int idDeptoTabla = Integer.parseInt(tabla.getValueAt(fila, 2).toString());
                    for (int i = 0; i < comboDepartamento.getItemCount(); i++) {
                        Departamento d = comboDepartamento.getItemAt(i);
                        if (d.getIdDepto() == idDeptoTabla) {
                            comboDepartamento.setSelectedIndex(i);
                            break;
                        }
            }
                }
                Object rutaObj = tabla.getValueAt(fila, 3);
                    rutaFotoActual = (rutaObj != null) ? rutaObj.toString() : "";
                    if (!rutaFotoActual.isEmpty()) {
                        ImageIcon icono = new ImageIcon(rutaFotoActual);
                        Image img = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        lblFoto.setIcon(new ImageIcon(img));
                    } else {
                        lblFoto.setIcon(null);
                    }
     }
        });

        //Botón Modificar (UPDATE) 
        btnModificar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                // Recoger los nuevos valores de los JTextField 
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                Departamento deptoSeleccionado = (Departamento) comboDepartamento.getSelectedItem();
                int idDepto = deptoSeleccionado.getIdDepto(); // Extraer el valor clave

                Empleado emp = new Empleado(id, nombre, idDepto, rutaFotoActual);
                dao.modificar(emp);

                // Refrescar tabla y limpiar formulario 
                cargarTabla(); 
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para modificar.");
            }
        });

        // Botón Eliminar (DELETE) 
        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                int id = Integer.parseInt(txtId.getText());
                
                dao.eliminar(id);
                
                cargarTabla(); 
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para eliminar.");
            }
        });

        btnBuscarFoto.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                rutaFotoActual = archivo.getAbsolutePath();
                
                ImageIcon icono = new ImageIcon(rutaFotoActual);
                Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblFoto.setIcon(new ImageIcon(imagenEscalada));
            }
        });

        // Evento Botón Nuevo (INSERT)
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            // Extraer el valor clave del ComboBox
            Departamento deptoSeleccionado = (Departamento) comboDepartamento.getSelectedItem();
            int idDepto = deptoSeleccionado.getIdDepto(); 

            Empleado emp = new Empleado(0, nombre, idDepto, rutaFotoActual);
            dao.insertar(emp);
            
            cargarTabla();
            limpiarFormulario();
        });
    }

    //Poblar la Tabla 
    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiar el modelo 
        // Invoca consultarTodos() e itera el ArrayList resultante 
        ArrayList<Empleado> lista = dao.consultarTodos();
        for (Empleado emp : lista) {
           Object[] fila = {emp.getId(), emp.getNombre(), emp.getIdDepto(), emp.getRutaFoto()};
            modeloTabla.addRow(fila);
        }
    }

    // Método para llenar el desplegable con la BD
    private void cargarDepartamentosComboBox() {
        ArrayList<Departamento> deptos = dao.obtenerDepartamentos();
        for (Departamento d : deptos) {
            comboDepartamento.addItem(d);
        }
    }

    // Método auxiliar para vaciar las cajas de texto
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        comboDepartamento.setSelectedIndex(0); // Resetea el desplegable al primer elemento
        rutaFotoActual = "";
        lblFoto.setIcon(null); // Borra la foto de la vista
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaEmpleados().setVisible(true);
        });
    }
}