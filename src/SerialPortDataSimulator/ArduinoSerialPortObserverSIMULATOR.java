package SerialPortDataSimulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ArduinoSerialPortObserverSIMULATOR {

	enum Condition {
		WET, NORMAL, DRY
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Which condition would you like to simulate? (wet, normal, dry)");
		String input = scanner.nextLine().trim().toUpperCase();

		Condition condition;
		try {
			condition = Condition.valueOf(input);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid condition entered. Please run the program again with one of the specified conditions: wet, normal, dry.");
			return;
		}

		String filePath = determineFilePath(condition);
		simulateDataOutput(filePath);
	}

	private static String determineFilePath(Condition condition) {
		switch (condition) {
			case WET:
				return "src/SerialPortDataSimulator/WetConditions";
			case NORMAL:
				return "src/SerialPortDataSimulator/NormalConditions";
			case DRY:
				return "src/SerialPortDataSimulator/DryConditions";
			default:
				return null; // This should never happen
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
					Thread.currentThread().interrupt(); // Restore the interrupted status
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
}
