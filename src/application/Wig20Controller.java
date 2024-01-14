package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Wig20Controller {

	private static Logger logger = LogManager.getLogger(Wig20Controller.class.getName());

	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button alior;

	@FXML
	private Button allegro;

	@FXML
	private Button asseco;

	@FXML
	private Button cdProjekt;

	@FXML
	private Button dino;

	@FXML
	private Button gk;

	@FXML
	private Button jswaSA;

	@FXML
	private Button kghm;

	@FXML
	private Button kruk;

	@FXML
	private Button mbank;

	@FXML
	private Button orange;

	@FXML
	private Button orlen;

	@FXML
	private Button pekao;

	@FXML
	private Button pepco;

	@FXML
	private Button pge;

	@FXML
	private Button pko;

	@FXML
	private Button polsat;

	@FXML
	private Button pzu;

	@FXML
	private Button lpp;

	@FXML
	private Button santander;

	@FXML
	private TextArea outputTextArea1;

	@FXML
	private TextArea outputTextArea2;

	@FXML
	private TableColumn<DataColumn, Double> changeColumn;

	@FXML
	private TableColumn<DataColumn, Double> impactColumn;

	@FXML
	private TableColumn<DataColumn, String> nameColumn;

	@FXML
	private TableColumn<DataColumn, Integer> packageColumn;

	@FXML
	private TableColumn<DataColumn, Double> portfolioStakeColumn;

	@FXML
	private TableColumn<DataColumn, Double> percentageChangeColumn;

	@FXML
	private TableColumn<DataColumn, Double> stockRateColumn;

	@FXML
	private TableView<DataColumn> tableView;

	@FXML
	private TableColumn<DataColumn, String> tickerColumn;

	@FXML
	private TableColumn<DataColumn, Double> tradingStakeColumn;

	@FXML
	private AreaChart<String, Number> wig20Chart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;
	
	@FXML
    private Label companyNameLabel;

	public void initialize() {
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		companyNameLabel.setText("WIG20");

		try {
			// Running each method in a separate CompletableFuture, executing in parallel
			// Asynchronously execute the fetchDataToChart method with the argument "WIG20"
			// using CompletableFuture
			CompletableFuture<Void> chartFuture = CompletableFuture.runAsync(() -> fetchDataToChart("WIG20"),
					executorService);
			CompletableFuture<Void> tableFuture = CompletableFuture.runAsync(this::fetchDataForTableView,
					executorService);
			CompletableFuture<Void> textAreaFuture = CompletableFuture.runAsync(()->fetchDataForTextArea("WIG20"),
					executorService);
			chartFuture.get();
			tableFuture.get();
			textAreaFuture.get();
		} catch (Exception e) {
			logger.error("Error with threads", e);
		} finally {
			executorService.shutdown();
		}
	}

	private void fetchDataForTextArea(String company) {
		logger.info("Fetching data for TextArea for company: {}", company);
		outputTextArea1.clear();
		outputTextArea2.clear();
		BankScraper.startWIGInformationScraper(company);
		logger.warn("Fetching data for the table");

		StringBuilder output1 = new StringBuilder();
		StringBuilder output2 = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			output1.append(BankScraper.titleText.get(i) + "\n");
			output2.append(BankScraper.valueText.get(i) + "\n");
		}

		try {
			if (outputTextArea1 != null && outputTextArea2 != null) {
				outputTextArea1.setText(output1.toString());
				outputTextArea2.setText(output2.toString());
				logger.info("Text set in TextArea successfully.");

			} else {
				logger.error("TextArea is null. Make sure it's properly initialized.");
			}
		} catch (Exception e) {
			logger.error("Error setting text in TextArea", e);
		}
	}

	private void fetchDataForTableView() {
		BankScraper.startWIG20Scraper();
		try {
			nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
			tickerColumn.setCellValueFactory(cellData -> cellData.getValue().tickerProperty());
			stockRateColumn.setCellValueFactory(cellData -> cellData.getValue().stockRateProperty().asObject());
			changeColumn.setCellValueFactory(cellData -> cellData.getValue().changeProperty().asObject());
			percentageChangeColumn
					.setCellValueFactory(cellData -> cellData.getValue().percentageChangeProperty().asObject());
			impactColumn.setCellValueFactory(cellData -> cellData.getValue().impactProperty().asObject());
			tradingStakeColumn.setCellValueFactory(cellData -> cellData.getValue().tradingStakeProperty().asObject());
			packageColumn.setCellValueFactory(cellData -> cellData.getValue().packageProperty().asObject());
			portfolioStakeColumn
					.setCellValueFactory(cellData -> cellData.getValue().portfolioStakeProperty().asObject());

			// Populating a TableView with data
			for (ArrayList<Object> row : BankScraper.ListOfCompanies) {
				if (row.size() == 9) {
					DataColumn dataColumn = new DataColumn((String) row.get(0), (String) row.get(1),
							(Double) row.get(2), (Double) row.get(3), (Double) row.get(4), (Double) row.get(5),
							(Double) row.get(6), (Integer) row.get(7), (Double) row.get(8));

					// Logging each row added to the TableView
					logger.debug("Adding row to TableView: {}", dataColumn);
					tableView.getItems().add(dataColumn);
				} else {
					logger.error("Incorrect number of elements in a row: {}", row);
				}
			}
			logger.info("End of fetching data for the table");
		} catch (Exception e) {
			logger.error("Error while populating table data", e);
		}
	}

	private void fetchDataToChart(String company) {
		logger.info("Fetching data for chart for company: {}", company);
		BankScraper.startInformationToChartScraper(company);
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		yAxis.setAutoRanging(false);

		for (int i = 0; i < BankScraper.chartXValue.size(); i++) {
			try {
				// Adding data to the series
				series.getData()
						.add(new XYChart.Data<>(BankScraper.chartXValue.get(i), BankScraper.chartYValue.get(i)));
			} catch (ClassCastException | IndexOutOfBoundsException e) {
				logger.error("Error processing data for the chart: {}", e);
			}
		}

		// setting the y-axis range
		yAxis.setLowerBound(Collections.min(BankScraper.chartYValue) - 5);
		yAxis.setUpperBound(Collections.max(BankScraper.chartYValue) + 5);
		wig20Chart.getData().add(series);
		logger.info("Chart data added successfully for company: {}", company);
	}

	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		BankScraper.chartXValue.clear();
		BankScraper.chartYValue.clear();
		wig20Chart.getData().clear();
		Button clickedButton = (Button) event.getSource();
		
		 logger.info("Button clicked: {}", clickedButton.getId());

		switch (clickedButton.getId()) {
		case "alior":
	        companyNameLabel.setText("ALIOR BANK");
	        fetchDataToChart("ALIOR-BANK");
	        fetchDataForTextArea("ALIOR");
	        break;
	    case "allegro":
	        companyNameLabel.setText("ALLEGRO");
	        fetchDataToChart("ALE");
	        fetchDataForTextArea("ALLEGRO");
	        break;
	    case "asseco":
	        companyNameLabel.setText("ASSECO POLAND");
	        fetchDataToChart("ASSECO-POLAND");
	        fetchDataForTextArea("ASSECOPOL");
	        break;
	    case "cdProjekt":
	        companyNameLabel.setText("CD PROJEKT");
	        fetchDataToChart("CD-PROJEKT");
	        fetchDataForTextArea("CDPROJEKT");
	        break;
	    case "dino":
	        companyNameLabel.setText("DINO");
	        fetchDataToChart("DNP");
	        fetchDataForTextArea("DINOPL");
	        break;
	    case "gk":
	        companyNameLabel.setText("GRUPA KĘTY");
	        fetchDataToChart("KETY");
	        fetchDataForTextArea("KETY");
	        break;
	    case "jswaSA":
	        companyNameLabel.setText("JASTRZĘBSKA SPÓŁKA WĘGLOWA");
	        fetchDataToChart("JSW-JASTRZEBSKA-SPOLKA-WEGLOWA");
	        fetchDataForTextArea("JSW");
	        break;
	    case "kghm":
	        companyNameLabel.setText("KGHM POLSKA MIEDZ");
	        fetchDataToChart("KGHM");
	        fetchDataForTextArea("KGHM");
	        break;
	    case "kruk":
	        companyNameLabel.setText("KRUK");
	        fetchDataToChart("KRUK");
	        fetchDataForTextArea("KRUK");
	        break;
	    case "mbank":
	        companyNameLabel.setText("MBANK");
	        fetchDataToChart("MBANK");
	        fetchDataForTextArea("MBANK");
	        break;
	    case "orange":
	        companyNameLabel.setText("ORANGE POLSKA");
	        fetchDataToChart("ORANGE");
	        fetchDataForTextArea("ORANGEPL");
	        break;
	    case "orlen":
	        companyNameLabel.setText("PKN ORLEN");
	        fetchDataToChart("ORLEN");
	        fetchDataForTextArea("PKNORLEN");
	        break;
	    case "pekao":
	        companyNameLabel.setText("PEKAO, BANK POLSKA KASA OPIEKI");
	        fetchDataToChart("PEKAO");
	        fetchDataForTextArea("PEKAO");
	        break;
	    case "pepco":
	        companyNameLabel.setText("PEPCO GROUP");
	        fetchDataToChart("PCO");
	        fetchDataForTextArea("PEPCO");
	        break;
	    case "pge":
	        companyNameLabel.setText("PGE, POLSKA GRUPA ENERGETYCZNA");
	        fetchDataToChart("PGE");
	        fetchDataForTextArea("PGE");
	        break;
	    case "pko":
	        companyNameLabel.setText("PKO, POWSZECHA KASA OSZCZĘDNOŚCI BANK POLSKI");
	        fetchDataToChart("PKO");
	        fetchDataForTextArea("PKOBP");
	        break;
	    case "polsat":
	        companyNameLabel.setText("CYFROWY POLSAT");
	        fetchDataToChart("CYFROWY-POLSAT");
	        fetchDataForTextArea("CYFRPLSAT");
	        break;
	    case "pzu":
	        companyNameLabel.setText("PZU, POWSZECHNY ZAKŁAD UBEZPIECZEŃ");
	        fetchDataToChart("PZU");
	        fetchDataForTextArea("PZU");
	        break;
	    case "santander":
	        companyNameLabel.setText("SANTANDER BANK POLSKA");
	        fetchDataToChart("SPL");
	        fetchDataForTextArea("SANPL");
	        break;
	    case "lpp":
	        companyNameLabel.setText("LPP");
	        fetchDataToChart("LPP");
	        fetchDataForTextArea("LPP");
	        break;
	    default:
	        companyNameLabel.setText("Unrecognized Company");
	        logger.warn("Unrecognized button: {}", clickedButton.getId());
	        break;

		}
	}

	public void switchToMain(ActionEvent event) {
		logger.info("Switching to the Main scene");
		try {
			BankScraper.ListOfCompanies.clear();
			logger.debug("Clearing ListOfCompanies");

			// Loading the Main.fxml file and switch to the Main scene
			root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			logger.error("Error while switching to the Main scene", e);
		}
	}

}
