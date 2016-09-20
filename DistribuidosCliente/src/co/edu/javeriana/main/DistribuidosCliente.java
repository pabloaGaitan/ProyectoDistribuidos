/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.data.DataObject;
import co.edu.javeriana.persistence.Persistence;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.util.locale.StringTokenIterator;

/**
 *
 * @author HP
 */
public class DistribuidosCliente {
    
    private static int contador;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Persistence.cargarCoordinador("coordinador.txt");
        DataObject data = new DataObject();
        Map <Integer,String> mapa = new HashMap<>();
        try {
            //while(true){
           // data = cantServers();
            data = menu();
            Socket socket = new Socket(Persistence.getIpCoordinador(),Persistence.getPuertoCoordinador());
            //socket.setSoLinger(true, 10);
            ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
            //data.setMensaje(mapa);
            buffer.writeObject(data);
            int mensajesRec = 0;
            ObjectInputStream buff = new ObjectInputStream(socket.getInputStream());
            DataObject da=(DataObject)buff.readObject();
            mensajesRec++;
            System.out.println(da.getMensaje().toString());
            boolean ultimo = false;
            while(mensajesRec != contador){
                buff = new ObjectInputStream(socket.getInputStream());
                da = (DataObject)buff.readObject();
                mensajesRec++;
                ultimo = da.isUltimo();
                System.out.println(da.getMensaje().toString());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static DataObject menu(){
        Map<Integer,Map<Integer, String>> mensaje = new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        String servs;
        String periodica="0",total="",intervalo="";
        DataObject data = new DataObject();
        Scanner in = new Scanner(System.in);
        System.out.println("servidores: ");
        //servs = in.next();
        servs = "1,2";
        contador = 0;
        
        StringTokenizer tok = new StringTokenizer(servs,",");
        contador = tok.countTokens();
        System.out.println(contador);
        while(tok.hasMoreElements()){
            System.out.println("Periodica?: ");
            //periodica = in.next();
            periodica = "0";
            if(periodica.equals("1")){
                System.out.println("total, intervalo: ");
                //total = in.next();
                total = "60";
                intervalo = "20";
                //intervalo = in.next();
            }
                
            System.out.println("1. Arquitecura");
            System.out.println("2. Cores");
            System.out.println("3. Total RAM");
            System.out.println("4. Consumo total de CPU");
            System.out.println("5. Memoria RAM usada");
            System.out.println("6. Memoria RAM disponible");
            System.out.println("7. Sistema archivos");
            System.out.println("Opciones: ");
            StringTokenizer tok1 = new StringTokenizer(in.next(),",");
            map =new HashMap<>();
            while(tok1.hasMoreElements()){
                map.put(Integer.parseInt(tok1.nextToken())+1, "");
            }
            System.out.println(map.toString());
            mensaje.put(Integer.parseInt(tok.nextToken()), map);
            System.out.println(mensaje.toString());
        }
        try {
            data.setIpSolicitante(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        data.setMensaje(mensaje);
        data.setOperacion(3);
        if(periodica.equals("1")){
            data.setPeriodica(true);
            data.setTiempoTotal(Integer.parseInt(total));
            data.setIntervalo(Integer.parseInt(intervalo));
            contador = (data.getTiempoTotal()/data.getIntervalo())*2; 
        }
        data.setUltimo(false);
        return data;
    }
    
    public static int cantServers(){
        Map<Integer,Map<Integer, String>> mensaje = new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        DataObject data = new DataObject();
        try {
            data.setIpSolicitante(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mensaje.put(1, map);
        data.setMensaje(mensaje);
        data.setOperacion(2);
        data.setPeriodica(false);
        data.setTiempoTotal(0);
        return Integer.parseInt(data.getMensaje().get(1).get(1));
    }
    
}
