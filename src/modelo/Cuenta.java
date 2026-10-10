package modelo;

/** Cuenta bancaria del cliente. */
public class Cuenta {

    private String numero;
    private Cliente titular;
    private double saldo;

    public Cuenta(String numero, Cliente titular, double saldo) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    /** Devuelve los cuatro últimos dígitos del número de cuenta. */
    public String ultimosCuatroDigitos() {
        return numero.substring(numero.length() - 4);
    }

    /** Descuenta el importe del saldo (monto retirado + comisión). */
    public void debitar(double importe) {
        if (importe > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        saldo -= importe;
    }
}
