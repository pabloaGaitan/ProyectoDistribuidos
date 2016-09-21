    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.javeriana.persistence;

import java.io.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class Persistence {
    
    private static String ipCoordinador;
    private static int puertoCoordinador;

    public synchronized static String getIpCoordinador() {
        return ipCoordinador;
    }

    public synchronized static int getPuertoCoordinador() {
        return puertoCoordinador;
    }
    
    
    
    public static void cargarCoordinador(String arch) throws Exception{
        String cadena = new String();
        try {
            FileInputStream f = new FileInputStream(arch);
            InputStreamReader in = new InputStreamReader(f);
            BufferedReader bf = new BufferedReader(in);
            cadena = bf.readLine();
            StringTokenizer tok = new StringTokenizer(cadena, ",");
            ipCoordinador = tok.nextToken();
            puertoCoordinador = Integer.parseInt(tok.nextToken());
            
        } catch (Exception ex) {
            throw ex;
        }
        
    }
}


