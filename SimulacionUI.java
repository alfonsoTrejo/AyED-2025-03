package com.mycompany.algoritmos2025lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimulacionUI extends JFrame {
    private JTextArea estadoColas;
    private JLabel tiempoLabel;
    private JLabel totalProcesados;
    private JButton verLogBtn;
    private JSlider velocidadSlider;
    private Simulacion simulacion;

    public SimulacionUI(Simulacion simulacion) {
        this.simulacion = simulacion;

        setTitle("Simulador de Autolavado");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        tiempoLabel = new JLabel("Hora actual: 00:00:00");
        topPanel.add(tiempoLabel, BorderLayout.WEST);

        velocidadSlider = new JSlider(1, 1000, 200);
        velocidadSlider.setMajorTickSpacing(200);
        velocidadSlider.setPaintTicks(true);
        velocidadSlider.setPaintLabels(true);
        topPanel.add(new JLabel("Velocidad"), BorderLayout.CENTER);
        topPanel.add(velocidadSlider, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        estadoColas = new JTextArea();
        estadoColas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(estadoColas);
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

        setVisible(true);
    }

    public int getVelocidad() {
        return velocidadSlider.getValue();
    }

    public void actualizarEstado(String hora, String estado, int total) {
        tiempoLabel.setText("Hora actual: " + hora);
        estadoColas.setText(estado);
        totalProcesados.setText("Total procesados: " + total);
    }
} 
 {
    
}
