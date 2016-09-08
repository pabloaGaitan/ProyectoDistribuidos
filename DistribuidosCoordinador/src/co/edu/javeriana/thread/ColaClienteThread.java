/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class ColaClienteThread extends Thread implements Runnable{
    
    public void run(){
        DataObject data;
        while(true){
            //System.out.println("len "+ServidorThread.getColaMensajes().size());
            if(!ClienteThread.getColaMensajes().isEmpty()){
                try {
                    data = ClienteThread.getColaMensajes().remove();
                    System.out.println("oper: "+data.getOperacion());
                    Socket socket = new Socket(data.getIpSolicitante(),1595);
                    data.getMensaje().put(1,"asdasdals");
                    ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
                    Scanner sc = new Scanner(System.in);
                    sc.next();
                    buffer.writeObject(data);
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ColaClienteThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
