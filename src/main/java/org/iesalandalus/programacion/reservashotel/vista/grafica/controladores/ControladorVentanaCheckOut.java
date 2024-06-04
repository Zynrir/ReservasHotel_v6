package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ControladorVentanaCheckOut {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private DatePicker dpFechaCheckOut;

    @FXML
    private TextField tfHoraCheckOut;

    private Reserva reserva;


    public void preparar(Reserva reserva){
        if(reserva==null){
            throw new NullPointerException("Error: La reserva no puede ser nula.");
        }

        this.reserva=reserva;
    }

    @FXML
    void aceptar(ActionEvent event) {

        if(Dialogos.mostrarDialogoConfirmacion("Realizar Check Out","Esta seguro de realizar el check Out")){
            try{

                LocalDateTime fechaHora = LocalDateTime.of(dpFechaCheckOut.getValue(), LocalTime.parse(tfHoraCheckOut.getText()));
                VistaGrafica.getInstancia().getControlador().realizarCheckOut(reserva,fechaHora);
                Dialogos.mostrarDialogoInformacion("Realizar Check Out","Check Out realizado correctamente.");

            }catch (NullPointerException | IllegalArgumentException e){
                Dialogos.mostrarDialogoError("Error Check Out",e.getMessage());
            }
        }

        ((Stage)btnAceptar.getScene().getWindow()).close();
    }

    @FXML
    void cancelar(ActionEvent event) {

        ((Stage)btnCancelar.getScene().getWindow()).close();

    }

}

