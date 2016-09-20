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
     */
    private int operacion;
    private int idServidor;
    private String ipSolicitante;
    
    /**
     * Esta información es relevante cuando el tipo de operación es 3
     * 1 = Servidores al cual solicita \ servidor respuesta
     * 2 = Arquitectura
     * 3 = Cores
     * 4 = total de RAM
     * 5 = Consumo total de cpu
     * 6 = Memoria RAM usada
     * 7 = Memoria RAM disponible
     * 8 = sistema de archivos
     * 9 = Periodico = 1, No periodico = 0
     * 10 = Tiempo Total
     * 11 = Intervalo
     * 404 = Servidor caido
     * 405 = servidor no existe
     */
    private Map<Integer, Map<Integer,String>> mensaje;

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

    public Map<Integer,Map<Integer, String>> getMensaje() {
        return mensaje;
    }

    public void setMensaje(Map<Integer,Map<Integer, String>> mensaje) {
        this.mensaje = mensaje;
    }

    public int getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(int idServidor) {
        this.idServidor = idServidor;
    }
}
