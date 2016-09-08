/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.main;

import co.edu.javeriana.data.DataObject;
import co.edu.javeriana.persistence.Persistence;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DistribuidosCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Persistence.cargarCoordinador("coordinador.txt");
        DataObject data = new DataObject();
        Map <Integer,String> mapa = new HashMap<>();
        try {
            data.setOperacion(2);
            data.setIpSolicitante(InetAddress.getLocalHost().getHostAddress());
            Socket socket = new Socket(Persistence.getIpCoordinador(),Persistence.getPuertoCoordinador());
            ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream());
            data.setMensaje(mapa);
            buffer.writeObject(data);
            ObjectInputStream buff = new ObjectInputStream(socket.getInputStream());
            DataObject da=(DataObject)buff.readObject();
            System.out.println(da.getIpSolicitante());
            System.out.println(da.getMensaje().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("LOKLOKLOKLOK");
    }
    
    
}
