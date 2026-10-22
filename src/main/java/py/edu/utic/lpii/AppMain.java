package py.edu.utic.lpii;

import py.edu.utic.lpii.dao.DBConnection;

public class AppMain {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("PRUEBA DE CONEXIÓN A POSTGRESQL 16 (CLASE 8)");
        System.out.println("=================================================");

        // 1. Solicitar instancia única
        DBConnection db = DBConnection.getInstance();

        // 2. Comprobar salud de la conexión
        if (db.testConnection()) {
            System.out.println("✓ ÉXITO: Conexión activa y funcionando perfectamente.");
            System.out.println("✓ Motor: PostgreSQL 16");
            System.out.println("✓ Base de Datos: db_sistema_comercial");
        } else {
            System.err.println("✗ FALLO: No se pudo conectar a PostgreSQL 16.");
            System.err.println("Verifique que el servicio esté activo y el puerto 5432 abierto.");
        }

        // 3. Desconectar al terminar la prueba
        db.disconnect();
        System.out.println("=================================================");
    }
}
