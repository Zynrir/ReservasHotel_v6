package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MongoDB {
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA= DateTimeFormatter.ofPattern("dd-MM-yyyy HH: mm");
    private static String SERVIDOR = "reservashotel.8wvg4af.mongodb.net";
    private static final int PUERTO = 27017;
    private static String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";
    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORRE0 ="correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + " . " + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + " . " + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + " . " + TIPO;
    public static final String TIPO_SIMPLE = "Simple";
    public static final String TIPO_DOBLE = "Doble";
    public static final String TIPO_TRIPLE = "Triple";
    public static final String TIPO_SUITE = "Suite";
    public static final String CAMAS_INDIVIDUALES = "Camas_Individuales";
    public static final String CAMAS_DOBLES = "Camas_Dobles";
    public static final String BANOS = "Baños";
    public static final String JACUZZI = "Jacuzzi";
    public static final String REGIMEN = "Regimen";
    public static final String FECHA_INICIO_RESERVA = "Fecha_Inicio_Reserva";
    public static final String FECHA_FIN_RESERVA = "Fecha_Fin_Reserva";
    public static final String CHECKIN = "Checkin";
    public static final String CHECKOUT = "Checkout";
    public static final String PRECIO_RESERVA = "Precio_Reserva";
    public static final String NUMERO_PERSONAS = "Numero_Personas";
    private static MongoClient conexion;

    private MongoDB(){
    }

    public static MongoDatabase getBD() {
        if (conexion == null) {
            establecerConexion();
        }

        return conexion.getDatabase(BD);
    }

    public static void establecerConexion() {
        String connectionString = "mongodb+srv://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + "/";
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).build();
        conexion = MongoClients.create(settings);
        try {
            MongoDatabase database = conexion.getDatabase(BD);
            database.runCommand(new Document("ping", 1));
            System.out.println("Conexion a MongoDB realizada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al establecer conexion a MongoDB: " + e.getMessage());
        }
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            conexion.close();
            System.out.println("Conexion cerrada correctamente");
        }
    }

    public static Document getDocumento(Huesped huesped){
        if(huesped == null){
            throw new NullPointerException("ERROR: El huesped no puede ser nulo.");
        }
        Document miDocumento= new Document().append(DNI,huesped.getDni()).append(NOMBRE, huesped.getNombre()).append(CORRE0,huesped.getCorreo()).append(TELEFONO, huesped.getTelefono()).append(FECHA_NACIMIENTO, huesped.getFechaNacimiento().format(FORMATO_DIA));
        return miDocumento;
    }

    public static Huesped getHuesped(Document documentoHuesped){
        if(documentoHuesped == null){
            throw new NullPointerException("ERROR: El documento no puede ser nulo.");
        }
        Huesped miHuesped = new Huesped(documentoHuesped.getString(NOMBRE),
                documentoHuesped.getString(DNI),
                documentoHuesped.getString(CORRE0),
                documentoHuesped.getString(TELEFONO),
                LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA));
        return miHuesped;
    }
    public static Document getDocumento(Habitacion habitacion){
        if(habitacion == null){
            throw new NullPointerException("ERROR: La habitación no puede ser nula.");
        }
        Document miDocumento= new Document().append(PLANTA, habitacion.getPlanta()).append(PUERTA, habitacion.getPuerta()).append(PRECIO, habitacion.getPrecio()).append(NUMERO_PERSONAS,habitacion.getNumeroMaximoPersonas()).append(IDENTIFICADOR, habitacion.getIdentificador());
        if(habitacion instanceof Simple){
            miDocumento.append(TIPO, TIPO_SIMPLE);
        }
        if(habitacion instanceof Doble){
            miDocumento.append(CAMAS_INDIVIDUALES, ((Doble) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES, ((Doble) habitacion).getNumCamasDobles()).append(TIPO, TIPO_DOBLE);
        }
        if(habitacion instanceof Triple){
            miDocumento.append(CAMAS_INDIVIDUALES, ((Triple) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES, ((Triple) habitacion).getNumCamasDobles()).append(BANOS, ((Triple) habitacion).getNumBanos()).append(TIPO, TIPO_TRIPLE);
        }
        if(habitacion instanceof Suite){
            miDocumento.append(BANOS, ((Suite) habitacion).getNumBanos()).append(JACUZZI, ((Suite) habitacion).isTieneJacuzzi()).append(TIPO, TIPO_SUITE);
        }
        return miDocumento;
    }
    public static Habitacion getHabitacion(Document documentoHabitacion){
        if(documentoHabitacion == null){
            throw new NullPointerException("ERROR: El documento no puede ser nulo.");
        }
        Habitacion miHabitacion=null;
        if (documentoHabitacion.getString(TIPO).equals(TIPO_SIMPLE)){
            miHabitacion= new Simple(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO));
        }
        if (documentoHabitacion.getString(TIPO).equals(TIPO_DOBLE)){
            miHabitacion= new Doble(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                    documentoHabitacion.getInteger(CAMAS_DOBLES));
        }
        if (documentoHabitacion.getString(TIPO).equals(TIPO_TRIPLE)){
            miHabitacion= new Triple(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS),documentoHabitacion.getInteger(CAMAS_INDIVIDUALES),
                    documentoHabitacion.getInteger(CAMAS_DOBLES));
        }
        if (documentoHabitacion.getString(TIPO).equals(TIPO_SUITE)){
            miHabitacion= new Suite(documentoHabitacion.getInteger(PLANTA), documentoHabitacion.getInteger(PUERTA),
                    documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS),
                    documentoHabitacion.getBoolean(JACUZZI));
        }
        return miHabitacion;
    }

    public static Reserva getReserva(Document documentoReserva){
        if(documentoReserva == null){
            throw new NullPointerException("ERROR: El documento no puede ser nulo.");
        }
        Reserva miReserva= new Reserva(
                getHuesped((Document) documentoReserva.get(HUESPED)),
                getHabitacion((Document) documentoReserva.get(HABITACION)),
                Regimen.valueOf(documentoReserva.getString(REGIMEN)),
                LocalDate.parse(documentoReserva.getString(FECHA_INICIO_RESERVA),FORMATO_DIA),
                LocalDate.parse(documentoReserva.getString(FECHA_FIN_RESERVA), FORMATO_DIA),
                documentoReserva.getInteger(NUMERO_PERSONAS));
        if(documentoReserva.containsKey(CHECKIN)){
            miReserva.setCheckIn(LocalDateTime.parse(documentoReserva.getString(CHECKIN), FORMATO_DIA_HORA));
        }
        if(documentoReserva.containsKey(CHECKOUT)){
            miReserva.setCheckOut(LocalDateTime.parse(documentoReserva.getString(CHECKOUT), FORMATO_DIA_HORA));
        }
        return miReserva;
    }
    public static Document getDocumento(Reserva reserva){
        if(reserva == null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        Document miDocumento= new Document().append(HUESPED,getDocumento(reserva.getHuesped())).append(HABITACION,getDocumento(reserva.getHabitacion())).append(REGIMEN,reserva.getRegimen().name()).append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_DIA)).append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva().format(FORMATO_DIA)).append(NUMERO_PERSONAS,reserva.getNumeroPersonas());
        return miDocumento;
    }

}