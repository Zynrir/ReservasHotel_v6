package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class LanzadorVentanaPrincipal extends Application {
    public static void comenzar(){
        launch();
    }

    @Override
    public void start(Stage escenarioPrincipal) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
        Parent raiz=fxmlLoader.load();
        Scene scene = new Scene(raiz, 600, 400);
        scene.getStylesheets().add(LocalizadorRecursos.class.getResource("estilos/estilos.css").toExternalForm());
        escenarioPrincipal.setTitle("Hotel Al-Andalus");
        escenarioPrincipal.setScene(scene);
        escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
        escenarioPrincipal.show();
    }

    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus", "Estas seguro que quieres salirte de la aplicacion"))
        {
            escenarioPrincipal.close();
        }
        else
            e.consume();

    }

}
