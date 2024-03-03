import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ArduinoSerialPortObserverSIMULATOR {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Which condition would you like to simulate? (wet, normal, dry)");
		String condition = scanner.nextLine().trim().toLowerCase();

		String filePath = determineFilePath(condition);

		if (filePath == null) {
			System.out.println("Invalid condition entered. Please run the program again with one of the specified conditions: wet, normal, dry.");
			return;
		}

		simulateDataOutput(filePath);
	}

	private static String determineFilePath(String condition) {
		switch (condition) {
			case "wet":
				return "src/SerialPortDataSimulator/WetConditions";
			case "normal":
				return "src/SerialPortDataSimulator/NormalConditions";
			case "dry":
				return "src/SerialPortDataSimulator/DryConditions";
			default:
				return null;
		}
	}

	private static void simulateDataOutput(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				try {
					Thread.sleep(60000); // Pause for one minute
				} catch (InterruptedException e) {
					System.out.println("Simulation interrupted.");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
}
