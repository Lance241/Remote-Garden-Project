package SerialPortDataSimulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ArduinoSerialPortObserverSIMULATOR {

	enum Condition {
		WET("src/SerialPortDataSimulator/WetConditions"),
		NORMAL("src/SerialPortDataSimulator/NormalConditions"),
		DRY("src/SerialPortDataSimulator/DryConditions");

		private final String filePath;

		Condition(String filePath) {
			this.filePath = filePath;
		}

		public String getFilePath() {
			return filePath;
		}
	}

	private static void simulateDataOutput(String filePath) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(filePath));
			ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			final int[] counter = {0}; // Use an array to allow modification inside lambda

			Runnable command = () -> {
				if (counter[0] < lines.size()) {
					System.out.println(lines.get(counter[0]++));
				} else {
					executor.shutdown(); // Shut down the executor once all lines are printed
				}
			};

			// Schedule the command to run once every second
			executor.scheduleAtFixedRate(command, 0, 1, TimeUnit.SECONDS);

		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
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

		simulateDataOutput(condition.getFilePath());
	}
}
