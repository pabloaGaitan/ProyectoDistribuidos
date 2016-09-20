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
import java.net.ConnectException;
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
    private long startTime;
    private int idServidor;
    
    public SendThread(Socket socket, DataObject data, int idServidor){
        cliente = socket;
        this.data = data;
        this.idServidor = idServidor;
    }
    
    public void run(){
        startTime = System.nanoTime();
        operaciones();
    }
    
    public void operaciones(){
        switch(data.getOperacion()){
            case 1:
                registrar();
                break;
            case 2:
                numeroServidores();
                System.out.println("pregunta num servidores");
                break;
            case 3:
                enviarServidor();
                break;
            default:
                break;
        }
    }
    
    public boolean containsServidor(String ip){
        for(ServidorPuerto s: MainThread.getServidores())
            if(s.getIp().equals(ip))
                return true;
        return false;
    }
    
    public void registrar(){
        int puerto = Integer.parseInt(data.getMensaje().get(1).get(1));
        ServidorPuerto server = new ServidorPuerto(data.getIpSolicitante(), puerto+"");
        
        if(!containsServidor(server.getIp())){
            MainThread.getServidores().add(server);
            System.out.println("Se registró servidor con IP: " + data.getIpSolicitante());
        }
    }
    
    public void numeroServidores(){
        Map<Integer,String> map;
        try{
            map = data.getMensaje().get(1);
            map.put(1,""+MainThread.getServidores().size());
            data.getMensaje().put(1, map);
            ObjectOutputStream buffer = new ObjectOutputStream(cliente.getOutputStream());
            buffer.writeObject(data);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void enviarServidor(){
        Socket socket = null;
        ObjectOutputStream buffer = null;
        ObjectInputStream bufferIn = null;
        if(idServidor >= MainThread.getServidores().size()){
            System.out.println("error, id no existe"); // servidor no existe
            data.getMensaje().get(idServidor+1).put(405, "Servidor no existe");
            MainThread.getServidores().remove(idServidor);
            responderCliente();
        }else{
            ServidorPuerto servidorDestino = MainThread.getServidores().get(idServidor);
            long currentTime;
            int cont = 1;
            if(containsServidor(servidorDestino.getIp())){
                try{
                    if(data.getMensaje().get(idServidor+1).get(9).equals("1")){
                        long tiempoTotal = Integer.parseInt(data.getMensaje().get(idServidor+1).get(10));
                        long intervalo = Integer.parseInt(data.getMensaje().get(idServidor+1).get(11));
                        currentTime = (System.nanoTime() - startTime)/1000000000;
                        while(currentTime <= tiempoTotal+2){
                            if((System.nanoTime()-startTime)/1000000000 == intervalo*cont){
                                socket = new Socket(servidorDestino.getIp(),Integer.parseInt(servidorDestino.getPuerto()));
                                buffer = new ObjectOutputStream(socket.getOutputStream());
                                /*if(cont == (data.getTiempoTotal()/data.getIntervalo()))
                                    data.setUltimo(true);*/
                                buffer.writeObject(data);
                                bufferIn = new ObjectInputStream(socket.getInputStream());
                                data = (DataObject)bufferIn.readObject();
                                socket.close();
                                responderCliente();
                                cont++;
                            }
                            currentTime = (System.nanoTime() - startTime)/1000000000;
                        }
                    }else{
                        socket = new Socket(servidorDestino.getIp(),Integer.parseInt(servidorDestino.getPuerto()));
                        buffer = new ObjectOutputStream(socket.getOutputStream());
                        buffer.writeObject(data);
                        bufferIn = new ObjectInputStream(socket.getInputStream());
                        data = (DataObject)bufferIn.readObject();
                        socket.close();
                        responderCliente();
                    }
                }catch(ConnectException ex){
                    System.out.println("time");
                    data.getMensaje().get(idServidor+1).put(404, "Servidor caido");
                    MainThread.getServidores().remove(idServidor);
                    responderCliente();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
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
