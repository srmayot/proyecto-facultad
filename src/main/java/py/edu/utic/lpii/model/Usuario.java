/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.edu.utic.lpii.model;

/**
 * Entidad de Dominio: Usuario
 * Representa los operadores y colaboradores con credenciales de acceso al sistema.
 * 
 * GUÍA PEDAGÓGICA PARA EL ALUMNO:
 * - Base Suministrada: Atributos, constructor y control de roles.
 * - SECCIÓN A IMPLEMENTAR POR EL ALUMNO: Validación de robustez de clave y método autenticar().
 */
public class Usuario {
    // =========================================================================
    // 🟢 BASE SUMINISTRADA: ATRIBUTOS PRIVADOS ENCAPSULADOS
    // =========================================================================
    private Integer id;
    private String username;
    private String password;
    private String nombreCompleto;
    private String rol; // "ADMIN", "CAJERO", "SUPERVISOR"
    private boolean activo;

    // =========================================================================
    // 🟢 BASE SUMINISTRADA: CONSTRUCTORES
    // =========================================================================
    public Usuario() {
        this.activo = true;
        this.rol = "CAJERO";
    }

    public Usuario(Integer id, String username, String password, String nombreCompleto, String rol) {
        this.id = id;
        this.setUsername(username);
        this.setPassword(password);
        this.setNombreCompleto(nombreCompleto);
        this.setRol(rol);
        this.activo = true;
    }

    // =========================================================================
    // 🟢 BASE SUMINISTRADA: GETTERS Y SETTERS CON CONTROL DE ROLES
    // =========================================================================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("El username debe tener al menos 3 caracteres.");
        }
        this.username = username.trim().toLowerCase();
    }

    public String getPassword() { return password; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del colaborador es obligatorio.");
        }
        this.nombreCompleto = nombreCompleto.trim();
    }

    public String getRol() { return rol; }
    public void setRol(String rol) {
        if (rol == null || (!rol.equals("ADMIN") && !rol.equals("CAJERO") && !rol.equals("SUPERVISOR"))) {
            throw new IllegalArgumentException("Rol inválido. Debe ser ADMIN, CAJERO o SUPERVISOR.");
        }
        this.rol = rol;
    }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // =========================================================================
    // 🔴 SECCIÓN A IMPLEMENTAR POR EL ESTUDIANTE (OBLIGATORIO PARA EVALUACIÓN)
    // =========================================================================

    /**
     * DESAFÍO 1 A CODIFICAR POR EL ALUMNO:
     * Validar fortaleza de la contraseña:
     * - No puede ser nula ni vacía.
     * - Longitud mínima de 6 caracteres.
     * - No debe contener espacios en blanco.
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener un mínimo de 6 caracteres de seguridad.");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("La contraseña no puede contener espacios en blanco.");
        }
        this.password = password;
    }

    /**
     * DESAFÍO 2 A CODIFICAR POR EL ALUMNO:
     * Realizar la verificación de autenticación de login:
     * Comprobar que el usuario esté activo y que coincidan username y password.
     */
    public boolean autenticar(String usernameIntento, String passwordIntento) {
        if (!this.activo) {
            return false; // Usuario inhabilitado por la administración
        }
        if (usernameIntento == null || passwordIntento == null) {
            return false;
        }
        return this.username.equalsIgnoreCase(usernameIntento.trim()) && this.password.equals(passwordIntento);
    }

    @Override
    public String toString() {
        return username + " (" + rol + ") - " + nombreCompleto;
    }
}