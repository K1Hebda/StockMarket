package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

	private static Logger logger = LogManager.getLogger(Main.class.getName());

	@Override
	public void start(Stage stage) {
		logger.info("Start GUI ");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root, 600, 400, Color.AZURE);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image("logo.jpg");

			stage.getIcons().add(icon);
			stage.setTitle("Aplikacja giełdowa");

			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			logger.error("Błąd GUI", e);
			e.printStackTrace();
		}
		logger.info("End GUI");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
