/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class SendThread extends Thread implements Runnable{
    
    private Socket cliente;
    private DataObject data;
    
    public SendThread(Socket socket, DataObject data){
        cliente = socket;
        this.data = data;
    }
    
    public void run(){
        operaciones();
    }
    
    public void operaciones(){
        switch(data.getOperacion()){
            case 1:
                registrar();
                break;
            case 2:
                numeroServidores();
                break;
            case 3:
                enviarServidor();
                break;
            default:
                break;
        }
    }
    
    public void registrar(){
        int puerto = Integer.parseInt(data.getMensaje().get(1));
        
        if(!MainThread.getServidores().containsKey(data.getIpSolicitante())){
            MainThread.getServidores().put(data.getIpSolicitante(),puerto);
            System.out.println("Se registró servidor con IP: " + data.getIpSolicitante());
        }
    }
    
    public void numeroServidores(){
        try{
            data.getMensaje().put(1, ""+MainThread.getServidores().size());
            ObjectOutputStream buffer = new ObjectOutputStream(cliente.getOutputStream());
            buffer.writeObject(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void enviarServidor(){
        //Socket socket = null;
        String cadena = data.getMensaje().get(1);
        StringTokenizer tok = new StringTokenizer(cadena,",");
        List<String> servidoresDestino = new ArrayList<>();
        while(tok.hasMoreElements()){
            servidoresDestino.add(tok.nextToken());
        }
        for (String s : servidoresDestino) {
            if(MainThread.getServidores().containsKey(s)){
                try{
                    Socket socket = new Socket(s,MainThread.getServidores().get(s));
                    data.getMensaje().put(1, s); // cambiar la ip del servidor a quien se envia
                    ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
                    buffer.writeObject(data);
                    ObjectInputStream bufferIn = new ObjectInputStream(socket.getInputStream());
                    data = (DataObject)bufferIn.readObject();
                    responderCliente();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else
                System.out.println("ip no está");
        }
    }
    
    public void responderCliente(){
        try{
            ObjectOutputStream bufferOut = new ObjectOutputStream(cliente.getOutputStream());
            bufferOut.writeObject(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
