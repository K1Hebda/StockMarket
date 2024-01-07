package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

			
			/*
			 * Text text = new Text(); text.setText("Pablo Escobar"); text.setX(400);
			 * text.setY(50); text.setFont(Font.font("Verdana", 30));
			 * text.setFill(Color.GREY);
			 * 
			 * Image image = new Image("pablo.jpg"); ImageView imageView = new
			 * ImageView(image); imageView.setX(100); imageView.setY(100);
			 * 
			 * root.getChildren().add(text); root.getChildren().add(imageView);
			 */

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
