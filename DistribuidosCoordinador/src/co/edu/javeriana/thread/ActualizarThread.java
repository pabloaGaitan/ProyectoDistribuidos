/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.thread;

/**
 *
 * @author ASUS
 */
public class ActualizarThread extends Thread implements Runnable{
    
    private long startTime;
    
    public void run(){
        long currentTime = 0;
        while(true){
            if((System.nanoTime()-startTime)/1000000000 % 180 == 0){
                
            }
        }
    }
    
}
