/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ColaClienteThread extends Thread implements Runnable{
    
    public void run(){
        DataObject data;
        Socket socket;
        while(true){
            //System.out.println("len "+ServidorThread.getColaMensajes().size());
            if(!ClienteThread.getColaMensajes().isEmpty()){
                try {
                    data = ClienteThread.getColaMensajes().remove();
                    socket = ClienteThread.getColaSockets().remove();
                    operaciones(data,socket);
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ColaClienteThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void operaciones(DataObject data, Socket cliente){
        switch(data.getOperacion()){
            case 2:
                numeroServidores(data,cliente);
                break;
            case 3:
                enviarServidor(data);
                break;
            default:
                break;
        }
    }
    
    public void numeroServidores(DataObject data, Socket cliente){
        try{
            data.getMensaje().put(1, ""+ColaServidorThread.getServidores().size());
            ObjectOutputStream buffer = new ObjectOutputStream(cliente.getOutputStream());
            buffer.writeObject(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void enviarServidor(DataObject data){
        Socket socket = null;
        String cadena = data.getMensaje().get(1);
        StringTokenizer tok = new StringTokenizer(cadena,",");
        List<String> servidoresDestino = new ArrayList<>();
        while(tok.hasMoreElements()){
            servidoresDestino.add(tok.nextToken());
        }
        for (String s : servidoresDestino) {
            if(ColaServidorThread.getServidores().containsKey(s)){
                try{
                    socket = new Socket(s,ColaServidorThread.getServidores().get(s));
                    data.getMensaje().put(1, s); // cambiar la ip del servidor a quien se envia
                    ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
                    buffer.writeObject(data);
                    socket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else
                System.out.println("ip no está");
        }
    }
    
}
