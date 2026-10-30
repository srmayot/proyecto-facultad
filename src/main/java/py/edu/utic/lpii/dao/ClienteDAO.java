/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.edu.utic.lpii.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import py.edu.utic.lpii.model.Cliente;

/**
 * Implementación JDBC del DAO para la entidad Cliente. Sistema Comercial UTIC
 * @author smayot
 */
public class ClienteDAO implements IClienteDAO {

    @Override
    public void insertar(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (ruc, razon_social, telefono, direccion, tipo, activo) "
                   + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getRuc().trim());
            ps.setString(2, c.getRazonSocial().trim());
            ps.setString(3, c.getTelefono() != null ? c.getTelefono().trim() : null);
            ps.setString(4, c.getDireccion() != null ? c.getDireccion().trim() : null);
            ps.setString(5, c.getTipo());
            ps.setBoolean(6, c.isActivo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c.setId(rs.getInt(1)); // Asigna el SERIAL generado en PostgreSQL
                    System.out.println("✅ [ClienteDAO] Cliente registrado con ID #" + c.getId());
                }
            }
        }
    }

    @Override
    public void actualizar(Cliente c) throws SQLException {
        String sql = "UPDATE clientes SET ruc = ?, razon_social = ?, telefono = ?, "
                   + "direccion = ?, tipo = ?, activo = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getRuc().trim());
            ps.setString(2, c.getRazonSocial().trim());
            ps.setString(3, c.getTelefono() != null ? c.getTelefono().trim() : null);
            ps.setString(4, c.getDireccion() != null ? c.getDireccion().trim() : null);
            ps.setString(5, c.getTipo());
            ps.setBoolean(6, c.isActivo());
            ps.setInt(7, c.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontró el cliente ID #" + c.getId() + " para actualizar.");
            }
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        String sql = "UPDATE clientes SET activo = FALSE WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Cliente buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT id, ruc, razon_social, telefono, direccion, tipo, activo FROM clientes WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    // =========================================================================
    // 💡 LISTADO GENERAL DE CLIENTES ACTIVOS
    // =========================================================================
    @Override
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, ruc, razon_social, telefono, direccion, tipo, activo "
                   + "FROM clientes WHERE activo = TRUE ORDER BY razon_social ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }

    // =========================================================================
    // 💡 BÚSQUEDA REACTIVA EN TIEMPO REAL CON OPERADOR ILIKE
    // =========================================================================
    @Override
    public List<Cliente> buscarPorCriterio(String criterio) throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, ruc, razon_social, telefono, direccion, tipo, activo "
                   + "FROM clientes WHERE activo = TRUE AND (ruc ILIKE ? OR razon_social ILIKE ?) "
                   + "ORDER BY razon_social ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio.trim() + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public Cliente buscarPorRuc(String ruc) throws SQLException {
        String sql = "SELECT id, ruc, razon_social, telefono, direccion, tipo, activo FROM clientes WHERE ruc = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ruc.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    // =========================================================================
    // 🔒 MÉTODO PRIVADO AUXILIAR DE MAPEO
    // =========================================================================
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setRuc(rs.getString("ruc"));
        c.setRazonSocial(rs.getString("razon_social"));
        c.setTelefono(rs.getString("telefono"));
        c.setDireccion(rs.getString("direccion"));
        c.setTipo(rs.getString("tipo"));
        c.setActivo(rs.getBoolean("activo"));
        return c;
    }
}