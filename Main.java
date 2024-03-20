import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating a VBox for the plant list
        VBox plantList = new VBox();
        plantList.setSpacing(5);
        plantList.setPadding(new Insets(10));

        // Creating a ScrollPane to hold the plant list
        ScrollPane scrollPane = new ScrollPane(plantList);
        scrollPane.setFitToWidth(true);

        // Creating buttons for adding and removing plants
        Button addPlantButton = new Button("Add Plant");
        Button removePlantButton = new Button("Remove Plant");

        // Adding functionality to the add and remove buttons
        addPlantButton.setOnAction(e -> addPlant(plantList));
        removePlantButton.setOnAction(e -> removePlant(plantList));

        // Creating a VBox for the buttons
        VBox buttonBox = new VBox();
        buttonBox.getChildren().addAll(addPlantButton, removePlantButton);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        // Creating a BorderPane to hold all components
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setRight(buttonBox);

        // Creating the scene
        Scene scene = new Scene(root, 400, 300);

        // Setting the scene to the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("S.C.A.M");
        primaryStage.show();
    }

    // Method to add a new plant to the list
    private void addPlant(VBox plantList) {
        // Creating a dialog for adding a new plant
        Dialog<Plant> dialog = new Dialog<>();
        dialog.setTitle("Add Plant");
        dialog.setHeaderText("Add a new plant");

        // Set the button types
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the labels and text fields for plant properties
        TextField nameField = new TextField();
        TextField hydrationField = new TextField();
        TextField soilField = new TextField();
        TextField humidityField = new TextField();
        TextField typeField = new TextField();

        // Set the labels for the text fields
        Label nameLabel = new Label("Name:");
        Label hydrationLabel = new Label("Hydration %:");
        Label soilLabel = new Label("Soil:");
        Label humidityLabel = new Label("Humidity %:");
        Label typeLabel = new Label("Plant Type:");

        // Create a VBox to hold the labels and text fields
        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(nameLabel, nameField, hydrationLabel, hydrationField,
                soilLabel, soilField, humidityLabel, humidityField, typeLabel, typeField);
        dialogContent.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(dialogContent);

        // Convert the result to a plant object when the add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    double hydration = Double.parseDouble(hydrationField.getText());
                    double humidity = Double.parseDouble(humidityField.getText());
                    return new Plant(nameField.getText(), hydration, soilField.getText(), humidity, typeField.getText());
                } catch (NumberFormatException e) {
                    showErrorAlert("Please enter valid numeric values for hydration and humidity.");
                    return null;
                }
            }
            return null;
        });

        // Show the dialog and add the plant to the list if "Add" is clicked
        dialog.showAndWait().ifPresent(plant -> {
            Button plantButton = new Button(plant.getName());
            plantButton.setOnAction(e -> editPlantProperties(plant));
            plantList.getChildren().add(plantButton);
        });
    }

    // Method to remove the last plant from the list
    private void removePlant(VBox plantList) {
        // Removing the last plant from the list
        if (!plantList.getChildren().isEmpty()) {
            plantList.getChildren().remove(plantList.getChildren().size() - 1);
        }
    }

    // Method to edit properties of a plant
    private void editPlantProperties(Plant plant) {
        // Creating a dialog for editing plant properties
        Dialog<Plant> dialog = new Dialog<>();
        dialog.setTitle("Edit Plant Properties");
        dialog.setHeaderText("Editing properties of " + plant.getName());

        // Set the button types
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        // Create the labels and text fields for plant properties
        TextField nameField = new TextField(plant.getName());
        TextField hydrationField = new TextField(Double.toString(plant.getHydration()));
        TextField soilField = new TextField(plant.getSoil());
        TextField humidityField = new TextField(Double.toString(plant.getHumidity()));
        TextField typeField = new TextField(plant.getType());

        // Set the labels for the text fields
        Label nameLabel = new Label("Name:");
        Label hydrationLabel = new Label("Hydration:");
        Label soilLabel = new Label("Soil:");
        Label humidityLabel = new Label("Humidity:");
        Label typeLabel = new Label("Plant Type:");

        // Create a VBox to hold the labels and text fields
        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(nameLabel, nameField, hydrationLabel, hydrationField,
                soilLabel, soilField, humidityLabel, humidityField, typeLabel, typeField);
        dialogContent.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(dialogContent);

        // Convert the result to a plant object when the save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButton) {
                try {
                    double hydration = Double.parseDouble(hydrationField.getText());
                    double humidity = Double.parseDouble(humidityField.getText());
                    return new Plant(nameField.getText(), hydration, soilField.getText(), humidity, typeField.getText());
                } catch (NumberFormatException e) {
                    showErrorAlert("Please enter valid numeric values for hydration and humidity.");
                    return null;
                }
            }
            return null;
        });

        // Show the dialog and update the plant if "Save" is clicked
        dialog.showAndWait().ifPresent(updatedPlant -> {
            // Update the properties of the plant
            plant.setName(updatedPlant.getName());
            plant.setHydration(updatedPlant.getHydration());
            plant.setSoil(updatedPlant.getSoil());
            plant.setHumidity(updatedPlant.getHumidity());
            plant.setType(updatedPlant.getType());
        });
    }

    // Method to show an error alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Inner class representing a Plant with properties: name, hydration, soil, humidity, and type
    private static class Plant {
        private String name;
        private double hydration;
        private String soil;
        private double humidity;
        private String type;

        public Plant(String name, double hydration, String soil, double humidity, String type) {
            this.name = name;
            this.hydration = hydration;
            this.soil = soil;
            this.humidity = humidity;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getHydration() {
            return hydration;
        }

        public void setHydration(double hydration) {
            this.hydration = hydration;
        }

        public String getSoil() {
            return soil;
        }

        public void setSoil(String soil) {
            this.soil = soil;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Plant{" +
                    "name='" + name + '\'' +
                    ", hydration=" + hydration +
                    ", soil='" + soil + '\'' +
                    ", humidity=" + humidity +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}

