/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalAccessException {
        /*System.out.println(System.getProperties().getProperty("os.arch"));
        String ip = new String();
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
        System.out.println(new File("c:/").list().length);
        System.out.println(((Runtime.getRuntime().freeMemory()/1024)/1024));
        
    }   
    
    
}
