package com.mycompany.algoritmos2025lab3;

import java.util.ArrayList;

public class Simulacion {
    private int conColaAingresar;
    private timStamp tiempoInicio;
    private timStamp tiempoFin;
    private Cola<Vehiculo>[] lineaAspirado;
    private Cola<Vehiculo> lineaExpress;
    private Cola<Vehiculo> lineaAcceso;
    private Cola<Vehiculo> lineaLavado;
    private ArrayList<Vehiculo> logVehiculosLavados = new ArrayList<>();

    private SimulacionUI ui;

    public void setUI(SimulacionUI ui) {
        this.ui = ui;
    }

    public ArrayList<Vehiculo> getLogVehiculosLavados() {
        return logVehiculosLavados;
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

    public void iniciarSimulacion() {
        conColaAingresar = 0;
        lineaAspirado = new Cola[4];
        for (int i = 0; i < 4; i++) {
            lineaAspirado[i] = new Cola<>(4);
        }
        lineaExpress = new Cola<>(5);
        lineaAcceso = new Cola<>(10);
        lineaLavado = new Cola<>(3);

        tiempoInicio = new timStamp(8, 0, 0);
        tiempoFin = new timStamp(18, 1, 0);

        int minutosPasados = 0;
        int minutosParaProximoVehiculo = 2 + (int) (Math.random() * 7);

        while (tiempoInicio.comparar(tiempoFin) == -1) {
            if (tiempoInicio.comparar(new timStamp(17, 45, 0)) != 1 &&
                minutosPasados % minutosParaProximoVehiculo == 0 &&
                lineaAcceso.tamano() < 10) {

                Vehiculo nuevoVehiculo = generarVehiculo();
                if (nuevoVehiculo.isClientePreferente()) {
                    lineaAcceso.agregarAlInicio(nuevoVehiculo);
                } else {
                    lineaAcceso.agregar(nuevoVehiculo);
                }

                minutosParaProximoVehiculo = 2 + (int) (Math.random() * 7);
                minutosPasados = 0;
            }

            if (!lineaAcceso.estaVacia()) {
                if (tiempoInicio.minutosDesde(lineaAcceso.obtenerFrente().getHoraLlegada()) >= 1) {
                    if (lineaLavado.tamano() < 3) {
                        Vehiculo vehiculo = lineaAcceso.quitar();
                        vehiculo.setTiempoLLegadaLavado(tiempoInicio.getHoraCompleta());
                        lineaLavado.agregar(vehiculo);
                    }
                }
            }
            if (!lineaLavado.estaVacia()) {
                if (tiempoInicio.minutosDesde(lineaLavado.obtenerFrente().getTiempoLLegadaLavado()) >= 3) {
                    Vehiculo vehiculo = lineaLavado.obtenerFrente();
                    boolean agregado = false;

                    if (vehiculo.getServicio() == Vehiculo.Servicio.ASPIRADO) {
                        vehiculo.setTiempoLLegadaAspirado(tiempoInicio.getHoraCompleta());
                        int indexMenor = 0;
                        int minTamano = lineaAspirado[0].tamano();
                        
                        for (int i = 1; i < lineaAspirado.length; i++) {
                            if (lineaAspirado[i].tamano() < minTamano) {
                                minTamano = lineaAspirado[i].tamano();
                                indexMenor = i;
                            }
                        }
                        
                        agregado = lineaAspirado[indexMenor].agregar(vehiculo);
                    } else if (vehiculo.getServicio() == Vehiculo.Servicio.SECADO_EXPRESS) {
                        vehiculo.setTiempoLLegadaAspirado(tiempoInicio.getHoraCompleta());
                        agregado = lineaExpress.agregar(vehiculo);
                    }

                    if (agregado) {
                        lineaLavado.quitar();
                        conColaAingresar++;
                    }
                }
            }
            if(true){
                for (int i = 0; i < 4; i++) {
                    if (!lineaAspirado[i].estaVacia()) {
                        Vehiculo vehiculo = lineaAspirado[i].obtenerFrente();
                        int minutosEsperados = switch (vehiculo.getTamano()) {
                            case PEQUENO -> 5;
                            case MEDIANO -> 7;
                            case GRANDE -> 10;
                        };

                        if (tiempoInicio.minutosDesde(vehiculo.getTiempoLLegadaAspirado()) >= minutosEsperados) {
                            vehiculo.setHoraSalida(tiempoInicio.getHoraCompleta());
                            logVehiculosLavados.add(vehiculo);
                            lineaAspirado[i].quitar();
                        }
                    }
                }

                if (!lineaExpress.estaVacia()) {
                    Vehiculo vehiculo = lineaExpress.obtenerFrente();
                    if (tiempoInicio.minutosDesde(vehiculo.getTiempoLLegadaAspirado()) >= 3) {
                        vehiculo.setHoraSalida(tiempoInicio.getHoraCompleta());
                        logVehiculosLavados.add(vehiculo);
                        lineaExpress.quitar();
                    }
                }
            }
            tiempoInicio.incrementarMinuto();
            minutosPasados++;

            if (ui != null) {
                StringBuilder estado = new StringBuilder();
                estado.append("Cola Acceso: ").append(lineaAcceso.toString()).append("\n");
                estado.append("Cola Lavado: ").append(lineaLavado.toString()).append("\n");
                for (int i = 0; i < 4; i++) {
                    estado.append("Aspirado ").append(i).append(": ").append(lineaAspirado[i].toString()).append("\n");
                }
                estado.append("Express: ").append(lineaExpress.toString()).append("\n");

                ui.actualizarEstado(tiempoInicio.getHoraCompleta().toString(), construirEstadoVisual(), logVehiculosLavados.size());
                
            }

            try {
                Thread.sleep(ui != null ? ui.getVelocidad() : 200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\\n\\n🚘 Vehículos lavados al final del día:");
        for (Vehiculo v : logVehiculosLavados) {
            System.out.println(v);
        }
        System.out.println("Total: " + logVehiculosLavados.size() + " vehículos.");
        if (ui != null) {
            ui.mostrarLogFinal();
        }
    }
    private String[][][] construirEstadoVisual() {
        String[][][] estado = new String[7][][];
    
        estado[0] = obtenerEstadoCola(lineaAcceso, 10);
        estado[1] = obtenerEstadoCola(lineaLavado, 3);
        for (int i = 0; i < 4; i++) {
            estado[2 + i] = obtenerEstadoCola(lineaAspirado[i], 4);
        }
        estado[6] = obtenerEstadoCola(lineaExpress, 5);
    
        return estado;
    }
    
    private String[][] obtenerEstadoCola(Cola<Vehiculo> cola, int maxTam) {
        String[][] datos = new String[maxTam][];
        for (int i = 0; i < maxTam; i++) {
            if (i < cola.tamano()) {
                Vehiculo v = cola.obtenerElemento(i); // Necesitas tener este método en tu clase Cola
                if (v != null) {
                    datos[i] = new String[]{
                        v.getTamano().toString(),
                        v.getServicio().toString(),
                        Boolean.toString(v.isClientePreferente())
                    };
                }
            } else {
                datos[i] = null;
            }
        }
        return datos;
    }
    public static void main(String[] args) {
        Simulacion simulacion = new Simulacion();
        SimulacionUI ui = new SimulacionUI(simulacion);
        simulacion.setUI(ui);

        new Thread(() -> simulacion.iniciarSimulacion()).start();
    }
}
