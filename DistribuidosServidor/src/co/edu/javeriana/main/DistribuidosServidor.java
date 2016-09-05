/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import java.io.DataOutputStream;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalAccessException {
        System.out.println(System.getProperties().getProperty("os.arch"));
        /*String ip = new String();
        int puerto = 1594;
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
            System.out.println(ip);
            Socket socket = new Socket("172.20.10.9",1594);
            DataOutputStream buffer = new DataOutputStream(socket.getOutputStream());
            buffer.writeUTF(ip+":"+puerto);
            socket.close();
        }catch(Exception e){
            e.printStackTrace();
        } */
        
        /*HashMap<Integer,String> mapa = new HashMap<>();
        Integer ids = 0 ;
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
    method.setAccessible(true);
    //if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
    if(method.getName().equals("getFreePhysicalMemorySize")||method.getName().equals("getTotalPhysicalMemorySize")){
            ids++;
            Object value = null;
            Long valor = new Long(0);
        try {
            value = method.invoke(operatingSystemMXBean);
            valor = (Long)value;
            valor = (valor/1024)/1024;
            mapa.put(ids, valor.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } // try
        
        System.out.println(method.getName() + " = " + valor);
    } 
  }*/
        Mem mem = null;
    CpuPerc cpuperc = null;
    FileSystemUsage filesystemusage = null;
    try {
        mem = sigar.getMem();
        cpuperc = sigar.getCpuPerc();
        filesystemusage = sigar.getFileSystemUsage("C:");          
    } catch (SigarException se) {
        se.printStackTrace();
    }


    System.out.print(mem.getUsedPercent()+"\t");
    System.out.print((cpuperc.getCombined()*100)+"\t");
    System.out.print(filesystemusage.getUsePercent()+"\n");
}   
    
    
}
