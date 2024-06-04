package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SUITE("Suite"),
    TRIPLE("Triple"),
    DOBLE("Doble"),
    SIMPLE("Simple");

    private final String cadenaAMotrar;

    TipoHabitacion(String cadenaAMotrar) {
        this.cadenaAMotrar = cadenaAMotrar;
    }


    @Override
    public String toString() {
        return "TipoHabitacion{" +
                "cadenaAMotrar='" + cadenaAMotrar + '\'' +
                '}';
    }
}
