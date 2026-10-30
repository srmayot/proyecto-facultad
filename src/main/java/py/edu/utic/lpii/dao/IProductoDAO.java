package py.edu.utic.lpii.dao;

import java.sql.SQLException;
import java.util.List;
import py.edu.utic.lpii.model.Producto;

/**
 * Contrato de persistencia para la entidad Producto.
 * Desacopla las operaciones de base de datos de la interfaz gráfica Swing.
 * Sistema Comercial UTIC - Prof. Emanuel Martínez
 */
public interface IProductoDAO {

    /**
     * Inserta un nuevo producto en PostgreSQL y recupera el ID autogenerado.
     * @param p Objeto producto con datos validados (sin ID previo).
     * @throws SQLException Si ocurre duplicidad de código o fallo de conexión.
     */
    void insertar(Producto p) throws SQLException;

    /**
     * Actualiza un producto existente en la tabla por su clave primaria.
     * @param p Objeto producto con ID asignado.
     * @throws SQLException Si ocurre error durante la ejecución SQL.
     */
    void actualizar(Producto p) throws SQLException;

    /**
     * Elimina un producto por su clave primaria (o aplica borrado lógico).
     * @param id Clave primaria del producto.
     * @throws SQLException Si el producto tiene integridad referencial en ventas.
     */
    void eliminar(Integer id) throws SQLException;

    /**
     * Busca un producto por su clave primaria única.
     * @param id Identificador en PostgreSQL.
     * @return Producto encontrado o null si no existe.
     * @throws SQLException Ante fallos en la consulta SQL.
     */
    Producto buscarPorId(Integer id) throws SQLException;

    /**
     * Retorna todos los productos activos ordenados alfabéticamente.
     * @return Lista de entidades Producto para alimentar la JTable.
     * @throws SQLException Ante errores en el ResultSet.
     */
    List<Producto> listarTodos() throws SQLException;

    /**
     * Busca productos por código o nombre mediante el operador ILIKE.
     * @param criterio Texto ingresado en el buscador.
     * @return Lista de coincidencias.
     * @throws SQLException Ante fallos en el cursor.
     */
    List<Producto> buscarPorCriterio(String criterio) throws SQLException;
}