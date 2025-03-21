package com.mycompany.algoritmos2025lab3;

import java.util.LinkedList;
import java.util.Queue;

public class ColaAspirado {
    private Queue<Carro> colaAspirado;
    private final int CAPACIDAD_MAXIMA = 4;

    public ColaAspirado() {
        this.colaAspirado = new LinkedList<>();
    }

    // Agregar un vehículo a la línea de aspirado
    public boolean agregarCarro(Carro v) {
        if (colaAspirado.size() < CAPACIDAD_MAXIMA) {
            colaAspirado.add(v);
            return true;
        } else {
            System.out.println("⚠️ Línea de aspirado llena. No se puede agregar: " + v);
            return false;
        }
    }

    // Procesar el aspirado de un vehículo
    public Carro procesarAspirado() {
        if (!colaAspirado.isEmpty()) {
            return colaAspirado.poll(); // Retira el vehículo más antiguo en la cola
        }
        return null;
    }

    // Verifica si hay espacio en la línea de aspirado
    public boolean hayEspacio() {
        return colaAspirado.size() < CAPACIDAD_MAXIMA;
    }

    // Muestra el estado actual de la línea de aspirado
    public void mostrarColaAspirado() {
        System.out.println("🌀 Estado de la Línea de Aspirado:");
        for (Carro v : colaAspirado) {
            System.out.println("   " + v);
        }
    }

    // Obtener la cantidad de vehículos en la línea
    public int getCantidadCarros() {
        return colaAspirado.size();
    }
}
