package src.views;

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
    private JTextField txtId, txtNombre, txtDepartamento;
    private JButton btnModificar, btnEliminar;
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
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // Bloqueamos el ID porque es autoincremental y no debe editarse a mano
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Departamento:"));
        txtDepartamento = new JTextField();
        panelFormulario.add(txtDepartamento);

        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        panelFormulario.add(btnModificar);
        panelFormulario.add(btnEliminar);

        add(panelFormulario, BorderLayout.WEST);

        // --- Panel Derecho: JTable y JScrollPane ---
        String[] columnas = {"ID", "Nombre", "Departamento"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // --- Cargar datos iniciales al abrir la ventana ---
        cargarTabla();

        // --- Eventos de la Interfaz ---
        
        // 3 y 4. Evento de Selección (MouseListener) y Transferencia de Datos
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener la fila seleccionada 
                int fila = tabla.getSelectedRow();
                if (fila != -1) {
                    // Extraer datos y pasarlos a los JTextField
                    txtId.setText(tabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                    txtDepartamento.setText(tabla.getValueAt(fila, 2).toString());
                }
            }
        });

        // 5. Botón Modificar (UPDATE) 
        btnModificar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                // Recoger los nuevos valores de los JTextField 
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                String depto = txtDepartamento.getText();
                
                // Crear un objeto Empleado y pasarlo al método modificar 
                Empleado emp = new Empleado(id, nombre, depto);
                dao.modificar(emp);
                
                // Refrescar tabla y limpiar formulario 
                cargarTabla(); 
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para modificar.");
            }
        });

        // 6. Botón Eliminar (DELETE) 
        btnEliminar.addActionListener(e -> {
            if (!txtId.getText().isEmpty()) {
                // Recoger únicamente el ID seleccionado 
                int id = Integer.parseInt(txtId.getText());
                
                // Pasarlo al método eliminar(id) del DAO 
                dao.eliminar(id);
                
                // Refrescar visualmente la tabla y limpiar las cajas de texto
                cargarTabla(); 
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para eliminar.");
            }
        });
    }

    // 2. Poblar la Tabla 
    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiar el modelo 
        // Invoca consultarTodos() e itera el ArrayList resultante 
        ArrayList<Empleado> lista = dao.consultarTodos();
        for (Empleado emp : lista) {
            Object[] fila = {emp.getId(), emp.getNombre(), emp.getDepartamento()};
            // Llenar el DefaultTableModel asociado a tu JTable 
            modeloTabla.addRow(fila);
        }
    }

    // Método auxiliar para vaciar las cajas de texto
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtDepartamento.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaEmpleados().setVisible(true);
        });
    }
}