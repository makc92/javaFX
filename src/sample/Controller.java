package sample;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button start;

    @FXML
    private Button stop;

    @FXML
    void initialize() {
        assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'Untitled'.";
        assert stop != null : "fx:id=\"stop\" was not injected: check your FXML file 'Untitled'.";
        Recording rec = new Recording();


        start.setOnAction(actionEvent -> {
            File f = rec.getNewFile();
            rec.startRecording();
        });

        stop.setOnAction(actionEvent -> {
            rec.stopRecording();
        });
    }


}
