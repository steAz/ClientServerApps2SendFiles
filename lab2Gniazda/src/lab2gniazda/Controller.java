/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2gniazda;

/**
 *
 * @author Kazanostra
 */
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;

public class Controller implements Initializable {
    @FXML
    private Label label;
    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    protected void sendFileAction(ActionEvent event) {
        File file = new FileChooser().showOpenDialog(null);
        if (file == null) {
            return;
        }

        SendFileTask sendFT = new SendFileTask(file);
        label.textProperty().bind(sendFT .messageProperty());
        progressBar.progressProperty().bind(sendFT .progressProperty());
        try {
            sendFT .call();
        } catch (Exception ex) {
            label.textProperty().unbind();
            progressBar.progressProperty().unbind();
            label.setText("ERROR: " + ex.getMessage());
        }
    }
    
    @FXML
    protected void startReceivingAction(ActionEvent event) {
        Thread serverThread = new Thread(new Server());
        serverThread.start();
        
        if (!label.textProperty().isBound())
            label.setText("Receiving...");
    }
}
