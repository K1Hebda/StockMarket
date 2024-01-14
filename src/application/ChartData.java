package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChartData {
    private static final Logger logger = LogManager.getLogger(ChartData.class.getName());

    private String date;
    private double high;

    public ChartData(String date, double high) {
        this.date = date;
        this.high = high;

        logger.info("ChartData created with date: {} and high: {}", date, high);
    }

    public String getDate() {
        return date;
    }

    public double getHigh() {
        return high;
    }

}
