package org.iesalandalus.programacion.reservashotel.vista.texto;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class VistaTexto extends Vista {
    Controlador controlador;


    public VistaTexto(){
        Opcion.setVista(this);
    }

    public void setControlador(Controlador controlador){
        if (controlador != null){
            this.controlador = controlador;
        }
    }
    @Override
    public void comenzar(){
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
    }
    @Override
    public void terminar(){
        System.out.println("Hasta luego!!!");
    }

    public void insertarHuesped(){
        try {
            Huesped nuevoHuesped = Consola.leerHuesped();
            controlador.insertar(nuevoHuesped);
            System.out.println("Huesped insertado correctamente.");
        }
        catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }
    public Huesped buscarHuesped() {
        System.out.println("Ingresa el dni del huesped: ");
        String dni = Entrada.cadena();
        List<Huesped> listaHuespedes = controlador.getHuespedes();
        if (!listaHuespedes.isEmpty()) {
            Iterator<Huesped> iterator = listaHuespedes.stream()
                    .sorted(Comparator.comparing(Huesped::getNombre))
                    .iterator();
            while (iterator.hasNext()) {
                Huesped currentHuesped = iterator.next();
                if (currentHuesped.getDni().equals(dni)) {
                    System.out.println("Huesped encontrado: " + currentHuesped);
                    return currentHuesped;
                }
            }
            System.out.println("Huesped no encontrado.");
        } else {
            System.out.println("No hay huespedes registrados.");
        }
        return null;
    }
    public void borrarHuesped()   {
        try {
            System.out.println("Ingresa el dni del huesped: ");
            String dni = Entrada.cadena();
            List<Huesped> listaHuespedes = controlador.getHuespedes();
            if (!listaHuespedes.isEmpty()) {
                Iterator<Huesped> iterator = listaHuespedes.stream().sorted(Comparator.comparing(Huesped::getNombre)).iterator();
                while (iterator.hasNext()) {
                    Huesped currentHuesped = iterator.next();
                    if (currentHuesped.getDni().equals(dni)) {
                        controlador.borrar(currentHuesped);
                        System.out.println("Huesped borrado con exito.");
                    }
                }
            }
        }catch (OperationNotSupportedException e){
            System.out.println("Error: " + e);
        }

    }

    public void mostrarHuespedes() {
        List<Huesped> listaHuespedes = controlador.getHuespedes();
        if (!listaHuespedes.isEmpty()) {
            Iterator<Huesped> iterator = listaHuespedes.stream()
                    .sorted(Comparator.comparing(Huesped::getNombre))
                    .iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } else {
            System.out.println("No hay huespedes registrados.");
        }
    }
    public  void insertarHabitacion() {
       try {
           Habitacion nuevaHabitacion = Consola.leerHabitacion();
           if (nuevaHabitacion != null) {
               controlador.insertar(nuevaHabitacion);
               System.out.println("Habitacion insertada con exito.");
           } else {
               System.out.println("Error al insertar la habitacion.");
           }
       }catch (OperationNotSupportedException e) {
           System.out.println(e.getMessage());
       }
    }

    public void buscarHabitacion() {
        System.out.println("Ingresa el identificador de la habitacion: ");
        String identificador = Entrada.cadena();
        List<Habitacion> listaHabitaciones = controlador.getHabitaciones();
        if (!listaHabitaciones.isEmpty()) {
            Iterator<Habitacion> iterator = listaHabitaciones.stream().sorted(Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta)).iterator();
            while (iterator.hasNext()) {
                Habitacion currentHabitacion = iterator.next();
                if (currentHabitacion.getIdentificador().equals(identificador)){
                    System.out.println(currentHabitacion);
                }
            }
        } else {
            System.out.println("No hay habitaciones registradas.");
        }
    }

    public void borrarHabitacion() {
       try {
           System.out.println("Ingresa el identificador de la habitacion: ");
           String identificador = Entrada.cadena();
           List<Habitacion> listaHabitaciones = controlador.getHabitaciones();
           if (!listaHabitaciones.isEmpty()) {
               Iterator<Habitacion> iterator = listaHabitaciones.stream().sorted(Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta)).iterator();
               while (iterator.hasNext()) {
                   Habitacion currentHabitacion = iterator.next();
                   if (currentHabitacion.getIdentificador().equals(identificador)){
                       controlador.borrar(currentHabitacion);
                       System.out.println("Habitacion borrada con exito.");
                   }
               }
           } else {
               System.out.println("No hay habitaciones registradas.");
           }
       }catch (OperationNotSupportedException e) {
           System.out.println(e.getMessage());
       }
    }
    public void mostrarHabitaciones() {
        List<Habitacion> listaHabitaciones = controlador.getHabitaciones();
        if (!listaHabitaciones.isEmpty()) {
            Iterator<Habitacion> iterator = listaHabitaciones.stream().sorted(Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta)).iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } else {
            System.out.println("No hay habitaciones registradas.");
        }
    }

    public void insertarReserva() {
        try {
            System.out.println("Introduce los datos de la reserva:");
            Huesped huesped = buscarHuesped();
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            Regimen regimen=Consola.leerRegimen();
            System.out.println("Indica la fecha de inicio de la reserva:");
            LocalDate fechaInicioReserva = Consola.leerFecha();
            System.out.println("Indica la fecha de fin de la reserva:");
            LocalDate fechaFinReserva = Consola.leerFecha();
            int numeroPersonas= Consola.leerNumeroPersonas();
            Reserva nuevaReserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, numeroPersonas);
            Huesped huespedIntroducido = nuevaReserva.getHuesped();
            huespedIntroducido = controlador.buscar(huespedIntroducido);
            Habitacion habitacionIntroducida = nuevaReserva.getHabitacion();
            habitacionIntroducida = controlador.buscar(habitacionIntroducida);
            nuevaReserva = new Reserva(huespedIntroducido, habitacionIntroducida,nuevaReserva.getRegimen(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva(), nuevaReserva.getNumeroPersonas());
            TipoHabitacion habitacionTipo;
            if(nuevaReserva.getHabitacion() instanceof Simple){
                habitacionTipo = TipoHabitacion.SIMPLE;
            }
            else if(nuevaReserva.getHabitacion() instanceof Doble){
                habitacionTipo = TipoHabitacion.DOBLE;
            }
            else if(nuevaReserva.getHabitacion() instanceof Triple){
                habitacionTipo = TipoHabitacion.TRIPLE;
            }
            else{
                habitacionTipo = TipoHabitacion.SUITE;
            }
            if (consultarDisponibilidad(habitacionTipo, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva()) != null){
                controlador.insertar(nuevaReserva);
                System.out.println("Reserva insertada correctamente.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void anularReserva() {
        Huesped huesped = Consola.getHuespedPorDni();
        List<Reserva> reservasAnulables = controlador.getReservas(huesped);
        reservasAnulables = getReservasAnulables(reservasAnulables);
        if (reservasAnulables.isEmpty()) {
            System.out.println("No hay reservas para anular.");
        } else if (getNumElementosNoNulos(reservasAnulables) == 1) {
            System.out.println("¿Confirma la anulacion de la reserva? Escribe si o no" + reservasAnulables.get(0));
            if (Entrada.cadena().equalsIgnoreCase("si")) {
                try {
                    controlador.borrar(reservasAnulables.get(0));
                    System.out.println("Reserva anulada correctamente.");
                } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Anulación cancelada.");
            }
        } else {
            int contador = 0;
            for (Reserva elemento : reservasAnulables) {
                System.out.println(contador + " : " + elemento);
                contador++;
            }
            int indiceReserva;
            do {
                System.out.println("¿Que reserva desea anular?");
                indiceReserva = Entrada.entero();
            } while (indiceReserva < 0 || indiceReserva >= reservasAnulables.size());
            try {
                controlador.borrar(reservasAnulables.get(indiceReserva));
                System.out.println("Reserva anulada correctamente.");
            } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static List<Reserva> getReservasAnulables(List<Reserva> reservasAnular) {
        List<Reserva> misReservasAnulables= new ArrayList<>();
        for(Reserva misReservas : reservasAnular){
            if(misReservas.getFechaInicioReserva().isAfter(LocalDate.now())){
                misReservasAnulables.add(new Reserva(misReservas));
            }
        }
        return misReservasAnulables;
    }

    public void mostrarReservas() {
        List<Reserva> listaReservas = controlador.getReservas();
        if (!listaReservas.isEmpty()) {
            Iterator<Reserva> iterator = listaReservas.stream().sorted(Comparator.comparing(Reserva::getFechaInicioReserva).reversed().thenComparing(reserva -> {
                if (reserva.getHabitacion() != null) {
                    return reserva.getHabitacion().getIdentificador();
                }
                    return null;
            })).iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } else {
            System.out.println("No hay reservas registradas.");
        }
    }
    public void mostrarReservasHuesped() {
        listarReservasPorDni(Consola.getHuespedPorDni());
    }

    public void mostrarReservasTipoHabitacion() {
        listarReservas(Consola.leerTipoHabitacion());
    }
    public boolean comprobarDisponibilidad() {
        System.out.println("Introduce el tipo de habitacion: ");
        TipoHabitacion tipoHabitacionEscogida = Consola.leerTipoHabitacion();
        System.out.println("Introduce la fecha de inicio de reserva: ");
        LocalDate fechaInicioEscogida = Consola.leerFecha();
        System.out.println("Introduce la fecha de fin de reserva: ");
        LocalDate fechaFinEscogida = Consola.leerFecha();
        Habitacion habitaciondisponible = consultarDisponibilidad(tipoHabitacionEscogida, fechaInicioEscogida, fechaFinEscogida);
        if (habitaciondisponible == null){
            System.out.println("No hay disponibilidad.");
            return true;

        }else {
            System.out.println("Si hay disponibilidad.");
            return false;
        }
    }
    private Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        List<Habitacion> habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);
        for (Iterator<Habitacion> iterator = habitacionesTipoSolicitado.iterator(); iterator.hasNext() && !tipoHabitacionEncontrada; ) {
            Habitacion habitacion = iterator.next();
            if (habitacion != null){
                List<Reserva> reservasFuturas = new ArrayList<>(controlador.getReservasFuturas(habitacion));
                if (reservasFuturas.isEmpty()){
                    if (habitacion instanceof Simple){
                        habitacionDisponible = new Simple((Simple) habitacion);
                    }else if (habitacion instanceof Doble){
                        habitacionDisponible = new Doble((Doble) habitacion);
                    } else if (habitacion instanceof Triple) {
                        habitacionDisponible = new Triple((Triple) habitacion);
                    } else if (habitacion instanceof Suite) {
                        habitacionDisponible = new Suite((Suite) habitacion);
                    }
                    tipoHabitacionEncontrada = true;
                }else {
                    reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva).reversed());
                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())){
                        if (habitacion instanceof Simple){
                            habitacionDisponible = new Simple((Simple) habitacion);
                        }else if (habitacion instanceof Doble){
                            habitacionDisponible = new Doble((Doble) habitacion);
                        } else if (habitacion instanceof Triple) {
                            habitacionDisponible = new Triple((Triple) habitacion);
                        } else if (habitacion instanceof Suite) {
                            habitacionDisponible = new Suite((Suite) habitacion);
                        }
                        tipoHabitacionEncontrada = true;
                    }
                    if (!tipoHabitacionEncontrada){
                        reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva));
                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())){
                            if (habitacion instanceof Simple){
                                habitacionDisponible = new Simple((Simple) habitacion);
                            }else if (habitacion instanceof Doble){
                                habitacionDisponible = new Doble((Doble) habitacion);
                            } else if (habitacion instanceof Triple) {
                                habitacionDisponible = new Triple((Triple) habitacion);
                            } else if (habitacion instanceof Suite) {
                                habitacionDisponible = new Suite((Suite) habitacion);
                            }
                            tipoHabitacionEncontrada = true;
                        }
                    }
                    if (!tipoHabitacionEncontrada){
                        for (Iterator<Reserva> reservaIterator = reservasFuturas.iterator(); reservaIterator.hasNext() && !tipoHabitacionEncontrada; ) {
                            Reserva reservaAnterior = reservaIterator.next();
                            if (reservaIterator.hasNext()) {
                                Reserva reservaActual = reservaIterator.next();
                                if (fechaInicioReserva.isAfter(reservaAnterior.getFechaFinReserva()) && fechaFinReserva.isBefore(reservaActual.getFechaInicioReserva()))
                                {
                                    if (habitacion instanceof Simple) {
                                        habitacionDisponible = new Simple((Simple) habitacion);
                                    } else if (habitacion instanceof Doble) {
                                        habitacionDisponible = new Doble((Doble) habitacion);
                                    } else if (habitacion instanceof Triple) {
                                        habitacionDisponible = new Triple((Triple) habitacion);
                                    } else if (habitacion instanceof Suite) {
                                        habitacionDisponible = new Suite((Suite) habitacion);
                                    }
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return habitacionDisponible;
    }
    public void realizarCheckin() {
        Huesped huesped = buscarHuesped();
        System.out.println("Introduce la fecha (dd/MM/yyyy) y la hora (hh:mm:ss) del checkin:");
        LocalDateTime fechaCheckin = Consola.leerFecha().atStartOfDay();
        List<Reserva> reservasHuesped = controlador.getReservas(huesped);
        if (reservasHuesped.isEmpty()) {
            System.out.println("El hu?sped no tiene reservas.");
        } else if(getNumElementosNoNulos(reservasHuesped)==1){
            System.out.println("¿Quiere confirmar el checkIn de esta reserva? Escriba \"si\" o \"no\"");
            System.out.println(reservasHuesped.get(0));
            String confirmacion=Entrada.cadena();
            if(confirmacion.equalsIgnoreCase("si")){
                controlador.realizarCheckIn(reservasHuesped.get(0), fechaCheckin);
                System.out.println("CheckIn confirmado.");
            }
        }else{
            System.out.println("Reservas del huesped:");
            Iterator<Reserva> iterator = reservasHuesped.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                System.out.println(i + ": " + iterator.next());
                i++;
            }
            int indiceReserva;
            do {
                System.out.println("¡Que reserva desea hacer checkin?");
                indiceReserva = Entrada.entero();
            } while (indiceReserva < 0 || indiceReserva >= reservasHuesped.size());
            try {
                controlador.realizarCheckIn(reservasHuesped.get(indiceReserva), fechaCheckin);
                System.out.println("Checkin realizado correctamente.");
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void realizarCheckOut() {
        Huesped huesped = buscarHuesped();
        System.out.println("Introduce la fecha (dd/MM/yyyy) y la hora (hh:mm:ss) del checkOut:");
        LocalDateTime fechaCheckOut = Consola.leerFecha().atStartOfDay();
        List<Reserva> reservasHuesped = controlador.getReservas(huesped);
        if (reservasHuesped.isEmpty()) {
            System.out.println("El huesped no tiene reservas.");
        } else if (getNumElementosNoNulos(reservasHuesped) == 1) {
            System.out.println("¿Quiere confirmar el checkOut de esta reserva? Escriba \"si\" o \"no\"");
            System.out.println(reservasHuesped.get(0));
            String confirmacion = Entrada.cadena();
            if (confirmacion.equalsIgnoreCase("si")) {
                controlador.realizarCheckOut(reservasHuesped.get(0), fechaCheckOut);
                System.out.println("CheckOut confirmado.");
            }
        } else {
            System.out.println("Reservas del huesped:");
            Iterator<Reserva> iterator = reservasHuesped.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                System.out.println(i + ": " + iterator.next());
                i++;
            }
            int indiceReserva;
            do {
                System.out.println("¿Que reserva desea hacer checkOut?");
                indiceReserva = Entrada.entero();
            } while (indiceReserva < 0 || indiceReserva >= reservasHuesped.size());
            try {
                controlador.realizarCheckOut(reservasHuesped.get(indiceReserva), fechaCheckOut);
                System.out.println("CheckOut realizado correctamente.");
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public int getNumElementosNoNulos(List<Reserva> reservas) {
        int contador = 0;
        for (Reserva elemento : reservas) {
            if (elemento != null) {
                contador++;
            }
        }
        return contador;
    }
    public void listarReservas(TipoHabitacion tipoHabitacion) {
        List<Reserva> reservasTipoHabitacion = controlador.getReservas(tipoHabitacion);
        if (!reservasTipoHabitacion.isEmpty()) {
            for (Reserva reserva : reservasTipoHabitacion) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas para el tipo de habitacion " + tipoHabitacion);
        }
    }
    public void listarReservasPorDni(Huesped huesped) {
        List<Reserva> reservashuesped = controlador.getReservas(huesped);
        if (!reservashuesped.isEmpty()) {
            for (Reserva reserva : reservashuesped) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas para este huesped: " + huesped);
        }
    }
}
