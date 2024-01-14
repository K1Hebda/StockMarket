package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataColumn {

	private static Logger logger = LogManager.getLogger(DataColumn.class.getName());

	private final StringProperty ticker;
	private final StringProperty name;
	private final IntegerProperty package_;
	private final DoubleProperty stockRate;
	private final DoubleProperty change;
	private final DoubleProperty percentageChange;
	private final DoubleProperty impact;
	private final DoubleProperty tradingStake;
	private final DoubleProperty portfolioStake;

	// Constructor to initialize DataColumn with data
	public DataColumn(String name, String ticker, Double stockRate, Double change, Double percentageChange,
			Double impact, Double tradingStake, Integer package_, Double portfolioStake) {
		this.name = new SimpleStringProperty(name);
		this.ticker = new SimpleStringProperty(ticker);
		this.stockRate = new SimpleDoubleProperty(stockRate);
		this.change = new SimpleDoubleProperty(change);
		this.percentageChange = new SimpleDoubleProperty(percentageChange);
		this.impact = new SimpleDoubleProperty(impact);
		this.tradingStake = new SimpleDoubleProperty(tradingStake);
		this.package_ = new SimpleIntegerProperty(package_);
		this.portfolioStake = new SimpleDoubleProperty(portfolioStake);

		// Logging the creation of a new DataColumn instance
		logger.debug("Created DataColumn: {}", this);
	}

	// Access methods for ticker
	public String getTicker() {
		return ticker.get();
	}

	public void setTicker(String ticker) {
		this.ticker.set(ticker);
	}

	public StringProperty tickerProperty() {
		return ticker;
	}

	// Access methods for name
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	// Access methods for package
	public Integer getPackage() {
		return package_.get();
	}

	public void setPackage(Integer package_) {
		this.package_.set(package_);
	}

	public IntegerProperty packageProperty() {
		return package_;
	}

	// Access methods for stockRate
	public Double getStockRate() {
		return stockRate.get();
	}

	public void setStockRate(Double stockRate) {
		this.stockRate.set(stockRate);
	}

	public DoubleProperty stockRateProperty() {
		return stockRate;
	}

	// Access methods for change
	public Double getChange() {
		return change.get();
	}

	public void setChange(Double change) {
		this.change.set(change);
	}

	public DoubleProperty changeProperty() {
		return change;
	}

	// Access methods for percentageChange
	public Double getPercentageChange() {
		return percentageChange.get();
	}

	public void setPercentageChange(Double percentageChange) {
		this.percentageChange.set(percentageChange);
	}

	public DoubleProperty percentageChangeProperty() {
		return percentageChange;
	}

	// Access methods for impact
	public Double getImpact() {
		return impact.get();
	}

	public void setImpact(Double impact) {
		this.impact.set(impact);
	}

	public DoubleProperty impactProperty() {
		return impact;
	}

	// Access methods for portfolioStake
	public Double getPortfolioStake() {
		return portfolioStake.get();
	}

	public void setPortfolioStake(Double portfolioStake) {
		this.portfolioStake.set(portfolioStake);
	}

	public DoubleProperty portfolioStakeProperty() {
		return portfolioStake;
	}

	// Access methods for tradingStake
	public Double getTradingStake() {
		return tradingStake.get();
	}

	public void setTradingStake(Double tradingStake) {
		this.tradingStake.set(tradingStake);
	}

	public DoubleProperty tradingStakeProperty() {
		return tradingStake;
	}

}
