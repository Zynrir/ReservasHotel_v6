package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Doble;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Suite;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Triple;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentanaHabitaciones {

    @FXML
    private Button btAgregarHabitaciones;

    @FXML
    private Button btBorrarHabitaciones;

    @FXML
    private Button btBuscarReservasHabitaciones;

    @FXML
    private MenuItem mnEliminarHabitacion;

    @FXML
    private MenuItem mnInsertarHabitacion;

    @FXML
    private TableView<Habitacion> tvListadoHabitaciones;

    @FXML
    private TableColumn<Habitacion, String> tcBanio;

    @FXML
    private TableColumn<Habitacion, String> tcCamasDob;

    @FXML
    private TableColumn<Habitacion, String> tcCamasInd;

    @FXML
    private TableColumn<Habitacion, String> tcIdentificador;

    @FXML
    private TableColumn<Habitacion, String> tcJacuzzi;

    @FXML
    private TableColumn<Habitacion, String> tcPlanta;

    @FXML
    private TableColumn<Habitacion, String> tcPrecio;

    @FXML
    private TableColumn<Habitacion, String> tcPuerta;

    @FXML
    private TableColumn<Habitacion, String> tcTipoHabitacion;

    @FXML
    private TextField tfIdentificadorHabitacionReserva;
    private List<Habitacion> coleccionHabitacion=new ArrayList<>();
    private ObservableList<Habitacion> obsHabitacion= FXCollections.observableArrayList();
    private FilteredList<Habitacion> filtro;



    private void cargaDatosHabitacion()
    {

        coleccionHabitacion = VistaGrafica.getInstancia().getControlador().getHabitaciones();
        obsHabitacion.setAll(coleccionHabitacion);
        filtro = new FilteredList<>(obsHabitacion);
        tvListadoHabitaciones.setItems(filtro);



    }

    @FXML
    private void initialize(){

        cargaDatosHabitacion();

        tcIdentificador.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getIdentificador()));;
        tcPlanta.setCellValueFactory(habitacion-> new SimpleStringProperty(Integer.toString(habitacion.getValue().getPlanta())));;
        tcPuerta.setCellValueFactory(habitacion-> new SimpleStringProperty(Integer.toString(habitacion.getValue().getPuerta())));;
        tcPrecio.setCellValueFactory(habitacion-> new SimpleStringProperty(Double.toString(habitacion.getValue().getPrecio())));;
        tcTipoHabitacion.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));;

        tcCamasInd.setCellValueFactory(habitacion -> {

            if(habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Simple")) {
                return new SimpleStringProperty("1");
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Suite")) {
                return new SimpleStringProperty("0");
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Doble")) {
            return new SimpleStringProperty(Integer.toString(((Doble)(habitacion.getValue())).getNumCamasIndividuales()));
        } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Triple")) {
                return new SimpleStringProperty(Integer.toString(((Triple)(habitacion.getValue())).getNumCamasIndividuales()));

        }
        return null;
        });
        tcCamasDob.setCellValueFactory(habitacion -> {

            if(habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Simple")) {
                return new SimpleStringProperty("0");
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Suite")) {
                return new SimpleStringProperty("2");
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Doble")) {
                return new SimpleStringProperty(Integer.toString(((Doble)(habitacion.getValue())).getNumCamasDobles()));
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Triple")) {
                return new SimpleStringProperty(Integer.toString(((Triple)(habitacion.getValue())).getNumCamasDobles()));

            }
            return null;
        });

        tcBanio.setCellValueFactory(habitacion -> {

            if(habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Simple")) {
                return new SimpleStringProperty("0");
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Suite")) {
                return new SimpleStringProperty(Integer.toString(((Suite)(habitacion.getValue())).getNumBanos()));
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Doble")) {
                return new SimpleStringProperty("0");
            } else if (habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Triple")) {
                return new SimpleStringProperty(Integer.toString(((Triple)(habitacion.getValue())).getNumBanos()));

            }
            return null;
        });
        tcJacuzzi.setCellValueFactory(habitacion-> {
            if(habitacion.getValue().getClass().getSimpleName().equalsIgnoreCase("Suite")){
                if(((Suite)(habitacion.getValue())).isTieneJacuzzi()){
                return new SimpleStringProperty("si");
                }
            }
            return new SimpleStringProperty("no");
        });

        tvListadoHabitaciones.setItems(obsHabitacion);

        tfIdentificadorHabitacionReserva.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro.setPredicate(habitacion -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return habitacion.getIdentificador().contains(newValue);
            });
        });

        cargaDatosHabitacion();


    }

    @FXML
    void agregarHabitaciones(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertaHabitacion.fxml"));
        try
        {
            Parent raiz=fxmlLoader.load();

            Scene escenaVentanaHabitacion=new Scene(raiz,600,400);
            Stage escenarioVentanaHabitacion=new Stage();
            escenarioVentanaHabitacion.setScene(escenaVentanaHabitacion);
            escenarioVentanaHabitacion.setTitle("Hotel Al-Andalus - Insertar Habitacion" );
            escenarioVentanaHabitacion.initModality(Modality.APPLICATION_MODAL);
            escenarioVentanaHabitacion.showAndWait();
            cargaDatosHabitacion();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void borrarHabitaciones(ActionEvent event) {

        Habitacion habitacion=tvListadoHabitaciones.getSelectionModel().getSelectedItem();

         if (habitacion!=null &&
                Dialogos.mostrarDialogoConfirmacion("Hotel Al Andalus - Eliminar Habitacion", "Desea borrar la habitacion seleccionada"))
        {

            try {
                VistaGrafica.getInstancia().getControlador().borrar(habitacion);
                Dialogos.mostrarDialogoInformacion("Hotel Al Andalus - Eliminar Habitacion", "Habitacion borrada correctamente");
            }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error borrar habitacion",e.getMessage());
            }
            cargaDatosHabitacion();

        }

        if (habitacion==null)
            Dialogos.mostrarDialogoAdvertencia("Hotel Al Andalus - Eliminar Habitacion","Debes seleccionar una habitacion para borrarla");

    }

    @FXML
    void buscarReservasHabitaciones(ActionEvent event) {

        Habitacion habitacion=tvListadoHabitaciones.getSelectionModel().getSelectedItem();


        if (habitacion==null){
            event.consume();
            Dialogos.mostrarDialogoAdvertencia("Reservas habitacion","Debe seleccionar una habitacion.");
        }else {

            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaBuscarReservasHabitacion.fxml"));
            try {
                Parent raiz = fxmlLoader.load();

                ControladorBuscarReservaHabitacion controladorBuscarReservaHabitacion = fxmlLoader.getController();
                controladorBuscarReservaHabitacion.preparar(habitacion);

                Scene escenaVentanaHabitacion = new Scene(raiz, 600, 400);
                Stage escenarioVentanaHabitacion = new Stage();
                escenarioVentanaHabitacion.setScene(escenaVentanaHabitacion);
                escenarioVentanaHabitacion.setTitle("Hotel Al-Andalus - Reservas Habitacion");
                escenarioVentanaHabitacion.initModality(Modality.APPLICATION_MODAL);

                escenarioVentanaHabitacion.showAndWait();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void insertaHabitacion(ActionEvent event) {

        agregarHabitaciones(event);

    }

    @FXML
    void eliminaHabitacion(ActionEvent event) {

        borrarHabitaciones(event);

    }


}

