/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import co.edu.javeriana.data.ServidorPuerto;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 *
 * @author ASUS
 * Este hilo es el encargado de revisar y actualizar la lista de servidores
 * que se encuentran en línea cada 3 minutos.
 */
public class ActualizarThread extends Thread implements Runnable{
    
    private long startTime;
    
    /**
     * Método que manda mensajes a los servidores para que se estén reportando,
     * de esta forma se sabe quienes están en línea.
     */
    public void run(){
        long currentTime = 0;
        while(true){
            if((System.nanoTime()-startTime)/1000000000 % 180 == 0){
                for (ServidorPuerto s : MainThread.getServidores()) {
                    try{
                        Socket socket = new Socket(s.getIp(),Integer.parseInt(s.getPuerto()));
                        socket.setSoLinger(true, 5);
                        ObjectOutputStream bufferOut = new ObjectOutputStream(socket.getOutputStream());
                        DataObject data = new DataObject();
                        data.setOperacion(1);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                MainThread.getServidores().clear();
            }
        }
    }
    
}
