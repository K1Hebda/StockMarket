
<table>
  <tr>
    <td valign="top" style="text-align: left ">
      <h1>Stock Market App </h1>
    </td>
    <td valign="top" style="text-align: right "><img src="https://raw.githubusercontent.com/Kamil-Hebda/StockMarket/main/resources/logo.jpg" width="100"></td>
  </tr>
</table>


This project is a graphical stock market simulation written in Java. It fetches and displays current WIG20 stock market quotations using web scraping techniques. The application features logging for performance analysis and employs a multi-threaded approach to optimize data fetching and display.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Project Structure](#project-structure)
- [Classes and Their Functions](#classes-and-their-functions)
  - [ChartData](#chartdata)
  - [BankScraper](#bankscraper)
  - [DataColumn](#datacolumn)
  - [InternetChecker](#internetchecker)
  - [Main](#main)
  - [SceneController](#scenecontroller)
  - [Wig20Controller](#wig20controller)
- [Logging](#logging)

## Features

- Monitor real-time WIG20 stock prices
- Graphical user interface created with Scene Builder
- Logging for application analysis

## Requirements

- Java Runtime Environment (JRE)
- Externals libraries
  - `org.eclipse.fx.ide.css.jfx8_3.10.0.202303150700.jar`
  - `jsoup-1.17.2.jar`
  - `log4j-api-2.22.1.jar` and `log4j-core-2.22.1.jar`
- JavaFX

## Project Structure

The project consists of the following Java files:

- **ChartData.java**: Class representing data for chart plotting.
- **BankScraper.java**: Class for scraping stock market data from various online sources.
- **DataColumn.java**: Class representing company data to be displayed in a table.
- **InternetChecker.java**: Class for checking internet connectivity.
- **Main.java**: Main class of the application launching the user interface.
- **SceneController.java**: Controller class responsible for managing the application's scenes.
- **Wig20Controller.java**: Controller class responsible for displaying WIG20 data and handling user interactions.

## Classes and Their Functions

### ChartData
- **Package**: application
- **Purpose**: Holds data for chart plotting.
- **Attributes**:
  - `date`: Date of the data point.
  - `high`: High value of the data point.
- **Constructor**:
  - `ChartData(String date, double high)`: Initializes the date and high value, logs creation info.
- **Methods**:
  - `getDate()`: Returns the date.
  - `getHigh()`: Returns the high value.

### BankScraper
- **Package**: application
- **Purpose**: Scrapes stock market data from specified URLs.
- **Attributes**:
  - `ListOfCompanies`: Stores scraped data of companies.
  - `titleText`: Titles of the WIG information.
  - `valueText`: Values of the WIG information.
  - `chartYValue`: Y-axis values for charts.
  - `chartXValue`: X-axis values for charts.
- **Methods**:
  - `startWIG20Scraper()`: Scrapes WIG20 stock quotes.
  - `startWIGInformationScraper(String company)`: Scrapes WIG information for a specified company.
  - `startInformationToChartScraper(String company)`: Scrapes data for chart plotting.

### DataColumn
- **Package**: application
- **Purpose**: Represents a column of data for a company.
- **Attributes**:
  - `ticker`, `name`, `package_`, `stockRate`, `change`, `percentageChange`, `impact`, `tradingStake`, `portfolioStake`: Various properties of the company data.
- **Constructor**:
  - `DataColumn(String name, String ticker, Double stockRate, Double change, Double percentageChange, Double impact, Double tradingStake, Integer package_, Double portfolioStake)`: Initializes all properties, logs creation info.
- **Methods**: Getter and setter methods for all properties.

### InternetChecker
- **Package**: application
- **Purpose**: Checks internet connectivity.
- **Methods**:
  - `isInternetReachableUsingPing()`: Checks if the internet is reachable using a ping to Google.
  - `errorScene()`: Displays an error scene if no internet connection is detected.

### Main
- **Package**: application
- **Purpose**: Entry point for the application.
- **Methods**:
  - `start(Stage stage)`: Initializes and shows the main stage.
  - `main(String[] args)`: Launches the application.

### SceneController
- **Package**: application
- **Purpose**: Handles scene transitions within the application.
- **Methods**:
  - `switchToWIG20(ActionEvent event)`: Switches to the WIG20 scene if the internet is reachable.

### Wig20Controller
- **Package**: application
- **Purpose**: Controls the WIG20 scene.
- **Attributes**: Various UI elements like buttons, charts, text areas, table columns.
- **Methods**:
  - `initialize()`: Initializes the controller, fetches data for charts, tables, and text areas.
  - `fetchDataForTextArea(String company)`: Fetches and displays WIG information in text areas.
  - `fetchDataForTableView()`: Fetches and populates data in the table view.
  - `fetchDataToChart(String company)`: Fetches and displays data for the chart.
  - `handleButtonAction(ActionEvent event)`: Handles button clicks to update data displays.

## Logging

The application uses the Log4j library for logging events. Log information is saved to log files and displayed on the console. The Log4j configuration file should be located in the project's logs folder.

