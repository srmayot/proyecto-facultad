/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package py.edu.utic.lpii.view;

/**
 *
 * @author srmayot
 */
public class FrmProductos extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmProductos
     */
    public FrmProductos() {
        initComponents();

        // 🟢 Configuración inicial del formulario:
        this.setTitle("Mantenimiento de Productos");
        limpiarCampos();
        habilitarCampos(false);
        configurarEstadoBotones(false);
    }

    private void inicializarTabla() {
        String[] titulos = {
            "Código", "Descripción", "Categoría", "Precio (Gs.)", "Stock", "IVA", "Estado"
        };

        // 🔒 Modelo de tabla con sobrescritura anónima para evitar edición accidental:
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(titulos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda puede ser modificada haciendo doble clic en la grilla
            }
        };

        tblProductos.setModel(modelo);

        // Configuración de anchos visuales preferidos de columna:
        tblProductos.getColumnModel().getColumn(0).setPreferredWidth(70);  // Código
        tblProductos.getColumnModel().getColumn(1).setPreferredWidth(200); // Descripción
        tblProductos.getColumnModel().getColumn(2).setPreferredWidth(110); // Categoría
        tblProductos.getColumnModel().getColumn(3).setPreferredWidth(100); // Precio
        tblProductos.getColumnModel().getColumn(4).setPreferredWidth(60);  // Stock
        tblProductos.getColumnModel().getColumn(5).setPreferredWidth(70);  // IVA
        tblProductos.getColumnModel().getColumn(6).setPreferredWidth(80);  // Estado
    }

    /**
     * Valida reglas de negocio y campos requeridos antes de persistir. Retorna
     * true si todo es correcto; false si hubo algún error.
     */
    private boolean validarCampos() {
        // 1. Validar Código obligatorio:
        if (txtCodigo.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "El Código del producto es obligatorio.",
                    "Validación",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return false;
        }

        // 2. Validar Descripción obligatoria:
        if (txtDescripcion.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "La Descripción del producto es obligatoria.",
                    "Validación",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            txtDescripcion.requestFocus();
            return false;
        }

        // 3. Validar Precio numérico positivo mayor a 0 Gs.:
        try {
            long p = Long.parseLong(txtPrecio.getText().trim());
            if (p <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "El Precio debe ser mayor a 0 Gs.",
                        "Validación",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                txtPrecio.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Ingrese un importe numérico válido sin letras ni símbolos.",
                    "Error de Formato",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            txtPrecio.requestFocus();
            return false;
        }

        return true; // Todas las validaciones pasaron con éxito
    }

    /**
     * Restablece los campos de entrada a su estado inicial en blanco. Se ubica
     * en la pestaña Source fuera del bloque <editor-fold>.
     */
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        spnStock.setValue(0);
        cmbCategoria.setSelectedIndex(0);
        cmbIva.setSelectedIndex(0);
        chkActivo.setSelected(true);
    }

    /**
     * Activa o bloquea la edición de los controles según el flujo del
     * formulario.
     *
     * @param estado true para permitir escribir, false para bloquear.
     */
    private void habilitarCampos(boolean estado) {
        txtCodigo.setEnabled(estado);
        txtDescripcion.setEnabled(estado);
        txtPrecio.setEnabled(estado);
        spnStock.setEnabled(estado);
        cmbCategoria.setEnabled(estado);
        cmbIva.setEnabled(estado);
        chkActivo.setEnabled(estado);
    }

    /**
     * Controla el ciclo de vida de la botonera: - Modo reposo (editando=false):
     * Nuevo habilitado; Guardar y Cancelar bloqueados. - Modo edición
     * (editando=true): Guardar y Cancelar habilitados; Nuevo bloqueado.
     */
    private void configurarEstadoBotones(boolean editando) {
        btnNuevo.setEnabled(!editando);
        btnGuardar.setEnabled(editando);
        btnCancelar.setEnabled(editando);
        //btnModificar.setEnabled(false);
        //btnEliminar.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        pnlDatos = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        cmbCategoria = new javax.swing.JComboBox<>();
        cmbIva = new javax.swing.JComboBox<>();
        chkActivo = new javax.swing.JCheckBox();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        spnStock = new javax.swing.JSpinner();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Mantenimiento de Productos");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descripción", "Categoría", "Precio (Gs.)", "Stock", "IVA", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        txtCodigo.setEditable(false);
        txtCodigo.setColumns(10);
        txtCodigo.setText("Código");

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bebidas", "Lácteos", "Almacén", "Limpieza" }));

        cmbIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10%", "5%", "Exenta (Ley 6380/19)" }));

        chkActivo.setSelected(true);
        chkActivo.setText("Activo para Facturación");

        txtDescripcion.setColumns(30);
        txtDescripcion.setText("Descripción");
        txtDescripcion.setToolTipText("Nombre comercial");

        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecio.setText("Precio Unit.");
        txtPrecio.addActionListener(this::txtPrecioActionPerformed);
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        spnStock.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbIva, 0, 1, Short.MAX_VALUE)
                    .addComponent(cmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigo))
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(chkActivo))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        pnlDatosLayout.setVerticalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnNuevo)
                            .addGap(18, 18, 18)
                            .addComponent(btnGuardar)
                            .addGap(18, 18, 18)
                            .addComponent(btnCancelar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir))
                        .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNuevo)
                        .addComponent(btnGuardar)
                        .addComponent(btnCancelar))
                    .addComponent(btnSalir))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    /**
     * Intercepta cada pulsación de tecla física antes de que el texto sea
     * dibujado. Solo acepta dígitos del 0 al 9 y la tecla retroceso
     * (Backspace).
     */
    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();

        // Si el carácter NO es un número y NO es tecla retroceso:
        if (!Character.isDigit(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
            evt.consume(); // 🚫 Cancela el evento en el Event Dispatch Thread (EDT)
            java.awt.Toolkit.getDefaultToolkit().beep(); // 🔊 Pitido sonoro de advertencia
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(true);
        configurarEstadoBotones(true);

        // Enfocar automáticamente el cursor en el primer campo a rellenar:
        txtDescripcion.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        // 1. Validar reglas de negocio y campos requeridos:
        if (!validarCampos()) {
            return; // Detiene el flujo si algún campo no cumple las reglas
        }

        // 2. Extraer los datos limpios de la interfaz gráfica:
        String cod = txtCodigo.getText().trim();
        String desc = txtDescripcion.getText().trim();
        long precio = Long.parseLong(txtPrecio.getText().trim());
        int stock = (Integer) spnStock.getValue();
        String categoria = cmbCategoria.getSelectedItem().toString();
        String iva = cmbIva.getSelectedItem().toString();
        boolean activo = chkActivo.isSelected();

        // 3. Notificación de éxito al usuario (JOptionPane):
        javax.swing.JOptionPane.showMessageDialog(this,
                "¡Producto registrado exitosamente!",
                "Sistema Comercial UTIC",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // 4. Regresar el formulario al modo de reposo:
        limpiarCampos();
        habilitarCampos(false);
        configurarEstadoBotones(false);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(false);
        configurarEstadoBotones(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        // Cierra y libera los recursos de la ventana interna sin matar la ventana principal:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:
        int fila = tblProductos.getSelectedRow();
        if (fila != -1) {
            // 1. Extraer los datos de las columnas de la fila seleccionada:
            txtCodigo.setText(tblProductos.getValueAt(fila, 0).toString());
            txtDescripcion.setText(tblProductos.getValueAt(fila, 1).toString());
            cmbCategoria.setSelectedItem(tblProductos.getValueAt(fila, 2).toString());

            // 2. Extraer precio y sanitizar formato monetario ("12.000 Gs." -> "12000"):
            String precioStr = tblProductos.getValueAt(fila, 3).toString().replaceAll("[^0-9]", "");
            txtPrecio.setText(precioStr);

            spnStock.setValue(Integer.parseInt(tblProductos.getValueAt(fila, 4).toString()));
            cmbIva.setSelectedItem(tblProductos.getValueAt(fila, 5).toString());

            // 3. Habilitar botones de acción para el registro seleccionado:
            //btnModificar.setEnabled(true);
            //btnEliminar.setEnabled(true);
            btnNuevo.setEnabled(false);
        }
    }//GEN-LAST:event_tblProductosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbIva;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JSpinner spnStock;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables

}
