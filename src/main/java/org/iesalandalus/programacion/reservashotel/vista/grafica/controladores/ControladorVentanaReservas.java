package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentanaReservas {

    @FXML
    private Button btAgregarReservas;

    @FXML
    private Button btBorrarReservas;

    @FXML
    private Button btCheckIn;

    @FXML
    private Button btCheckOut;

    @FXML
    private MenuItem mnEliminaReserva;

    @FXML
    private MenuItem mnInsertarReserva;
    @FXML
    private MenuBar mnVentanaPrincipal;

    @FXML
    private TableView<Reserva> tvListadoReservas;
    @FXML
    private TableColumn<Reserva, String> tcDni;

    @FXML
    private TableColumn<Reserva, String> tcFcechaInicio;

    @FXML
    private TableColumn<Reserva, String> tcFechaFin;

    @FXML
    private TableColumn<Reserva, String> tcIdentificador;

    @FXML
    private TableColumn<Reserva, String> tcNombre;

    @FXML
    private TableColumn<Reserva, String> tcTipoHabitacion;
    @FXML
    private TableColumn<Reserva, String> tcPrecio;
    @FXML
    private TableColumn<Reserva, String> tcRegimen;

    @FXML
    private MenuItem mnRealizarCheckIn;

    @FXML
    private MenuItem mnRealizarCheckOut;

    private Reserva reserva;

    private List<Reserva> coleccionReserva=new ArrayList<>();
    private ObservableList<Reserva> obsReserva= FXCollections.observableArrayList();

    private void cargaDatosReserva()
    {
        coleccionReserva = VistaGrafica.getInstancia().getControlador().getReservas();
        obsReserva.setAll(coleccionReserva);
    }

    @FXML
    private void initialize(){
        cargaDatosReserva();
        tcIdentificador.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));;
        tcDni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));;
        tcFcechaInicio.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        tcFechaFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        tcNombre.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre()));;
        tcPrecio.setCellValueFactory(reserva-> new SimpleStringProperty(Double.toString(reserva.getValue().getHabitacion().getPrecio())));;
        tcRegimen.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getRegimen().toString()));;
        tcTipoHabitacion.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getClass().getSimpleName()));;

        tvListadoReservas.setItems(obsReserva);

    }

    @FXML
    void agregarReservas(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertaReserva.fxml"));
        try
        {
            Parent raiz=fxmlLoader.load();
            Scene escenaVentanaReserva=new Scene(raiz,600,400);
            Stage escenarioVentanaReserva=new Stage();
            escenarioVentanaReserva.setScene(escenaVentanaReserva);
            escenarioVentanaReserva.setTitle("Hotel Al-Andalus - Insertar Reserva" );
            escenarioVentanaReserva.initModality(Modality.APPLICATION_MODAL);
            escenarioVentanaReserva.showAndWait();
            cargaDatosReserva();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void borrarReservas(ActionEvent event) {
        Reserva reserva=tvListadoReservas.getSelectionModel().getSelectedItem();
        if (reserva!=null &&
                Dialogos.mostrarDialogoConfirmacion("Hotel Al Andalus - Eliminar Reserva", "Desea borrar la reserva seleccionada"))
        {
            try{
                VistaGrafica.getInstancia().getControlador().borrar(reserva);
            }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error borrar reserva", e.getMessage());
            }
            cargaDatosReserva();
            Dialogos.mostrarDialogoInformacion("Hotel Al Andalus - Eliminar Reserva", "Reserva borrada correctamente");
        }
        if (reserva==null)
            Dialogos.mostrarDialogoAdvertencia("Hotel Al Andalus - Eliminar Reserva","Debes seleccionar una reserva para borrarla");
    }

    @FXML
    void checkInReservas(ActionEvent event) {
        reserva=tvListadoReservas.getSelectionModel().getSelectedItem();
        if (reserva==null){
            event.consume();
            Dialogos.mostrarDialogoAdvertencia("Hotel Al Andalus - Check in Reserva","Debes seleccionar una reserva para realizar el Check in");}
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaCheckInReserva.fxml"));
            try {
                Parent raiz = fxmlLoader.load();
                ControladorVentanaCheckIn controladorVentanaCheckIn = fxmlLoader.getController();
                Scene escenaVentanaCheckIn = new Scene(raiz);
                Stage escenarioVentanaCheckIn = new Stage();
                escenarioVentanaCheckIn.setScene(escenaVentanaCheckIn);
                escenarioVentanaCheckIn.setTitle("Hotel Al-Andalus - Check In Reserva");
                escenarioVentanaCheckIn.initModality(Modality.APPLICATION_MODAL);
                controladorVentanaCheckIn.preparar(reserva);
                escenarioVentanaCheckIn.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void checkOutReservas(ActionEvent event) {
        reserva=tvListadoReservas.getSelectionModel().getSelectedItem();
        if (reserva==null){
            event.consume();
            Dialogos.mostrarDialogoAdvertencia("Hotel Al Andalus - Check Out Reserva","Debes seleccionar una reserva para realizar el Check Out");}
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaCheckOutReserva.fxml"));
            try {
                Parent raiz = fxmlLoader.load();
                ControladorVentanaCheckOut controladorVentanaCheckOut = fxmlLoader.getController();
                Scene escenaVentanaCheckOut = new Scene(raiz);
                Stage escenarioVentanaCheckOut = new Stage();
                escenarioVentanaCheckOut.setScene(escenaVentanaCheckOut);
                escenarioVentanaCheckOut.setTitle("Hotel Al-Andalus - Check Out Reserva");
                escenarioVentanaCheckOut.initModality(Modality.APPLICATION_MODAL);
                controladorVentanaCheckOut.preparar(reserva);
                escenarioVentanaCheckOut.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void eliminaReserva(ActionEvent event) {
        borrarReservas(event);
    }

    @FXML
    void insertaReserva(ActionEvent event) {
        agregarReservas(event);
    }


    @FXML
    void realizaCheckIn(ActionEvent event) {
        checkInReservas(event);
    }

    @FXML
    void realizaCheckOut(ActionEvent event) {
        checkOutReservas(event);
    }

}
