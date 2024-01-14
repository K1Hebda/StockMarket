package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BankScraper {

    private static Logger logger = LogManager.getLogger(BankScraper.class.getName());

    // Lists to store scraped data
    public static ArrayList<ArrayList<Object>> ListOfCompanies = new ArrayList<ArrayList<Object>>();
    public static ArrayList<String> titleText = new ArrayList<String>();
    public static ArrayList<String> valueText = new ArrayList<String>();
    public static ArrayList<Double> chartYValue = new ArrayList<Double>();
    public static ArrayList<String> chartXValue = new ArrayList<String>();

    // Scraper for WIG20 stock quotes
    public static void startWIG20Scraper() {
        logger.info("Using BankScraper");

        try {
            // URL address for the WIG20 
            String url = "https://www.bankier.pl/inwestowanie/profile/quote.html?symbol=WIG20";
            logger.debug("URL: {}", url);

            // Retrieving HTML page
            logger.info("Connecting to the URL...");
            Document document = Jsoup.connect(url).timeout(10000).get();
            logger.info("Connection successful!");

           
            Elements tableRows = document.select("table.sortTableMixedData tbody tr");

            int i = 0;
            logger.warn("Iterating through table rows");
            for (Element row : tableRows) {
                Elements cells = row.select("td");

                // Checking if the row has an adequate number of cells
                if (cells.size() >= 9) {
                    ListOfCompanies.add(new ArrayList<Object>());
                    ListOfCompanies.get(i).add(cells.get(0).select("a").text());
                    ListOfCompanies.get(i).add(cells.get(1).text());

                    for (int j = 2; j < 9; j++) {
                        String cellText = cells.get(j).text();

                        // Removing percentage signs from the text
                        String cellTextWithoutPercentAndComma = cellText.replace("%", "").replace(",", ".").replace(" ",
                                "");

                        try {
                            if (j == 7) {
                                int intValue = Integer.parseInt(cellTextWithoutPercentAndComma);
                                ListOfCompanies.get(i).add(intValue);
                            } else {
                                double doubleValue = Double.parseDouble(cellTextWithoutPercentAndComma);
                                ListOfCompanies.get(i).add(doubleValue);
                            }

                        } catch (NumberFormatException e) {
                            logger.error("Error during conversion to double: " + e.getMessage());
                        }
                    }

                    i++;
                }
            }
            logger.info("End of iterating through table rows.");

        } catch (IOException e) {
            logger.error("Error during connecting with URL or saving data", e);
            e.printStackTrace();
        }
        logger.info("End of Using BankScraper");
    }

    // Scraper for WIG information
    public static void startWIGInformationScraper(String company) {
        String url = "https://www.bankier.pl/inwestowanie/profile/quote.html?symbol=" + company;

        try {
            Document document = Jsoup.connect(url).timeout(10000).get();
            
            titleText.clear();
            valueText.clear();

            Elements rows = document.select("tr.startingData");

            for (Element row : rows) {
                Element title = row.select("td").first();
                Element value = row.select("td.textBold").last();

                if (title != null && value != null) {
                    titleText.add(title.text());
                    valueText.add(value.text());
                }
            }
        } catch (IOException e) {
            logger.error("Error during WIG information scraping", e);
            e.printStackTrace();
        }
    }

    // Scraper for information to be used in a chart
    public static void startInformationToChartScraper(String company) {
        try {
            String url = "https://www.biznesradar.pl/notowania-historyczne/"+company;
            // Connecting to the website
            Document document = Jsoup.connect(url).timeout(10000).get();

            Element stockDataTable = document.select("table.qTableFull tbody").first();

            if (stockDataTable != null) {
                Elements rows = stockDataTable.select("tr");
                for (int i = 0; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements columns = row.select("td");
                    // Checking if the row has an adequate number of columns
                    if (columns.size() >= 6) {
                        // Getting data from columns: Date and Maximum Value
                        String date = columns.get(0).text();
                        String max = columns.get(2).text();
                        chartXValue.add(date);
                        chartYValue.add(Double.parseDouble(max.replace(",", ".")));
                    }
                }
                Collections.reverse(chartXValue);
                Collections.reverse(chartYValue);

            } else {
                logger.error("Stock data table not found on the page.");
            }

        } catch (IOException e) {
            logger.error("Error in retrieving elements for the chart", e);
            e.printStackTrace();

        }
    }

}
