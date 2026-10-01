package py.edu.utic.lpii.model;

import java.util.Objects;

/**
 * Entidad de Dominio: Categoria
 * Representa la clasificación taxonómica de los artículos comerciales.
 * Utilizada para poblar componentes JComboBox en la vista Swing y agrupar en reportes.
 */
public class Categoria {
    // =========================================================================
    // BLOQUE 1: ATRIBUTOS DE INSTANCIA ENCAPSULADOS (ESTADO COHERENTE)
    // =========================================================================
    private Integer id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean activo;

    // =========================================================================
    // BLOQUE 2: CONSTRUCTORES SOBRECARGADOS
    // =========================================================================
    public Categoria() {
        this.activo = true; // Por defecto toda nueva categoría nace activa
    }

    public Categoria(Integer id, String codigo, String nombre, String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.setNombre(nombre); // Reutiliza la validación defensiva del setter
        this.descripcion = descripcion;
        this.activo = true;
    }

    // =========================================================================
    // BLOQUE 3: MÉTODOS MUTADORES Y ACCESORES CON VALIDACIÓN DEFENSIVA
    // =========================================================================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        // VALIDACIÓN: Evita que existan categorías con nombres en blanco o nulos
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // =========================================================================
    // BLOQUE 4: INTEGRACIÓN CON SWING (JComboBox) Y COMPARACIÓN DE OBJETOS
    // =========================================================================
    @Override
    public String toString() {
        // Retorna el nombre comercial para que el JComboBox lo muestre de forma automática
        return nombre != null ? nombre : "";
    }

    @Override
    public boolean equals(Object o) {
        // Dos categorías son idénticas si comparten la misma clave primaria (id)
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
