/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.GUI.InfoMensajes;
import co.edu.javeriana.data.DataObject;
import static co.edu.javeriana.main.DistribuidosCliente.menu;
import co.edu.javeriana.persistence.Persistence;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author redes
 */
public class SistemaCliente {
    
    private int contador;
    private InfoMensajes inf;
    private Map<Integer,Integer> conts = new HashMap<>();
    
    public void setInfoMensaje(InfoMensajes inf){
        this.inf = inf;
    }
    
    public InfoMensajes getInfo(){
        return inf;
    }
    
    public DataObject construirPaquete(List<Integer> servs,List<String> opc,List<String> total,List<String> intervalo,List<Boolean> periodico){
        Map<Integer,Map<Integer, String>> mensaje = new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        DataObject data = new DataObject();
        contador = 0;
        
        for (int i = 0; i<servs.size();i++) {
            StringTokenizer tok1 = new StringTokenizer(opc.get(i),",");
            map =new HashMap<>();
            while(tok1.hasMoreElements()){
                map.put(Integer.parseInt(tok1.nextToken())+1, "");
                
            }
            if(periodico.get(i)){
                map.put(9,"1");
                map.put(10,total.get(i));
                map.put(11,intervalo.get(i));
                contador += Integer.parseInt(total.get(i))/Integer.parseInt(intervalo.get(i));
                conts.put(servs.get(i), Integer.parseInt(total.get(i))/Integer.parseInt(intervalo.get(i)));
            }else{
                contador++;
                map.put(9,"0");
                conts.put(servs.get(i), 1);
            }
            mensaje.put(servs.get(i), map);
        }
        try {
            data.setIpSolicitante(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        data.setMensaje(mensaje);
        data.setOperacion(3);
        return data;
    }
    
    public void enviarMensaje(List<Integer> servs,List<String> opc,List<String> total,List<String> intervalo,List<Boolean> periodico){
        DataObject data = new DataObject();
        try {
            data = construirPaquete(servs, opc, total, intervalo, periodico);
            Socket socket = new Socket(Persistence.getIpCoordinador(),Persistence.getPuertoCoordinador());
            ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
            buffer.writeObject(data);
            int mensajesRec = 0;
            ObjectInputStream buff = new ObjectInputStream(socket.getInputStream());
            data =(DataObject)buff.readObject();
            if(data.getMensaje().get(data.getIdServidor()).containsKey(404)){
                contador -= conts.get(data.getIdServidor());
                JOptionPane.showMessageDialog(null, "El servidor "+ data.getIdServidor()+" esta caido", "ERROR", JOptionPane.ERROR_MESSAGE); 
            }else{
                inf.agregar(data.getMensaje().get(data.getIdServidor()), data.getIdServidor());
                mensajesRec++;
            }
            conts.put(data.getIdServidor(),conts.get(data.getIdServidor())-1);
            while(mensajesRec != contador){
                buff = new ObjectInputStream(socket.getInputStream());
                data = (DataObject)buff.readObject();
                if(data.getMensaje().get(data.getIdServidor()).containsKey(404)){
                    contador -= conts.get(data.getIdServidor());
                    JOptionPane.showMessageDialog(null, "El servidor "+ data.getIdServidor()+" esta caido", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    inf.agregar(data.getMensaje().get(data.getIdServidor()), data.getIdServidor());
                    mensajesRec++;
                }
                conts.put(data.getIdServidor(),conts.get(data.getIdServidor())-1);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String cantServers(){
        Map<Integer,Map<Integer, String>> mensaje = new HashMap<>();
        Map<Integer, String> map = new HashMap<>();
        DataObject data = new DataObject();
        DataObject da = null;
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
        try {
            Socket socket = new Socket(Persistence.getIpCoordinador(),Persistence.getPuertoCoordinador());
            ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
            buffer.writeObject(data);
            ObjectInputStream buff = new ObjectInputStream(socket.getInputStream());
            da=(DataObject)buff.readObject();
            socket.close();
        } catch (Exception ex) {
            Logger.getLogger(SistemaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return da.getMensaje().get(1).get(1);
    }
    
}
