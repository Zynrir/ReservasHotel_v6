package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;

public class ControladorVentanaInsertaHuesped {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private TextField tfCorreo;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    void aceptar(ActionEvent event) {

        try{
            if(Dialogos.mostrarDialogoConfirmacion("Adventencia insertar huesped","Esta seguro de insertar este huesped")){
                VistaGrafica.getInstancia().getControlador().insertar(new Huesped(tfNombre.getText(), tfDni.getText(),tfCorreo.getText(),tfTelefono.getText(),dpFechaNacimiento.getValue()));
                Dialogos.mostrarDialogoInformacion("Confirmacion huesped", "huesped insertado correctamente");
            }
        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("Error al insertar huesped",e.getMessage());
        }
        ((Stage)btnAceptar.getScene().getWindow()).close();
    }

    @FXML
    void cancelar(ActionEvent event) {

        ((Stage)btnCancelar.getScene().getWindow()).close();

    }


}
