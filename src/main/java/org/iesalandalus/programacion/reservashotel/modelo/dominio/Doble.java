package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion{
    public static final int NUM_MAXIMO_PERSONAS = 2;
    public static final int MIN_NUM_CAMAS_INDIVIDUALES = 0;
    public static final int MAX_NUM_CAMAS_INDIVIDUALES = 2;
    public static final int MIN_NUM_CAMAS_DOBLES = 0;
    public static final int MAX_NUM_CAMAS_DOBLES = 1;
    private static int numCamasIndividuales;
    private static int numCamasDobles;

    public Doble(int planta, int puerta, double precio, int numCamasIndividuales, int numCamasDobles){
        super(planta,puerta,precio);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();

    }
    public Doble(Doble habitacionDoble){
        super(habitacionDoble);
        setNumCamasIndividuales(getNumCamasIndividuales());
        setNumCamasDobles(getNumCamasDobles());
        validaNumCamas();
    }

    public static int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        if (numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES){
            throw new IllegalArgumentException("Error: El numero de camas individuales de una habitacion doble no puede ser inferior a 0 ni superior a 2.");
        }else {
            Doble.numCamasIndividuales = numCamasIndividuales;
        }
    }

    public static int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        if (numCamasDobles < MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES){
            throw new IllegalArgumentException("Error: El numero de camas dobles de una habitacion doble no puede ser inferior a 0 ni superior a 1.");
        }else {
            Doble.numCamasDobles = numCamasDobles;
        }
    }

    private void validaNumCamas(){
        if (getNumCamasIndividuales() < MIN_NUM_CAMAS_INDIVIDUALES || getNumCamasIndividuales() > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: El numero de camas individuales de una habitacion doble no puede ser inferior a 0 ni mayor que 2");
        }
        if (getNumCamasDobles() < MIN_NUM_CAMAS_DOBLES || getNumCamasDobles() > MAX_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El numero de camas dobles de una habitacion doble no puede ser inferior a 0 ni mayor que 1");
        }
        if(getNumCamasIndividuales() != 2 && getNumCamasDobles() == 0 ){
            throw new IllegalArgumentException("ERROR: La distribucion de camas en una habitacion doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        }
        if (getNumCamasIndividuales() != 0 && getNumCamasDobles() == 1){
            throw new IllegalArgumentException("ERROR: La distribucion de camas en una habitacion doble tiene que ser 2 camas individuales y 0 doble o 0 camas individuales y 1 doble");
        }
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return "Doble{" +
                "numCamasIndividuales=" + numCamasIndividuales +
                ", numCamasDobles=" + numCamasDobles +
                '}';
    }
}
