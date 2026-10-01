package py.edu.utic.lpii.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Entidad de Dominio: Producto
 * Representa los artículos disponibles para compra y venta en el sistema comercial.
 * Cumple con la normativa tributaria paraguaya (Ley 6380/19) para la liquidación de IVA.
 */
public class Producto {
    // =========================================================================
    // BLOQUE 1: ATRIBUTOS PRIVADOS CON PRECISIÓN ARBITRARIA PARA MONEDA
    // =========================================================================
    private Integer id;
    private String codigo;
    private String nombre;
    private BigDecimal precio; // REGLA: Nunca usar double/float para importes en Guaraníes
    private int stock;
    private int iva; // Tasas válidas en Paraguay: 10 (IVA 10%), 5 (IVA 5%), 0 (Exenta)
    private Categoria categoria; // Relación de agregación: el producto pertenece a una categoría
    private boolean activo;

    // =========================================================================
    // BLOQUE 2: CONSTRUCTORES CON INICIALIZACIÓN SEGURA
    // =========================================================================
    public Producto() {
        this.precio = BigDecimal.ZERO;
        this.activo = true;
        this.iva = 10; // Tasa predominante en el comercio minorista paraguayo
        this.stock = 0;
    }

    public Producto(Integer id, String codigo, String nombre, BigDecimal precio, int stock, int iva, Categoria categoria) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.setPrecio(precio); // Valida que el precio no sea nulo ni negativo
        this.setStock(stock);   // Valida que el stock sea >= 0
        this.setIva(iva);       // Valida tasa fiscal paraguaya (0, 5, 10)
        this.categoria = categoria;
        this.activo = true;
    }

    // =========================================================================
    // BLOQUE 3: LÓGICA DE NEGOCIO TRIBUTARIO (DNIT / SET PARAGUAY)
    // =========================================================================
    /**
     * Calcula la liquidación del IVA incluido en el precio final de venta.
     * En Paraguay, por ley, todo precio al público ya incluye el impuesto:
     * - Tasa 10%:  Precio / 11
     * - Tasa 5%:   Precio / 21
     * - Exenta:    0 Gs.
     * 
     * Se utiliza escala 0 y RoundingMode.HALF_UP porque el Guaraní (PYG) no posee centavos.
     */
    public BigDecimal calcularIvaMonto() {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (iva == 10) {
            // Ejemplo: 110.000 Gs / 11 = 10.000 Gs de IVA
            return precio.divide(new BigDecimal("11"), 0, RoundingMode.HALF_UP);
        } else if (iva == 5) {
            // Ejemplo: 21.000 Gs / 21 = 1.000 Gs de IVA
            return precio.divide(new BigDecimal("21"), 0, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Retorna la base imponible neta sin el impuesto de IVA.
     * Base Imponible = Precio Bruto - Monto de IVA Liquidado.
     */
    public BigDecimal calcularPrecioNeto() {
        if (precio == null) return BigDecimal.ZERO;
        return precio.subtract(calcularIvaMonto());
    }

    // =========================================================================
    // BLOQUE 4: MÉTODOS MUTADORES CON REGLAS DE INTEGRIDAD DEFENSIVA
    // =========================================================================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del artículo no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) {
        // En BigDecimal se usa compareTo para verificar magnitud numérica
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo ni nulo.");
        }
        this.precio = precio;
    }

    public int getStock() { return stock; }
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock de existencias no puede ser negativo.");
        }
        this.stock = stock;
    }

    public int getIva() { return iva; }
    public void setIva(int iva) {
        if (iva != 0 && iva != 5 && iva != 10) {
            throw new IllegalArgumentException("Tasa tributaria inválida. Las tasas legales son 0% (Exenta), 5% o 10%.");
        }
        this.iva = iva;
    }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " [" + precio + " Gs]";
    }
}
