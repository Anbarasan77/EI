package observer;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        setupLogger();
        LOGGER.info("Weather Station Application Started.");

        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        Scanner scanner = new Scanner(System.in);
        String input;

        LOGGER.info("Enter weather data (temperature, humidity, pressure) separated by spaces. Type 'exit' to quit.");
        LOGGER.info("Example: 25.5 60 1012.3");

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                LOGGER.info("Exiting Weather Station Application.");
                break;
            }

            try {
                String[] parts = input.split(" ");
                if (parts.length != 3) {
                    LOGGER.warning("Invalid input format. Please enter temperature, humidity, and pressure.");
                    continue;
                }

                double temperature = Double.parseDouble(parts[0]);
                double humidity = Double.parseDouble(parts[1]);
                double pressure = Double.parseDouble(parts[2]);

                // Basic validation
                if (temperature < -50 || temperature > 50 || humidity < 0 || humidity > 100 || pressure < 900 || pressure > 1100) {
                    LOGGER.warning("Input values are out of a reasonable range. Please check your data.");
                    continue;
                }

                weatherData.setMeasurements(temperature, humidity, pressure);
                LOGGER.info("Measurements updated: Temp=" + temperature + ", Humidity=" + humidity + ", Pressure=" + pressure);

            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid number format. Please ensure all inputs are valid numbers.", e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An unexpected error occurred during input processing.", e);
            }
        }

        scanner.close();
        LOGGER.info("Weather Station Application Finished.");
    }

    private static void setupLogger() {
        LOGGER.setLevel(Level.INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false); // Prevent logging to console twice
    }
}
