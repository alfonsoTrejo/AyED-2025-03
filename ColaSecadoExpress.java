package com.mycompany.algoritmos2025lab3;

import java.util.LinkedList;
import java.util.Queue;

public class ColaSecadoExpress {
    private Queue<Carro> colaSecado;
    private final int CAPACIDAD_MAXIMA = 5;

    public ColaSecadoExpress() {
        this.colaSecado = new LinkedList<>();
    }

    // Agregar un vehículo a la línea de secado express
    public boolean agregarCarro(Carro v) {
        if (colaSecado.size() < CAPACIDAD_MAXIMA) {
            colaSecado.add(v);
            return true;
        } else {
            System.out.println("⚠️ Línea de secado express llena. No se puede agregar: " + v);
            return false;
        }
    }

    // Procesar el secado de un vehículo
    public Carro procesarSecado() {
        if (!colaSecado.isEmpty()) {
            return colaSecado.poll(); // Retira el vehículo más antiguo en la cola
        }
        return null;
    }

    // Verifica si hay espacio en la línea de secado express
    public boolean hayEspacio() {
        return colaSecado.size() < CAPACIDAD_MAXIMA;
    }

    // Muestra el estado actual de la línea de secado express
    public void mostrarColaSecado() {
        System.out.println("💨 Estado de la Línea de Secado Express:");
        for (Carro v : colaSecado) {
            System.out.println("   " + v);
        }
    }

    // Obtener la cantidad de vehículos en la línea
    public int getCantidadCarros() {
        return colaSecado.size();
    }
}
