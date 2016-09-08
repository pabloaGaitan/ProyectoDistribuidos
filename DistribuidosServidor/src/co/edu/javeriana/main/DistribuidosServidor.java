/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.thread.MainThread;
import java.io.DataOutputStream;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 *
 * @author HP
 */
public class DistribuidosServidor {
    
    private static String ipCoordinador;
    private static int puertoCoordinador;

    public static String getIpCoordinador() {
        return ipCoordinador;
    }

    public static void setIpCoordinador(String ipCoordinador) {
        DistribuidosServidor.ipCoordinador = ipCoordinador;
    }

    public static int getPuertoCoordinador() {
        return puertoCoordinador;
    }

    public static void setPuertoCoordinador(int puertoCoordinador) {
        DistribuidosServidor.puertoCoordinador = puertoCoordinador;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalAccessException {
        //System.out.println(System.getProperties().getProperty("os.arch"));
        //LLAMAR AL METODO DE ARCHIVO
        registrarse();
        MainThread listener = new MainThread();
        listener.start();
        //System.out.println(new File("c:/").list().length);
        //System.out.println(((Runtime.getRuntime().freeMemory()/1024)/1024));
        
    }
    
    public static void registrarse(){
        String ip;
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
            //Socket socket = new Socket(ipCoordinador,puertoCoordinador);
            Socket socket = new Socket("172.20.10.9",1594);
            DataOutputStream buffer = new DataOutputStream(socket.getOutputStream());
            //buffer.writeUTF(ip+":"+puertoCoordinador);
            buffer.writeUTF(ip+":"+1594);
            
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    
    
    
    
}
