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
 * En este hilo llegan todos los mensajes por parte de los clientes y servidores
 * de aquí se distribuye a un hilo para que se trate dicho mensaje.
 */
public class MainThread extends Thread implements Runnable{
    
    private static List<ServidorPuerto> servidores;
    
    public MainThread(){
        servidores = new ArrayList<>();
    }
    
    /**
     * Se encarga de ejecutar el listener de mensajes en el puerto 1595
     */
    @Override
    public void run(){
        ServerSocket socket = null;
        try{
            socket = new ServerSocket(1595);
            while(true){
                Socket cliente = socket.accept();
                ObjectInputStream buffer = new ObjectInputStream(cliente.getInputStream());
                DataObject data = (DataObject)buffer.readObject();
                Set<Integer> setKey = data.getMensaje().keySet();
                for (Integer s : setKey) {
                    SendThread sent = new SendThread(cliente, copy(data,s-1),s-1);
                    sent.start();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Crea una copia del objeto obj para que pueda ser distribuido entre
     * los diferentes hilos que lo requieren.
     * @param obj Objeto a copiar
     * @param id ID del servidor al cual se le está haciendo la copia
     * @return La copia del objeto
     */
    public static DataObject copy(DataObject obj, int id){
        DataObject d = new DataObject();
        d.setIdServidor(id+1);
        d.setIpSolicitante(obj.getIpSolicitante());
        d.setMensaje(obj.getMensaje());
        d.setOperacion(obj.getOperacion());
        return d;
    }
    
    /**
     * Obtiene la lista actual de servidores
     * @return Lista de servidores
     */
    public synchronized static List<ServidorPuerto> getServidores(){
        return servidores;
    }
}