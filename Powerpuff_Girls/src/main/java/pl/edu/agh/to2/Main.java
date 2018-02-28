package pl.edu.agh.to2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            URL url = getClass().getClassLoader().getResource("./boardView.fxml");
            Group root = FXMLLoader.load(url);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Powerpuff Girl");
            scene.getStylesheets().add("classic.css");

            initThemeButton(scene, root);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initThemeButton(Scene scene, Group root) {
        final String classic = "classic.css";
        final String light = "light.css";

        final Button button = new Button("Dark/Light Theme");
        button.getStyleClass().add("LightTheme");
        button.setOnAction(event -> {
            if (scene.getStylesheets().contains(classic)) {
                scene.getStylesheets().remove(classic);
                scene.getStylesheets().add(light);
            } else if (scene.getStylesheets().contains(light)) {
                scene.getStylesheets().remove(light);
                scene.getStylesheets().add(classic);
            }
        });
        VBox vBox = new VBox();
        vBox.spacingProperty().setValue(10);
        vBox.layoutXProperty().setValue(15.0);
        vBox.layoutYProperty().setValue(600.0);
        vBox.getChildren().add(button);
        root.getChildren().add(vBox);
    }
}