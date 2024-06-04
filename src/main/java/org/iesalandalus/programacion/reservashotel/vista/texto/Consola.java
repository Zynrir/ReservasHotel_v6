package org.iesalandalus.programacion.reservashotel.vista.texto;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;

public class Consola {

    static void mostrarMenu()  {
      for (Opcion opcion : Opcion.values()){
          System.out.println(opcion);
      }
    }

    public static Opcion elegirOpcion() {
        int opcionElegida;
        do {
            System.out.print("Elija una opcion: ");
             opcionElegida = Entrada.entero();
        }while (opcionElegida < 0 || opcionElegida > Opcion.values().length);
        return Opcion.values()[opcionElegida];
    }
    public static Huesped leerHuesped() {
        System.out.println("---- Introducir datos de Huesped ----");
        System.out.print("Introduzca el Nombre del Huesped: ");
        String nombre = Entrada.cadena();
        System.out.print("Introduzca el DNI del Huesped: ");
        String dni = Entrada.cadena();
        System.out.print("Introduzca el Correo del Huesped: ");
        String correo = Entrada.cadena();
        System.out.print("Introduzca el Telefono del Huesped: ");
        String telefono = Entrada.cadena();
        System.out.print("Introduzca el Fecha de nacimiento del Huesped: ");
        LocalDate fechaNacimiento = Consola.leerFecha();
        try {
            return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static LocalDate leerFecha() {
        String fecha = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            System.out.println("Formato dd/MM/yyyy");
            fecha = Entrada.cadena();
            if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                fechaValida = true;
        }
        DateTimeFormatter formato= DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);
        LocalDate fechaFormato = LocalDate.parse(fecha, formato);
        return fechaFormato;
    }
    public static Habitacion leerHabitacion() {
        System.out.println("---- Introduce los datos de la habitacion ----");
        System.out.print("Introduzca la Planta de la habitacion: ");
        int planta = Entrada.entero();
        System.out.print("Introduzca el numero de la Puerta de la habitacion: ");
        int puerta = Entrada.entero();
        System.out.print("Introduzca el precio de la habitacion: ");
        double precio = Entrada.realDoble();
        System.out.println("Introduzca el tipo de habitacion: ");
        TipoHabitacion tipoHabitacion = leerTipoHabitacion();
        try {
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)){
                return new Simple(planta, puerta,precio);
            }else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)){
                System.out.println("Cuantas camas individuales quieres? (Escoje entre 0 y 2)");
                int camasIndividuales = Entrada.entero();
                System.out.println("Cuantas camas dobles quieres? (Escoje entre 0 y 1)");
                int camasDobles = Entrada.entero();
                return new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                System.out.println("Cuantas camas individuales? (Entre 1 y 3)");
                int numCamasIndividuales = Entrada.entero();
                System.out.println("Cuantas camas dobles? (Entre 0 y 1)");
                int numCamasDobles = Entrada.entero();
                System.out.println("Cuantos baños quieres? (Minimo 1 y maximo 2)");
                int numBanos = Entrada.entero();
                return new Triple(planta, puerta, precio, numBanos, numCamasIndividuales, numCamasDobles);
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                String jacuzzi;
                do{
                    System.out.println("¿Desea Jacuzzi en la habitacion? Introduzca si o no");
                    jacuzzi=Entrada.cadena();
                }while(!jacuzzi.equalsIgnoreCase("si") && !jacuzzi.equalsIgnoreCase("no"));
                boolean tieneJacuzzi=false;
                if(jacuzzi.equalsIgnoreCase("si")) {
                    tieneJacuzzi=true;
                }
                return new Suite(planta, puerta, precio, 2, tieneJacuzzi);
            }else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static Habitacion leerHabitacionPorIdentificador() {
        TipoHabitacion tipoHabitacion = leerTipoHabitacion();
        System.out.println("---- Introduce los datos de la habitacion ----");
        System.out.print("Cual es la planta de tu habitacion: ");
        int planta = Entrada.entero();
        System.out.print("Cual es la puerta de tu habitacion: ");
        int puerta = Entrada.entero();
        try {
            if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)){
                return new Simple(planta, puerta, 50);
            } else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                return new Doble(planta, puerta, 50, 2, 0);
            } else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                return new Triple(planta, puerta, 50, 2, 1, 1);
            } else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                return new Suite(planta, puerta, 50, 2, true);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    public static TipoHabitacion leerTipoHabitacion() {
        System.out.println("Tipos de habitacion: ");
        for (TipoHabitacion tipoHabitacion : EnumSet.allOf(TipoHabitacion.class)) {
            System.out.println(tipoHabitacion);
        }
        System.out.print("Elige un tipo de habitacion: ");
        int tipoElegido;
        do {
            tipoElegido = Entrada.entero();
        } while (tipoElegido < 0 || tipoElegido >= TipoHabitacion.values().length);
        return TipoHabitacion.values()[tipoElegido];
    }
    public static Regimen leerRegimen() {
        System.out.println("Tipo de regimen: ");
        for (Regimen regimen : Regimen.values()){
            System.out.println(regimen);
        }
        System.out.println("Elige un tipo de Regimen: ");
        int regimenElegido;
        do {
            regimenElegido = Entrada.entero();
        }while (regimenElegido < 0 || regimenElegido >= Regimen.values().length);
        return Regimen.values()[regimenElegido];
    }
    public static Huesped getHuespedPorDni() {
        System.out.print("Introduzca el DNI del Huesped: ");
        String dni = Entrada.cadena();
        try {
            return new Huesped(dni);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static int leerNumeroPersonas() {
        int numeroPersonas;
        do {
            System.out.println("Ingresa el numero de personas: ");
            numeroPersonas = Entrada.entero();
            if (numeroPersonas <= 0) {
                System.out.println("El numero de personas debe ser mayor que 0. Por favor, intenta nuevamente.");
            }
        } while (numeroPersonas <= 0);
        return numeroPersonas;
    }
}
