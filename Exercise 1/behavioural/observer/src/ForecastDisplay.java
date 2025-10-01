package observer;

import java.util.logging.Logger;

public class ForecastDisplay implements Observer, DisplayElement {
    private static final Logger LOGGER = Logger.getLogger(ForecastDisplay.class.getName());
    private double currentPressure = 0.0;
    private double lastPressure;
    private Subject weatherData;

    public ForecastDisplay(Subject weatherData) {
        if (weatherData == null) {
            throw new IllegalArgumentException("WeatherData subject cannot be null.");
        }
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
        LOGGER.info("ForecastDisplay registered with WeatherData.");
    }

    @Override
    public void update(double temperature, double humidity, double pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;
        display();
    }

    @Override
    public void display() {
        LOGGER.info("Forecast: ");
        if (currentPressure > lastPressure) {
            LOGGER.info("  Improving weather on the way!");
        } else if (currentPressure == lastPressure) {
            LOGGER.info("  More of the same.");
        } else if (currentPressure < lastPressure) {
            LOGGER.info("  Watch out for cooler, rainy weather.");
        }
    }
}
