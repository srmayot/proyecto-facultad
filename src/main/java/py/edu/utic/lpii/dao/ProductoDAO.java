package py.edu.utic.lpii.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import py.edu.utic.lpii.model.Producto;

public class ProductoDAO implements IProductoDAO {

    // =========================================================================
    // 💡 INSERCIÓN SEGURA CON RETURNING id (PostgreSQL 16)
    // =========================================================================
    @Override
    public void insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (codigo, nombre, precio, stock, iva, activo) "
                   + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCodigo().trim());
            ps.setString(2, p.getNombre().trim());
            ps.setBigDecimal(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getIva());
            ps.setBoolean(6, p.isActivo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                    System.out.println("✅ [DAO] Producto insertado con ID asignado: " + p.getId());
                }
            }
        }
    }

    // =========================================================================
    // 💡 ACTUALIZACIÓN POR CLAVE PRIMARIA
    // =========================================================================
    @Override
    public void actualizar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET codigo = ?, nombre = ?, precio = ?, "
                   + "stock = ?, iva = ?, activo = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCodigo().trim());
            ps.setString(2, p.getNombre().trim());
            ps.setBigDecimal(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getIva());
            ps.setBoolean(6, p.isActivo());
            ps.setInt(7, p.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontró el producto con ID " + p.getId() + " para actualizar.");
            }
        }
    }

    // =========================================================================
    // 💡 BORRADO LÓGICO
    // =========================================================================
    @Override
    public void eliminar(Integer id) throws SQLException {
        String sql = "UPDATE productos SET activo = FALSE WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new SQLException("No se encontró el producto con ID " + id + " para inactivar.");
            }
        }
    }

    // =========================================================================
    // 💡 BÚSQUEDA POR ID
    // =========================================================================
    @Override
    public Producto buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT id, codigo, nombre, precio, stock, iva, activo FROM productos WHERE id = ?";
        Producto p = null;

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p = mapearProducto(rs);
                }
            }
        }
        return p;
    }

    // =========================================================================
    // 💡 LISTADO COMPLETO DE PRODUCTOS ACTIVOS
    // =========================================================================
    @Override
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre, precio, stock, iva, activo "
                   + "FROM productos WHERE activo = TRUE ORDER BY nombre ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }
        return lista;
    }

    // =========================================================================
    // 💡 BÚSQUEDA REACTIVA CON OPERADOR ILIKE (Case-Insensitive)
    // =========================================================================
    @Override
    public List<Producto> buscarPorCriterio(String criterio) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre, precio, stock, iva, activo "
                   + "FROM productos WHERE activo = TRUE AND (codigo ILIKE ? OR nombre ILIKE ?) "
                   + "ORDER BY nombre ASC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio.trim() + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProducto(rs));
                }
            }
        }
        return lista;
    }

    // =========================================================================
    // 🔒 MÉTODO PRIVADO DE MAPEO (DRY: Don't Repeat Yourself)
    // =========================================================================
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getInt("id"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setStock(rs.getInt("stock"));
        p.setIva(rs.getInt("iva"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}