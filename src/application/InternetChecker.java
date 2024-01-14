package application;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
