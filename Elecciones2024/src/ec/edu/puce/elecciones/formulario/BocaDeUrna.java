package ec.edu.puce.elecciones.formulario;

import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ec.edu.puce.elecciones.dominio.Prefecto;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class BocaDeUrna extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    private List<Prefecto> prefectos;
    private JPanel panel;
    private JButton btnCancelar;
    private JButton btnActualizar;  
    private JLabel lblNombres;
    private JComboBox<String> comboBoxProvincia;
    private JComboBox<String> comboBoxCiudad;
    private JLabel lblCiudad;

    public BocaDeUrna(List<Prefecto> prefectos) {
        this.prefectos = prefectos;
        setTitle("BOCA DE URNA - REGISTRO");
        setBounds(100, 100, 600, 427);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 172, 566, 167);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(model.getValueAt(0, 0));
            }
        });
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Nombre", "Votos"}));
        scrollPane.setViewportView(table);

        panel = new JPanel();
        panel.setBounds(12, 76, 566, 84);
        getContentPane().add(panel);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setBounds(157, 351, 117, 25);
        getContentPane().add(btnCancelar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(this);
        btnActualizar.setBounds(337, 31, 117, 25);
        getContentPane().add(btnActualizar);

        lblNombres = new JLabel("Provincia");
        lblNombres.setBounds(12, 21, 70, 15);
        getContentPane().add(lblNombres);

        comboBoxProvincia = new JComboBox<>();
        comboBoxProvincia.setModel(new DefaultComboBoxModel<>(new String[]{"Azuay", "Cañar", "Pichincha", "Manabí", "Guayas"}));
        comboBoxProvincia.setBounds(79, 12, 231, 24);
        comboBoxProvincia.addActionListener(this);
        getContentPane().add(comboBoxProvincia);

        comboBoxCiudad = new JComboBox<>();
        comboBoxCiudad.setBounds(79, 43, 231, 24);
        getContentPane().add(comboBoxCiudad);

        lblCiudad = new JLabel("Ciudad");
        lblCiudad.setBounds(12, 47, 70, 15);
        getContentPane().add(lblCiudad);

        agregarCiudadesAzuay();

        cargarCandidatos();
        llenarTabla();
    }

    private void cargarCandidatos() {
        int x = 0;
        panel.removeAll();
        for (Prefecto prefecto : prefectos) {
            if (prefecto.getCiudad().equals(comboBoxCiudad.getSelectedItem())) {
                JButton btnPrefecto = new JButton(prefecto.getNombre());
                btnPrefecto.setBounds(x * 155, 0, 150, 80);
                btnPrefecto.addActionListener(this);
                panel.add(btnPrefecto);
                x++;
            }
        }
        panel.revalidate();
    }

    private void llenarTabla() {
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Prefecto prefecto : prefectos) {
            if (prefecto.getCiudad().equals(comboBoxCiudad.getSelectedItem())) {
                Object[] fila = new Object[2];
                fila[0] = prefecto.getNombre();
                fila[1] = prefecto.getVotos();
                model.addRow(fila);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancelar) {
            dispose();
        } else if (e.getSource() == btnActualizar) {
            cargarCandidatos();
            llenarTabla();
        } else if (e.getSource() instanceof JButton) {
            for (Prefecto prefecto : prefectos) {
                if (prefecto.getNombre().equals(((JButton) e.getSource()).getText())) {
                    prefecto.setVotos(prefecto.getVotos() + 1);
                    llenarTabla();
                }
            }
        } else if (e.getSource() == comboBoxProvincia || e.getSource() == comboBoxCiudad) {
            actualizarCiudades();
            cargarCandidatos();
            llenarTabla();
        }
    }

    private void actualizarCiudades() {
        String provinciaSeleccionada = (String) comboBoxProvincia.getSelectedItem();
        comboBoxCiudad.removeAllItems();

        if ("Azuay".equals(provinciaSeleccionada)) {
            agregarCiudadesAzuay();
        } else if ("Cañar".equals(provinciaSeleccionada)) {
            agregarCiudadesCañar();
        } else if ("Pichincha".equals(provinciaSeleccionada)) {
            agregarCiudadesPichincha();
        } else if ("Manabí".equals(provinciaSeleccionada)) {
            agregarCiudadesManabi();
        } else if ("Guayas".equals(provinciaSeleccionada)) {
            agregarCiudadesGuayas();
        }
    }

    private void agregarCiudadesAzuay() {
        comboBoxCiudad.addItem("Cuenca");
        comboBoxCiudad.addItem("Gualaceo");
        comboBoxCiudad.addItem("Paute");
    }

    private void agregarCiudadesCañar() {
        comboBoxCiudad.addItem("Azogues");
        comboBoxCiudad.addItem("Biblián");
        comboBoxCiudad.addItem("La Troncal");
    }

    private void agregarCiudadesPichincha() {
        comboBoxCiudad.addItem("Quito");
        comboBoxCiudad.addItem("Cayambe");
        comboBoxCiudad.addItem("Sangolquí");
    }

    private void agregarCiudadesManabi() {
        comboBoxCiudad.addItem("Portoviejo");
        comboBoxCiudad.addItem("Manta");
        comboBoxCiudad.addItem("Bahía de Caráquez");
    }

    private void agregarCiudadesGuayas() {
        comboBoxCiudad.addItem("Guayaquil");
        comboBoxCiudad.addItem("Samborondón");
        comboBoxCiudad.addItem("Daule");
    }
}