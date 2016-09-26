/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.main.SistemaCliente;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author redes
 * Este hilo se encarga de poner la informacion en la tabla conforme va llegando
 */
public class RecibirThread extends Thread implements Runnable{
    
    private SistemaCliente sistema;
    private List<Integer> servs;
    private List<String> opc;
    private List<String> total;
    private List<String> intervalo;
    private List<Boolean> periodico;
    
    public RecibirThread(SistemaCliente sistema,List<Integer> servs,List<String> opc,List<String> total,List<String> intervalo,List<Boolean> periodico){
        this.sistema = sistema;
        this.servs = servs;
        this.opc = opc;
        this.total = total;
        this.intervalo = intervalo;
        this.periodico = periodico;
    }
    /**
     * Se encarga de enviar, recibir y actualizar la tabla conforme llega un mensaje
     */
    public void run(){
        try{
            sistema.enviarMensaje(servs, opc, total, intervalo, periodico);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        sistema.getInfo().getAcep().setEnabled(true);
    }
}
