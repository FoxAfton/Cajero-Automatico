package modelo;

import java.time.LocalDateTime;

/** Recibo que se genera al concluir un retiro. */
public class Recibo {

    private String ultimosDigitos;
    private String nombreCliente;
    private double cantidadRetirada;
    private double comision;
    private LocalDateTime fecha;

    public Recibo(String ultimosDigitos, String nombreCliente, double cantidadRetirada, double comision) {
        this.ultimosDigitos = ultimosDigitos;
        this.nombreCliente = nombreCliente;
        this.cantidadRetirada = cantidadRetirada;
        this.comision = comision;
        this.fecha = LocalDateTime.now();
    }

    public String getUltimosDigitos() {
        return ultimosDigitos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getCantidadRetirada() {
        return cantidadRetirada;
    }

    public double getComision() {
        return comision;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
