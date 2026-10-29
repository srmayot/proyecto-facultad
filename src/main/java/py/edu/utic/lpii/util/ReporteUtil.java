package py.edu.utic.lpii.util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class ReporteUtil {

    public static void mostrarReporte(String rutaArchivoJasper, Connection conexion) {
        try {
            InputStream reporteStream = ReporteUtil.class.getResourceAsStream(rutaArchivoJasper);
            
            if (reporteStream == null) {
                JOptionPane.showMessageDialog(null, "No se encontró el archivo de reporte: " + rutaArchivoJasper, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("TITULO_EMPRESA", "Sistema Comercial UTIC");

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporteStream, parametros, conexion);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte en PDF:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}