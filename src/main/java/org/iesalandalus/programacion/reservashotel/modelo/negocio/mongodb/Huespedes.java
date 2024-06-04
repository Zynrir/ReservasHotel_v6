package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
public class Huespedes implements IHuespedes {

    private List<Huesped> coleccionHuespedes;
    private final String COLECCION="huespedes";

    // Constructor
    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
        comenzar();
    }


    public List<Huesped> get() {

        List<Huesped> miHuesped = new ArrayList<>();
        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find().sort(Sorts.ascending(MongoDB.DNI));
        for(Document miDocumento : miIterador){
            miHuesped.add(MongoDB.getHuesped(miDocumento));
        }
        return miHuesped;
    }

    public int getTamano() {
        return (int) MongoDB.getBD().getCollection(COLECCION).countDocuments();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un hu�sped nulo.");
        }
        if (buscar(huesped) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un hu�sped con ese dni.");
        }
        Document miDocumento = MongoDB.getDocumento(huesped);
        MongoDB.getBD().getCollection(COLECCION).insertOne(miDocumento);
        coleccionHuespedes.add(new Huesped(huesped));
    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un hu�sped nulo.");
        }
        Document miDocumento=MongoDB.getBD().getCollection(COLECCION).find(Filters.eq(MongoDB.DNI,huesped.getDni())).first();
        if (miDocumento == null)
            return null;
        else{
            return MongoDB.getHuesped(miDocumento);
        }
    }

    // Para eliminar un hu�sped de la colecci�n
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un hu�sped nulo.");
        }
        if(buscar(huesped)==null){
            throw new OperationNotSupportedException("ERROR: No existe ning�n hu�sped como el indicado.");
        }
        if(MongoDB.getBD().getCollection("reservas").find(Filters.eq(MongoDB.HUESPED_DNI,huesped.getDni())).first()!=null){
            throw new OperationNotSupportedException("ERROR: No se puede borrar un huesped con reserva.");
        }
        MongoDB.getBD().getCollection(COLECCION).deleteOne(Filters.eq(MongoDB.DNI,huesped.getDni()));
        coleccionHuespedes.remove(huesped);

    }

    @Override
    public void comenzar() {

        FindIterable<Document> miIterador = MongoDB.getBD().getCollection(COLECCION).find();

        for(Document miDocumento : miIterador){
            coleccionHuespedes.add(MongoDB.getHuesped(miDocumento));
        }
    }

    @Override
    public void terminar() {

        MongoDB.cerrarConexion();
        coleccionHuespedes=null;
    }
}
