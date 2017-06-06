/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2gniazda;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.String.format;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import static java.util.logging.Level.SEVERE;
import java.util.logging.Logger;

/**
 *
 * @author Kazanostra
 */
public class ReceiveFiles implements Runnable {
     private static Logger log = Logger.getLogger(ReceiveFiles.class.getCanonicalName());
   
    private Socket socket;
//    private File file;

    public ReceiveFiles(Socket socket, File file) {
        this.socket = socket;
//        this.file = file;
    }

    @Override
    public void run() {
//        log.info(format("It is connected by the server: %s", socket.getInetAddress().getHostAddress()));
//        
//        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//                FileOutputStream out = new FileOutputStream(file)) {
//                    byte[] buffer = new byte[4096];
//                    int readSize;
//                    
//                    while ((readSize = in.read(buffer)) != -1) {
//                            out.write(buffer, 0, readSize);
//
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(ReceiveFiles.class.getName()).
//                            log(Level.SEVERE, ex.getMessage(), ex);
//                } finally {
//                    try {
//                        socket.close();
//                    } catch (IOException ex) {
//                        Logger.getLogger(ReceiveFiles.class.getName()).
//                                log(Level.SEVERE, ex.getMessage(), ex);
//                    }
//                }

        log.info(format("Client is connected to the server: %s", socket.getInetAddress().getHostAddress()));
        
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))){
            String fileName = in.readUTF();
            log.info(format("Server is receiving a file: %s", fileName));
            
            try(BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(fileName)))){
                byte [] buffer = new byte[4096];
                int readSize;
                
                while((readSize = in.read(buffer)) != -1){
                    out.write(buffer, 0, readSize);
                }
            }
            
        } catch (IOException ex) {
            log.log(SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                log.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
}
