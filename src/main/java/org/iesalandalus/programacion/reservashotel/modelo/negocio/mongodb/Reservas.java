package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reservas implements IReservas {

    // ArrayList para almacenar las reservas
    private List<Reserva> coleccionReservas;
    private final String COLECCION="reservas";

    // Constructor sin par�metro de capacidad, porque ya no hace falta
    public Reservas() {
        this.coleccionReservas = new ArrayList<>();
        comenzar();
    }

    public List<Reserva> get() {
        List <Reserva> miReserva = new ArrayList<>();
        FindIterable <Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));
        for(Document miDocumento : miIterador){
            miReserva.add(MongoDB.getReserva(miDocumento));
        }
        return miReserva;
    }

    public int getTamano() {
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }

    // Para insertar una nueva reserva en el ArrayList
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        // Compruebo si la reserva ya existe en la colecci�n
        if (buscar(reserva) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        Document miDocumento= MongoDB.getDocumento(reserva);
        MongoDB.getBD().getCollection(COLECCION).insertOne(miDocumento);
        coleccionReservas.add(new Reserva(reserva));
    }

    // Para buscar una reserva en la colecci�n
    public Reserva buscar(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Document miDocumento=MongoDB.getBD().getCollection(COLECCION).find(Filters.and(
                Filters.eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()),
                Filters.eq(MongoDB.FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        )).first();

        if(miDocumento==null){
            return null;
        }else{
            return MongoDB.getReserva(miDocumento);
        }
    }

    // Para eliminar una reserva de la colecci�n
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (buscar(reserva)==null) { //Si en la colecci�n no se encuentra la reserva introducida salta excepci�n
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.and(
                Filters.eq(MongoDB.HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador()),
                Filters.eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        ));
        coleccionReservas.remove(reserva);
    }

    // Para obtener las reservas de un hu�sped
    public List<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un hu�sped nulo.");
        }
        List<Reserva> miReserva = new ArrayList<>();
        // Utilizo un iterador para recorrer el ArrayList y agregar las reservas del hu�sped al nuevo ArrayList
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHuesped().equals(huesped)) {
                miReserva.add(new Reserva(actual));
            }
        }
        return miReserva;
    }

    // Para obtener las reservas de un tipo de habitaci�n
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");
        }
        List<Reserva> habitacionesTipo = new ArrayList<>();
        for(Reserva reserva : coleccionReservas) {
            if(tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                if (reserva.getHabitacion() instanceof Simple) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                if (reserva.getHabitacion() instanceof Doble) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                if (reserva.getHabitacion() instanceof Triple) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                if (reserva.getHabitacion() instanceof Suite) {
                    habitacionesTipo.add(new Reserva(reserva));
                }
            }
        }
        // Devuelvo una nueva lista con las habitaciones del tipo especificado copiadas
        return new ArrayList<>(habitacionesTipo);
    }


    // Para obtener las reservas futuras de una habitaci�n
    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        }
        LocalDate fechaActual = LocalDate.now();
        List<Reserva> reservasFuturas = new ArrayList<>();
        // Utilizo  un iterador para recorrer el ArrayList y agregar las reservas futuras de la habitaci�n al nuevo ArrayList
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHabitacion().equals(habitacion) && actual.getFechaInicioReserva().isAfter(fechaActual)) {
                reservasFuturas.add(new Reserva(actual));
            }
        }
        return reservasFuturas;
    }

    public List<Reserva> getReservas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        }
        List<Reserva> reservasHabitacion = new ArrayList<>();
        Iterator<Reserva> iterator = coleccionReservas.iterator();
        while (iterator.hasNext()) {
            Reserva actual = iterator.next();
            if (actual.getHabitacion().equals(habitacion)) {
                reservasHabitacion.add(new Reserva(actual));
            }
        }
        return reservasHabitacion;
    }

    // Para realizar el checkin de una reserva


    @Override
    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }
        if(!coleccionReservas.contains(reserva)){
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay())) {
            throw new IllegalArgumentException("ERROR: La fecha del checkIn no puede ser anterior a la reserva.");
        }
        MongoDB.getBD().getCollection(COLECCION).updateOne(Filters.and(
                Filters.eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()),
                Filters.eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        ), Updates.set(MongoDB.CHECKIN,fecha.format(MongoDB.FORMATO_DIA_HORA)));
    }

    // Para realizar el checkout de una reserva
    @Override
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null || fecha == null) {
            throw new NullPointerException("ERROR: La reserva y la fecha no pueden ser nulas.");
        }
        if (reserva.getCheckIn() == null) {
            throw new NullPointerException("ERROR: No puedes hacer checkOut si el checkIn es nulo.");
        }
        if(!coleccionReservas.contains(reserva)) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay()) || fecha.isBefore(reserva.getCheckIn())) {
            throw new IllegalArgumentException("ERROR: La fecha del checkOut no puede ser anterior a la de inicio de reserva o antes del checkIn.");
        }
        MongoDB.getBD().getCollection(COLECCION).updateOne(Filters.and(
                Filters.eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()),
                Filters.eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA))
        ), Updates.set(MongoDB.CHECKOUT,fecha.format(MongoDB.FORMATO_DIA_HORA)));
    }

    @Override
    public void comenzar() {
        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find();
        for(Document miDocumento : miIterador){
            coleccionReservas.add(MongoDB.getReserva(miDocumento));
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
        coleccionReservas=null;
    }
}
