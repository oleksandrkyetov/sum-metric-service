# Quick user guide
1. `mvn clean install -U`
2. `java -jar target/sum-metric-service-1.0.0-SNAPSHOT.jar`
3. `curl -X POST -H 'Content-Type: application/json' localhost:8080/metric/key-0001 -d '{"value": 15}'`
4. `curl -X GET localhost:8080/metric/key-0001/sum`

# Long user guide
1. Build using maven `mvn clean install -U`
2. Start server `java -jar target/sum-metric-service-1.0.0-SNAPSHOT.jar`
3. Add some metric values `curl -X POST -H 'Content-Type: application/json' localhost:8080/metric/key-0001 -d '{"value": 15}'`
4. Get metric `curl -X GET localhost:8080/metric/key-0001/sum`

# Misc
1. By default server runs on port 8080
2. Metric window length is configurable in application.properties through window.length.seconds, default value is 3600 seconds
 