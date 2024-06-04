package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reservas implements IReservas {
    private final List<Reserva> coleccionReservas;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String RUTA_FICHERO = "datos/reservas.xml";
    private static final String RAIZ = "Reservas";
    private static final String RESERVA = "Reserva";
    private static final String DNI_HUESPED = "Dni";
    private static final String PLANTA_HABITACION = "Planta";
    private static final String PUERTA_HABITACION = "Puerta";
    private static final String FECHA_INICIO_RESERVA = "FechaInicioReserva";
    private static final String FECHA_FIN_RESERVA = "FechaFinReserva";
    private static final String REGIMEN = "Regimen";
    private static final String NUMERO_PERSONAS = "Personas";
    private static final String CHECKIN = "FechaCheckin";
    private static final String CHECKOUT = "FechaCheckout";
    private static final String PRECIO = "Precio";
    private static Reservas instancia;

    public Reservas() {
                this.coleccionReservas = new ArrayList<>();
        }

        public List<Reserva> get() {
                return copiaProfundaReservas();
        }
        private List<Reserva> copiaProfundaReservas() {
                List<Reserva> misReservas = new ArrayList<>();
            for (Reserva coleccionReserva : coleccionReservas) {
                misReservas.add(new Reserva(coleccionReserva));
            }
                return misReservas;
        }

        public int getTamano() {
                return coleccionReservas.size();
        }

        public void insertar(Reserva reserva) throws OperationNotSupportedException {
                if (reserva == null) {
                        throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
                }
                if (buscar(reserva) != null) {
                        throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
                }
                coleccionReservas.add(new Reserva(reserva));
        }

        public Reserva buscar(Reserva reserva) {
                if (reserva == null) {
                        throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
                }
            for (Reserva actual : coleccionReservas) {
                if (actual.equals(reserva)) {
                    return new Reserva(actual);
                }
            }
                return null;
        }

        public void borrar(Reserva reserva) throws OperationNotSupportedException {
                if (reserva == null) {
                        throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
                }
                if (!coleccionReservas.contains(reserva)) {
                        throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
                }
                Iterator<Reserva> iterator = coleccionReservas.iterator();
                while (iterator.hasNext()) {
                        Reserva actual = iterator.next();
                        if (actual.equals(reserva)) {
                                iterator.remove();
                                return;
                        }
                }
        }

        public List<Reserva> getReservas(Huesped huesped) {
                if (huesped == null) {
                        throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");
                }
                List<Reserva> miReserva = new ArrayList<>();
            for (Reserva actual : coleccionReservas) {
                if (actual.getHuesped().equals(huesped)) {
                    miReserva.add(new Reserva(actual));
                }
            }
                return miReserva;
        }
        public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
                if (tipoHabitacion == null) {
                        throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitacion nula.");
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
                return new ArrayList<>(habitacionesTipo);
        }

        public List<Reserva> getReservasFuturas(Habitacion habitacion) {
                if (habitacion == null) {
                        throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitacion nula.");
                }
                LocalDate fechaActual = LocalDate.now();
                List<Reserva> reservasFuturas = new ArrayList<>();
            for (Reserva actual : coleccionReservas) {
                if (actual.getHabitacion().equals(habitacion) && actual.getFechaInicioReserva().isAfter(fechaActual)) {
                    reservasFuturas.add(new Reserva(actual));
                }
            }
                return reservasFuturas;
        }

        public List<Reserva> getReservas(Habitacion habitacion) {
                if (habitacion == null) {
                        throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitacion nula.");
                }
                List<Reserva> reservasHabitacion = new ArrayList<>();
            for (Reserva actual : coleccionReservas) {
                if (actual.getHabitacion().equals(habitacion)) {
                    reservasHabitacion.add(new Reserva(actual));
                }
            }
                return reservasHabitacion;
        }

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
            for (Reserva actual : coleccionReservas) {
                if (actual.equals(reserva)) {
                    actual.setCheckIn(fecha);
                    return;
                }
            }
        }

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
            for (Reserva actual : coleccionReservas) {
                if (actual.equals(reserva)) {
                    actual.setCheckOut(fecha);
                    return;
                }
            }
        }
        @Override
        public void comenzar() {
            leerXML();
        }

        @Override
        public void terminar() {
            escribirXML();
        }

        public static Reservas getInstancia() {
        if (instancia == null) {
            instancia = new Reservas();
        }
        return instancia;
        }

        private void leerXML() {
            try {
                File archivoXML = new File(RUTA_FICHERO);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document documento = dBuilder.parse(archivoXML);
                documento.getDocumentElement().normalize();
                NodeList listaReservas = documento.getElementsByTagName(RESERVA);
                for (int i = 0; i < listaReservas.getLength(); i++) {
                    Element elementoReserva = (Element) listaReservas.item(i);
                    Reserva reserva = elementToReserva(elementoReserva);
                    coleccionReservas.add(reserva);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    private Reserva elementToReserva(Element elementoReserva) {
        String dni = elementoReserva.getAttribute(DNI_HUESPED);
        int planta = Integer.parseInt(elementoReserva.getElementsByTagName(PLANTA_HABITACION).item(0).getTextContent());
        int puerta = Integer.parseInt(elementoReserva.getElementsByTagName(PUERTA_HABITACION).item(0).getTextContent());
        String regimen = elementoReserva.getElementsByTagName(REGIMEN).item(0).getTextContent();
        String fechaInicioReservaStr = elementoReserva.getElementsByTagName(FECHA_INICIO_RESERVA).item(0).getTextContent();
        String fechaFinReservaStr = elementoReserva.getElementsByTagName(FECHA_FIN_RESERVA).item(0).getTextContent();
        int numeroPersonas = Integer.parseInt(elementoReserva.getElementsByTagName(NUMERO_PERSONAS).item(0).getTextContent());
        LocalDate fechaInicioReserva = LocalDate.parse(fechaInicioReservaStr, DateTimeFormatter.ofPattern(String.valueOf(FORMATO_FECHA)));
        LocalDate fechaFinReserva = LocalDate.parse(fechaFinReservaStr, DateTimeFormatter.ofPattern(String.valueOf(FORMATO_FECHA)));
        int precio = Integer.parseInt(elementoReserva.getElementsByTagName(PRECIO).item(0).getTextContent());
        return new Reserva(new Huesped(dni), new Habitacion(planta, puerta, precio) {
            @Override
            public int getNumeroMaximoPersonas() {
                return 0;
            }
        },Regimen.valueOf(regimen), fechaInicioReserva, fechaFinReserva, numeroPersonas);
    }


    private void escribirXML() {
        try {
            File file = new File(RUTA_FICHERO);
            Document documento;
            if (!file.exists()) {
                documento = UtilidadesXML.crearDomVacio(RAIZ);
            } else {
                documento = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            }
            Element raiz = documento.getDocumentElement();
            for (Reserva reserva : coleccionReservas) {
                Element elementoReserva = reservaToElement(documento, reserva);
                raiz.appendChild(elementoReserva);
            }
            UtilidadesXML.domToXml(documento, RUTA_FICHERO);
        } catch (ParserConfigurationException e) {
            e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Element reservaToElement(Document documento, Reserva reserva) {
        Element elementoReserva = documento.createElement(RESERVA);
        Element elementoDni = documento.createElement(DNI_HUESPED);
        elementoDni.appendChild(documento.createTextNode(reserva.getHuesped().getDni()));
        elementoReserva.appendChild(elementoDni);
        Element elementoPlanta = documento.createElement(PLANTA_HABITACION);
        elementoPlanta.appendChild(documento.createTextNode(String.valueOf(reserva.getHabitacion().getPlanta())));
        elementoReserva.appendChild(elementoPlanta);
        Element elementoPuerta = documento.createElement(PUERTA_HABITACION);
        elementoPuerta.appendChild(documento.createTextNode(String.valueOf(reserva.getHabitacion().getPuerta())));
        elementoReserva.appendChild(elementoPuerta);
        Element elementoFechaInicioReserva = documento.createElement(FECHA_INICIO_RESERVA);
        elementoFechaInicioReserva.appendChild(documento.createTextNode(reserva.getFechaInicioReserva().toString()));
        elementoReserva.appendChild(elementoFechaInicioReserva);
        Element elementoFechaFinReserva = documento.createElement(FECHA_FIN_RESERVA);
        elementoFechaFinReserva.appendChild(documento.createTextNode(reserva.getFechaFinReserva().toString()));
        elementoReserva.appendChild(elementoFechaFinReserva);
        Element elementoRegimen = documento.createElement(REGIMEN);
        elementoRegimen.appendChild(documento.createTextNode(reserva.getRegimen().toString()));
        elementoReserva.appendChild(elementoRegimen);
        Element elementoNumeroPersonas = documento.createElement(NUMERO_PERSONAS);
        elementoNumeroPersonas.appendChild(documento.createTextNode(String.valueOf(reserva.getNumeroPersonas())));
        elementoReserva.appendChild(elementoNumeroPersonas);
        Element elementoFechaCheckin = documento.createElement(CHECKIN);
        if (reserva.getCheckIn() != null) {
            elementoFechaCheckin.appendChild(documento.createTextNode(reserva.getCheckIn().toString()));
        }
        elementoReserva.appendChild(elementoFechaCheckin);
        Element elementoFechaCheckout = documento.createElement(CHECKOUT);
        if (reserva.getCheckOut() != null) {
            elementoFechaCheckout.appendChild(documento.createTextNode(reserva.getCheckOut().toString()));
        }
        elementoReserva.appendChild(elementoFechaCheckout);
        Element elementoPrecio = documento.createElement(PRECIO);
        elementoPrecio.appendChild(documento.createTextNode(String.valueOf(reserva.getPrecio())));
        elementoReserva.appendChild(elementoPrecio);
        return elementoReserva;
    }

}

