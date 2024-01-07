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

	public static void main(String[] args) {
		logger.info("Using BankScraper");
		ArrayList<ArrayList<String>> ListOfCompanies = new ArrayList<ArrayList<String>>();

		try {
			// Adres URL do strony z notowaniami WIG20
			String url = "https://www.bankier.pl/inwestowanie/profile/quote.html?symbol=WIG20";

			// Pobierz stronę HTML
			logger.info("Connecting to the URL...");
			Document document = Jsoup.connect(url).get();
			logger.info("Connection successful!");

			// Pobierz tabelę z notowaniami
			Elements tableRows = document.select("table.sortTableMixedData tbody tr");

			int i = 0;
			// Iteruj przez wiersze tabeli
			logger.warn("Iterating through table rows");
			for (Element row : tableRows) {

				// Pobierz komórki w wierszu
				Elements cells = row.select("td");

				// Sprawdź, czy wiersz ma wystarczającą ilość komórek
				if (cells.size() >= 9) {
					ListOfCompanies.add(new ArrayList<String>());
					// Pobierz dane z komórek
					ListOfCompanies.get(i).add(cells.get(0).select("a").text());

					for (int j = 1; j < 9; j++) {
						ListOfCompanies.get(i).add(cells.get(j).text());
					}
					i++;
				}
			}
			logger.info("End of iterating through table rows.");
			// Wyświetl dane
			for (int l = 0; l < i; l++) {
				System.out.println("Spółka: " + ListOfCompanies.get(l).get(0));
				System.out.println("Symbol: " + ListOfCompanies.get(l).get(1));
				System.out.println("Ostatnia cena: " + ListOfCompanies.get(l).get(2));
				System.out.println("Zmiana: " + ListOfCompanies.get(l).get(3));
				System.out.println("Zmiana procentowa: " + ListOfCompanies.get(l).get(4));
				System.out.println("Wpływ na indeks: " + ListOfCompanies.get(l).get(5));
				System.out.println("Udział w obrocie: " + ListOfCompanies.get(l).get(6));
				System.out.println("Pakiet: " + ListOfCompanies.get(l).get(7));
				System.out.println("Udział w portfelu: " + ListOfCompanies.get(l).get(8));
				System.out.println("---------------------");
			}
		} catch (IOException e) {
			logger.error("Error during connecting with URL or saving data" + e);
			e.printStackTrace();
		}
		logger.info("End of Using BankScraper");
	}
}
