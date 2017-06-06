/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kazanostra
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;


/**
 *
 * @author Kazanostra
 */
public class Server {
    private static Logger log = Logger.getLogger(Server.class.getCanonicalName());

    ExecutorService executor = Executors.newFixedThreadPool(4);  // TWORZENIE PULI WATKOW,  MOZE OBSLUZYC 4
    
    public void listen(){
        try (ServerSocket serverSocket = new ServerSocket(31337)){
            log.info(format("Server is actually listening on port %d", 31337));
            while(true){
                Socket socket = serverSocket.accept();
//                ReceiveFiles receiver = new ReceiveFiles(socket, new File("F:\\test"+ Math.random()));                     ////////////////////////////////////////////////
//                Thread thread = new Thread(receiver);                                                                     //obsluzenie watkow przez ODDZIELNE NOWE WATKI//
//                thread.start();                                                                                          ////////////////////////////////////////////////
                executor.submit(() -> handleClient(socket));  // obsluzenie watkow przez PULE WATKOW
            }
        } catch (IOException ex){
            log.log(SEVERE, ex.getMessage(), ex);
        }
    }
    
    private void handleClient(Socket socket){
        log.info(format("It is connected by the server: %s", socket.getInetAddress().getHostAddress()));
        
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

