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

public class ControladorVentanaInsertaHabitacion {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<TipoHabitacion> cbTipoHabitacion;

    @FXML
    private ToggleGroup grupoBanios;

    @FXML
    private ToggleGroup grupoCamasDobles;

    @FXML
    private ToggleGroup grupoCamasIndividuales;

    @FXML
    private ToggleGroup grupoJacuzzi;

    @FXML
    private RadioButton rbCeroCamasDobles;

    @FXML
    private RadioButton rbDosBanios;

    @FXML
    private RadioButton rbDosCamasIndividuales;

    @FXML
    private RadioButton rbJacuzziNo;

    @FXML
    private RadioButton rbJacuzziSi;

    @FXML
    private RadioButton rbTresCamasIndividuales;

    @FXML
    private RadioButton rbUnBanio;

    @FXML
    private RadioButton rbUnaCamaDoble;

    @FXML
    private RadioButton rbUnaCamaIndividual;

    @FXML
    private RadioButton rbCeroCamaIndividual;

    @FXML
    private TextField tfPlanta;

    @FXML
    private TextField tfPrecio;

    @FXML
    private TextField tfPuerta;

    @FXML
    void initialize(){
        cbTipoHabitacion.setItems(FXCollections.observableArrayList(TipoHabitacion.values()));
    }

    @FXML
    void aceptar(ActionEvent event) {

        int numCamasInd=0, numCamasDobles=0, numBanios=1;
        boolean jacuzzi=false;

        RadioButton rbSeleccionado=(RadioButton) grupoCamasIndividuales.getSelectedToggle();
        if (rbSeleccionado==rbCeroCamaIndividual)
        {
            numCamasInd=0;
        }
        else if (rbSeleccionado==rbUnaCamaIndividual)
        {
            numCamasInd=1;
        }
        else if (rbSeleccionado==rbDosCamasIndividuales)
        {
            numCamasInd=2;

        }else if(rbSeleccionado==rbTresCamasIndividuales){

            numCamasInd=3;
        }

        RadioButton rbSeleccionado2=(RadioButton) grupoCamasDobles.getSelectedToggle();
        if (rbSeleccionado2==rbCeroCamasDobles){
            numCamasDobles=0;
        } else if(rbSeleccionado2==rbUnaCamaDoble){
            numCamasDobles=1;
        }

        RadioButton rbSeleccionado3=(RadioButton) grupoBanios.getSelectedToggle();

        if(rbSeleccionado3==rbUnBanio){
            numBanios=1;
        }else if(rbSeleccionado3==rbDosBanios){
            numBanios=2;
        }

        RadioButton rbSeleccionado4=(RadioButton) grupoJacuzzi.getSelectedToggle();

        if(rbSeleccionado4==rbJacuzziSi){
            jacuzzi=true;
        }else if(rbSeleccionado4==rbJacuzziNo){
            jacuzzi=false;
        }

        try{
        if(cbTipoHabitacion.getSelectionModel().isSelected(0)){
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitacion", "Esta seguro de insertar esta habitacion")){
                VistaGrafica.getInstancia().getControlador().insertar(new Suite(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()),Double.parseDouble(tfPrecio.getText()), numBanios, jacuzzi));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitacion insertada correctamente");
            }
        }
        else if (cbTipoHabitacion.getSelectionModel().isSelected(1)) {
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitacion", "Esta seguro de insertar esta habitacion")){
                VistaGrafica.getInstancia().getControlador().insertar(new Simple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()),Double.parseDouble(tfPrecio.getText())));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitacion insertada correctamente");
            }
        }
        else if(cbTipoHabitacion.getSelectionModel().isSelected(2)){
            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitacion", "Esta seguro de insertar esta habitacion")){
                VistaGrafica.getInstancia().getControlador().insertar(new Doble(Integer.parseInt(tfPlanta.getText()),Integer.parseInt(tfPuerta.getText()),Double.parseDouble(tfPrecio.getText()),numCamasInd,numCamasDobles));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitacion insertada correctamente");
            }
        }
        else if (cbTipoHabitacion.getSelectionModel().isSelected(3)){

            if(Dialogos.mostrarDialogoConfirmacion("Insertar Habitacion", "Esta seguro de insertar esta habitacion")){
                VistaGrafica.getInstancia().getControlador().insertar(new Triple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()), numBanios,numCamasInd,numCamasDobles));
                Dialogos.mostrarDialogoInformacion("Habitacion insertada", "Habitacion insertada correctamente");
            }
        }}catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("Error al insertar habitacion",e.getMessage());
        }
        ((Stage)btnAceptar.getScene().getWindow()).close();


    }

    @FXML
    void cancelar(ActionEvent event) {

        ((Stage)btnCancelar.getScene().getWindow()).close();

    }


}

