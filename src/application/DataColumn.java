package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataColumn {
	
	private static Logger logger = LogManager.getLogger(Main.class.getName());
	
	private final StringProperty ticker;
	private final StringProperty name;
	private final StringProperty package_;
	private final StringProperty stockRate;
	private final StringProperty change;
	private final StringProperty percentageChange;
	private final StringProperty impact;
	private final StringProperty portfolioStake;
	private final StringProperty tradingStake;

	// Constructor to initialize DataColumn with data
	public DataColumn(String name, String ticker, String lastPrice, String change, String percentageChange,
			String impact, String tradingStake, String package_, String portfolioStake) {
		this.name = new SimpleStringProperty(name);
		this.ticker = new SimpleStringProperty(ticker);
		this.stockRate = new SimpleStringProperty(lastPrice);
		this.change = new SimpleStringProperty(change);
		this.percentageChange = new SimpleStringProperty(percentageChange);
		this.impact = new SimpleStringProperty(impact);
		this.tradingStake = new SimpleStringProperty(tradingStake);
		this.package_ = new SimpleStringProperty(package_);
		this.portfolioStake = new SimpleStringProperty(portfolioStake);
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
	public String getPackage() {
		return package_.get();
	}

	public void setPackage(String package_) {
		this.package_.set(package_);
	}

	public StringProperty packageProperty() {
		return package_;
	}

	// Access methods for stockRate
	public String getStockRate() {
		return stockRate.get();
	}

	public void setStockRate(String stockRate) {
		this.stockRate.set(stockRate);
	}

	public StringProperty stockRateProperty() {
		return stockRate;
	}

	// Access methods for change
	public String getChange() {
		return change.get();
	}

	public void setChange(String change) {
		this.change.set(change);
	}

	public StringProperty changeProperty() {
		return change;
	}

	// Access methods for percentageChange
	public String getProcentageChange() {
		return percentageChange.get();
	}

	public void setProcentageChange(String procentageChange) {
		this.percentageChange.set(procentageChange);
	}

	public StringProperty procentageChangeProperty() {
		return percentageChange;
	}

	// Access methods for impact
	public String getImpact() {
		return impact.get();
	}

	public void setImpact(String impact) {
		this.impact.set(impact);
	}

	public StringProperty impactProperty() {
		return impact;
	}

	// Access methods for portfolioStake
	public String getPortfolioStake() {
		return portfolioStake.get();
	}

	public void setPortfolioStake(String portfolioStake) {
		this.portfolioStake.set(portfolioStake);
	}

	public StringProperty portfolioStakeProperty() {
		return portfolioStake;
	}

	// Access methods for tradingStake
	public String getTradingStake() {
		return tradingStake.get();
	}

	public void setTradingStake(String tradingStake) {
		this.tradingStake.set(tradingStake);
	}

	public StringProperty tradingStakeProperty() {
		return tradingStake;
	}

}
