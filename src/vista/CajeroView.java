package vista;

import controlador.RetiroController;
import modelo.Recibo;
import modelo.Retiro;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CajeroView extends JFrame {

    private JList<Double> listaMontos;
    private DefaultListModel<Double> modeloLista;
    private JButton btnRetirar;
    private JTextArea areaDetalle;
    private TitledBorder bordeDerecho;
    private RetiroController controlador;
    private boolean actualizando = false;

    public CajeroView() {
        super("Cajero Automático - Retirar efectivo");
        construirInterfaz();
    }

    public void setControlador(RetiroController controlador) {
        this.controlador = controlador;
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 480);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ----- Zona izquierda: lista de montos + botón -----
        JPanel izquierda = new JPanel(new BorderLayout(8, 8));
        izquierda.setBorder(BorderFactory.createTitledBorder("Seleccione el monto a retirar"));
        modeloLista = new DefaultListModel<>();
        listaMontos = new JList<>(modeloLista);
        listaMontos.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        listaMontos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMontos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                JLabel c = (JLabel) super.getListCellRendererComponent(l, v, i, s, f);
                c.setText(formato((Double) v));
                c.setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });
        listaMontos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !actualizando && controlador != null
                    && listaMontos.getSelectedValue() != null) {
                controlador.seleccionarMonto(listaMontos.getSelectedValue());
            }
        });
        izquierda.add(new JScrollPane(listaMontos), BorderLayout.CENTER);

        btnRetirar = new JButton("Retirar efectivo");
        btnRetirar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        btnRetirar.setEnabled(false);
        btnRetirar.addActionListener(e -> {
            if (controlador != null) {
                controlador.retirarEfectivo();
            }
        });
        izquierda.add(btnRetirar, BorderLayout.SOUTH);

        // ----- Zona derecha: estado de cuenta / recibo -----
        JPanel derecha = new JPanel(new BorderLayout());
        bordeDerecho = BorderFactory.createTitledBorder("Estado de cuenta");
        derecha.setBorder(bordeDerecho);
        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);
        areaDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        areaDetalle.setMargin(new Insets(10, 10, 10, 10));
        areaDetalle.setText("\n  Elija un monto de la lista\n  para ver su estado de cuenta.");
        derecha.add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        add(izquierda);
        add(derecha);
    }

    public void mostrar() {
        setVisible(true);
    }

    public void mostrarMontos(List<Double> montos) {
        modeloLista.clear();
        for (Double m : montos) {
            modeloLista.addElement(m);
        }
    }

    public void mostrarEstadoCuenta(Retiro r) {
        bordeDerecho.setTitle("Estado de cuenta");
        StringBuilder sb = new StringBuilder("\n");
        sb.append(linea("Cliente", r.getNombreCliente()));
        sb.append(linea("Monto actual", formato(r.getSaldoActual())));
        sb.append(linea("Cantidad a retirar", formato(r.getMontoSolicitado())));
        sb.append(linea("Comisión por retiro", formato(r.getComision())));
        sb.append("  ------------------------------------\n");
        sb.append(linea("Monto final", formato(r.getSaldoFinal())));
        if (!r.isFondosSuficientes()) {
            sb.append("\n  *** SALDO INSUFICIENTE ***\n");
        }
        areaDetalle.setText(sb.toString());
        repaint();
    }

    public void mostrarRecibo(Recibo r) {
        bordeDerecho.setTitle("Recibo de retiro");
        StringBuilder sb = new StringBuilder("\n");
        sb.append("        RECIBO DE RETIRO\n");
        sb.append("  ------------------------------------\n");
        sb.append(linea("Fecha", r.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        sb.append(linea("Cuenta", "**** " + r.getUltimosDigitos()));
        sb.append(linea("Cliente", r.getNombreCliente()));
        sb.append(linea("Cantidad retirada", formato(r.getCantidadRetirada())));
        sb.append(linea("Cobro por comisión", formato(r.getComision())));
        sb.append("  ------------------------------------\n");
        sb.append("     Gracias por usar nuestro cajero\n");
        areaDetalle.setText(sb.toString());
        repaint();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Cajero Automático", JOptionPane.WARNING_MESSAGE);
    }

    public void habilitarRetiro(boolean habilitar) {
        btnRetirar.setEnabled(habilitar);
    }

    /** Quita la selección de la lista sin disparar el controlador. */
    public void limpiarSeleccion() {
        actualizando = true;
        listaMontos.clearSelection();
        actualizando = false;
    }

    private String linea(String etiqueta, String valor) {
        return String.format("  %-20s %14s%n", etiqueta + ":", valor);
    }

    public static String formato(double v) {
        return String.format(Locale.US, "$%,.2f", v);
    }
}
