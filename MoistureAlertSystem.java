/**
 *
 * @author Cameron Meng
 *
 * @since Version 1.0
 *
 */
public class MoistureAlertSystem {
    private static final int DRY_THRESHOLD = 20; // can be removed to set thresholds within database per plant.
    private static final int WET_THRESHOLD = 80; // can be removed to set thresholds within database per plant.

    public static void main(String[] args) {
        int currentMoistureLevel = getCurrentMoistureLevel();
        if (currentMoistureLevel < DRY_THRESHOLD) {
            triggerAlert("Moisture level is too low! Please water your plants.");
        } else if (currentMoistureLevel > WET_THRESHOLD) {
            triggerAlert("Moisture level is too high! Consider reducing watering.");
        } else {
            System.out.println("Moisture level is within the acceptable range, way to go!");
        }
    }
    private static int getCurrentMoistureLevel() { // For demonstration purposes it currently returns a random value between 0 and 100.
        return (int) (Math.random() * 101);
    }  // The getCurrentMoistureLevel method is a placeholder until I can figure out how to obtain the actual moisture level from the sensor.
    private static void triggerAlert(String message) {
        System.out.println("ALERT: " + message);
    }
}