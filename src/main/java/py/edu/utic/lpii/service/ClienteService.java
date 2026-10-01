package py.edu.utic.lpii.service;

import py.edu.utic.lpii.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SERVICIO DE CLIENTES (Reto Asincrónico)
 *
 * GUÍA PEDAGÓGICA PARA EL ALUMNO: - Debes implementar esta clase guiándote por
 * lo que hicimos en ProductoService. - La validación de unicidad será sobre el
 * atributo 'ruc' del Cliente.
 */
public class ClienteService {

    // =========================================================================
    // 🟢 BASE SUMINISTRADA: CAJA EN MEMORIA
    // =========================================================================
    // Al igual que en Producto, usamos ArrayList que es una herramienta que ya viene en Java.
    private final List<Cliente> clientes = new ArrayList<>();

    // =========================================================================
    // 🔴 SECCIÓN A INVENTAR POR EL ESTUDIANTE (TU TURNO)
    // =========================================================================
    /**
     * DESAFÍO 1: Crea el método registrar() (tu propia acción) Debe verificar
     * que el RUC no exista llamando a buscarPorRuc().
     */
    public void registrar(Cliente c) {
        // TODO: 1. Validar que el cliente 'c' no venga vacío (null).
        // TODO: 2. Buscar si el RUC ya existe usando buscarPorRuc(c.getRuc()).isPresent().
        // TODO: 3. Si el RUC ya existe, frenar todo con un throw new IllegalStateException.
        // TODO: 4. Si no existe, agregarlo a la lista 'clientes' usando clientes.add(c).
        if (c == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (buscarPorRuc(c.getRuc()).isPresent()) {
            throw new IllegalStateException("El RUC de cliente '" + c.getRuc() + "' ya existe.");
        }
        clientes.add(c);
    }

    /**
     * DESAFÍO 2: Implementa el método buscarPorRuc() Utiliza Streams para
     * encontrar el cliente que coincida con el RUC, asegurándote de ignorar
     * mayúsculas y espacios (equalsIgnoreCase y trim).
     */
    public Optional<Cliente> buscarPorRuc(String ruc) {
        // TODO: Retornar clientes.stream().filter(...).findFirst()
        if (ruc == null || ruc.trim().isEmpty()) {
            return Optional.empty();
        }
        return clientes.stream()
                .filter(c -> c.getRuc().equalsIgnoreCase(ruc.trim()))
                .findFirst();
    }

    /**
     * DESAFÍO 3: Implementa listarTodos()
     */
    public List<Cliente> listarTodos() {
        // TODO: Retornar una nueva ArrayList instanciada con el contenido de 'clientes'
        return new ArrayList<>(clientes);
    }
}
