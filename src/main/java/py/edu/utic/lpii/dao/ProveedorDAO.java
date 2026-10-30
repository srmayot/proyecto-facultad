package py.edu.utic.lpii.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import py.edu.utic.lpii.model.Proveedor;
import py.edu.utic.lpii.dao.DBConnection;

/**
 * Implementación JDBC del DAO para la entidad Proveedor. Sistema Comercial UTIC
 */
public class ProveedorDAO implements IProveedorDAO {

    @Override
    public void insertar(Proveedor prov) throws SQLException {
        String sql = "INSERT INTO proveedores (ruc, razon_social, contacto, telefono, email, direccion, rubro, activo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prov.getRuc().trim());
            ps.setString(2, prov.getRazonSocial().trim());
            ps.setString(3, prov.getContacto() != null ? prov.getContacto().trim() : null);
            ps.setString(4, prov.getTelefono() != null ? prov.getTelefono().trim() : null);
            ps.setString(5, prov.getEmail() != null ? prov.getEmail().trim() : null);
            ps.setString(6, prov.getDireccion() != null ? prov.getDireccion().trim() : null); 
            ps.setString(7, prov.getRubro());
            ps.setBoolean(8, prov.isActivo());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prov.setId(rs.getInt(1));
                    System.out.println("✅ [ProveedorDAO] Proveedor insertado con ID #" + prov.getId());
                }
            }
        }
    }

    @Override
    public List<Proveedor> listarTodos() throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        // 🟢 Incluimos 'direccion' en el SELECT
        String sql = "SELECT id, ruc, razon_social, contacto, telefono, email, direccion, rubro, activo "
                   + "FROM proveedores WHERE activo = TRUE ORDER BY razon_social ASC";

        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setRuc(rs.getString("ruc"));
                p.setRazonSocial(rs.getString("razon_social"));
                p.setContacto(rs.getString("contacto"));
                p.setTelefono(rs.getString("telefono"));
                p.setEmail(rs.getString("email"));
                p.setDireccion(rs.getString("direccion")); // 🟢 Mapeamos la dirección correctamente
                p.setRubro(rs.getString("rubro"));
                p.setActivo(rs.getBoolean("activo"));
                lista.add(p);
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Proveedor prov) throws SQLException {
        // 🟢 Implementamos el UPDATE completo incluyendo la dirección
        String sql = "UPDATE proveedores SET ruc = ?, razon_social = ?, contacto = ?, telefono = ?, email = ?, direccion = ?, rubro = ?, activo = ? WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prov.getRuc().trim());
            ps.setString(2, prov.getRazonSocial().trim());
            ps.setString(3, prov.getContacto() != null ? prov.getContacto().trim() : null);
            ps.setString(4, prov.getTelefono() != null ? prov.getTelefono().trim() : null);
            ps.setString(5, prov.getEmail() != null ? prov.getEmail().trim() : null);
            ps.setString(6, prov.getDireccion() != null ? prov.getDireccion().trim() : null);
            ps.setString(7, prov.getRubro());
            ps.setBoolean(8, prov.isActivo());
            ps.setInt(9, prov.getId());

            ps.executeUpdate();
            System.out.println("✅ [ProveedorDAO] Proveedor actualizado con ID #" + prov.getId());
        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        // Soft delete (marcar como inactivo)
        String sql = "UPDATE proveedores SET activo = FALSE WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Proveedor buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT id, ruc, razon_social, contacto, telefono, email, direccion, rubro, activo FROM proveedores WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Proveedor p = new Proveedor();
                    p.setId(rs.getInt("id"));
                    p.setRuc(rs.getString("ruc"));
                    p.setRazonSocial(rs.getString("razon_social"));
                    p.setContacto(rs.getString("contacto"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setEmail(rs.getString("email"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setRubro(rs.getString("rubro"));
                    p.setActivo(rs.getBoolean("activo"));
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public Proveedor buscarPorRuc(String ruc) throws SQLException {
        return null;
    }

    @Override
    public List<Proveedor> listarPorRubro(String rubro) throws SQLException {
        return null;
    }

    @Override
    public List<Proveedor> buscarPorCriterio(String criterio) throws SQLException {
        return null;
    }
}