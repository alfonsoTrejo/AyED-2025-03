package com.mycompany.algoritmos2025lab3;

public class Vehiculo {
    public enum Tamano { PEQUENO, MEDIANO, GRANDE }
    public enum Servicio { ASPIRADO, SECADO_EXPRESS }

    private Tamano tamano;
    private Servicio servicio;
    private boolean clientePreferente;
    private timStamp horaLlegada; // Representa la hora de llegada en minutos de la simulación
    private timStamp horaSalida; // Representa la hora de salida en minutos de la simulación    
    private timStamp tiempoLLegadaLavado; // Representa la hora de llegada al lavado en minutos de la simulación
    private timStamp tiempoLLegadaAspirado; // Representa la hora de llegada al aspirado en minutos de la simulación


    public Vehiculo(Tamano tamano, Servicio servicio, boolean clientePreferente, timStamp horaLlegada) {
        this.tamano = tamano;
        this.servicio = servicio;
        this.clientePreferente = clientePreferente;
        this.horaLlegada = horaLlegada;
    }

    // Getters
    public Tamano getTamano() {
        return tamano;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public boolean isClientePreferente() {
        return clientePreferente;
    }

    public timStamp getHoraLlegada() {
        return horaLlegada;
    }
    public timStamp getHoraSalida() {
        return horaSalida;
    }
    public timStamp getTiempoLLegadaLavado() {
        return tiempoLLegadaLavado;
    }
    public timStamp getTiempoLLegadaAspirado() {
        return tiempoLLegadaAspirado;
    }


    //setters tiempo de salida, lavado, aspirado
    public void setHoraSalida(timStamp horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setTiempoLLegadaLavado(timStamp tiempoLLegadaLavado) {
        this.tiempoLLegadaLavado = tiempoLLegadaLavado;
    }

    public void setTiempoLLegadaAspirado(timStamp tiempoLLegadaAspirado) {
       this.tiempoLLegadaAspirado = tiempoLLegadaAspirado;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "tamano=" + tamano +
                ", servicio=" + servicio +
                ", clientePreferente=" + clientePreferente +
                ", horaLlegada=" + horaLlegada.toString() +
                '}';
    }
}
