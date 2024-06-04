package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.FuenteDatosFicheros;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.FuenteDatosMongoDB;

public enum FactoriaFuenteDatos {
    MEMORIA {
        @Override
        public IFuenteDatos crearFuenteDatos() {
            return FuenteDatosMemoria.getInstance();
        }
    },
    MONGODB {
        @Override
        public IFuenteDatos crearFuenteDatos() {
            return FuenteDatosMongoDB.getInstance();
        }
    },

    FICHEROS {
        @Override
        public IFuenteDatos crearFuenteDatos() {
            return FuenteDatosFicheros.getInstance();
        }
    };

    public abstract IFuenteDatos crearFuenteDatos();
}
