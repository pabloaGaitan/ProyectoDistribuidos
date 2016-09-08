/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ClienteThread extends Thread implements Runnable{
    
    private static Queue<DataObject> colaMensajes;
    
    public ClienteThread(){
        colaMensajes = new LinkedList<>();
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
                cliente.setSoLinger (true, 10);
                
                ObjectInputStream buffer = new ObjectInputStream(cliente.getInputStream());
                DataObject data = (DataObject)buffer.readObject();
                colaMensajes.add(data);
                // Se cierra el socket con el cliente.
                // La llamada anterior a setSoLinger() hará
                // que estos cierres esperen a que el cliente retire los datos.
                cliente.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServidorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public synchronized static Queue<DataObject> getColaMensajes(){
        return colaMensajes;
    }
}