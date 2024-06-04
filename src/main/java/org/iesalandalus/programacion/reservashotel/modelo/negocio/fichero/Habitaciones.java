package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
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
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private final List<Habitacion> coleccionHabitaciones;
    private static final String RUTA_FICHERO = "datos/habitaciones.xml";
    private static final String RAIZ = "Habitaciones";
    private static final String HABITACION = "Habitacion";
    private static final String IDENTIFICADOR = "Identificador";
    private static final String PLANTA = "Planta";
    private static final String PUERTA = "Puerta";
    private static final String PRECIO = "Precio";
    private static final String CAMAS_INDIVIDUALES = "CamasIndividuales";
    private static final String CAMAS_DOBLES = "CamasDobles";
    private static final String BANOS = "Banos";
    private static final String JACUZZI = "Jacuzzi";
    private static final String TIPO = "Tipo";
    private static final String SIMPLE = "Simple";
    private static final String DOBLE = "Doble";
    private static final String TRIPLE = "Triple";
    private static final String SUITE = "Suite";
    private static Habitaciones instancia;

    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
    }

    public List<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    private List<Habitacion> copiaProfundaHabitaciones() {
        List<Habitacion> miHabitacion = new ArrayList<>();
        for (Habitacion habitacion : coleccionHabitaciones) {
            if (habitacion instanceof Simple) {
                miHabitacion.add(new Simple((Simple) habitacion));
            } else if (habitacion instanceof Doble) {
                miHabitacion.add(new Doble((Doble) habitacion));
            } else if (habitacion instanceof Triple) {
                miHabitacion.add(new Triple((Triple) habitacion));
            } else if (habitacion instanceof Suite) {
                miHabitacion.add(new Suite((Suite) habitacion));
            }
        }
        return new ArrayList<>(miHabitacion);
    }

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
        return new ArrayList<>(habitacionesTipo);
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitacion nula.");
        }
        if (buscar(habitacion) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitacion con ese identificador.");
        }
        if (habitacion instanceof Simple) {
            coleccionHabitaciones.add(new Simple((Simple) habitacion));
        } else if (habitacion instanceof Doble) {
            coleccionHabitaciones.add(new Doble((Doble) habitacion));
        } else if (habitacion instanceof Triple) {
            coleccionHabitaciones.add(new Triple((Triple) habitacion));
        } else if (habitacion instanceof Suite) {
            coleccionHabitaciones.add(new Suite((Suite) habitacion));
        }
    }

    private int buscarIndice(Habitacion habitacion) {
        return coleccionHabitaciones.indexOf(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitacion nula.");
        }
        int indice = buscarIndice(habitacion);
        if (indice != -1) {
            if (coleccionHabitaciones.get(indice) instanceof Simple) {
                return new Simple((Simple) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Doble) {
                return new Doble((Doble) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Triple) {
                return new Triple((Triple) coleccionHabitaciones.get(indice));
            } else if (coleccionHabitaciones.get(indice) instanceof Suite) {
                return new Suite((Suite) coleccionHabitaciones.get(indice));
            }
        } return null;
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitacion nula.");
        }
        int indice = buscarIndice(habitacion);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitacion como la indicada.");
        }
        coleccionHabitaciones.remove(indice);
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    @Override
    public void terminar() {
        escribirXML();
    }
    public static Habitaciones getInstancia() {
        if (instancia == null) {
            instancia = new Habitaciones();
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
            NodeList listaHabitaciones = documento.getElementsByTagName(HABITACION);
            for (int i = 0; i < listaHabitaciones.getLength(); i++) {
                Element elementoHabitacion = (Element) listaHabitaciones.item(i);
                Habitacion habitacion = elementToHabitacion(elementoHabitacion);
                coleccionHabitaciones.add(habitacion);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Habitacion elementToHabitacion(Element elementoHabitacion) {
        Habitacion habitacion;
        String tipoHabitacion = elementoHabitacion.getAttribute(TIPO);
        int planta = Integer.parseInt(elementoHabitacion.getElementsByTagName(PLANTA).item(0).getTextContent());
        int puerta = Integer.parseInt(elementoHabitacion.getElementsByTagName(PUERTA).item(0).getTextContent());
        float precio = Float.parseFloat(elementoHabitacion.getElementsByTagName(PRECIO).item(0).getTextContent());
        switch (tipoHabitacion) {
            case SIMPLE:
                habitacion = new Simple(planta, puerta, precio);
                break;
            case DOBLE:
                int camasDobles = Integer.parseInt(elementoHabitacion.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                int camasIndividuales = Integer.parseInt(elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                habitacion = new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
                break;
            case TRIPLE:
                camasDobles = Integer.parseInt(elementoHabitacion.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                camasIndividuales = Integer.parseInt(elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                int banos = Integer.parseInt(elementoHabitacion.getElementsByTagName(BANOS).item(0).getTextContent());
                habitacion = new Triple(planta, puerta, precio, banos, camasIndividuales, camasDobles);
                break;
            case SUITE:
                camasDobles = Integer.parseInt(elementoHabitacion.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                camasIndividuales = Integer.parseInt(elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                banos = Integer.parseInt(elementoHabitacion.getElementsByTagName(BANOS).item(0).getTextContent());
                boolean jacuzzi = Boolean.parseBoolean(elementoHabitacion.getElementsByTagName(JACUZZI).item(0).getTextContent());
                habitacion = new Suite(planta, puerta, precio, banos, jacuzzi);
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación desconocido: " + tipoHabitacion);
        }
        return habitacion;
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
            for (Habitacion habitaciones : coleccionHabitaciones) {
                Element elementoHabitacion = habitacionToElement(documento, habitaciones);
                raiz.appendChild(elementoHabitacion);
            }
            UtilidadesXML.domToXml(documento, RUTA_FICHERO);
        } catch (ParserConfigurationException e) {
            e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Element habitacionToElement(Document document, Habitacion habitacion) {
        Element elementoHabitacion = document.createElement(HABITACION);
        elementoHabitacion.setAttribute(IDENTIFICADOR, habitacion.getIdentificador());
        elementoHabitacion.appendChild(crearElemento(document, PLANTA, String.valueOf(habitacion.getPlanta())));
        elementoHabitacion.appendChild(crearElemento(document, PUERTA, String.valueOf(habitacion.getPuerta())));
        elementoHabitacion.appendChild(crearElemento(document, PRECIO, String.valueOf(habitacion.getPrecio())));
        if (habitacion instanceof Doble) {
            Element elementoDoble = document.createElement(DOBLE);
            elementoDoble.appendChild(crearElemento(document, CAMAS_INDIVIDUALES, String.valueOf(Doble.getNumCamasIndividuales())));
            elementoDoble.appendChild(crearElemento(document, CAMAS_DOBLES, String.valueOf(Doble.getNumCamasDobles())));
            elementoHabitacion.appendChild(elementoDoble);
        }
        return elementoHabitacion;
    }

    private Element crearElemento(Document document, String etiqueta, String valor) {
        Element elemento = document.createElement(etiqueta);
        elemento.appendChild(document.createTextNode(valor));
        return elemento;
    }
}
