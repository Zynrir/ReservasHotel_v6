package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

public class ControladorVentanaInsertaReserva {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<Regimen> cbRegimen;

    @FXML
    private ComboBox<TipoHabitacion> cbTipoHabitacionReserva;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextField tfDniReserva;

    @FXML
    private ComboBox<?> cbPersonasReserva;

    @FXML
    private TextField tfPlanta;

    @FXML
    private TextField tfPuerta;

    @FXML
    private ToggleGroup grupoPersonas;

    @FXML
    private RadioButton rbPersonas1;

    @FXML
    private RadioButton rbPersonas2;

    @FXML
    private RadioButton rbPersonas3;

    @FXML
    private RadioButton rbPersonas4;


    @FXML

    void initialize(){

        cbTipoHabitacionReserva.setItems(FXCollections.observableArrayList(TipoHabitacion.values()));
        cbRegimen.setItems(FXCollections.observableArrayList(Regimen.values()));

    }

    @FXML
    void aceptar(ActionEvent event) {

        Huesped huesped= VistaGrafica.getInstancia().getControlador().buscar(new Huesped("sdf", tfDniReserva.getText(), "asdf@fdsad.xom", "999223344", LocalDate.of(1999,12,12)));

        Habitacion habitacion= VistaGrafica.getInstancia().getControlador().buscar(new Simple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), 50));

        //TipoHabitacion tipo=null;

        Regimen regimen=null;

        int numPersonas=0;

            /*if(cbTipoHabitacionReserva.getSelectionModel().isSelected(0)){
                tipo=TipoHabitacion.SUITE;
            }
            else if (cbTipoHabitacionReserva.getSelectionModel().isSelected(1)) {

                tipo=TipoHabitacion.SIMPLE;
            }

            else if(cbTipoHabitacionReserva.getSelectionModel().isSelected(2)){
                tipo=TipoHabitacion.DOBLE;
            }

            else if (cbTipoHabitacionReserva.getSelectionModel().isSelected(3)){
                tipo=TipoHabitacion.TRIPLE;
            }*/

        if(cbRegimen.getSelectionModel().isSelected(0)){
            regimen=Regimen.SOLO_ALOJAMIENTO;
        }
        else if (cbRegimen.getSelectionModel().isSelected(1)) {

            regimen=Regimen.ALOJAMIENTO_CON_DESAYUNO;
        }

        else if(cbRegimen.getSelectionModel().isSelected(2)){
            regimen=Regimen.MEDIA_PENSION;
        }

        else if (cbRegimen.getSelectionModel().isSelected(3)){
            regimen=Regimen.PENSION_COMPLETA;
        }

        RadioButton rbSeleccionado=(RadioButton) grupoPersonas.getSelectedToggle();
        if (rbSeleccionado==rbPersonas1)
        {
            numPersonas=1;
        }
        else if (rbSeleccionado==rbPersonas2)
        {
            numPersonas=2;
        }
        else if (rbSeleccionado==rbPersonas3)
        {
            numPersonas=3;

        }else if(rbSeleccionado==rbPersonas4){

            numPersonas=4;
        }

        try{
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Reserva", "Esta seguro de querer insertar esta reserva")){
                VistaGrafica.getInstancia().getControlador().insertar(new Reserva(huesped, habitacion, regimen, dpFechaInicio.getValue(),dpFechaFin.getValue(),numPersonas));
                Dialogos.mostrarDialogoInformacion("Reserva insertada", "Reserva insertada correctamente");
                ((Stage)btnAceptar.getScene().getWindow()).close();
        }

        }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("Error al insertar reserva",e.getMessage());
        }

    }

    @FXML
    void cancelar(ActionEvent event) {

        ((Stage)btnCancelar.getScene().getWindow()).close();

    }

}
