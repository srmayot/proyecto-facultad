package py.edu.utic.lpii;

import java.math.BigDecimal;
import py.edu.utic.lpii.model.Cliente;
import py.edu.utic.lpii.model.Proveedor;
import py.edu.utic.lpii.model.Usuario;

public class AppMain {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" SISTEMA COMERCIAL - PRUEBAS DE DOMINIO (LPII)");
        System.out.println("==================================================");

        try {

            int dv = Cliente.calcularDigitoVerificador("80000001");
            Proveedor proveedor = new Proveedor(1, "80000001-" + dv, "Distribuidora S.A.", "Juan Pérez", "021123456", "contacto@distribuidora.com", "Asunción");
            System.out.println("\n[OK] Proveedor registrado: " + proveedor);
            
            BigDecimal totalFactura = new BigDecimal("2200000");
            BigDecimal retencion = proveedor.calcularRetencionIva(totalFactura, 10, 30);
            System.out.println(" -> Retención IVA (30% sobre IVA 10% de 2.200.000 Gs): " + retencion + " Gs");

        } catch (Exception e) {
            System.err.println("Error en Proveedor: " + e.getMessage());
        }

        try {

            Usuario admin = new Usuario(1, "admin", "secreto123", "Carlos Administrador", "ADMIN");
            System.out.println("\n[OK] Usuario registrado: " + admin);
            
            boolean loginExitoso = admin.autenticar("admin", "secreto123");
            System.out.println(" -> Prueba de Login Válido: " + loginExitoso);
            
            boolean loginFallido = admin.autenticar("admin", "clavemal");
            System.out.println(" -> Prueba de Login Inválido (Esperado false): " + loginFallido);

        } catch (Exception e) {
            System.err.println("Error en Usuario: " + e.getMessage());
        }
        
        System.out.println("\n==================================================");
    }
}