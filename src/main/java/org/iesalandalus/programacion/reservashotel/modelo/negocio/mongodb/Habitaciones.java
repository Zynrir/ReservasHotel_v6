package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {

    private List<Habitacion> coleccionHabitaciones;
    private final String COLECCION="habitaciones";

    // M�TODOS

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
        comenzar();
    }

    public List<Habitacion> get() {
        List <Habitacion> miHabitacion = new ArrayList<>();
        FindIterable <Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));
        for(Document miDocumento : miIterador){
            miHabitacion.add(MongoDB.getHabitacion(miDocumento));
        }
        return miHabitacion;
    }

    // M�todo para obtener las habitaciones de un tipo espec�fico
    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> habitacionesTipo = new ArrayList<>();

        for(Habitacion habitacion : coleccionHabitaciones) {
            if(tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                if (habitacion instanceof Simple) {
                    habitacionesTipo.add(new Simple((Simple) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                if (habitacion instanceof Doble) {
                    habitacionesTipo.add(new Doble((Doble) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                if (habitacion instanceof Triple) {
                    habitacionesTipo.add(new Triple((Triple) habitacion));
                }
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                if (habitacion instanceof Suite) {
                    habitacionesTipo.add(new Suite((Suite) habitacion));
                }
            }
        }

        return habitacionesTipo;
    }

    // M�todo para obtener el tama�o actual de la colecci�n
    public int getTamano() {

        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }

    // M�todo para insertar una nueva habitaci�n en la colecci�n
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");
        }

        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
        }

        Document miDocumento = MongoDB.getDocumento(habitacion);
        MongoDB.getBD().getCollection(COLECCION).insertOne(miDocumento);
        coleccionHabitaciones.add(habitacion);
    }

    // M�todo para buscar una habitaci�n en la colecci�n
    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitaci�n nula.");
        }

        Document miDocumento=MongoDB.getBD().getCollection(COLECCION).find(
                Filters.eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador())).first();

        if (miDocumento == null)
            return null;
        else{
            return MongoDB.getHabitacion(miDocumento);
        }
    }

    // M�todo para borrar una habitaci�n de la colecci�n
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: La habitaci�n no puede ser nula.");
        }

        if(buscar(habitacion)==null){
            throw new OperationNotSupportedException("No existe esa habitaci�n.");
        }

        if(MongoDB.getBD().getCollection("reservas").find(Filters.eq(MongoDB.HABITACION_IDENTIFICADOR,habitacion.getIdentificador())).first()!=null){
            throw new OperationNotSupportedException("ERROR: No se puede borrar una habitacion con reserva.");
        }

        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.eq(MongoDB.IDENTIFICADOR, habitacion.getIdentificador()));
        coleccionHabitaciones.remove(habitacion);
    }

    @Override
    public void comenzar() {

        FindIterable<Document>miIterador = MongoDB.getBD().getCollection(COLECCION).find();

        for(Document miDocumento : miIterador){
            coleccionHabitaciones.add(MongoDB.getHabitacion(miDocumento));
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
        coleccionHabitaciones=null;
    }
}