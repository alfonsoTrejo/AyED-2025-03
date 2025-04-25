package com.mycompany.algoritmos2025lab3;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Simulacion {
    private int conColaAingresar; // contador que ayuda a saber en que cola se va a ingresar el vehiculo
    private int tiempoEspera; // delay emtre cada incremento de segundo
    private timStamp tiempoInicio;
    private timStamp tiempoFin;
    private Cola<Vehiculo>[] lineaAspirado;
    private Cola<Vehiculo> lineaExpress;
    private Cola<Vehiculo> lineaAcceso;
    private Cola<Vehiculo> lineaLavado;
    //Log historico de todos los vehiculos lavados
    private ArrayList<Vehiculo> logVehiculosLavados = new ArrayList<>();

    public timStamp getTiempoInicio() {
        return tiempoInicio;
    }
    public timStamp getTiempoFin() {
        return tiempoFin;
    }

    public Cola<Vehiculo>[] getLineaAspirado() {
        return lineaAspirado;
    }

    public Cola<Vehiculo> getLineaExpress() {
        return lineaExpress;
    }

    public Cola<Vehiculo> getLineaAcceso() {
        return lineaAcceso;
    }

    public Cola<Vehiculo> getLineaLavado() {
        return lineaLavado;
    }

    public void setTiempoInicio(timStamp tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public void setTiempoFin(timStamp tiempoFin) {
        this.tiempoFin = tiempoFin;
    }

    public void setLineaAspirado(Cola<Vehiculo>[] lineaAspirado) {
        this.lineaAspirado = lineaAspirado;
    }

    public void setLineaExpress(Cola<Vehiculo> lineaExpress) {
        this.lineaExpress = lineaExpress;
    }

    public void setLineaAcceso(Cola<Vehiculo> lineaAcceso) {
        this.lineaAcceso = lineaAcceso;
    }

    public void setLineaLavado(Cola<Vehiculo> lineaLavado) {
        this.lineaLavado = lineaLavado;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public Vehiculo generarVehiculo() {
        double probTamano = Math.random();
        Vehiculo.Tamano tamano;
        if (probTamano < 0.5) {
            tamano = Vehiculo.Tamano.PEQUENO;
        } else if (probTamano < 0.85) {
            tamano = Vehiculo.Tamano.MEDIANO;
        } else {
            tamano = Vehiculo.Tamano.GRANDE;
        }

        double probServicio = Math.random();
        Vehiculo.Servicio servicio = (probServicio < 0.8) ? Vehiculo.Servicio.ASPIRADO
                : Vehiculo.Servicio.SECADO_EXPRESS;

        boolean clientePreferente = Math.random() < 0.5;

        return new Vehiculo(tamano, servicio, clientePreferente, tiempoInicio.getHoraCompleta());
    }

    // main
    public static void main(String[] args) {

        // Crear una instancia de la clase Simulacion
        Simulacion simulacion = new Simulacion();

        // inicializar el contador de colas
        simulacion.conColaAingresar = 0; // contador que ayuda a saber en que cola se va a ingresar el vehiculo

        // Inicializar el tiempo de espera
        int tiempoEspera = 200; // 1 segundo

        // Crear las colas
        simulacion.lineaAspirado = new Cola[4];
        for (int i = 0; i < 4; i++) {
            simulacion.lineaAspirado[i] = new Cola<>(4);
        }
        simulacion.lineaExpress = new Cola<>(5);
        simulacion.lineaAcceso = new Cola<>(10);
        simulacion.lineaLavado = new Cola<>(3);

        // Inicializar el tiempo de llegada
        simulacion.tiempoInicio = new timStamp(8, 0, 0);
        simulacion.tiempoFin = new timStamp(16, 1, 0);

        int minutosPasados = 0;
        int minutosParaProximoVehiculo = 2 + (int) (Math.random() * 7); // 2 a 8
        int deltaMaquinaLavado = 0; // Variable para almacenar el tiempo de espera en minutos

        // Incrementar el tiempo de llegada
        while (simulacion.tiempoInicio.comparar(simulacion.tiempoFin) == -1) {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();       
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            
            
            System.out.println("Tiempo actual: " + simulacion.tiempoInicio.getHora() + ":"
                    + simulacion.tiempoInicio.getMinuto() + ":" + simulacion.tiempoInicio.getSegundo());

            // se genera un nuevo vehiculo cada 2 a 8 minutos
            if (minutosPasados % minutosParaProximoVehiculo == 0 && simulacion.lineaAcceso.tamano() < 10) {
                System.out.println("\n\n\n" + "se genero carro a los " + minutosParaProximoVehiculo + " minutos\n\n\n");
                Vehiculo nuevoVehiculo = simulacion.generarVehiculo();

                if (nuevoVehiculo.isClientePreferente()) {
                    simulacion.lineaAcceso.agregarAlInicio(nuevoVehiculo);
                } else {
                    simulacion.lineaAcceso.agregar(nuevoVehiculo);
                }
                
                // Actualizamos el tiempo para el siguiente vehículo
                minutosParaProximoVehiculo = 2 + (int) (Math.random() * 7);
                minutosPasados = 0;
            }

            // si la cola de acceso no esta vacia y la cola de lavado tiene espacio, se agrega el vehiculo a la cola de lavado
            if (!simulacion.lineaAcceso.estaVacia()){
                if(simulacion.lineaLavado.tamano() < 3){
                    Vehiculo vehiculo = simulacion.lineaAcceso.quitar();
                    System.out.println("Procesando vehículo: " + vehiculo.toString());
                    vehiculo.setTiempoLLegadaLavado(simulacion.tiempoInicio.getHoraCompleta());
                    simulacion.lineaLavado.agregar(vehiculo);
                }
            }

            //Despues de 3 minutos se agrega el vehiculo a la cola de aspirado
            if (!simulacion.lineaLavado.estaVacia()) {
                if (simulacion.tiempoInicio.minutosDesde(simulacion.lineaLavado.obtenerFrente().getTiempoLLegadaLavado()) >= 3) {
                    Vehiculo vehiculo = simulacion.lineaLavado.obtenerFrente();
                    System.out.println("Procesando vehículo linea lavado: " + vehiculo.toString());
            
                    boolean agregado = false;
            
                    if (vehiculo.getServicio() == Vehiculo.Servicio.ASPIRADO) {
                        vehiculo.setTiempoLLegadaAspirado(simulacion.tiempoInicio.getHoraCompleta());
                        agregado = simulacion.lineaAspirado[simulacion.conColaAingresar % 4].agregar(vehiculo);
                    } else if (vehiculo.getServicio() == Vehiculo.Servicio.SECADO_EXPRESS) {
                        vehiculo.setTiempoLLegadaAspirado(simulacion.tiempoInicio.getHoraCompleta());
                        agregado = simulacion.lineaExpress.agregar(vehiculo);
                    }
            
                    if (agregado) {
                        simulacion.lineaLavado.quitar();
                        simulacion.conColaAingresar++;
                        System.out.println("Tiempokoooooo:" + simulacion.conColaAingresar);
                    }
                }
            }
            
            // procesar la cola de aspirado y la de secado express
//            for (int i = 0; i < 4; i++) {
//                if (!simulacion.lineaAspirado[i].estaVacia()) {
//                    if (simulacion.tiempoInicio.minutosDesde(simulacion.lineaAspirado[i].obtenerFrente().getTiempoLLegadaAspirado()) >= 3) {
//                        Vehiculo vehiculo = simulacion.lineaAspirado[i].quitar();
//                        simulacion.logVehiculosLavados.add(vehiculo);
//                    }
//                }
//            }
            



            simulacion.tiempoInicio.incrementarMinuto();
            minutosPasados++;
            
            System.out.println("cola linea de acceso");
            System.out.println(simulacion.lineaAcceso);
            System.out.println("cola linea de lavada");
            System.out.println(simulacion.lineaLavado.toString());
            System.out.println("cola linea de aspirado");
            for (int i = 0; i < 4; i++) {
                System.out.println("Cola " + i + " : ");
                System.out.println(simulacion.lineaAspirado[i].toString());
            }
            System.out.println("cola linea de express");
            System.out.println(simulacion.lineaExpress.toString());
            System.out.println("minutos pasdos" + deltaMaquinaLavado);
            try {
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("NOs fuimos a la verga");
    }
}
