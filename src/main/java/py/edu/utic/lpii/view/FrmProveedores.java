/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package py.edu.utic.lpii.view;

import py.edu.utic.lpii.util.ValidadorUtil;

import javax.swing.JOptionPane;

/**
 *
 * @author smayot
 */
public class FrmProveedores extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmProveedores
     */
    public FrmProveedores() {
        initComponents();
        inicializarTabla();
    }

    /**
     * Bloquea o desbloquea los campos de captura de datos
     *
     * @param habilitar true para permitir edición, false para proteger contra
     * cambios
     */
    private void habilitarCampos(boolean habilitar) {
        // El código siempre es autogenerado o gestionado por el sistema:
        txtCodigo.setEditable(false);

        // Controles de entrada de datos:
        txtRuc.setEnabled(habilitar);
        txtRazonSocial.setEnabled(habilitar);
        txtContacto.setEnabled(habilitar);
        txtContacto.setEnabled(habilitar);
        txtEmail.setEnabled(habilitar);
        txtDireccion.setEnabled(habilitar);
        cboRubro.setEnabled(habilitar);
        chkActivo.setEnabled(habilitar);
    }

    /**
     * Valida integralmente los datos requeridos del proveedor antes de guardar
     *
     * @return true si todos los datos son válidos; false si hay inconsistencias
     */
    private boolean validarCampos() {
        // 1. Validar RUC obligatorio:
        String ruc = txtRuc.getText().trim();
        if (ruc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el RUC del proveedor.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtRuc.requestFocus();
            return false;
        }

        // 2. Validar formato y algoritmo matemático Módulo 11 de la SET:
        if (!ValidadorUtil.validarRucParaguay(ruc)) {
            JOptionPane.showMessageDialog(this, "El RUC ingresado no es válido según el algoritmo Módulo 11 de la DNIT/SET (Ej: 80012345-6).", "RUC Inválido", JOptionPane.ERROR_MESSAGE);
            txtRuc.selectAll();
            txtRuc.requestFocus();
            return false;
        }

        // 3. Validar Razón Social:
        if (txtRazonSocial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la Razón Social o Nombre Comercial.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtRazonSocial.requestFocus();
            return false;
        }

        // 4. Validar Persona de Contacto:
        if (txtContacto.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe indicar una Persona o Representante de Contacto.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtContacto.requestFocus();
            return false;
        }

        // 5. Validar Teléfono:
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un Teléfono de contacto comercial.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }

        // 6. Validar Correo Electrónico (si se ingresó):
        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "El formato del correo electrónico no es válido (Ej: contacto@empresa.com.py).", "Correo Inválido", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        return true; // Todo en orden
    }

    /**
     * Restablece todos los campos del formulario a sus valores por defecto
     */
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtRuc.setText("");
        txtRazonSocial.setText("");
        txtContacto.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        cboRubro.setSelectedIndex(0);
        chkActivo.setSelected(true);

        // Limpiar selección de la tabla si existiera:
        tblProveedores.clearSelection();
    }

    /**
     * Alterna la botonera de la barra de herramientas según el modo operativo
     *
     * @param enEdicion true si el usuario está creando o modificando un
     * proveedor
     */
    private void configurarEstadoBotones(boolean enEdicion) {
        btnNuevo.setEnabled(!enEdicion);
        btnGuardar.setEnabled(enEdicion);
        btnCancelar.setEnabled(enEdicion);

        // Modificar y Eliminar solo se habilitan si NO estamos editando Y hay una fila seleccionada:
        boolean haySeleccion = tblProveedores.getSelectedRow() != -1;
        btnModificar.setEnabled(!enEdicion && haySeleccion);
        btnEliminar.setEnabled(!enEdicion && haySeleccion);

        // Salir siempre está habilitado:
        btnSalir.setEnabled(true);
    }

    /**
     * Configura el modelo de datos no editable y el diseño de anchos de columna
     * de tblProveedores
     */
    private void inicializarTabla() {
        String[] cabeceras = {"Código", "RUC / C.I.", "Razón Social", "Contacto Comercial", "Teléfono", "Rubro"};

        // 🔒 Modelo de datos con bloqueo de edición directa en celdas:
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(cabeceras, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda editable con doble clic
            }
        };

        tblProveedores.setModel(modelo);
        tblProveedores.setRowHeight(26); // Altura ergonómica para FlatLaf
        tblProveedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // 📐 Configurar anchos proporcionales de columnas:
        tblProveedores.getColumnModel().getColumn(0).setPreferredWidth(80);   // Código
        tblProveedores.getColumnModel().getColumn(1).setPreferredWidth(120);  // RUC
        tblProveedores.getColumnModel().getColumn(2).setPreferredWidth(220);  // Razón Social
        tblProveedores.getColumnModel().getColumn(3).setPreferredWidth(160);  // Contacto
        tblProveedores.getColumnModel().getColumn(4).setPreferredWidth(110);  // Teléfono
        tblProveedores.getColumnModel().getColumn(5).setPreferredWidth(120);  // Rubro
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        txtRazonSocial = new javax.swing.JTextField();
        txtContacto = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        cboRubro = new javax.swing.JComboBox<>();
        txtEmail = new javax.swing.JTextField();
        chkActivo = new javax.swing.JCheckBox();
        txtDireccion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedores = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        txtCodigo.setEditable(false);
        txtCodigo.setColumns(10);

        txtRuc.setColumns(15);
        txtRuc.setToolTipText("Ej: 80012345-6");
        txtRuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRucFocusLost(evt);
            }
        });
        txtRuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucKeyTyped(evt);
            }
        });

        txtRazonSocial.setColumns(25);

        txtContacto.setColumns(20);

        txtTelefono.setColumns(10);
        txtTelefono.setToolTipText("Ej: 021-945000");

        cboRubro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bebidas", "Lácteos", "Almacén,", "Limpieza", "Tecnología", "Logística" }));

        txtEmail.setColumns(20);

        chkActivo.setText("jCheckBox1");

        txtDireccion.setColumns(10);

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "RUC / CI", "Razón Social", "Contacto Comercial", "Teléfono", "Rubro"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProveedores);

        btnNuevo.setText("Nuevo");

        btnGuardar.setText("Guardar");

        btnCancelar.setText("Cancelar");

        btnEliminar.setText("Eliminar");

        btnModificar.setText("Modificar");

        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkActivo)
                                    .addComponent(cboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btnNuevo)
                            .addGap(18, 18, 18)
                            .addComponent(btnGuardar)
                            .addGap(18, 18, 18)
                            .addComponent(btnModificar)
                            .addGap(18, 18, 18)
                            .addComponent(btnEliminar)
                            .addGap(18, 18, 18)
                            .addComponent(btnCancelar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(308, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo)
                            .addComponent(btnGuardar)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)
                            .addComponent(btnCancelar)))
                    .addComponent(btnSalir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo))
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void txtRucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRucFocusLost
        // TODO add your handling code here:
        ValidadorUtil.validarRucParaguay(txtRuc.getText().trim());
    }//GEN-LAST:event_txtRucFocusLost

    private void txtRucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        // Solo permitir números del '0' al '9', el guion '-' y la tecla de borrado:
        if (!Character.isDigit(c) && c != '-' && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume(); // 🚫 Cancela el tipeo del carácter inválido
            java.awt.Toolkit.getDefaultToolkit().beep(); // Sonido de alerta
        }

        // Evitar que el usuario escriba más de un guion:
        if (c == '-' && txtRuc.getText().contains("-")) {
            evt.consume();
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }//GEN-LAST:event_txtRucKeyTyped

    private void tblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresMouseClicked
        // TODO add your handling code here:
        int filaSeleccionada = tblProveedores.getSelectedRow();

        if (filaSeleccionada != -1) {
            // 📥 Extraer los datos de las columnas de la fila seleccionada:
            String codigo = tblProveedores.getValueAt(filaSeleccionada, 0).toString();
            String ruc = tblProveedores.getValueAt(filaSeleccionada, 1).toString();
            String razonSocial = tblProveedores.getValueAt(filaSeleccionada, 2).toString();
            String contacto = tblProveedores.getValueAt(filaSeleccionada, 3).toString();
            String telefono = tblProveedores.getValueAt(filaSeleccionada, 4).toString();
            String rubro = tblProveedores.getValueAt(filaSeleccionada, 5).toString();

            // 📤 Transferir datos a los campos de edición visuales:
            txtCodigo.setText(codigo);
            txtRuc.setText(ruc);
            txtRazonSocial.setText(razonSocial);
            txtContacto.setText(contacto);
            txtTelefono.setText(telefono);
            cboRubro.setSelectedItem(rubro);

            // Mantener campos bloqueados hasta que el usuario presione "Modificar":
            habilitarCampos(false);
            configurarEstadoBotones(false);
        }
    }//GEN-LAST:event_tblProveedoresMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboRubro;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtContacto;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
