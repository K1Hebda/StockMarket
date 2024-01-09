package application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankScraper {

	private static Logger logger = LogManager.getLogger(BankScraper.class.getName());
	public static ArrayList<ArrayList<String>> ListOfCompanies = new ArrayList<ArrayList<String>>();

	public static void startScraper() {
		logger.info("Using BankScraper");

		try {
			// URL address for the WIG20 stock quotes page
			String url = "https://www.bankier.pl/inwestowanie/profile/quote.html?symbol=WIG20";
			logger.debug("URL: {}", url);

			// Retrieve HTML page
			logger.info("Connecting to the URL...");
			Document document = Jsoup.connect(url).get();
			logger.info("Connection successful!");

			// Retrieve the table with stock quotes
			Elements tableRows = document.select("table.sortTableMixedData tbody tr");

			int i = 0;
			// Iterate through the table rows
			logger.warn("Iterating through table rows");
			for (Element row : tableRows) {

				// Retrieve cells in the row
				Elements cells = row.select("td");

				// Check if the row has an adequate number of cells
				if (cells.size() >= 9) {
					ListOfCompanies.add(new ArrayList<String>());
					// Retrieve data from cells
					ListOfCompanies.get(i).add(cells.get(0).select("a").text());

					for (int j = 1; j < 9; j++) {
						ListOfCompanies.get(i).add(cells.get(j).text());
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
}
