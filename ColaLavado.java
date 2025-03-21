package com.mycompany.algoritmos2025lab3;

import java.util.LinkedList;
import java.util.Queue;

public class ColaLavado {
    private Queue<Carro> colaLavado;
    private final int CAPACIDAD_MAXIMA = 3;

    public ColaLavado() {
        this.colaLavado = new LinkedList<>();
    }

    // Agregar un vehículo a la cola de lavado
    public boolean agregarCarro(Carro v) {
        if (colaLavado.size() < CAPACIDAD_MAXIMA) {
            colaLavado.add(v);
            return true;
        } else {
            System.out.println("⚠️ La máquina de lavado está llena. No se puede agregar: " + v);
            return false;
        }
    }

    // Procesar el lavado de un vehículo
    public Carro procesarLavado() {
        if (!colaLavado.isEmpty()) {
            return colaLavado.poll(); // Retira el vehículo más antiguo en la cola
        }
        return null;
    }

    // Verifica si hay espacio en la máquina de lavado
    public boolean hayEspacio() {
        return colaLavado.size() < CAPACIDAD_MAXIMA;
    }

    // Muestra el estado actual de la máquina de lavado
    public void mostrarColaLavado() {
        System.out.println("🚿 Estado de la Máquina de Lavado:");
        for (Carro v : colaLavado) {
            System.out.println("   " + v);
        }
    }
}
