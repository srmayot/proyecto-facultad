package py.edu.utic.lpii.dao;

import java.sql.SQLException;
import java.util.List;
import py.edu.utic.lpii.model.Proveedor;

/**
 * Contrato de persistencia para la entidad Proveedor.
 * Cierre de la Tríada de Maestros Comerciales.
 * Sistema Comercial UTIC - Prof. Emanuel Martínez
 */
public interface IProveedorDAO {

    /**
     * Inserta un nuevo proveedor y captura el ID SERIAL de PostgreSQL.
     */
    void insertar(Proveedor prov) throws SQLException;

    /**
     * Actualiza un proveedor existente por clave primaria.
     */
    void actualizar(Proveedor prov) throws SQLException;

    /**
     * Aplica borrado lógico (activo = FALSE) protegiendo órdenes de compra pasadas.
     */
    void eliminar(Integer id) throws SQLException;

    /**
     * Busca proveedor por clave primaria.
     */
    Proveedor buscarPorId(Integer id) throws SQLException;

    /**
     * Busca proveedor por RUC fiscal.
     */
    Proveedor buscarPorRuc(String ruc) throws SQLException;

    /**
     * Lista todos los proveedores activos ordenados por razón social.
     */
    List<Proveedor> listarTodos() throws SQLException;

    /**
     * Filtra proveedores por rubro comercial (Alimentos, Tecnología, etc.).
     */
    List<Proveedor> listarPorRubro(String rubro) throws SQLException;

    /**
     * Búsqueda dinámica con ILIKE por RUC o Razón Social.
     */
    List<Proveedor> buscarPorCriterio(String criterio) throws SQLException;
}