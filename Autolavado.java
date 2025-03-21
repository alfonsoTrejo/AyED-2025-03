package com.mycompany.algoritmos2025lab3;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Autolavado {
    private ColaPrioridadVehiculos acceso;
    private ColaLavado lavado;
    private ColaAspirado[] aspirado;
    private ColaSecadoExpress secadoExpress;
    private List<Carro> registroServicios;
    private LocalTime tiempoActual;
    private final LocalTime HORA_INICIO = LocalTime.of(8, 0);
    private final LocalTime HORA_FIN = LocalTime.of(18, 0);
    private Random random;

    public Autolavado() {
        this.acceso = new ColaPrioridadVehiculos();
        this.lavado = new ColaLavado();
        this.aspirado = new ColaAspirado[4];
        for (int i = 0; i < 4; i++) {
            this.aspirado[i] = new ColaAspirado();
        }
        this.secadoExpress = new ColaSecadoExpress();
        this.registroServicios = new ArrayList<>();
        this.tiempoActual = HORA_INICIO;
        this.random = new Random();
    }

    // Inicia la simulación del autolavado
    public void iniciarSimulacion() {
        while (!tiempoActual.isAfter(HORA_FIN) || hayCarrosPendientes()) {
            System.out.println("\n🕒 Hora simulada: " + tiempoActual);
            
            generarCarro();
            procesarLavado();
            distribuirCarros();
            actualizarServicios();
            mostrarEstado();

            // Incrementar el tiempo en 1 minuto
            tiempoActual = tiempoActual.plusMinutes(1);
            
            // Simular un pequeño retardo para visualizar el proceso
            try {
                Thread.sleep(500); // Se puede ajustar la velocidad de la simulación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n🚗 Jornada finalizada. Mostrando registro de servicios:");
        mostrarRegistroFinal();
    }

    // Genera un nuevo vehículo aleatorio cada cierto tiempo
    private void generarCarro() {
        if (random.nextInt(7) + 2 == 2) { // Genera un nuevo vehículo entre 2 y 8 minutos
            String tamaño = obtenerTamañoAleatorio();
            String servicio = obtenerServicioAleatorio();
            boolean preferente = random.nextBoolean();

            Carro nuevoCarro = new Carro(tamaño, servicio, preferente, tiempoActual);
            if (!acceso.agregarCarro(nuevoCarro)) {
                System.out.println("❌ Vehículo rechazado, cola de acceso llena: " + nuevoCarro);
            }
        }
    }

    // Procesa los vehículos en la máquina de lavado
    private void procesarLavado() {
        if (lavado.hayEspacio() && !acceso.estaLlena()) {
            Carro siguiente = acceso.obtenerSiguienteCarro();
            if (siguiente != null && hayEspacioEnColaFinal(siguiente)) {
                lavado.agregarCarro(siguiente);
                System.out.println("🚿 Vehículo ingresó a lavado: " + siguiente);
            }
        }
    }

    // Distribuye los vehículos a las líneas de aspirado o secado express
    private void distribuirCarros() {
        Carro CarroLavado = lavado.procesarLavado();
        if (CarroLavado != null) {
            if (CarroLavado.getServicio().equals("aspirado")) {
                ColaAspirado mejorLinea = obtenerLineaAspiradoDisponible();
                if (mejorLinea != null) {
                    mejorLinea.agregarCarro(CarroLavado);
                    System.out.println("🌀 Vehículo enviado a aspirado: " + CarroLavado);
                }
            } else {
                if (secadoExpress.hayEspacio()) {
                    secadoExpress.agregarCarro(CarroLavado);
                    System.out.println("💨 Vehículo enviado a secado express: " + CarroLavado);
                }
            }
        }
    }

    // Actualiza los servicios en aspirado y secado
    private void actualizarServicios() {
        for (ColaAspirado linea : aspirado) {
            Carro aspiradoCompletado = linea.procesarAspirado();
            if (aspiradoCompletado != null) {
                aspiradoCompletado.setHoraSalida(tiempoActual);
                registroServicios.add(aspiradoCompletado);
                System.out.println("✅ Vehículo finalizó aspirado: " + aspiradoCompletado);
            }
        }

        Carro secadoCompletado = secadoExpress.procesarSecado();
        if (secadoCompletado != null) {
            secadoCompletado.setHoraSalida(tiempoActual);
            registroServicios.add(secadoCompletado);
            System.out.println("✅ Vehículo finalizó secado express: " + secadoCompletado);
        }
    }

    // Muestra el estado actual del autolavado
    private void mostrarEstado() {
        System.out.println("\n🔹 Estado del Autolavado:");
        acceso.mostrarColaAcceso();
        lavado.mostrarColaLavado();
        for (int i = 0; i < 4; i++) {
            System.out.println("Aspirado Línea " + (i + 1) + ":");
            aspirado[i].mostrarColaAspirado();
        }
        secadoExpress.mostrarColaSecado();
    }

    // Obtiene una línea de aspirado con espacio disponible
    private ColaAspirado obtenerLineaAspiradoDisponible() {
        ColaAspirado mejorOpcion = null;
        int minCarros = Integer.MAX_VALUE;

        for (ColaAspirado linea : aspirado) {
            int cantidad = linea.getCantidadCarros();
            if (cantidad < 4 && cantidad < minCarros) {
                mejorOpcion = linea;
                minCarros = cantidad;
            }
        }
        return mejorOpcion;
    }

    // Verifica si hay espacio en la línea final de servicio del vehículo
    private boolean hayEspacioEnColaFinal(Carro v) {
        if (v.getServicio().equals("aspirado")) {
            return obtenerLineaAspiradoDisponible() != null;
        } else {
            return secadoExpress.hayEspacio();
        }
    }

    // Verifica si hay vehículos pendientes en el sistema
    private boolean hayCarrosPendientes() {
        return !acceso.estaLlena() || lavado.hayEspacio() || secadoExpress.hayEspacio();
    }

    // Muestra el registro de los vehículos atendidos
    private void mostrarRegistroFinal() {
        for (Carro v : registroServicios) {
            System.out.println(v);
        }
    }

    // Genera un tamaño de vehículo aleatorio basado en las probabilidades dadas
    private String obtenerTamañoAleatorio() {
        int probabilidad = random.nextInt(100);
        if (probabilidad < 50) return "pequeño";
        if (probabilidad < 85) return "mediano";
        return "grande";
    }

    // Genera un servicio aleatorio basado en las probabilidades dadas
    private String obtenerServicioAleatorio() {
        return (random.nextInt(100) < 80) ? "aspirado" : "secado express";
    }
}
