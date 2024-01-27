package ec.edu.puce.elecciones.formulario;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ec.edu.puce.elecciones.dominio.Prefecto;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class CrearPrefecto extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField txtNombre;

    private Prefecto prefecto;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnGuardar;
    private JButton btnNuevo;
    private JButton btnCancelar;

    private List<Prefecto> prefectos;
    private int idPrefecto;

    private JComboBox<String> comboBoxProvincia;
    private JComboBox<String> comboBoxCiudad;

    public CrearPrefecto(List<Prefecto> prefectos) {
        idPrefecto = 1;
        this.prefectos = prefectos;
        setTitle("CANDIDATOS A PREFECTO");
        setBounds(100, 100, 443, 385);
        getContentPane().setLayout(null);

        JLabel lblNombres = new JLabel("Nombres");
        lblNombres.setBounds(33, 78, 70, 15);
        getContentPane().add(lblNombres);

        txtNombre = new JTextField();
        txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        txtNombre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agregarPrefecto();
            }
        });
        txtNombre.setBounds(100, 75, 231, 19);
        getContentPane().add(txtNombre);
        txtNombre.setColumns(10);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.addActionListener(this);
        btnNuevo.setBounds(33, 105, 117, 25);
        getContentPane().add(btnNuevo);

        btnGuardar = new JButton("Agregar");
        btnGuardar.addActionListener(this);
        btnGuardar.setBounds(158, 105, 117, 25);
        getContentPane().add(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setBounds(286, 105, 117, 25);
        getContentPane().add(btnCancelar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.addAncestorListener(new AncestorListener() {
        	public void ancestorAdded(AncestorEvent event) {
        	}
        	public void ancestorMoved(AncestorEvent event) {
        	}
        	public void ancestorRemoved(AncestorEvent event) {
        	}
        });
        scrollPane.setBounds(10, 141, 407, 198);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(model.getValueAt(0, 0));
            }
        });
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Nombre", "Provincia", "Ciudad"}));
        scrollPane.setViewportView(table);

        JLabel lblNombres_1 = new JLabel("Provincia");
        lblNombres_1.setBounds(33, 10, 70, 15);
        getContentPane().add(lblNombres_1);

        comboBoxProvincia = new JComboBox();
        comboBoxProvincia.setModel(new DefaultComboBoxModel(new String[]{"Azuay", "Cañar", "Pichincha", "Manabí", "Guayas"}));
        comboBoxProvincia.setBounds(100, 5, 231, 24);
        comboBoxProvincia.addActionListener(this);
        getContentPane().add(comboBoxProvincia);

        JLabel lblCiudad = new JLabel("Ciudad");
        lblCiudad.setBounds(33, 39, 70, 15);
        getContentPane().add(lblCiudad);

        comboBoxCiudad = new JComboBox();
        comboBoxCiudad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        comboBoxCiudad.setBounds(100, 35, 231, 24);
        getContentPane().add(comboBoxCiudad);
        
        agregarCiudadesAzuay();

        model = (DefaultTableModel) table.getModel();
        agregarFila();
    }

    private void nuevo() {
        prefecto = new Prefecto();
        txtNombre.setText(prefecto.getNombre());
    }
    
    private void agregarPrefecto() {
        prefecto = new Prefecto();
        prefecto.setId(idPrefecto);
        prefecto.setNombre(txtNombre.getText());
        prefecto.setProvincia((String) comboBoxProvincia.getSelectedItem());
        prefecto.setCiudad((String) comboBoxCiudad.getSelectedItem());
        prefectos.add(prefecto);
        agregarFila();
        txtNombre.setText("");
        idPrefecto++;
    }

    private void agregarFila() {
        model.setRowCount(0);
        for (Prefecto prefecto : prefectos) {
            Object[] fila = new Object[3];
            fila[0] = prefecto.getNombre();
            fila[1] = prefecto.getProvincia();
            fila[2] = prefecto.getCiudad();
            model.addRow(fila);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevo) {
            nuevo();
        } else if (e.getSource() == btnGuardar || e.getSource() == txtNombre) {
            agregarPrefecto();
        } else if (e.getSource() == btnCancelar) {
            dispose();
        } else if (e.getSource() == comboBoxProvincia) {
            actualizarCiudades();
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
