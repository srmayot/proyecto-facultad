package py.edu.utic.lpii.service;

import py.edu.utic.lpii.model.Producto;
import java.util.ArrayList; // Herramienta de Java para listas dinámicas
import java.util.List;      // Interfaz general de listas en Java
import java.util.Optional;

/**
 * SERVICIO DE PRODUCTOS (Pre-Base de Datos)
 * Esta clase será el "cerebro" que maneja nuestros productos. 
 * Todavía no usamos base de datos, así que los guardaremos temporalmente en la memoria de la computadora.
 */
public class ProductoService {
    
    // =========================================================================
    // 🟢 ALMACENAMIENTO EN MEMORIA
    // =========================================================================
    // "List" y "ArrayList" son herramientas que ya vienen incluidas en Java (java.util).
    // Las usamos para crear una caja que guarde muchos "Productos".
    // "Producto" es la clase Java que nosotros mismos creamos en la semana 1.
    // "private final" significa que esta caja no puede ser reemplazada por otra caja entera accidentalmente.
    private final List<Producto> catalogo = new ArrayList<>();

    // =========================================================================
    // 🟢 MÉTODOS CRUD (ACCIONES QUE INVENTAMOS NOSOTROS)
    // =========================================================================
    
    /**
     * "registrar" es un método (una acción) que estamos inventando ahora.
     * Recibe un objeto de tipo "Producto" que llamamos "p".
     */
    public void registrar(Producto p) {
        // 1. Validamos que el producto que nos pasan no esté vacío (null)
        if (p == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        
        // 2. Validamos que el código no esté repetido.
        // Llamamos al otro método que inventamos abajo (buscarPorCodigo) para ver si ya existe.
        if (buscarPorCodigo(p.getCodigo()).isPresent()) {
            throw new IllegalStateException("El código de producto '" + p.getCodigo() + "' ya existe.");
        }
        
        // 3. Si pasó las dos pruebas de arriba, finalmente lo agregamos a la caja de Java (catalogo)
        catalogo.add(p);
    }

    /**
     * Otro método que estamos inventando para buscar.
     */
    public Optional<Producto> buscarPorCodigo(String codigo) {
        return catalogo.stream()
                // Compara el código ignorando mayúsculas y espacios (trim)
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst();
    }

    /**
     * Método que inventamos para devolver todos los productos guardados.
     */
    public List<Producto> listarTodos() {
        return new ArrayList<>(catalogo); // Devolvemos una copia para proteger la lista original
    }
}