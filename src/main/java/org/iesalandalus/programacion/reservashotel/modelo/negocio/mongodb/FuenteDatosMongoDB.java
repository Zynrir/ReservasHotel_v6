package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.FuenteDatosFicheros;


public class FuenteDatosMongoDB implements IFuenteDatos {
    private static FuenteDatosMongoDB instancia;
    @Override
    public IHuespedes crearHuespedes() {
        return new Huespedes();
    }

    @Override
    public IHabitaciones crearHabitaciones() {
        return new Habitaciones();
    }

    @Override
    public IReservas crearReservas() {
        return new Reservas();
    }

    public static FuenteDatosMongoDB getInstance(){
        if (instancia == null){
            instancia = new FuenteDatosMongoDB();
        }
        return instancia;
    }
}