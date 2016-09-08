/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

import co.edu.javeriana.data.DataObject;

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
                data = ClienteThread.getColaMensajes().remove();
                System.out.println(ServidorThread.getColaMensajes().size());
            }
        }
    }
    
}
