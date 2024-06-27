# TemperatureRegulator

## Run TemperatureRegulator locally
You can build a jar files and run them from the command line (it should work just as well with Java 17 or newer). A JavaFX 17+ installed module is also required.
```bash
git clone https://github.com/Alexiusss/temperature-regulator
cd temperature-regulator
mvn package
```
For starting server app:
```bash
java -jar ./server/target/*.jar 
```
For starting client app:
```bash
java --module-path path-to-javafx-module-here --add-modules javafx.controls,javafx.fxml -jar ./client/target/*.jar
```

You can then access the REST API documentation [here](http://localhost:9090/swagger-ui/index.html#/).
