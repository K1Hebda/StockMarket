package application;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Wig20Controller {
	
	private static Logger logger = LogManager.getLogger(Main.class.getName());

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<DataColumn, String> changeColumn;

    @FXML
    private TableColumn<DataColumn, String> impactColumn;

    @FXML
    private TableColumn<DataColumn, String> nameColumn;

    @FXML
    private TableColumn<DataColumn, String> packageColumn;

    @FXML
    private TableColumn<DataColumn, String> portfolioStakeColumn;

    @FXML
    private TableColumn<DataColumn, String> percentageChangeColumn;

    @FXML
    private TableColumn<DataColumn, String> stockRateColumn;

    @FXML
    private TableView<DataColumn> tableView;

    @FXML
    private TableColumn<DataColumn, String> tickerColumn;

    @FXML
    private TableColumn<DataColumn, String> tradingStakeColumn;

    public void initialize() {
    	BankScraper.startScraper();
        logger.warn("Fetching data for the table");
        try {
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            tickerColumn.setCellValueFactory(cellData -> cellData.getValue().tickerProperty());
            stockRateColumn.setCellValueFactory(cellData -> cellData.getValue().stockRateProperty());
            changeColumn.setCellValueFactory(cellData -> cellData.getValue().changeProperty());
            percentageChangeColumn.setCellValueFactory(cellData -> cellData.getValue().procentageChangeProperty());
            impactColumn.setCellValueFactory(cellData -> cellData.getValue().impactProperty());
            tradingStakeColumn.setCellValueFactory(cellData -> cellData.getValue().tradingStakeProperty());
            packageColumn.setCellValueFactory(cellData -> cellData.getValue().packageProperty());
            portfolioStakeColumn.setCellValueFactory(cellData -> cellData.getValue().portfolioStakeProperty());

            // Populate TableView with data
            for (ArrayList<String> row : BankScraper.ListOfCompanies) {
                if (row.size() == 9) {
                    DataColumn dataColumn = new DataColumn(row.get(0), row.get(1), row.get(2), row.get(3),
                            row.get(4), row.get(5), row.get(6), row.get(7), row.get(8));

                    // Log each row added to the TableView
                    logger.debug("Adding row to TableView: {}", dataColumn);
                    tableView.getItems().add(dataColumn);
                } else {
                    logger.error("Incorrect number of elements in a row: {}", row);
                    System.err.println("Incorrect number of elements");
                }
            }
            logger.info("End of fetching data for the table");
        } catch (Exception e) {
        	logger.error("Error while populating table data", e);
            e.printStackTrace();
        }
    }

    public void switchToMain(ActionEvent event) {
    	logger.info("Switching to the Main scene");
        try {
            BankScraper.ListOfCompanies.clear();

            // Log when clearing ListOfCompanies
            logger.debug("Clearing ListOfCompanies");

            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
        	logger.error("Error while switching to the Main scene", e);
            e.printStackTrace();
        }
    }
}
