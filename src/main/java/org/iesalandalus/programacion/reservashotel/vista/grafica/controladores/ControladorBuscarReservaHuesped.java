package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorBuscarReservaHuesped {

    @FXML
    private Label lbDniReservasHuesped;
    @FXML
    private Label lbNombreReservasHuesped;
    @FXML
    private TextField tfDniHuespedReserva;
    @FXML
    private TextField tfNombreHuespedReserva;

    @FXML
    private TableView<Reserva> tvListadoReservas;

    @FXML
    private TableColumn<Reserva, String> tvclDni;

    @FXML
    private TableColumn<Reserva, String> tvclFechaFin;

    @FXML
    private TableColumn<Reserva, String> tvclFechaInicio;

    @FXML
    private TableColumn<Reserva, String> tvclIdentificador;

    @FXML
    private TableColumn<Reserva, String> tvclNombre;

    @FXML
    private TableColumn<Reserva, String> tvclPrecio;

    @FXML
    private TableColumn<Reserva, String> tvclRegimen;

    @FXML
    private TableColumn<Reserva, String> tvclTipoHabitacion;

    private Huesped huesped;

    private List<Reserva> coleccionReserva=new ArrayList<>();
    private ObservableList<Reserva> obsReserva= FXCollections.observableArrayList();

    private FilteredList<Reserva> filtro;


    public void preparar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("Error: El hu�sped no puede ser nulo.");
        }
        this.huesped = huesped;
        cargaDatosReserva();
    }

    private void cargaDatosReserva()
    {
        if(huesped !=null){
        coleccionReserva = VistaGrafica.getInstancia().getControlador().getReservas(huesped);
        obsReserva.setAll(coleccionReserva);
        filtro = new FilteredList<>(obsReserva);
        tvListadoReservas.setItems(filtro);}

    }

    @FXML
    private void initialize(){

        cargaDatosReserva();

        tvclIdentificador.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));;
        tvclDni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));;
        tvclFechaInicio.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        tvclFechaFin.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));;
        tvclNombre.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre()));;
        tvclPrecio.setCellValueFactory(reserva-> new SimpleStringProperty(Double.toString(reserva.getValue().getHabitacion().getPrecio())));;
        tvclRegimen.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getRegimen().toString()));;
        tvclTipoHabitacion.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHabitacion().getClass().getSimpleName()));;

        tvListadoReservas.setItems(obsReserva);

        tfDniHuespedReserva.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro.setPredicate(reserva -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return reserva.getHuesped().getDni().contains(newValue);
            });
        });

        tfNombreHuespedReserva.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro.setPredicate(reserva -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return reserva.getHuesped().getNombre().contains(newValue);
            });
        });

    }

}


