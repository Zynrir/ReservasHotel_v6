package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public interface IModelo {
    void comenzar();
    void terminar();
    void insertarHuesped(Huesped huesped) throws OperationNotSupportedException;
    Huesped buscarHuesped(Huesped huesped);
    void borrarHuesped(Huesped huesped) throws OperationNotSupportedException;
    List<Huesped> getHuespedes();
    void insertarHabitacion(Habitacion habitacion) throws OperationNotSupportedException;
    Habitacion buscarHabitacion(Habitacion habitacion);
    void borrarHabitacion(Habitacion habitacion) throws OperationNotSupportedException;
    List<Habitacion> getHabitaciones();
    List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion);
    void insertarReserva(Reserva reserva) throws OperationNotSupportedException;
    Reserva buscarReserva(Reserva reserva);
    void borrarReserva(Reserva reserva) throws OperationNotSupportedException;
    List<Reserva> getReservas();
    List<Reserva> getReservas(Huesped huesped);
    List<Reserva> getReservas(Habitacion habitacion);
    List<Reserva> getReservasFuturas(Habitacion habitacion);
    void realizarCheckIn(Reserva reserva, LocalDateTime fecha);
    void realizarCheckOut(Reserva reserva, LocalDateTime fecha);
}
