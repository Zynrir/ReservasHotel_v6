package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Habitaciones;

import java.util.Objects;

public abstract class Habitacion extends Habitaciones {
    public static final double MIN_PRECIO_HABITACION = 40.0;
    public static final double MAX_PRECIO_HABITACION = 150.0;
    public static final int MIN_NUMERO_PUERTA = 1;
    public static final int MAX_NUMERO_PUERTA = 15;
    public static final int MIN_NUMERO_PLANTA = 1;
    public static final int MAX_NUMERO_PLANTA = 3;
    public String identificador;
    public int planta;
    public int puerta;
    public double precio;

    public Habitacion(int planta, int puerta, double precio) {
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
    }

    public Habitacion(){
    }

    public Habitacion(Habitacion habitacion){
        if(habitacion==null){
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        this.planta = habitacion.planta;
        this.puerta = habitacion.puerta;
        this.precio = habitacion.precio;
        setIdentificador();
    }


    public abstract int getNumeroMaximoPersonas();

    public String getIdentificador() {
        return identificador;
    }
    protected void setIdentificador(){
        if (planta <= 0 || puerta < 0) {
            throw new IllegalArgumentException("ERROR: La planta y la puerta deben ser mayores que cero.");
        }
        this.identificador = String.format("%d%d", this.planta, this.puerta);//Pongo el formato de identificador que requiere el test
    }
    protected void setIdentificador(String identificador) {
        if (planta <= 0 || puerta < 0) {
            throw new IllegalArgumentException("ERROR: La planta y la puerta deben ser mayores que cero.");
        }
        this.identificador = String.format("%d%d", this.planta, this.puerta);
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta){
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
        }
        this.planta = planta;
        try {
            setIdentificador(identificador);
        }catch (NullPointerException e){
            System.out.println("ERROR: La planta de una habitación no puede ser nula.");
        }
    }

    public int getPuerta() {
        return puerta;
    }

    public void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA ){
            throw new IllegalArgumentException("ERROR: La puerta no puede ser superior a 15 ni inferior a 1.");
        }
        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION ){
            throw new IllegalArgumentException("ERROR: El precio no puede ser superior a 150 ni inferior a 40.");
        }
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return String.format("identificador=%s (%d-%d), precio habitación=%s",
                identificador, planta, puerta, precio);
    }
}
