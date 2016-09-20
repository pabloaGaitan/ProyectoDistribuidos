/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.data.DataObject;
import co.edu.javeriana.thread.MainThread;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
        MainThread ct = new MainThread();
        ct.start();
    }
}
