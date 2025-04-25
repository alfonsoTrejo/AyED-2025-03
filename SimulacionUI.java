package com.mycompany.algoritmos2025lab3;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimulacionUI extends JFrame {
    private JPanel panelContenido;
    private JLabel tiempoLabel;
    private JLabel totalProcesados;
    private JButton verLogBtn;
    private JSlider velocidadSlider;
    private Simulacion simulacion;

    public SimulacionUI(Simulacion simulacion) {
        this.simulacion = simulacion;

        setTitle("Simulador de Autolavado");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        tiempoLabel = new JLabel("Hora actual: 00:00:00");
        topPanel.add(tiempoLabel, BorderLayout.WEST);

        velocidadSlider = new JSlider(10, 1000, 200);
        velocidadSlider.setMajorTickSpacing(250);
        velocidadSlider.setPaintTicks(true);
        velocidadSlider.setPaintLabels(true);
        topPanel.add(new JLabel("Velocidad"), BorderLayout.CENTER);
        topPanel.add(velocidadSlider, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalProcesados = new JLabel("Total procesados: 0");
        bottomPanel.add(totalProcesados, BorderLayout.WEST);

        verLogBtn = new JButton("Ver Log de Vehículos");
        bottomPanel.add(verLogBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        verLogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Vehiculo> log = simulacion.getLogVehiculosLavados();
                JTextArea logArea = new JTextArea();
                for (Vehiculo v : log) {
                    logArea.append(v.toString() + "\n");
                }
                logArea.setEditable(false);
                JScrollPane logScroll = new JScrollPane(logArea);
                logScroll.setPreferredSize(new Dimension(700, 500));

                JOptionPane.showMessageDialog(null, logScroll, "Log de Vehículos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel leyenda = new JPanel();
        leyenda.setLayout(new GridLayout(2, 1));
        leyenda.add(new JLabel("Colores: Azul = Pequeño, Verde = Mediano, Rojo = Grande. Gris = Vacío"));
        leyenda.add(new JLabel("Texto: A = Aspirado, E = Express | Borde dorado = Cliente preferente"));
        add(leyenda, BorderLayout.AFTER_LAST_LINE);

        setVisible(true);
    }

    public int getVelocidad() {
        return velocidadSlider.getValue();
    }

    public void actualizarEstado(String hora, String[][][] colas, int total) {
        tiempoLabel.setText("Hora actual: " + hora);
        totalProcesados.setText("Total procesados: " + total);
        panelContenido.removeAll();

        String[] nombres = {"Cola Acceso", "Cola Lavado", "Aspirado 0", "Aspirado 1", "Aspirado 2", "Aspirado 3", "Cola Express"};

        for (int i = 0; i < colas.length; i++) {
            JPanel linea = new JPanel();
            linea.setLayout(new FlowLayout(FlowLayout.LEFT));
            linea.add(new JLabel(nombres[i] + ": "));

            for (String[] celda : colas[i]) {
                JPanel cuadrado = new JPanel();
                cuadrado.setPreferredSize(new Dimension(50, 50));
                cuadrado.setBorder(new LineBorder(Color.BLACK));

                if (celda != null && celda.length == 3) {
                    String tamano = celda[0];
                    String servicio = celda[1];
                    boolean preferente = Boolean.parseBoolean(celda[2]);

                    switch (tamano) {
                        case "PEQUENO" -> cuadrado.setBackground(Color.BLUE);
                        case "MEDIANO" -> cuadrado.setBackground(Color.GREEN);
                        case "GRANDE" -> cuadrado.setBackground(Color.RED);
                    }

                    JLabel letra = new JLabel(servicio.equals("ASPIRADO") ? "A" : "E");
                    letra.setForeground(Color.WHITE);
                    cuadrado.add(letra);

                    if (preferente) {
                        cuadrado.setBorder(new LineBorder(Color.ORANGE, 3));
                    }
                } else {
                    cuadrado.setBackground(Color.LIGHT_GRAY);
                }

                linea.add(cuadrado);
            }
            panelContenido.add(linea);
        }

        panelContenido.revalidate();
        panelContenido.repaint();
    }
    
    public void mostrarLogFinal() {
        ArrayList<Vehiculo> log = simulacion.getLogVehiculosLavados();
        JTextArea logArea = new JTextArea();
    
        for (Vehiculo v : log) {
            logArea.append(v.toString() + "\n");
        }
    
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setPreferredSize(new Dimension(700, 500));
    
        JOptionPane.showMessageDialog(this, scroll, "Log Final de Vehículos", JOptionPane.INFORMATION_MESSAGE);
    }
}  
