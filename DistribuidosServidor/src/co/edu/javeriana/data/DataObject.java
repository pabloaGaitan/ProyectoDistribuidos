package co.edu.javeriana.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class DataObject implements Serializable{
    
    /**
     * 1 = Registrarse (Servidores)
     * 2 = Cant. Servidores (Cliente)
     * 3 = Sol. Recursos (Cliente)
     * 4 = Reply
     */
    
    private int operacion; 
    private String ipSolicitante;
    private boolean periodica;
    private int tiempoTotal;
    private int intervalo;
    
    /**
     * Esta información es relevante cuando el tipo de operación es 3
     * 1 = Servidores al cual solicita \ servidor respuesta
     * 2 = Uso de CPU
     * 3 = Memoria física disponible
     * 4 = total de memoria fisica
     */
    private Map<Integer,String> mensaje;

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getIpSolicitante() {
        return ipSolicitante;
    }

    public void setIpSolicitante(String ipSolicitante) {
        this.ipSolicitante = ipSolicitante;
    }

    public boolean isPeriodica() {
        return periodica;
    }

    public void setPeriodica(boolean periodica) {
        this.periodica = periodica;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(int tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public Map<Integer, String> getMensaje() {
        return mensaje;
    }

    public void setMensaje(Map<Integer, String> mensaje) {
        this.mensaje = mensaje;
    }
}
