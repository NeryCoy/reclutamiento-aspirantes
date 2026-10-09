package main.java.com.recursos.humanos.reclutamiento.aspirantes.controller;

import main.java.com.recursos.humanos.reclutamiento.aspirantes.exception.BusinessException;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.model.AspiranteDTO;
import main.java.com.recursos.humanos.reclutamiento.aspirantes.service.AspiranteService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    //campos de texto para el formulario del aspirante
    @FXML private TextField txtIdAspirante;
    @FXML private TextField txtIdPais;
    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEdad;

    //tabla y columnas del Dashboard
    @FXML private TableView<AspiranteDTO> tableAspirantes;
    @FXML private TableColumn<AspiranteDTO, String> colIdAspirante;
    @FXML private TableColumn<AspiranteDTO, String> colIdPais;
    @FXML private TableColumn<AspiranteDTO, String> colIdUsuario;
    @FXML private TableColumn<AspiranteDTO, String> colNombre;
    @FXML private TableColumn<AspiranteDTO, String> colApellido;
    @FXML private TableColumn<AspiranteDTO, String> colTelefono;
    @FXML private TableColumn<AspiranteDTO, Integer> colEdad;

    private final AspiranteService aspiranteService = AspiranteService.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //vinculamos las columnas del TableView
        colIdAspirante.setCellValueFactory(new PropertyValueFactory<>("idAspirantes"));
        colIdPais.setCellValueFactory(new PropertyValueFactory<>("idPais"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        //cargar los datos en la tabla
        cargarTabla();

        //seleccionar un elemento de la tabla y pasarlo a los campos de texto
        tableAspirantes.setOnMouseClicked(event -> {
            AspiranteDTO seleccionado = tableAspirantes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                txtIdAspirante.setText(seleccionado.getIdAspirantes());
                txtIdAspirante.setEditable(false); // No permitimos cambiar la PK al actualizar
                txtIdPais.setText(seleccionado.getIdPais());
                txtIdUsuario.setText(seleccionado.getIdUsuario());
                txtNombre.setText(seleccionado.getNombre());
                txtApellido.setText(seleccionado.getApellido());
                txtTelefono.setText(seleccionado.getTelefono());
                txtEdad.setText(String.valueOf(seleccionado.getEdad()));
            }
        });
    }

    private void cargarTabla() {
        ObservableList<AspiranteDTO> lista = aspiranteService.obtenerAspirantes();
        tableAspirantes.setItems(lista);
    }

    @FXML
    private void handleGuardar() {
        try {
            boolean registrado = aspiranteService.registrarAspirante(
                    txtIdAspirante.getText(),
                    txtIdPais.getText(),
                    txtIdUsuario.getText(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText(),
                    txtEdad.getText()
            );

            if (registrado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Aspirante registrado correctamente.");
                cargarTabla();
                limpiarCampos();
            }
        } catch (BusinessException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar: " + e.getMessage());
        }
    }

    @FXML
    private void handleActualizar() {
        try {
            int edad = Integer.parseInt(txtEdad.getText());
            boolean actualizado = aspiranteService.actualizarAspirante(
                    txtIdAspirante.getText(),
                    txtIdPais.getText(),
                    txtIdUsuario.getText(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText(),
                    edad
            );

            if (actualizado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Aspirante actualizado correctamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el registro.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "La edad debe ser un número válido.");
        } catch (BusinessException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        AspiranteDTO seleccionado = tableAspirantes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar un aspirante de la tabla para eliminar.");
            return;
        }

        try {
            boolean eliminado = aspiranteService.eliminarAspirante(seleccionado.getIdAspirantes());
            if (eliminado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Aspirante eliminado correctamente.");
                cargarTabla();
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el registro.");
            }
        } catch (BusinessException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void handleLimpiar() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIdAspirante.clear();
        txtIdAspirante.setEditable(true);
        txtIdPais.clear();
        txtIdUsuario.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEdad.clear();
        tableAspirantes.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleCerrarSesion() {
        try {
            Stage currentStage = (Stage) tableAspirantes.getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/login-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Reclutamiento");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
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