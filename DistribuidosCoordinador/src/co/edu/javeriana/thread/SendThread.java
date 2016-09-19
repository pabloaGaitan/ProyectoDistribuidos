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
        }else
            System.out.println("ya esta  .. . . . ");
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
        System.out.println(idServidor);
        if(idServidor >= MainThread.getServidores().size()){
            System.out.println("error, id no existe");
        }else{
            ServidorPuerto servidorDestino = MainThread.getServidores().get(idServidor);
            long currentTime;
            int cont = 1;
            if(containsServidor(servidorDestino.getIp())){
                try{
                    Socket socket = new Socket(servidorDestino.getIp(),Integer.parseInt(servidorDestino.getPuerto()));
                    ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
                    buffer.writeObject(data);
                    ObjectInputStream bufferIn = new ObjectInputStream(socket.getInputStream());
                    data = (DataObject)bufferIn.readObject();
                    System.out.println(data.getMensaje().toString());
                    socket.close();
                    responderCliente();
                    if(data.isPeriodica()){
                        // something
                        long endTime = data.getTiempoTotal();
                        currentTime = (System.nanoTime() - startTime)/1000000000;
                        while(currentTime <= endTime+2){
                            if((System.nanoTime()-startTime)/1000000000 == data.getIntervalo()*cont){
                                socket = new Socket(servidorDestino.getIp(),Integer.parseInt(servidorDestino.getPuerto()));
                                buffer = new ObjectOutputStream(socket.getOutputStream());
                                if(cont == (data.getTiempoTotal()/data.getIntervalo()))
                                    data.setUltimo(true);
                                buffer.writeObject(data);
                                bufferIn = new ObjectInputStream(socket.getInputStream());
                                data = (DataObject)bufferIn.readObject();
                                socket.close();
                                responderCliente();
                                cont++;
                            }
                            currentTime = (System.nanoTime() - startTime)/1000000000;
                        }
                    }
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
