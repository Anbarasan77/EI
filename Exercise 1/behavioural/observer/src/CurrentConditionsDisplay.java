package observer;

import java.util.logging.Logger;

public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private static final Logger LOGGER = Logger.getLogger(CurrentConditionsDisplay.class.getName());
    private double temperature;
    private double humidity;
    private Subject weatherData;

    public CurrentConditionsDisplay(Subject weatherData) {
        if (weatherData == null) {
            throw new IllegalArgumentException("WeatherData subject cannot be null.");
        }
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
        LOGGER.info("CurrentConditionsDisplay registered with WeatherData.");
    }

    @Override
    public void update(double temperature, double humidity, double pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        LOGGER.info("Current conditions: " + temperature + "C degrees and " + humidity + "% humidity.");
    }
}
