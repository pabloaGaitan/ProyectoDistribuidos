/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author ASUS
 */
public class ColaServidorThread extends Thread implements Runnable{
    
    private static Map<String,Integer> servidores;
    
    public ColaServidorThread(){
        servidores = new HashMap<>();
    }
    
    public void run(){
        DataObject data;
        while(true){
            //System.out.println("len "+ServidorThread.getColaMensajes().size());
            if(!ServidorThread.getColaMensajes().isEmpty()){
                System.out.println("Entra");
                data = ServidorThread.getColaMensajes().remove();
                operaciones(data);
                System.out.println(ServidorThread.getColaMensajes().size());
            }
        }
    }
    
    public void operaciones(DataObject data){
        switch(data.getOperacion()){
            case 1:
                registrar(data.getIpSolicitante(),data.getMensaje());
                break;
            case 2:
                break;
            case 3:
                // manda cliente
                break;
            case 4:
                // manda servidor
                break;
            default:
                break;
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
            System.out.println(s);
            try{
                socket = new Socket(s,servidores.get(s));
                data.getMensaje().put(1, s); // cambiar la ip del servidor a quien se envia
                ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
                buffer.writeObject(data);
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void registrar(String ip, Map<Integer,String> mensaje){
        int puerto = Integer.parseInt(mensaje.get(1));
        
        if(!servidores.containsKey(ip)){
            servidores.put(ip,puerto);
        }
    }
    
    public synchronized static Map<String,Integer> getServidores(){
        return servidores;
    }
}
