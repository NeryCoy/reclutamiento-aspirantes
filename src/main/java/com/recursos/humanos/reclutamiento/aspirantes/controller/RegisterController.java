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

public class RegisterController {

    @FXML private TextField txtIdUsuario;     //id_usuario
    @FXML private TextField txtNombre;        //nombre del usuario
    @FXML private TextField txtEmail;         //correo electrónico
    @FXML private PasswordField txtPassword;  //contraseña
    @FXML private PasswordField txtConfirmPassword; //confirmación de contraseña

    private final UserService userService = UserService.getInstance();

    @FXML
    private void handleRegister() {
        try {
            String idUsuario = txtIdUsuario != null ? txtIdUsuario.getText() : "";
            String nombre = txtNombre != null ? txtNombre.getText() : "";
            String email = txtEmail != null ? txtEmail.getText() : "";
            String password = txtPassword != null ? txtPassword.getText() : "";
            String confirmPassword = txtConfirmPassword != null ? txtConfirmPassword.getText() : "";

            //rol por defecto para nuevos registros
            int rolId = 2; 

            //llamamos al servicio para validar
            boolean registered = userService.registrar(idUsuario, nombre, email, password, confirmPassword, rolId);

            if (registered) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Usuario registrado correctamente!");
                regresarAlLogin();
            }
        } catch (BusinessException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Error de validación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar: " + e.getMessage());
        }
    }

    @FXML
    private void regresarAlLogin() {
        try {
            //cerramos la ventana de registro
            Stage currentStage = (Stage) txtEmail.getScene().getWindow();
            currentStage.close();

            //abrimos de nuevo el Login
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/login-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Reclutamiento");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al volver al login: " + e.getMessage());
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