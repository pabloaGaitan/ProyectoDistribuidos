/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class SendThread {
    
    public void run(){
        DataObject data;
        while(true){
            if(MainThread.getCola().size()!=0){
                data = MainThread.getCola().remove();
                if(!data.isPeriodica()){
                    operaciones(data);
                }
                //Socket socket = new Socket(, 1594)
            }
            
        }
    }
    
    public void operaciones(DataObject mensaje){
        Map<Integer,String> mapa;
        Map<Integer,String> mapaDatos = recursos();
        mapa = mensaje.getMensaje();
        Set<Integer> set = mapa.keySet();
        for(Integer i:set){
            mapa.put(i, mapaDatos.get(i));
        }
        try {
            mapa.put(1,InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        mensaje.setMensaje(mapa);
    }
    public Map<Integer,String> recursos(){
        Map<Integer,String> mapa = new HashMap<>();
        Integer ids = 1 ;
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if(method.getName().equals("getFreePhysicalMemorySize")||method.getName().equals("getTotalPhysicalMemorySize")||method.getName().equals("getFreePhysicalMemorySize")){
                ids++;
                Object value = null;
                Long valor = new Long(0);
                try {
                    value = method.invoke(operatingSystemMXBean);
                    valor = (Long)value;
                    valor = (valor/1024)/1024;
                    mapa.put(ids, valor.toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }
                //System.out.println(method.getName() + " = " + valor);
            } 
        }
        return mapa;
    }
}
