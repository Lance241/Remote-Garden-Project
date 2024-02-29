import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
public class Main extends Application {
    @Override
    public void start(Stage window){
        BorderPane layout = new BorderPane();

        HBox buttons = new HBox();
        buttons.setSpacing(10);
        buttons.getChildren().add(new Button("Plant"));
        buttons.getChildren().add(new Button("Plant1"));
        buttons.getChildren().add(new Button("Plant2"));

        VBox texts = new VBox();
        texts.getChildren().add(new Label("Add/Remove"));

        layout.setTop(texts);
        layout.setLeft(buttons);

        layout.setCenter(new TextArea(""));

        Scene view = new Scene(layout);

        window.setScene(view);
        window.setTitle("S.C.A.M");
        window.show();
    }
    public static void main(String[] args){
        launch(Main.class);
    }

}
