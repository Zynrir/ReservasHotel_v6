package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.FuenteDatosFicheros;

public class FuenteDatosMemoria implements IFuenteDatos {
    private static FuenteDatosMemoria instancia;
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

    public static FuenteDatosMemoria getInstance(){
        if (instancia == null){
            instancia = new FuenteDatosMemoria();
        }
        return instancia;
    }
}
