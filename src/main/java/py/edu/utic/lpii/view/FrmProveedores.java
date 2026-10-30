/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package py.edu.utic.lpii.view;

import py.edu.utic.lpii.dao.IProveedorDAO;
import py.edu.utic.lpii.dao.ProveedorDAO;
import py.edu.utic.lpii.model.Proveedor;
import py.edu.utic.lpii.util.ValidadorUtil;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 *
 * @author smayot
 */
public class FrmProveedores extends javax.swing.JInternalFrame {

    private final IProveedorDAO proveedorDAO = new ProveedorDAO();
    private javax.swing.table.DefaultTableModel modeloTabla;
    private Integer idProveedorSeleccionado = null;

    /**
     * Creates new form FrmProveedores
     */
    public FrmProveedores() {
        initComponents();
        inicializarTabla();

        limpiarCampos();
        habilitarCampos(false);
        configurarEstadoBotones(false);

        cargarTablaDesdeBD();
    }

    /**
     * Bloquea o desbloquea los campos de captura de datos
     *
     * @param habilitar true para permitir edición, false para proteger contra
     * cambios
     */
    private void habilitarCampos(boolean habilitar) {
        // El código/ID es autogenerado por PostgreSQL, por lo que nunca se edita manualmente:
        txtCodigo.setEditable(false);
        txtCodigo.setEnabled(false);

        // Controles de entrada de datos del proveedor:
        txtRuc.setEnabled(habilitar);
        txtRazonSocial.setEnabled(habilitar);
        txtContacto.setEnabled(habilitar);
        txtTelefono.setEnabled(habilitar); // 🟢 Agregado que faltaba
        txtEmail.setEnabled(habilitar);
        txtDireccion.setEnabled(habilitar); // 🟢 Agregado que faltaba
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
            JOptionPane.showMessageDialog(this, "El RUC ingresado no es válido según el algoritmo Módulo 11 de la DNIT/SET (Ej: 80012345-0).", "RUC Inválido", JOptionPane.ERROR_MESSAGE);
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

    private void cargarTablaDesdeBD() {
        modeloTabla.setRowCount(0);

        try {
            java.util.List<py.edu.utic.lpii.model.Proveedor> proveedores = proveedorDAO.listarTodos();

            for (py.edu.utic.lpii.model.Proveedor p : proveedores) {
                modeloTabla.addRow(new Object[]{
                    p.getId(), // Columna 0: Código
                    p.getRuc(), // Columna 1: RUC
                    p.getRazonSocial(), // Columna 2: Razón Social
                    p.getContacto(), // Columna 3: Contacto
                    p.getTelefono(), // Columna 4: Teléfono
                    p.getRubro(), // Columna 5: Rubro
                    p.getEmail(), // Columna 6: Correo
                    p.getDireccion(), // Columna 7: Dirección 🟢
                    p.isActivo() ? "Activo" : "Inactivo" // Columna 8: Estado
                });
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer los proveedores:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Configura el modelo de datos no editable y el diseño de anchos de columna
     * de tblProveedores
     */
    private void inicializarTabla() {
        // Orden exacto de columnas (Indices del 0 al 8)
        String[] cabeceras = {"Código", "RUC / C.I.", "Razón Social", "Contacto Comercial", "Teléfono", "Rubro", "Correo", "Dirección", "Estado"};

        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(cabeceras, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProveedores.setModel(modelo);
        this.modeloTabla = modelo;

        tblProveedores.setRowHeight(26);
        tblProveedores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        // Anchos proporcionales para las 9 columnas:
        tblProveedores.getColumnModel().getColumn(0).setPreferredWidth(60);   // Código
        tblProveedores.getColumnModel().getColumn(1).setPreferredWidth(110);  // RUC
        tblProveedores.getColumnModel().getColumn(2).setPreferredWidth(180);  // Razón Social
        tblProveedores.getColumnModel().getColumn(3).setPreferredWidth(130);  // Contacto
        tblProveedores.getColumnModel().getColumn(4).setPreferredWidth(90);   // Teléfono
        tblProveedores.getColumnModel().getColumn(5).setPreferredWidth(100);  // Rubro
        tblProveedores.getColumnModel().getColumn(6).setPreferredWidth(140);  // Correo
        tblProveedores.getColumnModel().getColumn(7).setPreferredWidth(140);  // Dirección
        tblProveedores.getColumnModel().getColumn(8).setPreferredWidth(80);   // Estado
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
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblCodigo = new javax.swing.JLabel();
        lblRuc = new javax.swing.JLabel();
        lblContacto = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblRazonSocial = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        lblRubro = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Mantenimiento de Proveedores");

        txtCodigo.setEditable(false);
        txtCodigo.setColumns(10);

        txtRuc.setColumns(15);
        txtRuc.setToolTipText("Ej: 80012345-0");
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

        chkActivo.setText("Activo");

        txtDireccion.setColumns(20);

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
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        lblCodigo.setText("Código");

        lblRuc.setText("RUC");

        lblContacto.setText("Contacto");

        lblTelefono.setText("Teléfono");

        lblDireccion.setText("Dirección");

        jLabel1.setText("Correo Electrónico");

        lblRazonSocial.setText("Razón Social");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        lblRubro.setText("Rubro");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(lblRazonSocial)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
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
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblContacto))
                                    .addGap(163, 163, 163)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblRuc)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(112, 112, 112)
                                            .addComponent(chkActivo))
                                        .addComponent(lblTelefono)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(53, 53, 53)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDireccion)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(129, 129, 129)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblRubro)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnSalir)
                    .addComponent(btnCancelar))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblRuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContacto)
                    .addComponent(lblTelefono)
                    .addComponent(lblRubro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblDireccion))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(lblRazonSocial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        int filaSeleccionada = tblProveedores.getSelectedRow();

        if (filaSeleccionada != -1) {
            // 1. Recuperar el ID (Columna 0)
            idProveedorSeleccionado = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);

            // 2. Extraer los datos respetando los índices exactos de las columnas
            String ruc = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
            String razonSocial = modeloTabla.getValueAt(filaSeleccionada = filaSeleccionada, 2).toString(); // (o simplemente 2)
            String contacto = modeloTabla.getValueAt(filaSeleccionada, 3).toString();
            String telefono = modeloTabla.getValueAt(filaSeleccionada, 4).toString();
            String rubro = modeloTabla.getValueAt(filaSeleccionada, 5).toString();

            Object emailObj = modeloTabla.getValueAt(filaSeleccionada, 6);
            String email = (emailObj != null) ? emailObj.toString() : "";

            // 🟢 3. Leer la dirección exactamente de la Columna 7
            Object dirObj = modeloTabla.getValueAt(filaSeleccionada, 7);
            String direccion = (dirObj != null) ? dirObj.toString() : "";

            String estadoStr = modeloTabla.getValueAt(filaSeleccionada, 8).toString();

            // 4. Pasar los datos a los componentes visuales (incluyendo la dirección)
            txtCodigo.setText(idProveedorSeleccionado.toString());
            txtRuc.setText(ruc);
            txtRazonSocial.setText(razonSocial);
            txtContacto.setText(contacto);
            txtTelefono.setText(telefono);
            cboRubro.setSelectedItem(rubro);
            txtEmail.setText(email);
            txtDireccion.setText(direccion);   // 🟢 Se asigna al cuadro de texto de la dirección
            chkActivo.setSelected("Activo".equalsIgnoreCase(estadoStr));

            // 5. Configurar botonera
            habilitarCampos(false);

            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnNuevo.setEnabled(true);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }//GEN-LAST:event_tblProveedoresMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // 1. Validar campos integrales (Esto ya evalúa si está vacío y si el Módulo 11 es correcto)
        if (!validarCampos()) {
            return;
        }

        String rucTexto = txtRuc.getText().trim();

        try {
            Proveedor p = new Proveedor();
            p.setRuc(rucTexto); // Si la entidad ya valida, pasará directo sin duplicar llamados
            p.setRazonSocial(txtRazonSocial.getText().trim());
            p.setContacto(txtContacto.getText().trim());
            p.setTelefono(txtTelefono.getText().trim());
            p.setEmail(txtEmail.getText().trim());
            p.setDireccion(txtDireccion.getText().trim());
            p.setRubro(cboRubro.getSelectedItem().toString());
            p.setActivo(chkActivo.isSelected());

            if (idProveedorSeleccionado == null) {
                proveedorDAO.insertar(p);
                JOptionPane.showMessageDialog(this, "Proveedor registrado con ID #" + p.getId(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                p.setId(idProveedorSeleccionado);
                proveedorDAO.actualizar(p);
                JOptionPane.showMessageDialog(this, "Proveedor #" + p.getId() + " actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }

            cargarTablaDesdeBD();
            limpiarCampos();
            habilitarCampos(false);
            configurarEstadoBotones(false);
            idProveedorSeleccionado = null;

        } catch (SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                JOptionPane.showMessageDialog(this, "El RUC '" + rucTexto + "' ya existe para otro proveedor.", "RUC Duplicado", JOptionPane.WARNING_MESSAGE);
                txtRuc.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Error SQL:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            // Captura cualquier error de validación lanzado por los setters de la entidad Proveedor
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.WARNING_MESSAGE);
            txtRuc.requestFocus();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(true);          // Habilita las cajas de texto
        configurarEstadoBotones(true);   // Activa Guardar y Cancelar, bloquea Nuevo/Modificar/Eliminar
        idProveedorSeleccionado = null;  // Limpiamos el ID porque es un registro nuevo
        txtRuc.requestFocus();           // Pone el cursor listo para escribir el RUC
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        if (idProveedorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un proveedor de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        habilitarCampos(true);          // Habilita los campos para edición
        configurarEstadoBotones(true);   // Cambia la botonera al modo edición/guardar
        txtRuc.requestFocus();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(false);          // Bloquea los campos nuevamente
        configurarEstadoBotones(false);  // Restaura la botonera por defecto
        idProveedorSeleccionado = null;
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        // 1. Validar que realmente se haya seleccionado un proveedor de la tabla
        if (idProveedorSeleccionado == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Por favor, seleccione un proveedor de la tabla para eliminar.",
                    "Aviso",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2. Pedir confirmación antes de proceder a la baja lógica
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar (dar de baja) al proveedor seleccionado?",
                "Confirmar Eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            try {
                // 3. Ejecutar la baja lógica usando el DAO que ya creamos
                proveedorDAO.eliminar(idProveedorSeleccionado);

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Proveedor eliminado correctamente.",
                        "Éxito",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                // 4. Refrescar la tabla y limpiar el formulario
                cargarTablaDesdeBD();
                limpiarCampos();
                habilitarCampos(false);

                // Desactivar botones de edición hasta una nueva selección
                btnModificar.setEnabled(false);
                btnEliminar.setEnabled(false);
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
                btnCancelar.setEnabled(false);

                idProveedorSeleccionado = null;

            } catch (java.sql.SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Error al intentar eliminar el proveedor:\n" + ex.getMessage(),
                        "Error de Base de Datos",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboRubro;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblContacto;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblRazonSocial;
    private javax.swing.JLabel lblRubro;
    private javax.swing.JLabel lblRuc;
    private javax.swing.JLabel lblTelefono;
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
