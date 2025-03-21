package com.mycompany.algoritmos2025lab3;

import java.util.PriorityQueue;
import java.util.Comparator;

public class ColaPrioridadVehiculos {
    private PriorityQueue<Carro> colaAcceso;
    private final int CAPACIDAD_MAXIMA = 10;

    // Constructor
    public ColaPrioridadVehiculos() {
        this.colaAcceso = new PriorityQueue<>(Comparator.comparing(Carro::esPreferente).reversed());
    }

    // Agregar un vehículo a la cola
    public boolean agregarCarro(Carro v) {
        if (colaAcceso.size() < CAPACIDAD_MAXIMA) {
            colaAcceso.add(v);
            return true;
        } else {
            System.out.println("⚠️ La cola de acceso está llena. No se puede agregar: " + v);
            return false;
        }
    }

    // Obtener y remover el siguiente vehículo de la cola
    public Carro obtenerSiguienteCarro() {
        return colaAcceso.poll(); // Retorna el primero en la cola y lo elimina
    }

    // Verifica si la cola está llena
    public boolean estaLlena() {
        return colaAcceso.size() >= CAPACIDAD_MAXIMA;
    }

    // Mostrar el estado actual de la cola
    public void mostrarColaAcceso() {
        System.out.println("🚗 Estado de la Cola de Acceso:");
        for (Carro v : colaAcceso) {
            System.out.println("   " + v);
        }
    }
}
