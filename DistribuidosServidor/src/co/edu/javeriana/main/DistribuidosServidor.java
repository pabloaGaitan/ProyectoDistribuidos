/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.thread.MainThread;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import co.edu.javeriana.data.DataObject;
import java.io.ObjectOutputStream;
import java.util.Map;
import org.hyperic.sigar.*;
        
/**
 *
 * Clase que representa el servidor el cual escucha solicitudes y envia su respectiva respuesta
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
        cargarCoordinador("coordinador.txt");
        registrarse();
        MainThread listener = new MainThread();
        listener.start();
        
        
    }
    /**
     * Se encarga de enviar un mensaje de registro al coordinador
     * 
     * 
     */
    public static void registrarse(){
        String ip;
        DataObject dato = new DataObject();
        Map<Integer,Map<Integer,String>> mensaje = new HashMap<>();
        Map<Integer,String> mapa = new HashMap<>();
        try{
            dato.setOperacion(1);
            dato.setIpSolicitante(InetAddress.getLocalHost().getHostAddress());
            mapa.put(1,"1594");
            mensaje.put(1, mapa);
            dato.setMensaje(mensaje);
            Socket socket = new Socket(ipCoordinador,puertoCoordinador);
            ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
            buffer.writeObject(dato);
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    /**
     * Se encarga de leer el archivo y obtener la informacion del coordinador
     * @param arch nombre del archivo donde se encuentra la informacion del coordinador 
     * 
     */
    public static void cargarCoordinador(String arch){
        String cadena = new String();
        try {
            FileInputStream f = new FileInputStream(arch);
            InputStreamReader in = new InputStreamReader(f);
            BufferedReader bf = new BufferedReader(in);
            cadena = bf.readLine();
            StringTokenizer tok = new StringTokenizer(cadena, ",");
            ipCoordinador = tok.nextToken();
            puertoCoordinador = Integer.parseInt(tok.nextToken());
            
        } catch (Exception ex) {
            Logger.getLogger(DistribuidosServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
}
