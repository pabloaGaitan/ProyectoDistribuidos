/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.main.SistemaCliente;
import java.util.List;

/**
 *
 * @author redes
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
    
    public void run(){
        sistema.enviarMensaje(servs, opc, total, intervalo, periodico);
        sistema.getInfo().getAcep().setEnabled(true);
    }
}
