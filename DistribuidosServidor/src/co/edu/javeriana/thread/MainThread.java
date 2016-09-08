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
 * @author HP
 */
public class MainThread extends Thread implements Runnable{
    
    public void run(){
        ServerSocket socket = null;
        try{
            socket = new ServerSocket(1594);
            while(true){
                Socket cliente = socket.accept();
                cliente.setSoLinger(true,10);
                ObjectInputStream buffer = new ObjectInputStream(cliente.getInputStream());
                DataObject dato = (DataObject)buffer.readObject();
                SendThread trata = new SendThread(dato);
                trata.start();
                cliente.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
