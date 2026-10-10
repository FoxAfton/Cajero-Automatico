import controlador.RetiroController;
import modelo.Cajero;
import modelo.Cliente;
import modelo.Cuenta;
import vista.CajeroView;

import javax.swing.SwingUtilities;

/** Punto de entrada: arma Modelo, Vista y Controlador. */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cliente cliente = new Cliente("Juan Pérez López");
            Cuenta cuenta = new Cuenta("0123456789124578", cliente, 12500.00);
            Cajero cajero = new Cajero(cuenta);
            CajeroView vista = new CajeroView();
            RetiroController controlador = new RetiroController(cajero, vista);
            controlador.iniciar();
        });
    }
}
