package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("Solo Alojamiento", 0),
    ALOJAMIENTO_CON_DESAYUNO("Alojamiento con Desayuno", 10),
    MEDIA_PENSION("Media Pensión", 20),
    PENSION_COMPLETA("Pensión Completa", 30);
    private final String cadenaAMostrar;
    private final int incrementoPrecio;
    Regimen(String cadenaAMostrar, int incrementoPrecio) {
        this.cadenaAMostrar = cadenaAMostrar;
        this.incrementoPrecio = incrementoPrecio;
    }
    public int getIncrementoPrecio() {
        return incrementoPrecio;
    }
    @Override
    public String toString() {
        return cadenaAMostrar + " - Incremento de Precio: " + incrementoPrecio;
    }
}
