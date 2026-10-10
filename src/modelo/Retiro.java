package modelo;

/** Estado de cuenta del cliente para un monto de retiro elegido. */
public class Retiro {

    private String nombreCliente;
    private double saldoActual;
    private double montoSolicitado;
    private double comision;
    private double saldoFinal;

    public Retiro(String nombreCliente, double saldoActual, double montoSolicitado, double comision) {
        this.nombreCliente = nombreCliente;
        this.saldoActual = saldoActual;
        this.montoSolicitado = montoSolicitado;
        this.comision = comision;
        this.saldoFinal = saldoActual - montoSolicitado - comision;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public double getComision() {
        return comision;
    }

    public double getSaldoFinal() {
        return saldoFinal;
    }

    /** El retiro es posible solo si el saldo final no es negativo. */
    public boolean isFondosSuficientes() {
        return saldoFinal >= 0;
    }
}
