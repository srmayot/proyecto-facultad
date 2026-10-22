package py.edu.utic.lpii.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import javax.swing.SwingUtilities;

/**
 * GESTOR DE APARIENCIA Y TEMAS MODERNOS
 * 
 * Permite cambiar el Look and Feel en caliente (Dark / Light)
 * actualizando el árbol de componentes sin reiniciar el software.
 */
public class ThemeManager {

    public static void aplicarTemaOscuro(Component rootComponent) {
        try {
            FlatDarkLaf.setup();
            if (rootComponent != null) {
                SwingUtilities.updateComponentTreeUI(rootComponent);
            }
        } catch (Exception ex) {
            System.err.println("Error aplicando tema oscuro: " + ex.getMessage());
        }
    }

    public static void aplicarTemaClaro(Component rootComponent) {
        try {
            FlatLightLaf.setup();
            if (rootComponent != null) {
                SwingUtilities.updateComponentTreeUI(rootComponent);
            }
        } catch (Exception ex) {
            System.err.println("Error aplicando tema claro: " + ex.getMessage());
        }
    }
}