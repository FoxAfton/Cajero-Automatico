package controlador;

import modelo.Cajero;
import modelo.Recibo;
import modelo.Retiro;
import vista.CajeroView;

public class RetiroController {

    private Cajero cajero;
    private CajeroView vista;
    private double montoSeleccionado;

    public RetiroController(Cajero cajero, CajeroView vista) {
        this.cajero = cajero;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    /** Paso (a): muestra la lista de montos disponibles. */
    public void iniciar() {
        vista.mostrarMontos(cajero.getMontosDisponibles());
        vista.mostrar();
    }

    /** Paso (b): al elegir un monto se muestra el estado de cuenta. */
    public void seleccionarMonto(double monto) {
        montoSeleccionado = monto;
        Retiro retiro = cajero.simularRetiro(monto);
        vista.mostrarEstadoCuenta(retiro);
        vista.habilitarRetiro(retiro.isFondosSuficientes());
    }

    /** Pasos (c) y (d): ejecuta el retiro y muestra el recibo. */
    public void retirarEfectivo() {
        try {
            Recibo recibo = cajero.retirarEfectivo(montoSeleccionado);
            vista.mostrarRecibo(recibo);
            vista.habilitarRetiro(false);
            vista.limpiarSeleccion();
        } catch (IllegalArgumentException ex) {
            vista.mostrarMensaje(ex.getMessage());
        }
    }
}
