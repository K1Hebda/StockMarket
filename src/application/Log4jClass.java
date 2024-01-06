package application;
import org.apache.logging.log4j.*;		

public class Log4jClass {
	
	private static Logger logger =LogManager.getLogger(Log4jClass.class.getName());

	public static void main(String[] args) {
		logger.info("Click successfull");
		logger.error("DB conection failed");
		logger.debug("this os debug");

	}

}
