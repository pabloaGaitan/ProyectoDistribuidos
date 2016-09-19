package co.edu.javeriana.thread;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import co.edu.javeriana.data.DataObject;
import co.edu.javeriana.main.DistribuidosServidor;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarProxyCache;
import org.hyperic.sigar.Swap;

/**
 *
 * @author HP
 */
public class SendThread extends Thread implements Runnable {
    
    private DataObject mensaje;
    private Socket cliente;
    
    public SendThread(DataObject mens, Socket cliente){
        mensaje = mens;
        this.cliente = cliente;
    }
    
    public void run(){
        
        try {
            if(mensaje.getOperacion() == 1){
                DistribuidosServidor.registrarse();
            }
            if(mensaje.getOperacion() == 3){   
                //if(!mensaje.isPeriodica()){
                    clasificarRecursos();
                //}
                //Socket socket = new Socket(DistribuidosServidor.getIpCoordinador(), DistribuidosServidor.getPuertoCoordinador());
                ObjectOutputStream buffer = new ObjectOutputStream(cliente.getOutputStream());
                buffer.writeObject(mensaje);
                cliente.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clasificarRecursos(){
        Map<Integer,String> mapa;
        Map<Integer,String> mapaDatos = recursos();
        Map<Integer,Map<Integer,String>> mens = new HashMap<>();
        mapa = mensaje.getMensaje().get(mensaje.getIdServidor());
        Set<Integer> set = mapa.keySet();
        for(Integer i:set){
            if(mapaDatos.containsKey(i))
                mapa.put(i, mapaDatos.get(i));
        }
        try {
            mapa.put(1,InetAddress.getLocalHost().getHostAddress());//revisar!
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mens.put(1, mapa);
        mensaje.setMensaje(mens);
        System.out.println(mapa.toString());
    }
    
    public Map<Integer,String> recursos(){
        Map<Integer,String> mapa = new HashMap<>();
        OperatingSystem sys = OperatingSystem.getInstance();
        mapa.put(2, sys.getArch());
        try {
            Sigar sigar = new Sigar();
            CpuInfo[] infos = null;
            infos = sigar.getCpuInfoList();
            CpuInfo info = infos[0];
            mapa.put(3, info.getTotalSockets()+"");
            Mem memoria;
            memoria = sigar.getMem();
            Swap intercambio = sigar.getSwap();
            mapa.put(4,memoria.getRam()+"");
            mapa.put(5, CpuPerc.format(sigar.getCpuPerc().getUser())+"");
            memoria = sigar.getMem();
            mapa.put(6, (memoria.getUsed()/1024)/1024+"");
            mapa.put(7, (memoria.getFree()/1024)/1024+"");
            
            SigarProxy proxy = SigarProxyCache.newInstance(sigar);
            FileSystem sistemaArchivos = proxy.getFileSystemList()[0];
            long usado, disponible, total;
            FileSystemUsage uso = sigar.getFileSystemUsage(sistemaArchivos.getDirName());
            usado = uso.getTotal() - uso.getFree();
            disponible = uso.getAvail();
            total = uso.getTotal();             
            mapa.put(8,sistemaArchivos.getDevName()+". Total: "+ total/1024/1024+". Usado: "+usado/1024/1024+". Disponible: "+ disponible/1024/1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapa;
    }
}
