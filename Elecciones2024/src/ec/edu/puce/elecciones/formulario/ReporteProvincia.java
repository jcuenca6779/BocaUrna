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
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ReporteProvincia extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    private List<Prefecto> prefectos;
    private JButton btnCancelar;
    private JLabel lblNombres;
    private JComboBox<String> comboBox;

    public ReporteProvincia(List<Prefecto> prefectos) {
        this.prefectos = prefectos;
        setTitle("REPORTE GENERAL POR PROVINCIA");
        setBounds(100, 100, 600, 309);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 48, 566, 167);
        getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(model.getValueAt(table.getSelectedRow(), 0));
            }
        });
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre", "Votos" }));
        scrollPane.setViewportView(table);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setBounds(234, 227, 117, 25);
        getContentPane().add(btnCancelar);

        lblNombres = new JLabel("Provincia");
        lblNombres.setBounds(12, 21, 70, 15);
        getContentPane().add(lblNombres);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Azuay", "Cañar", "Pichincha", "Manabí", "Guayas" }));
        comboBox.setBounds(79, 12, 231, 24);
        comboBox.addActionListener(this);
        getContentPane().add(comboBox);

        model = (DefaultTableModel) table.getModel();
        llenarTabla();
    }

    private void llenarTabla() {
        model.setRowCount(0);
        String provinciaSeleccionada = (String) comboBox.getSelectedItem();
        for (Prefecto prefecto : prefectos) {
            if (prefecto.getProvincia().equals(provinciaSeleccionada)) {
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
        } else if (e.getSource() == comboBox) {
            llenarTabla();
        }
    }

    public List<Prefecto> getPrefectos() {
        return prefectos;
    }

    public void setPrefectos(List<Prefecto> prefectos) {
        this.prefectos = prefectos;
    }
}