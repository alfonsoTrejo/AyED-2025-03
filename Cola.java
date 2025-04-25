package com.mycompany.algoritmos2025lab3;

public class Cola<T> {
    private T[] cola;
    private int frente = 0;
    private int fin = 0;
    private int tamaño = 0;

    public Cola(int capacidad) {
        cola = (T[]) new Object[capacidad];
    }

    public Cola() {
        this(10);
    }

    public boolean agregar(T elemento) {
        if (tamaño == cola.length) {
            return false; // Cola llena
        }
        cola[fin] = elemento;
        fin = (fin + 1) % cola.length;
        tamaño++;
        return true;
    }

    public boolean agregarAlInicio(T elemento) {
        if (tamaño == cola.length) {
            return false; // Cola llena
        }
        frente = (frente - 1 + cola.length) % cola.length;
        cola[frente] = elemento;
        tamaño++;
        return true;
    }

    public T quitar() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }
        T elemento = cola[frente];
        cola[frente] = null;
        frente = (frente + 1) % cola.length;
        tamaño--;
        return elemento;
    }

    public boolean estaVacia() {
        return tamaño == 0;
    }

    public int tamano() {
        return tamaño;
    }

    public T obtenerFrente() {
        if (estaVacia()) {
            throw new RuntimeException("La cola está vacía");
        }
        return cola[frente];
    }

    public T[] obtenerCola() {
        T[] resultado = (T[]) new Object[tamaño];
        for (int i = 0; i < tamaño; i++) {
            resultado[i] = cola[(frente + i) % cola.length];
        }
        return resultado;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cola: ");
        for (int i = 0; i < tamaño; i++) {
            sb.append(cola[(frente + i) % cola.length]).append("\n");
        }
        return sb.toString();
    }
}
