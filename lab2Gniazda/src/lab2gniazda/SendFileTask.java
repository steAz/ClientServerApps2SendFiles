/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2gniazda;

import javafx.concurrent.Task; 
import java.io.IOException;
import java.net.Socket;
import static java.util.logging.Level.SEVERE;
import java.io.BufferedInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.io.File;
import java.net.ConnectException;

/**
 *
 * @author Kazanostra
 */
public class SendFileTask extends Task<Void> {
    File file;
    private final static java.util.logging.Logger log = java.util.logging.Logger.getLogger(SendFileTask.class.getCanonicalName());
    
    public SendFileTask(File file){
        this.file = file;
    }
    
    @Override
    protected Void call() throws Exception {
        updateMessage("Initializing process");
        
        try(Socket socket = new Socket("localhost", 31337);
                ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                BufferedInputStream input = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            
            updateMessage("Sending the file");
            
            output.writeUTF(file.getName());
            
            byte[] buffer = new byte[4096];
            int readBytes;
            int sentBytes = 0;
            
            while((readBytes = input.read(buffer)) != -1) {
                    output.write(buffer, 0 , readBytes);
                    sentBytes += readBytes;
                    updateProgress(sentBytes, file.length());
                }
            
            updateMessage("Sending is over");
            
        } catch (ConnectException ex){
            updateMessage("Connection failed");
        } catch (IOException ex){
            log.log(SEVERE, ex.getMessage(), ex);
        }
        
        return null;
    }
}
