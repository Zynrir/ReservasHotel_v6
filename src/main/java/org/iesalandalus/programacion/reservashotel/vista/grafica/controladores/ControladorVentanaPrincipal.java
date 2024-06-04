package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class ControladorVentanaPrincipal {

    @FXML
    private Button btHabitacionesPrincipal;

    @FXML
    private Button btHuespedesPrincipal;

    @FXML
    private Button btReservasPrincipal;

    @FXML
    private Menu mnAcercaDePrincipal;

    @FXML
    private Menu mnArchivoPrincipal;

    @FXML
    private MenuBar mnVentanaPrincipal;
    @FXML
    private MenuItem mnInstituto;

    @FXML
    void abrirAcercaDe(ActionEvent event) {

        FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaAcercaDe.fxml"));
        ControladorVentanaAcercaDe c=fxmlLoader.getController();
        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,600,400);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel Al-Andalus - Acerca de");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }


    @FXML
    void abrirVentanaHabitaciones(ActionEvent event) {
        try {
            // Asegúrate de usar la ruta correcta al archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vistas/ventanaHabitaciones.fxml"));
            Parent raiz = fxmlLoader.load();
            // Obtener el controlador después de cargar el FXML
            ControladorVentanaHabitaciones controladorVentanaHabitaciones = fxmlLoader.getController();
            Scene escena = new Scene(raiz, 600, 400);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel Al-Andalus - Habitaciones");
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Mejor opción para depurar
        }
    }


    @FXML
    void abrirVentanaHuespedes(ActionEvent event) {

        FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaHuespedes.fxml"));
        ControladorVentanaHuespedes c=fxmlLoader.getController();
        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,600,400);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel Al-Andalus - Huespedes");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void abrirVentanaReservas(ActionEvent event) {

        FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaReservas.fxml"));
        ControladorVentanaReservas c=fxmlLoader.getController();
        try {
            Parent raiz=fxmlLoader.load();

            Scene escena=new Scene(raiz,600,400);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel Al-Andalus - Reservas");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void clickSalirAplicacion(ActionEvent event) {

        if (Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus", "Estas seguro que quieres salir de la aplicacion"))
        {
            System.exit(0);
        }
        else
            event.consume();
    }

}

