package py.edu.utic.lpii.dao;

import java.sql.SQLException;
import java.util.List;
import py.edu.utic.lpii.model.Cliente;

/**
 * Contrato de persistencia para la entidad Cliente.
 * Sistema Comercial UTIC - Prof. Emanuel Martínez
 */
public interface IClienteDAO {

    /**
     * Inserta un nuevo cliente en PostgreSQL y asigna el ID generado por la secuencia SERIAL.
     * @param c Cliente con RUC validado previamente mediante Módulo 11.
     * @throws SQLException Si ocurre duplicidad de RUC (SQLState 23505) o fallo de red.
     */
    void insertar(Cliente c) throws SQLException;

    /**
     * Actualiza los datos de un cliente existente por su clave primaria.
     * @param c Cliente con ID asignado.
     * @throws SQLException Ante errores en la sentencia UPDATE.
     */
    void actualizar(Cliente c) throws SQLException;

    /**
     * Aplica borrado lógico (activo = false) protegiendo el histórico de ventas.
     * @param id Clave primaria del cliente.
     * @throws SQLException Si el registro no existe o falla la conexión.
     */
    void eliminar(Integer id) throws SQLException;

    /**
     * Busca un cliente por su clave primaria.
     * @param id Identificador único.
     * @return Entidad Cliente o null.
     */
    Cliente buscarPorId(Integer id) throws SQLException;

    /**
     * Busca un cliente directamente por su RUC fiscal (usado en Facturación).
     * @param ruc RUC formateado (ej: 80015698-3).
     * @return Cliente correspondiente o null.
     */
    Cliente buscarPorRuc(String ruc) throws SQLException;

    /**
     * Retorna todos los clientes activos ordenados por razón social.
     * @return Lista de clientes para la JTable.
     */
    List<Cliente> listarTodos() throws SQLException;

    /**
     * Búsqueda dinámica por RUC o Razón Social mediante ILIKE en PostgreSQL.
     * @param criterio Texto ingresado en el buscador en tiempo real.
     * @return Lista de coincidencias.
     */
    List<Cliente> buscarPorCriterio(String criterio) throws SQLException;
}