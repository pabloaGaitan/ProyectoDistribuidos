/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.thread.ServidorThread;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class DistribuidosCoordinador {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServidorThread st = new ServidorThread();
        st.start();
        
        Scanner sc = new Scanner(System.in);
        while(true){
            String line = sc.nextLine();
            System.out.println(line);
        }
    }
    
}