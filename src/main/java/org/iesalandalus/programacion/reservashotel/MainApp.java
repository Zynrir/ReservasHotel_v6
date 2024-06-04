package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.FactoriaVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

public class MainApp {

    public static void main(String[] args) {
        Modelo modelo = procesarArgumentosFuenteDatos(args);
        Vista vista = procesarArgumentosVista(args);
        Controlador controlador = new Controlador(modelo, vista);
        controlador.comenzar();
        controlador.terminar();

    }

    public static Modelo procesarArgumentosFuenteDatos(String[] args) {
        Modelo modelo = null;
        if (args.length == 0 || args.length == 1) {
            System.out.println("No ha escogido un modelo, se pondrá por defecto -fdfichero.");
            modelo = new Modelo(FactoriaFuenteDatos.FICHEROS);
        } else if (args[0].equalsIgnoreCase("-fdmemoria")) {
            modelo = new Modelo(FactoriaFuenteDatos.MEMORIA);
        } else if (args[0].equalsIgnoreCase("-fdmongodb")) {
            modelo = new Modelo(FactoriaFuenteDatos.MONGODB);
        }else if (args[0].equalsIgnoreCase("-fdfichero")) {
            modelo = new Modelo(FactoriaFuenteDatos.FICHEROS);
        }
        return modelo;
    }

    public static Vista procesarArgumentosVista(String [] args){
        Vista vista = null;
        if (args.length == 0 || args.length == 1) {
            System.out.println("No ha escogido una vista, se pondrá por defecto -vTexto.");
            vista = FactoriaVista.TEXTO.crearVista();
        } else if (args[1].equalsIgnoreCase("-vTexto")) {
            vista = FactoriaVista.TEXTO.crearVista();
        } else if (args[1].equalsIgnoreCase("-vGrafica")) {
            vista = FactoriaVista.GRAFICA.crearVista();
        }
        return vista;
    }
}
