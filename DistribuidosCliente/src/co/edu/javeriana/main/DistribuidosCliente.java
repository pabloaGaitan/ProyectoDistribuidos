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
        //Persistence.cargarCoordinador("coordinador.txt");
        DataObject data = new DataObject();
        Map <Integer,String> mapa = new HashMap<>();
        try {
            //while(true){
           // data = cantServers();
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
            while(mensajesRec != contador){
                buff = new ObjectInputStream(socket.getInputStream());
                da = (DataObject)buff.readObject();
                mensajesRec++;
                System.out.println(da.getMensaje().toString());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        return Integer.parseInt(data.getMensaje().get(1).get(1));
    }
    
}
