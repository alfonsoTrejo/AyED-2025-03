package com.mycompany.algoritmos2025lab3;

public class tes {
    //preuba para saber si la cola agrega y elimina bien 
    public static void main(String[] args) {
        Cola<Integer> cola = new Cola<>(3);
    
        cola.agregar(1);
        cola.agregar(2);
        cola.agregar(3);
        cola.agregar(4);
        cola.quitar();
        cola.quitar();
        System.out.println(cola.toString());

        cola.agregar(5);
        cola.agregar(6);
        
        System.out.println(cola.toString());
    }
}
