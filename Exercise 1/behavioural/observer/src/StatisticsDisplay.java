package observer;

import java.util.logging.Logger;

public class StatisticsDisplay implements Observer, DisplayElement {
    private static final Logger LOGGER = Logger.getLogger(StatisticsDisplay.class.getName());
    private double maxTemp = Double.MIN_VALUE;
    private double minTemp = Double.MAX_VALUE;
    private double tempSum = 0.0;
    private int numReadings;
    private Subject weatherData;

    public StatisticsDisplay(Subject weatherData) {
        if (weatherData == null) {
            throw new IllegalArgumentException("WeatherData subject cannot be null.");
        }
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
        LOGGER.info("StatisticsDisplay registered with WeatherData.");
    }

    @Override
    public void update(double temperature, double humidity, double pressure) {
        tempSum += temperature;
        numReadings++;

        if (temperature > maxTemp) {
            maxTemp = temperature;
        }

        if (temperature < minTemp) {
            minTemp = temperature;
        }

        display();
    }

    @Override
    public void display() {
        if (numReadings == 0) {
            LOGGER.info("Statistics: No readings yet.");
            return;
        }
        LOGGER.info("Avg/Max/Min temperature = " + (tempSum / numReadings) + "C/" + maxTemp + "C/" + minTemp + "C");
    }
}
