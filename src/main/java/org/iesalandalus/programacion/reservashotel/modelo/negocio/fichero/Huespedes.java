package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private final List<Huesped> coleccionHuespedes;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RUTA_FICHERO="datos/huespedes.xml";
    private static final String RAIZ="Huespedes" ;
    private static final String HUESPED="Huesped";
    private static final String NOMBRE="Nombre";
    private static final String DNI="Dni";
    private static final String CORREO="Correo";
    private static final String TELEFONO="Telefono";
    private static final String FECHA_NACIMIENTO= "Fechanacimiento";
    private static Huespedes instancia;

    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
    }

    public List<Huesped> get() {
        return copiaProfundaHuespedes();
    }

    private List<Huesped> copiaProfundaHuespedes() {
        List<Huesped> misHuesped = new ArrayList<>();
        for (Huesped coleccionHuespede : coleccionHuespedes) {
            misHuesped.add(new Huesped(coleccionHuespede));
        }
        return misHuesped;
    }

    public int getTamano() {
        return coleccionHuespedes.size();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (buscar(huesped) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }

        coleccionHuespedes.add(new Huesped(huesped));

    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }
        for (Huesped actual : coleccionHuespedes) {
            if (actual.equals(huesped)) {
                return new Huesped(actual);
            }
        }
        return null;
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        if (!coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        Iterator<Huesped> iterator = coleccionHuespedes.iterator();
        while (iterator.hasNext()) {
            Huesped actual = iterator.next();
            if (actual.equals(huesped)) {
                iterator.remove();
                return;
            }
        }
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    private void leerXML() {
        try {
            File archivo = new File("datos/huespedes.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = (Document) documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(HUESPED);
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String dni = element.getAttribute("Dni");
                    String nombre = element.getElementsByTagName("Nombre").item(0).getTextContent();
                    String correo = element.getElementsByTagName("Correo").item(0).getTextContent();
                    String telefono = element.getElementsByTagName("Telefono").item(0).getTextContent();
                    LocalDate fechaNacimiento = LocalDate.parse(element.getElementsByTagName("FechaNacimiento").item(0).getTextContent());
                    Huesped huesped = new Huesped(dni, nombre, correo, telefono, fechaNacimiento);
                    coleccionHuespedes.add(huesped);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    /*private void escribirXML(){
        try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document documento = dBuilder.newDocument();
                Element raiz = documento.createElement(RAIZ);
                documento.appendChild(raiz);
                for (Huesped huesped : coleccionHuespedes) {
                    Element elementoHuesped = huespedToElement(documento, huesped);
                    raiz.appendChild(elementoHuesped);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(documento);
                StreamResult result = new StreamResult(new File(RUTA_FICHERO));
                transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

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
            for (Huesped huesped : coleccionHuespedes) {
                Element elementoHuesped = huespedToElement(documento, huesped);
                raiz.appendChild(elementoHuesped);
            }
            UtilidadesXML.domToXml(documento, RUTA_FICHERO);
        } catch (ParserConfigurationException e) {
            e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        private Huesped elementToHuesped(Element elementoHuesped){
            String dni = elementoHuesped.getAttribute(DNI);
            String nombre = elementoHuesped.getElementsByTagName(NOMBRE).item(0).getTextContent();
            String correo = elementoHuesped.getElementsByTagName(CORREO).item(0).getTextContent();
            String telefono = elementoHuesped.getElementsByTagName(TELEFONO).item(0).getTextContent();
            LocalDate fechaNacimiento = LocalDate.parse(elementoHuesped.getElementsByTagName(String.valueOf(FECHA_NACIMIENTO)).item(0).getTextContent());
            return new Huesped(dni, nombre, correo, telefono, fechaNacimiento);
        }

        public static Huespedes getInstancia () {
            if (instancia == null) {
                instancia = new Huespedes();
            }
            return instancia;
        }
    private Element huespedToElement(Document documento, Huesped huesped) {
        Element elementoHuesped = documento.createElement(HUESPED);
        elementoHuesped.setAttribute(DNI, huesped.getDni());
        Element elementoNombre = documento.createElement(NOMBRE);
        elementoNombre.appendChild(documento.createTextNode(huesped.getNombre()));
        elementoHuesped.appendChild(elementoNombre);
        Element elementoCorreo = documento.createElement(CORREO);
        elementoCorreo.appendChild(documento.createTextNode(huesped.getCorreo()));
        elementoHuesped.appendChild(elementoCorreo);
        Element elementoTelefono = documento.createElement(TELEFONO);
        elementoTelefono.appendChild(documento.createTextNode(huesped.getTelefono()));
        elementoHuesped.appendChild(elementoTelefono);
        Element elementoFechaNacimiento = documento.createElement(String.valueOf(FECHA_NACIMIENTO));
        elementoFechaNacimiento.appendChild(documento.createTextNode(String.valueOf(huesped.getFechaNacimiento())));
        elementoHuesped.appendChild(elementoFechaNacimiento);
        return elementoHuesped;
    }
}