package application;

import java.awt.Button;
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

	public void initialize() {
	    ExecutorService executorService = Executors.newFixedThreadPool(3);

	    try {
	        // Running each method in a separate CompletableFuture, executing in parallel
	        CompletableFuture<Void> chartFuture = CompletableFuture.runAsync(this::fetchDataForChart, executorService);
	        CompletableFuture<Void> tableFuture = CompletableFuture.runAsync(this::fetchDataForTableView, executorService);
	        CompletableFuture<Void> textAreaFuture = CompletableFuture.runAsync(this::fetchDataForTextArea, executorService);
	        chartFuture.get();
	        tableFuture.get();
	        textAreaFuture.get();
	    } catch (Exception e) {
	        logger.error("Error with threads", e);
	    } finally {
	        executorService.shutdown();
	    }
	}

	private void fetchDataForTextArea() {
		BankScraper.startWIGInformationScraper();
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
				
			} else {
				System.err.println("TextArea is null. Make sure it's properly initialized.");
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

	private void fetchDataForChart() {
		BankScraper.startInformationToChartScraper();
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

		yAxis.setLowerBound(Collections.min(BankScraper.chartYValue) - 5);
		yAxis.setUpperBound(Collections.max(BankScraper.chartYValue) + 5);
		wig20Chart.getData().add(series);
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

	/*
	 * @FXML private void switchToCompanyChart(ActionEvent event) { try { Button
	 * clickedButton = (Button) event.getSource(); String companyName = (String)
	 * clickedButton.getUserData();
	 * 
	 * // Wywołaj metodę, która ustawia wykres dla danej spółki
	 * setCompanyChart(companyName); } catch (Exception e) {
	 * logger.error("Error switching to company chart", e); } }
	 */
	/*
	 * private void handleOpenSecondWindow() { try { FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource("ChartCompany.fxml")); Parent root =
	 * loader.load(); Stage secondStage = new Stage(); secondStage.setScene(new
	 * Scene(root)); secondStage.show(); } catch (Exception e) {
	 * logger.error("Error opening the second window", e); e.printStackTrace(); } }
	 */
}
