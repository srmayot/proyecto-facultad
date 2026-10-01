// =========================================================================
// 📁 UBICACIÓN DEL ARCHIVO: Source Packages > py.edu.utic.lpii.view
// 📄 NOMBRE DEL ARCHIVO: DlgAcercaDe.java
// =========================================================================
package py.edu.utic.lpii.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * DIÁLOGO MODAL INSTITUCIONAL (ACERCA DE...)
 * 
 * Capa de Presentación (View):
 * Despliega los créditos académicos de la UTIC, la cátedra Lenguajes de
 * Programación II, el profesor titular y las especificaciones tecnológicas
 * del software comercial.
 */
public class DlgAcercaDe extends JDialog {

    public DlgAcercaDe(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Acerca del Sistema Comercial UTIC");
        this.setSize(480, 360);
        this.setResizable(false);
        this.setLocationRelativeTo(this.getParent());
        this.setLayout(new BorderLayout());

        // 1. Panel de Cabecera (Título y Universidad)
        JPanel pnlHeader = new JPanel(new GridLayout(3, 1, 0, 4));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(18, 20, 14, 20));
        pnlHeader.setBackground(new Color(24, 28, 36));

        JLabel lblUniv = new JLabel("UNIVERSIDAD TECNOLÓGICA INTERCONTINENTAL", SwingConstants.CENTER);
        lblUniv.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUniv.setForeground(new Color(245, 158, 11)); // Ámbar UTIC

        JLabel lblCarrera = new JLabel("Licenciatura en Análisis de Sistemas Informáticos", SwingConstants.CENTER);
        lblCarrera.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCarrera.setForeground(new Color(203, 213, 225));

        JLabel lblSistema = new JLabel("SISTEMA COMERCIAL UTIC v1.0", SwingConstants.CENTER);
        lblSistema.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSistema.setForeground(new Color(255, 255, 255));

        pnlHeader.add(lblUniv);
        pnlHeader.add(lblCarrera);
        pnlHeader.add(lblSistema);
        this.add(pnlHeader, BorderLayout.NORTH);

        // 2. Panel Central con Ficha Técnica
        JPanel pnlCenter = new JPanel(new GridLayout(6, 1, 0, 6));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel lblMateria = new JLabel("📚 Asignatura: Lenguajes de Programación II (LP II)");
        lblMateria.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel lblDocente = new JLabel("👨‍🏫 Profesor Titular: Prof. Emanuel Martínez");
        JLabel lblAutor = new JLabel("💻 Desarrollado por: Estudiante UTIC - Semestre 2026");
        JLabel lblJava = new JLabel("⚙️ Plataforma: Java SE 21 LTS • Apache NetBeans IDE 23");
        JLabel lblTheme = new JLabel("🎨 Apariencia: FlatLaf 3.5.1 (Look & Feel Moderno MDI)");
        JLabel lblDb = new JLabel("🗄️ Base de Datos: PostgreSQL 16 (UTF-8) con Driver JDBC");

        pnlCenter.add(lblMateria);
        pnlCenter.add(lblDocente);
        pnlCenter.add(lblAutor);
        pnlCenter.add(lblJava);
        pnlCenter.add(lblTheme);
        pnlCenter.add(lblDb);
        this.add(pnlCenter, BorderLayout.CENTER);

        // 3. Panel Inferior con Botón Aceptar / Cerrar
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        pnlFooter.setBorder(BorderFactory.createEtchedBorder());

        JButton btnCerrar = new JButton("Aceptar y Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.addActionListener(evt -> this.dispose());
        pnlFooter.add(btnCerrar);

        this.add(pnlFooter, BorderLayout.SOUTH);
    }
}
