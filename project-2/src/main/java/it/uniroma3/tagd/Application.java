package it.uniroma3.tagd;

public class Application {

    public static void main(String[] args) {
        Database.reset();
        for (Anomaly anomaly : Anomaly.values()) {
            for (IsolationLevel isolationLevel : IsolationLevel.values()) {
                Anomalies.testAnomaly(isolationLevel, anomaly);
            }
        }
    }
}
