package py.edu.utic.lpii.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestor de Conexión a PostgreSQL 16 bajo Patrón Singleton. Garantiza una única
 * instancia compartida y previene fugas de sockets.
 */
public class DBConnection {

    // 🔒 1. Única instancia estática privada en memoria
    private static DBConnection instance;
    private Connection connection;

    // 🌐 Constantes de Conexión a PostgreSQL 16
    private static final String URL = "jdbc:postgresql://localhost:5432/db_sistema_comercial";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    // 🔒 2. Constructor privado: nadie afuera puede hacer "new DBConnection()"
    private DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("[DBConnection] Conexión establecida con éxito a PostgreSQL 16.");
        } catch (ClassNotFoundException e) {
            System.err.println("[DBConnection] ERROR: Driver org.postgresql.Driver no encontrado en pom.xml.");
        } catch (SQLException e) {
            System.err.println("[DBConnection] ERROR SQL (" + e.getSQLState() + "): " + e.getMessage());
        }
    }

    // 🌍 3. Punto de acceso global único y sincronizado (Thread-Safe)
    public static synchronized DBConnection getInstance() {
        try {
            // 🎯 Si la instancia no existe o su conexión fue cerrada, crear una nueva
            if (instance == null || instance.connection == null || instance.connection.isClosed()) {
                instance = new DBConnection();
            }
        } catch (SQLException e) {
            // En caso de fallo al verificar isClosed(), forzar reinicialización
            instance = new DBConnection();
        }
        return instance;
    }

    // Obtener el objeto Connection activo
    public Connection getConnection() {
        try {
            // 🎯 Verificar que la conexión no esté cerrada antes de entregarla
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("[DBConnection] Error al reconectar: " + e.getMessage());
        }
        return this.connection;
    }

    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                System.out.println("[DBConnection] Conexión cerrada limpiamente sin fugas.");
            }
        } catch (SQLException e) {
            System.err.println("[DBConnection] Error al cerrar conexión: " + e.getMessage());
        } finally {
            this.connection = null;
            instance = null;
        }
    }

    public boolean testConnection() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                return false;
            }
            // 🎯 Validar conexión con timeout de 3 segundos
            return this.connection.isValid(3);
        } catch (SQLException e) {
            return false;
        }
    }
}
