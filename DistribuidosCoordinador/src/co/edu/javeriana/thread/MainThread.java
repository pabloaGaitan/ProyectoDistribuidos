/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import co.edu.javeriana.data.ServidorPuerto;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class MainThread extends Thread implements Runnable{
    
    private static List<ServidorPuerto> servidores;
    
    public MainThread(){
        /*colaMensajes = new LinkedList<>();
        colaSockets = new LinkedList<>();*/
        servidores = new ArrayList<>();
    }
    
    @Override
    public void run(){
        ServerSocket socket = null;
        try{
            // puerto diferente
            socket = new ServerSocket(1595);
            while(true){
                Socket cliente = socket.accept();
                // Se hace que el cierre del socket sea "gracioso". Esta llamada sólo
                // es necesaria si cerramos el socket inmediatamente después de
                // enviar los datos (como en este caso).
                // setSoLinger() a true hace que el cierre del socket espere a que
                // el cliente lea los datos, hasta un máximo de 10 segundos de espera.
                // Si no ponemos esto, el socket se cierra inmediatamente y si el 
                // cliente no ha tenido tiempo de leerlos, los datos se pierden.
                //cliente.setSoLinger (true, 10);
                ObjectInputStream buffer = new ObjectInputStream(cliente.getInputStream());
                DataObject data = (DataObject)buffer.readObject();
                Set<Integer> setKey = data.getMensaje().keySet();
                for (Integer s : setKey) {
                    SendThread sent = new SendThread(cliente, copy(data,s-1),s-1);
                    sent.start();
                }
                // Se cierra el socket con el cliente.
                // La llamada anterior a setSoLinger() hará
                // que estos cierres esperen a que el cliente retire los datos.
                //cliente.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static DataObject copy(DataObject obj, int id){
        DataObject d = new DataObject();
        d.setIdServidor(id+1);
        d.setIntervalo(obj.getIntervalo());
        d.setIpSolicitante(obj.getIpSolicitante());
        d.setMensaje(obj.getMensaje());
        d.setOperacion(obj.getOperacion());
        d.setPeriodica(obj.isPeriodica());
        d.setUltimo(obj.isUltimo());
        d.setTiempoTotal(obj.getTiempoTotal());
        return d;
    }
    
    public synchronized static List<ServidorPuerto> getServidores(){
        return servidores;
    }
}