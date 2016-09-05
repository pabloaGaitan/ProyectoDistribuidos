/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ServidorThread extends Thread implements Runnable{
    
    Map<String,Integer> servidores;
    Queue<DataObject> colaMensajes;
    
    public ServidorThread(){
        servidores = new HashMap<>();
        colaMensajes = new LinkedList<>();
    }
    
    @Override
    public void run(){
        ServerSocket socket = null;
        try{
            socket = new ServerSocket(1594);
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
                
                DataObject dato = (DataObject)buffer.readObject();
                colaMensajes.add(dato);

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
    
    public void operaciones(DataObject data){
        switch(data.getOperacion()){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}
