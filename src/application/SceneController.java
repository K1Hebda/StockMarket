package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

	private static Logger logger = LogManager.getLogger(Main.class.getName());
	
    private Stage stage;
    private Scene scene;
    private Parent root;

 // Method handling the transition to the WIG20 scene
    public void switchToWIG20(ActionEvent event) {
        try {
        	logger.info("Switching to WIG20 scene");
            root = FXMLLoader.load(getClass().getResource("WIG20.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
        	logger.error("Switching to WIG20 scene error ", e);
            e.printStackTrace();
        }
    }
}