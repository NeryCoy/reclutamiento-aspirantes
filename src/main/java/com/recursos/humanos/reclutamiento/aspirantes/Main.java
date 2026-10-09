package main.java.com.recursos.humanos.reclutamiento.aspirantes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource("/main/resources/view/login-view.fxml");
            
            if (fxmlUrl == null) {
                throw new IOException("No se pudo encontrar el archivo login.fxml en los recursos.");
            }

            Parent root = FXMLLoader.load(fxmlUrl);
            primaryStage.setTitle("Portal de Reclutamiento - Iniciar Sesión");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error crítico al iniciar la aplicación: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}