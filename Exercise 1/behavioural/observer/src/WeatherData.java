package observer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherData implements Subject {
    private static final Logger LOGGER = Logger.getLogger(WeatherData.class.getName());
    private List<Observer> observers;
    private double temperature;
    private double humidity;
    private double pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        if (o == null) {
            LOGGER.warning("Attempted to register a null observer.");
            return;
        }
        if (!observers.contains(o)) {
            observers.add(o);
            LOGGER.info("Observer registered: " + o.getClass().getSimpleName());
        } else {
            LOGGER.info("Observer already registered: " + o.getClass().getSimpleName());
        }
    }

    @Override
    public void removeObserver(Observer o) {
        if (o == null) {
            LOGGER.warning("Attempted to remove a null observer.");
            return;
        }
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
            LOGGER.info("Observer removed: " + o.getClass().getSimpleName());
        } else {
            LOGGER.info("Observer not found for removal: " + o.getClass().getSimpleName());
        }
    }

    @Override
    public void notifyObservers() {
        if (observers.isEmpty()) {
            LOGGER.info("No observers to notify.");
            return;
        }
        LOGGER.info("Notifying observers of new measurements.");
        for (Observer observer : observers) {
            try {
                observer.update(temperature, humidity, pressure);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error notifying observer " + observer.getClass().getSimpleName(), e);
            }
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(double temperature, double humidity, double pressure) {
        // Defensive programming: validate inputs
        if (Double.isNaN(temperature) || Double.isNaN(humidity) || Double.isNaN(pressure)) {
            LOGGER.warning("Attempted to set measurements with NaN values. Ignoring update.");
            return;
        }
        if (temperature < -50 || temperature > 50 || humidity < 0 || humidity > 100 || pressure < 900 || pressure > 1100) {
            LOGGER.warning("Attempted to set measurements with out-of-range values. Ignoring update.");
            return;
        }

        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }
}
