package com.mycompany.algoritmos2025lab3;

public class timStamp {
    private int hora;
    private int minuto;
    private int segundo;

    public timStamp(int hora, int minuto, int segundo) {
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getSegundo() {
        return segundo;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }
    public void incrementar() {
        segundo++;
        if (segundo == 60) {
            segundo = 0;
            minuto++;
            if (minuto == 60) {
                minuto = 0;
                hora++;
                if (hora == 24) {
                    hora = 0;
                }
            }
        }
    }
    public void incrementarMinuto() {
        minuto++;
        if (minuto == 60) {
            minuto = 0;
            hora++;
            if (hora == 24) {
                hora = 0;
            }
        }
    }
    public void incrementarHora() {
        hora++;
        if (hora == 24) {
            hora = 0;
        }
    }
    public void decrementar() {
        segundo--;
        if (segundo < 0) {
            segundo = 59;
            minuto--;
            if (minuto < 0) {
                minuto = 59;
                hora--;
                if (hora < 0) {
                    hora = 23;
                }
            }
        }
    }
    public void decrementarMinuto() {
        minuto--;
        if (minuto < 0) {
            minuto = 59;
            hora--;
            if (hora < 0) {
                hora = 23;
            }
        }
    }
    public void decrementarHora() {
        hora--;
        if (hora < 0) {
            hora = 23;
        }
    }
    public int comparar(timStamp otro) {
        if (this.hora == otro.hora && this.minuto == otro.minuto && this.segundo == otro.segundo) {
            return 0; // Son iguales
        } else if (this.hora < otro.hora || (this.hora == otro.hora && this.minuto < otro.minuto) || (this.hora == otro.hora && this.minuto == otro.minuto && this.segundo < otro.segundo)) {
            return -1; // this es menor que otro
        } else {
            return 1; // this es mayor que otro
        }
    }
    public int minutosDesde(timStamp otro) {
        int thisTotal = this.hora * 60 + this.minuto;
        int otroTotal = otro.hora * 60 + otro.minuto;
        return thisTotal - otroTotal;
    }
    public int segundosDesde(timStamp otro) {
        int thisTotal = this.hora * 3600 + this.minuto * 60 + this.segundo;
        int otroTotal = otro.hora * 3600 + otro.minuto * 60 + otro.segundo;
        return thisTotal - otroTotal;
    }
    public timStamp getHoraCompleta(){
        return new timStamp(hora, minuto, segundo);
    }
    public String toString() {
        return String.format("%02d:%02d:%02d", hora, minuto, segundo);
    }
}
