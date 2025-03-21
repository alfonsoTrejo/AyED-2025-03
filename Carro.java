package com.mycompany.algoritmos2025lab3;

/**
 *
 * @author trejo
 */
import java.time.LocalTime;

public class Carro {
    private String tamaño; // "pequeño", "mediano", "grande"
    private String servicio; // "aspirado" o "secado express"
    private boolean clientePreferente;
    private int tiempoServicio;
    private LocalTime horaLlegada;
    private LocalTime horaSalida;

    // Constructor
    public Carro(String tamaño, String servicio, boolean clientePreferente, LocalTime horaLlegada) {
        this.tamaño = tamaño;
        this.servicio = servicio;
        this.clientePreferente = clientePreferente;
        this.horaLlegada = horaLlegada;
        this.tiempoServicio = calcularTiempoServicio();
    }

    // Método para calcular el tiempo de servicio basado en el tamaño y servicio
    private int calcularTiempoServicio() {
        if (servicio.equals("secado express")) {
            return 3; // Secado express siempre tarda 3 minutos
        }
        switch (tamaño) {
            case "pequeño": return 5;
            case "mediano": return 7;
            case "grande": return 10;
            default: throw new IllegalArgumentException("Tamaño inválido");
        }
    }

    // Métodos Getters
    public String getTamaño() { return tamaño; }
    public String getServicio() { return servicio; }
    public boolean esPreferente() { return clientePreferente; }
    public int getTiempoServicio() { return tiempoServicio; }
    public LocalTime getHoraLlegada() { return horaLlegada; }
    public LocalTime getHoraSalida() { return horaSalida; }

    // Establece la hora de salida del vehículo
    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    // Representación en texto del vehículo
    @Override
    public String toString() {
        return String.format("Vehiculo - Tamaño: %s, Servicio: %s, Preferente: %b, Llegada: %s, Salida: %s",
                 tamaño, servicio, clientePreferente, horaLlegada, (horaSalida != null ? horaSalida : "En proceso"));
    }
}
