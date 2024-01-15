package application;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class InternetChecker {
    private static final Logger logger = LogManager.getLogger(InternetChecker.class.getName());

    public static boolean isInternetReachableUsingPing() {
        try {
            // Attempting to access the Google server using ping
            InetAddress address = InetAddress.getByName("www.google.com");
            boolean isReachable = address.isReachable(5000); // Timeout 5 seconds

            if (isReachable) {
                logger.info("Internet connection is reachable.");
            } else {
                logger.info("Internet connection is not reachable.");
            }

            return isReachable;
        } catch (IOException e) {
            logger.error("Error while checking internet connectivity: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean errorScene() {
    	// Display an informational window about the lack of Internet access
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Brak dostępu do internetu");
        alert.setContentText("Sprawdz swoje połączenie.");

        // Add a "Try Again" button
        ButtonType tryAgainButton = new ButtonType("Spróbuj ponownie");
        alert.getButtonTypes().setAll(tryAgainButton, ButtonType.CANCEL);

        // Event handling for the "Try Again" button
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == tryAgainButton;
    }
}
