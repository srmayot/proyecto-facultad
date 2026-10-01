/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.edu.utic.lpii.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Entidad de Dominio: Proveedor
 * Modela a las empresas y personas físicas que proveen mercaderías al comercio.
 * 
 * GUÍA PEDAGÓGICA PARA EL ALUMNO:
 * - Base Suministrada: Atributos privados y constructores.
 * - SECCIÓN A IMPLEMENTAR POR EL ALUMNO: Validación de RUC paraguayo y cálculo de retención.
 */
public class Proveedor {
    // =========================================================================
    // 🟢 BASE SUMINISTRADA: ATRIBUTOS PRIVADOS ENCAPSULADOS
    // =========================================================================
    private Integer id;
    private String ruc;
    private String razonSocial;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private boolean activo;

    // =========================================================================
    // 🟢 BASE SUMINISTRADA: CONSTRUCTORES
    // =========================================================================
    public Proveedor() {
        this.activo = true;
    }

    public Proveedor(Integer id, String ruc, String razonSocial, String contacto, String telefono, String email, String direccion) {
        this.id = id;
        this.setRuc(ruc);
        this.setRazonSocial(razonSocial);
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.activo = true;
    }

    // =========================================================================
    // 🟢 BASE SUMINISTRADA: GETTERS Y SETTERS CONVENCIONALES
    // =========================================================================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRuc() { return ruc; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) {
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            throw new IllegalArgumentException("La razón social del proveedor no puede estar vacía.");
        }
        this.razonSocial = razonSocial.trim();
    }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // =========================================================================
    // 🔴 SECCIÓN A IMPLEMENTAR POR EL ESTUDIANTE (OBLIGATORIO PARA EVALUACIÓN)
    // =========================================================================

    /**
     * DESAFÍO 1 A CODIFICAR POR EL ALUMNO:
     * Validar que el RUC no solo no sea nulo, sino que tenga el formato paraguayo
     * y su Dígito Verificador sea matemáticamente exacto (invocando Cliente.validarRucParaguay).
     */
    public void setRuc(String ruc) {
        if (ruc == null || ruc.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUC del proveedor es obligatorio.");
        }
        // TODO ALUMNO: Descomentar y conectar con la validación de RUC paraguayo:
        if (!Cliente.validarRucParaguay(ruc.trim())) {
        throw new IllegalArgumentException("El RUC del proveedor es inválido según el algoritmo Módulo 11.");
        }
        this.ruc = ruc.trim();
    }

    /**
     * DESAFÍO 2 A CODIFICAR POR EL ALUMNO:
     * Calcular el monto de retención del IVA que la empresa compradora debe descontar
     * al proveedor. Por ejemplo: para una factura de 2.200.000 Gs con IVA 10% (IVA = 200.000 Gs),
     * una retención del 30% del IVA equivale a 60.000 Gs.
     * 
     * @param totalFactura Monto total facturado en Guaraníes
     * @param tasaIva Tasa tributaria (10 o 5)
     * @param porcentajeRetencion Porcentaje de retención legal (ej. 30 para 30%)
     * @return Importe de retención en Guaraníes redondeado con HALF_UP
     */
    public BigDecimal calcularRetencionIva(BigDecimal totalFactura, int tasaIva, int porcentajeRetencion) {
        // TODO ALUMNO: Implementar la fórmula tributaria con BigDecimal:
        // 1. Calcular el IVA contenido: si tasa es 10, iva = totalFactura / 11; si es 5, iva = totalFactura / 21
        // 2. Multiplicar el IVA por (porcentajeRetencion / 100)
        // 3. Retornar el valor redondeado a escala 0
        BigDecimal divisorIva = (tasaIva == 10) ? new BigDecimal("11") : new BigDecimal("21");
        BigDecimal ivaContenido = totalFactura.divide(divisorIva, 0, RoundingMode.HALF_UP);
        BigDecimal factorRetencion = new BigDecimal(porcentajeRetencion).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        return ivaContenido.multiply(factorRetencion).setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return ruc + " - " + razonSocial;
    }
}