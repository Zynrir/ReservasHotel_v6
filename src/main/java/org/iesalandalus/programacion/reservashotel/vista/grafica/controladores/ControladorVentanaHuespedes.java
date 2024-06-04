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
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ControladorVentanaHuespedes {
    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private Button btAgregarHuespedes;

    @FXML
    private Button btBorrarHuespedes;

    @FXML
    private Button btBuscarReservasHuespedes;
    @FXML
    private TextField tfDniHuespedReserva;

    @FXML
    private TextField tfNombreHuespedReserva;

    @FXML
    private TableView<Huesped> tvListadoHuespedes;

    @FXML
    private TableColumn<Huesped, String> tcCorreo;

    @FXML
    private TableColumn<Huesped, String> tcDni;

    @FXML
    private TableColumn<Huesped, String> tcFechaNacimiento;

    @FXML
    private TableColumn<Huesped, String> tcNombre;

    @FXML
    private TableColumn<Huesped, String> tcTelefono;

    @FXML
    private MenuItem mnEliminarHuesped;

    @FXML
    private MenuItem mnInsertarHuesped;

    private ObservableList<Huesped> obsHuesped = FXCollections.observableArrayList();
    private List<Huesped> coleccionHuesped=new ArrayList<>();
    private FilteredList<Huesped> filtro;

    private void cargaDatosHuesped()
    {

        coleccionHuesped = VistaGrafica.getInstancia().getControlador().getHuespedes();
        obsHuesped.setAll(coleccionHuesped);
        filtro = new FilteredList<>(obsHuesped);
        tvListadoHuespedes.setItems(filtro);


    }
    @FXML
    private void initialize(){

        cargaDatosHuesped();

        tvListadoHuespedes.setItems(obsHuesped);

        tcNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));
        tcDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcCorreo.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getCorreo()));
        tcTelefono.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getTelefono()));
        tcFechaNacimiento.setCellValueFactory(huesped->new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(FORMATO_FECHA).toString()));

        tfDniHuespedReserva.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro.setPredicate(huesped -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return huesped.getDni().contains(newValue);
            });
        });

        tfNombreHuespedReserva.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro.setPredicate(huesped -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return huesped.getNombre().contains(newValue);
            });
        });

        cargaDatosHuesped();

    }

    @FXML
    void agregarHuespedes(ActionEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaInsertaHuesped.fxml"));
        try
        {
            Parent raiz=fxmlLoader.load();


            Scene escenaVentanaHuesped=new Scene(raiz,600,400);
            Stage escenarioVentanaHuesped=new Stage();
            escenarioVentanaHuesped.setScene(escenaVentanaHuesped);
            escenarioVentanaHuesped.setTitle("Hotel Al-Andalus - Insertar Huesped" );
            escenarioVentanaHuesped.initModality(Modality.APPLICATION_MODAL);
            escenarioVentanaHuesped.showAndWait();
            cargaDatosHuesped();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void borrarHuespedes(ActionEvent event){

        Huesped huesped=tvListadoHuespedes.getSelectionModel().getSelectedItem();

        if (huesped!=null &&
                Dialogos.mostrarDialogoConfirmacion("Hotel Al Andalus - Eliminar Huesped", "Desea borrar el huesped seleccionado"))
        {
            try {
                VistaGrafica.getInstancia().getControlador().borrar(huesped);
                Dialogos.mostrarDialogoInformacion("Hotel Al Andalus - Eliminar Huesped", "Huesped borrado correctamente");
            }catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error borrar huesped",e.getMessage());
            }

            cargaDatosHuesped();

        }

        if (huesped==null)
            Dialogos.mostrarDialogoAdvertencia("Hotel Al Andalus - Eliminar Huesped","Debes seleccionar un huesped para borrarlo");

    }

    @FXML
    void buscarReservasHuespedes(ActionEvent event) {
        Huesped huesped = tvListadoHuespedes.getSelectionModel().getSelectedItem();
        if (huesped == null) {
            event.consume();
            Dialogos.mostrarDialogoAdvertencia("Reservas huesped","Debes seleccionar un huesped.");
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaBuscarReservasHuesped.fxml"));
                Parent raiz = fxmlLoader.load();
                ControladorBuscarReservaHuesped controladorBuscarReservaHuesped = fxmlLoader.getController();

                controladorBuscarReservaHuesped.preparar(huesped);

                Scene escenaVentanaHuesped = new Scene(raiz, 600, 400);
                Stage escenarioVentanaHuesped = new Stage();
                escenarioVentanaHuesped.setScene(escenaVentanaHuesped);
                escenarioVentanaHuesped.setTitle("Hotel Al-Andalus - Reservas Huesped");
                escenarioVentanaHuesped.initModality(Modality.APPLICATION_MODAL);
                escenarioVentanaHuesped.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
        void insertaHuesped (ActionEvent event){

            agregarHuespedes(event);
        }


        @FXML
        void eliminaHuesped (ActionEvent event){

            borrarHuespedes(event);

        }

    }

