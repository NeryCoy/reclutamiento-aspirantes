package main.java.com.recursos.humanos.reclutamiento.aspirantes.controller;

import main.java.com.recursos.humanos.reclutamiento.aspirantes.service.UserService;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.exception.BusinessException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private final UserService userService = UserService.getInstance();

    @FXML
    private void handleLogin() {
        try {
            String correo = txtEmail.getText();
            String password = txtPassword.getText();

            //llamar al servicio para iniciar sesión
            String rol = userService.iniciarSesion(correo, password);

            if (rol != null) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Bienvenido", "Inicio de sesión exitoso. Rol: " + rol);
                abrirDashboard(rol);
            }
        } catch (BusinessException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de autenticación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al iniciar sesión: " + e.getMessage());
        }
    }

    @FXML
    private void abrirRegistro() {
        try {
            //cerrar la ventana de Login
            Stage currentStage = (Stage) txtEmail.getScene().getWindow();
            currentStage.close();

            //abrir la vista de Registro
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/register-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Usuarios");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al abrir la ventana de registro: " + e.getMessage());
        }
    }

    private void abrirDashboard(String rol) {
        try {
            //cerramos la ventana de Login
            Stage currentStage = (Stage) txtEmail.getScene().getWindow();
            currentStage.close();

            //abrimos la vista del Dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/dashboard-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel Principal - Reclutamiento de Aspirantes");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al abrir el dashboard: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}