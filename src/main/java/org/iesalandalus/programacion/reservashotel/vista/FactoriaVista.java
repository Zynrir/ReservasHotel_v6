package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

public enum FactoriaVista {
    TEXTO {
        @Override
        public Vista crearVista() {
            return new VistaTexto();
        }
    },
    GRAFICA {
        @Override
        public Vista crearVista() {
            return new VistaGrafica();
        }
    };

    public abstract Vista crearVista();
}

