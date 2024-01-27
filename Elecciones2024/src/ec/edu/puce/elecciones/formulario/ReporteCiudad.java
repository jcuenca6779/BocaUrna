package ec.edu.puce.elecciones.formulario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ec.edu.puce.elecciones.dominio.Prefecto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ReporteCiudad extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    private List<Prefecto> prefectos;
    private JLabel lblNombres;
    private JComboBox<String> comboBoxCiudad;
    private JButton btnCerrar; 

    public ReporteCiudad(List<Prefecto> prefectos) {
        this.prefectos = prefectos;
        setBounds(100, 100, 600, 309);
        getContentPane().setLayout(null);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre", "Votos" }));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 48, 410, 167);
        getContentPane().add(scrollPane);

        lblNombres = new JLabel("Ciudad");
        lblNombres.setBounds(12, 21, 70, 15);
        getContentPane().add(lblNombres);

        comboBoxCiudad = new JComboBox<>();
        comboBoxCiudad.setModel(new DefaultComboBoxModel<>(new String[] { "Cuenca", "Gualaceo", "Paute", "Azogues", "Biblián", "La Troncal", "Quito", "Cayambe", "Sangolquí", "Portoviejo", "Manta", "Bahía de Caráquez", "Guayaquil", "Samborondón", "Daule" }));
        comboBoxCiudad.setBounds(79, 12, 231, 24);
        comboBoxCiudad.addActionListener(this);
        getContentPane().add(comboBoxCiudad);

        btnCerrar = new JButton("Cerrar"); 
        btnCerrar.addActionListener(this);
        btnCerrar.setBounds(450, 227, 117, 25);
        getContentPane().add(btnCerrar);

        model = (DefaultTableModel) table.getModel();
        llenarTabla();
    }

    private void llenarTabla() {
        model.setRowCount(0);
        String ciudadSeleccionada = (String) comboBoxCiudad.getSelectedItem();
        for (Prefecto prefecto : prefectos) {
            if (prefecto.getCiudad().equals(ciudadSeleccionada)) {
                Object[] fila = new Object[2];
                fila[0] = prefecto.getNombre();
                fila[1] = prefecto.getVotos();
                model.addRow(fila);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBoxCiudad) {
            llenarTabla();
        } else if (e.getSource() == btnCerrar) {
            dispose();
        }
    }
}
