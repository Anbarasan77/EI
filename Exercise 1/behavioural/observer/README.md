# Weather Station - Observer Design Pattern Demonstration

This project demonstrates the **Observer design pattern** using a simple Weather Station application implemented in Java. It simulates a system where weather data changes, and multiple display devices automatically update themselves to reflect these changes.

## Design Pattern: Observer

The Observer pattern is a behavioral design pattern that defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

In this application:

- **Subject:** The `WeatherData` class acts as the Subject. It holds the current weather measurements (temperature, humidity, pressure) and maintains a list of observers. When its measurements change, it notifies all registered observers.
- **Observers:** The `CurrentConditionsDisplay`, `StatisticsDisplay`, and `ForecastDisplay` classes are the concrete Observers. They register with the `WeatherData` subject and are notified whenever new measurements are available. Each observer then updates and displays its specific information.

## Project Structure

The project is organized into the following files, adhering to best practices where each class/interface resides in its own `.java` file within the `observer` package:

```
.
├── bin/                      # Compiled Java bytecode (.class files)
│   └── observer/
│       ├── CurrentConditionsDisplay.class
│       ├── DisplayElement.class
│       ├── ForecastDisplay.class
│       ├── Main.class
│       ├── Observer.class
│       ├── StatisticsDisplay.class
│       ├── Subject.class
│       └── WeatherData.class
└── src/                      # Java source code files
    └── observer/
        ├── CurrentConditionsDisplay.java
        ├── DisplayElement.java
        ├── ForecastDisplay.java
        ├── Main.java
        ├── Observer.java
        ├── StatisticsDisplay.java
        ├── Subject.java
        └── WeatherData.java
```

## How to Run the Application

Follow these steps to compile and run the Weather Station application:

1. **Navigate to the Project Directory:**
   Open your terminal or command prompt and navigate to the root directory of this project (e.g., `c:\EI\Exercise 1\behavioural\observer`).

   ```bash
   cd c:\EI\Exercise 1\behavioural\observer
   ```

2. **Compile the Java Source Files:**
   Compile all the `.java` files located in the `src` directory. This will create `.class` files in the `bin` directory.

   ```bash
   javac -d bin src/*.java
   ```

3. **Run the Application:**
   Execute the `Main` class from the `bin` directory.

   ```bash
   java -cp bin observer.Main
   ```

4. **Interact with the Application:**
   - The application will start and prompt you to enter weather data.
   - Type three numbers separated by spaces (e.g., `25.0 65 1010.0`) for temperature, humidity, and pressure, then press Enter.
   - Observe how the `CurrentConditionsDisplay`, `StatisticsDisplay`, and `ForecastDisplay` update their information in the console.
   - You can enter new data as many times as you like.
   - To stop the application, type `exit` and press Enter.

## Sample Interactions

Here are some example inputs and their expected effects:

- **Initial Input:** `25.0 65 1010.0`

  - _Effect:_ Displays show initial readings. Forecast indicates "Improving weather" (due to initial pressure being 0.0 before this input).

- **Temperature Increase, Pressure Stable:** `28.5 62 1010.0`

  - _Effect:_ Current conditions and statistics update. Forecast shows "More of the same."

- **Humidity Decrease, Pressure Drop (Rainy Forecast):** `27.0 58 998.5`

  - _Effect:_ Current conditions and statistics update. Forecast shows "Watch out for cooler, rainy weather."

- **Temperature Decrease, Pressure Rise (Improving Forecast):** `22.1 70 1015.2`

  - _Effect:_ Current conditions and statistics update. Forecast shows "Improving weather on the way!"

- **Invalid Number Format:** `abc 60 1000`

  - _Effect:_ A `WARNING` log message about "Invalid number format" will appear. Displays will not update.

- **Incorrect Number of Values:** `25.0 60`

  - _Effect:_ A `WARNING` log message about "Invalid input format" will appear. Displays will not update.

- **Out-of-Range Values (e.g., very high temperature):** `100.0 50 1000.0`

  - _Effect:_ A `WARNING` log message about "Input values are out of a reasonable range" will appear. Displays will not update.

- **Exit Command:** `exit`
  - _Effect:_ The application will log an exit message and terminate.

## Best Practices Adhered To

- **Code Organization:** Each class and interface is in its own `.java` file.
- **Naming Conventions:** Standard Java naming conventions are followed.
- **Logging Mechanism:** Uses `java.util.logging` for informative output and error tracking.
- **Exception Handling:** Robust `try-catch` blocks are used for input parsing and unexpected errors.
- **Defensive Programming:** Inputs are validated (e.g., null checks, range checks) to prevent invalid state.
- **No Hardcoded Booleans:** The main application loop uses a `while(true)` with a clear `break` condition based on user input, avoiding hardcoded `while(true)` for menu display.
- **Performance Optimization:** For this console application, performance is optimized by using efficient data structures (ArrayList for observers) and avoiding unnecessary computations.
