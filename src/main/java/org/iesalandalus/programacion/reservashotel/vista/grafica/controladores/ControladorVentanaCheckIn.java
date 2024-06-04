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

public class ControladorVentanaCheckIn {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private DatePicker dpFechaCheckIn;

    @FXML
    private TextField tfHoraCheckIn;

    private Reserva reserva;


    public void preparar(Reserva reserva){
        if(reserva==null){
            throw new NullPointerException("Error: La reserva no puede ser nula.");
        }

        this.reserva=reserva;
    }

    @FXML
    void aceptar(ActionEvent event) {

        if(Dialogos.mostrarDialogoConfirmacion("Realizar Check In","Esta seguro de realizar el check In")){
            try{

                LocalDateTime fechaHora = LocalDateTime.of(dpFechaCheckIn.getValue(), LocalTime.parse(tfHoraCheckIn.getText()));
                VistaGrafica.getInstancia().getControlador().realizarCheckIn(reserva,fechaHora);
                Dialogos.mostrarDialogoInformacion("Realizar Check In","Check In realizado correctamente.");

            }catch (NullPointerException | IllegalArgumentException e){
                Dialogos.mostrarDialogoError("Error Check In",e.getMessage());
            }
        }

        ((Stage)btnAceptar.getScene().getWindow()).close();
    }

    @FXML
    void cancelar(ActionEvent event) {

        ((Stage)btnCancelar.getScene().getWindow()).close();

    }

}
