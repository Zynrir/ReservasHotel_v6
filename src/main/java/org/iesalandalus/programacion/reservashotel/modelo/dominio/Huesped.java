package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {
    private final String ER_TELEFONO = "^[0-9]{9}$";
    private final String ER_CORREO = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";
    private static final String ER_DNI = "^[0-9]{8}[A-Za-z]$";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    public Huesped(){
    }

    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) {
        getIniciales(nombre);
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    public Huesped(String dni) {
        setDni(dni);
    }

    public Huesped(Huesped huesped){
        if(huesped==null){
            throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
        }
        this.nombre = huesped.nombre;
        this.dni = huesped.dni;
        this.correo = huesped.correo;
        this.telefono = huesped.telefono;
        this.fechaNacimiento = huesped.fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if(telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }
        if(telefono.isBlank()){
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }
        if (telefono.matches(ER_TELEFONO)) {
            this.telefono = telefono;

        } else {
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if(correo==null){
            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        }
        if(correo.isBlank()){
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }
        if (correo.matches(ER_CORREO)) {
            this.correo = correo;
        } else {
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if(dni==null){
            throw new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        }
        if(dni.isBlank()){
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }
        if (comprobarLetraDni(dni)) {
            this.dni = dni;
        }
    }

    private static boolean comprobarLetraDni(String dni) {
        Pattern pattern = Pattern.compile(ER_DNI);
        Matcher matcher = pattern.matcher(dni);
        if (matcher.matches()) {
            String numeros = dni.substring(0, 8);
            String letra = dni.substring(8);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int resto = Integer.parseInt(numeros) % 23;
            return letra.equalsIgnoreCase(String.valueOf(letras.charAt(resto)));
        }
        return false;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if(fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        }
        if (fechaNacimiento.isAfter(LocalDate.now()) || fechaNacimiento.plusYears(120).isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de nacimiento no válida.");
        } else {
            this.fechaNacimiento = fechaNacimiento;
        }
    }

    public String getIniciales(String nombre) {
        StringBuilder iniciales = new StringBuilder();
        for (String palabra : nombre.split(" ")) {
            if (!palabra.isEmpty()) {
                iniciales.append(palabra.charAt(0));
            }
        }
        return iniciales.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Huesped huesped)) return false;
        return Objects.equals(nombre, huesped.nombre) && Objects.equals(telefono, huesped.telefono) && Objects.equals(correo, huesped.correo) && Objects.equals(dni, huesped.dni) && Objects.equals(fechaNacimiento, huesped.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("nombre=%s ("+ getIniciales(nombre) +"), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",
                nombre, dni, correo, telefono, fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}
