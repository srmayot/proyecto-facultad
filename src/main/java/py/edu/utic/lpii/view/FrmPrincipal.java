package py.edu.utic.lpii.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * VENTANA PRINCIPAL DEL SISTEMA (PATRÓN MDI)
 *
 * @author srmayot
 */
public class FrmPrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmPrincipal.class.getName());

    private FrmProductos ventanaProductos;
    private FrmClientes ventanaClientes;

    public FrmPrincipal() {
        initComponents();

        // Configuraciones personalizadas agregadas por el desarrollador:
        configurarVentana();

        // Abrimos la ventana interna de bienvenida centrada en el escritorio
        abrirFormularioCentrado(new FrmBienvenida());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        pnlStatusBar = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblSucursal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblFecha = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblHora = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        lblEstado = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        itemSalir = new javax.swing.JMenuItem();
        mantenimiento = new javax.swing.JMenu();
        itemProductos = new javax.swing.JMenuItem();
        itemClientes = new javax.swing.JMenuItem();
        movimientos = new javax.swing.JMenu();
        reportes = new javax.swing.JMenu();
        ver = new javax.swing.JMenu();
        itemTemaOscuro = new javax.swing.JMenuItem();
        itemTemaClaro = new javax.swing.JMenuItem();
        ayuda = new javax.swing.JMenu();
        itemAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        desktopPane.setBackground(new java.awt.Color(30, 34, 42));

        pnlStatusBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlStatusBar.setLayout(new java.awt.FlowLayout(0));

        lblUsuario.setText("Operador: Sergio Mayot");
        pnlStatusBar.add(lblUsuario);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlStatusBar.add(jSeparator3);

        lblSucursal.setText("UTIC Luque");
        pnlStatusBar.add(lblSucursal);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlStatusBar.add(jSeparator2);

        lblFecha.setText("Jueves, 01 de octubre de 2026");
        pnlStatusBar.add(lblFecha);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlStatusBar.add(jSeparator1);

        lblHora.setText("⏰ 00:00:00");
        pnlStatusBar.add(lblHora);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlStatusBar.add(jSeparator4);

        lblEstado.setText("JAVA 26");
        pnlStatusBar.add(lblEstado);

        desktopPane.setLayer(pnlStatusBar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addComponent(pnlStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 172, Short.MAX_VALUE))
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, desktopPaneLayout.createSequentialGroup()
                .addGap(0, 247, Short.MAX_VALUE)
                .addComponent(pnlStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        archivo.setMnemonic('A');
        archivo.setText("Archivo");

        itemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemSalir.setText("Salir");
        itemSalir.addActionListener(this::itemSalirActionPerformed);
        archivo.add(itemSalir);

        MenuBar.add(archivo);

        mantenimiento.setMnemonic('M');
        mantenimiento.setText("Mantenimiento");

        itemProductos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemProductos.setText("Productos");
        itemProductos.addActionListener(this::itemProductosActionPerformed);
        mantenimiento.add(itemProductos);

        itemClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemClientes.setText("Clientes");
        itemClientes.addActionListener(this::itemClientesActionPerformed);
        mantenimiento.add(itemClientes);

        MenuBar.add(mantenimiento);

        movimientos.setMnemonic('V');
        movimientos.setText("Movimientos");
        MenuBar.add(movimientos);

        reportes.setMnemonic('R');
        reportes.setText("Reportes");
        MenuBar.add(reportes);

        ver.setMnemonic('T');
        ver.setText("Ver");

        itemTemaOscuro.setText("Tema Oscuro");
        itemTemaOscuro.addActionListener(this::itemTemaOscuroActionPerformed);
        ver.add(itemTemaOscuro);

        itemTemaClaro.setText("Tema Claro");
        itemTemaClaro.addActionListener(this::itemTemaClaroActionPerformed);
        ver.add(itemTemaClaro);

        MenuBar.add(ver);

        ayuda.setMnemonic('H');
        ayuda.setText("Ayuda");

        itemAcercaDe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        itemAcercaDe.setText("Acerca de");
        itemAcercaDe.addActionListener(this::itemAcercaDeActionPerformed);
        ayuda.add(itemAcercaDe);

        MenuBar.add(ayuda);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents
   /**
     * Configuración de propiedades globales del JFrame.
     */
    private void configurarVentana() {
        iniciarRelojDigital();
        this.setTitle("Sistema Comercial UTIC v1.0 - Gestión Empresarial");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSalida();
            }
        });
        this.setLocationRelativeTo(null);
        formatearBarraEstado();
    }

    /**
     * Inyecta la fecha dinámica actual y los datos de sesión en las etiquetas
     * de la barra de estado inferior.
     */
    private void formatearBarraEstado() {
        if (lblFecha == null) {
            return;
        }
        try {
            LocalDate hoy = LocalDate.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es", "PY"));
            lblFecha.setText("📅 Fecha: " + hoy.format(fmt));
        } catch (Exception ex) {
            lblFecha.setText("📅 Fecha: " + LocalDate.now());
        }
    }


    private void itemClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemClientesActionPerformed
        // TODO add your handling code here:
        // 1. Control de Instancia Única: verificar si no existe o fue cerrada con la 'X'
        if (ventanaClientes == null || ventanaClientes.isClosed()) {
            ventanaClientes = new FrmClientes();
            jDesktopPane.add(ventanaClientes);

            // 2. Centrado Geométrico milimétrico dentro del área de trabajo del JDesktopPane:
            int x = (jDesktopPane.getWidth() - ventanaClientes.getWidth()) / 2;
            int y = (jDesktopPane.getHeight() - ventanaClientes.getHeight()) / 2;
            ventanaClientes.setLocation(Math.max(0, x), Math.max(0, y));

            // 3. Hacer visible el formulario en el escritorio:
            ventanaClientes.setVisible(true);
        } else {
            // 4. Si ya estaba abierta, restaurarla al frente y darle foco:
            ventanaClientes.toFront();
            try {
                ventanaClientes.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
                System.err.println("No se pudo seleccionar la ventana: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_itemClientesActionPerformed

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSalirActionPerformed
        // TODO add your handling code here:
        confirmarSalida();
    }//GEN-LAST:event_itemSalirActionPerformed

    private void itemProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemProductosActionPerformed
        // TODO add your handling code here:
        // 1. Control de Instancia Única: verificar si no existe o fue cerrada con la 'X'
        // TODO add your handling code here:
        // 1. Control de Instancia Única: verificar si no existe o fue cerrada con la 'X'
        if (ventanaProductos == null || ventanaProductos.isClosed()) {
            ventanaProductos = new FrmProductos();
            desktopPane.add(ventanaProductos); // <-- Aquí cambiamos jDesktopPane por desktopPane

            // 2. Centrado Geométrico milimétrico dentro del área de trabajo del JDesktopPane:
            int x = (desktopPane.getWidth() - ventanaProductos.getWidth()) / 2; // <-- Aquí también
            int y = (desktopPane.getHeight() - ventanaProductos.getHeight()) / 2; // <-- Y aquí
            ventanaProductos.setLocation(Math.max(0, x), Math.max(0, y));

            // 3. Hacer visible el formulario en el escritorio:
            ventanaProductos.setVisible(true);
        } else {
            // 4. Si ya estaba abierta, restaurarla al frente y darle foco:
            ventanaProductos.toFront();
            try {
                ventanaProductos.setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
                System.err.println("No se pudo seleccionar la ventana: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_itemProductosActionPerformed

    private void itemTemaOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTemaOscuroActionPerformed
        // TODO add your handling code here:
        aplicarTema(true);
    }//GEN-LAST:event_itemTemaOscuroActionPerformed

    private void itemTemaClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTemaClaroActionPerformed
        // TODO add your handling code here:
        aplicarTema(false);
    }//GEN-LAST:event_itemTemaClaroActionPerformed

    private void itemAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAcercaDeActionPerformed
        // TODO add your handling code here:
        // Creamos la instancia pasando 'this' como ventana principal y 'true' para que sea modal
        DlgAcercaDe dlg = new DlgAcercaDe(this, true);

        // Lo hacemos visible (el código de centrado y modal ya lo configuramos dentro del diálogo)
        dlg.setVisible(true);
    }//GEN-LAST:event_itemAcercaDeActionPerformed

    public void abrirFormularioCentrado(JInternalFrame formulario) {
        if (formulario == null || desktopPane == null) {
            return;
        }

        for (JInternalFrame frameExistente : desktopPane.getAllFrames()) {
            if (frameExistente.getClass().equals(formulario.getClass())) {
                try {
                    if (frameExistente.isIcon()) {
                        frameExistente.setIcon(false);
                    }
                    frameExistente.setSelected(true);
                    frameExistente.toFront();
                } catch (Exception ex) {
                    System.err.println("No se pudo enfocar la ventana: " + ex.getMessage());
                }
                return;
            }
        }

        desktopPane.add(formulario);
        int x = (desktopPane.getWidth() - formulario.getWidth()) / 2;
        int y = (desktopPane.getHeight() - formulario.getHeight()) / 2;
        formulario.setLocation(Math.max(0, x), Math.max(0, y));
        formulario.setVisible(true);
        try {
            formulario.setSelected(true);
        } catch (Exception ex) {
        }
    }

    private void abrirDialogoAcercaDe() {
        DlgAcercaDe dlg = new DlgAcercaDe(this, true);
        dlg.setVisible(true);
    }

    private boolean esModoOscuro = true;

    private void alternarTema() {
        esModoOscuro = !esModoOscuro;
        aplicarTema(esModoOscuro);
    }

    private void aplicarTema(boolean oscuro) {
        try {
            if (oscuro) {
                FlatDarkLaf.setup();
                if (desktopPane != null) {
                    desktopPane.setBackground(new java.awt.Color(30, 34, 42));
                }
            } else {
                FlatLightLaf.setup();
                if (desktopPane != null) {
                    desktopPane.setBackground(new java.awt.Color(220, 224, 230));
                }
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error alternando tema: " + ex.getMessage(), "Error UI", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmarSalida() {
        int resp = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea salir del Sistema Comercial UTIC?",
                "Confirmar Cierre de Aplicación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (resp == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            FlatDarkLaf.setup();
            javax.swing.UIManager.put("Button.arc", 12);
            javax.swing.UIManager.put("Component.arc", 10);
        } catch (Exception ex) {
            System.err.println("No se pudo iniciar FlatLaf: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FrmPrincipal().setVisible(true);
        });
    }

    /**
     * Desafío Examen Parcial: Inicia un hilo Timer de Swing que refresca el
     * segundero cada 1000 milisegundos (1 segundo) con la hora del sistema
     * operativo.
     */
    private void iniciarRelojDigital() {
        if (lblHora == null) {
            return;
        }

        // Timer de Swing sincronizado con el Event Dispatch Thread (EDT)
        javax.swing.Timer timerReloj = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                java.time.LocalTime ahora = java.time.LocalTime.now();
                java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
                lblHora.setText("⏰ Hora: " + ahora.format(fmt));
            }
        });
        timerReloj.start(); // Inicia el latido del reloj
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu archivo;
    private javax.swing.JMenu ayuda;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem itemAcercaDe;
    private javax.swing.JMenuItem itemClientes;
    private javax.swing.JMenuItem itemProductos;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuItem itemTemaClaro;
    private javax.swing.JMenuItem itemTemaOscuro;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblSucursal;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JMenu mantenimiento;
    private javax.swing.JMenu movimientos;
    private javax.swing.JPanel pnlStatusBar;
    private javax.swing.JMenu reportes;
    private javax.swing.JMenu ver;
    // End of variables declaration//GEN-END:variables
}
