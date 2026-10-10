package modelo;

import java.util.ArrayList;
import java.util.List;

/** Cajero automático: ofrece montos, calcula comisión y ejecuta el retiro. */
public class Cajero {

    public static final double MONTO_MINIMO = 200.00;
    public static final double MONTO_MAXIMO = 5000.00;
    public static final double INCREMENTO = 200.00;
    public static final double TASA_COMISION = 0.015;

    private Cuenta cuenta;

    public Cajero(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    /** Montos disponibles*/
    public List<Double> getMontosDisponibles() {
        List<Double> montos = new ArrayList<>();
        for (double m = MONTO_MINIMO; m <= MONTO_MAXIMO; m += INCREMENTO) {
            montos.add(m);
        }
        return montos;
    }

    public double calcularComision(double monto) {
        return Math.round(monto * TASA_COMISION * 100.0) / 100.0;
    }

    /** Genera el estado de cuenta para el monto elegido. */
    public Retiro simularRetiro(double monto) {
        double comision = calcularComision(monto);
        return new Retiro(cuenta.getTitular().getNombre(), cuenta.getSaldo(), monto, comision);
    }

    /** Descuenta monto + comisión de la cuenta y genera el recibo. */
    public Recibo retirarEfectivo(double monto) {
        double comision = calcularComision(monto);
        cuenta.debitar(monto + comision);
        return new Recibo(cuenta.ultimosCuatroDigitos(), cuenta.getTitular().getNombre(), monto, comision);
    }
}
