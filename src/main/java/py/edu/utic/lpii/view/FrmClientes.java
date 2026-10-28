/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package py.edu.utic.lpii.view;

/**
 *
 * @author srmayot
 */
  

    public class FrmClientes extends javax.swing.JInternalFrame {

        private javax.swing.table.TableRowSorter<javax.swing.table.DefaultTableModel> sorter;

        /**
         * Creates new form FrmClientes
         */
        public FrmClientes() {
            initComponents();

            // 🟢 Configuración de arranque del módulo de clientes:
            this.setTitle("Gestión y Registro de Clientes");
            inicializarTabla();
            limpiarCampos();
            habilitarCampos(false);
            configurarEstadoBotones(false);
        }

        /**
         * Valida el RUC de Paraguay utilizando el algoritmo oficial de Módulo
         * 11 exigido por la Dirección Nacional de Ingresos Tributarios (DNIT /
         * SET). Formato esperado: "80012345-6" o "4567890-1"
         *
         * @param rucCompleto Cadena que incluye el número base, guion y dígito
         * verificador.
         * @return true si el dígito verificador coincide matemáticamente; false
         * si es apócrifo.
         */
        public static boolean validarRucParaguay(String rucCompleto) {
            if (rucCompleto == null || !rucCompleto.contains("-")) {
                return false;
            }

            String[] partes = rucCompleto.trim().split("-");
            if (partes.length != 2) {
                return false;
            }

            String numero = partes[0].trim();
            String dvString = partes[1].trim();

            if (numero.isEmpty() || dvString.length() != 1) {
                return false;
            }

            try {
                int dvIngresado = Integer.parseInt(dvString);
                int factor = 2;
                int suma = 0;

                for (int i = numero.length() - 1; i >= 0; i--) {
                    char c = numero.charAt(i);
                    if (!Character.isDigit(c)) {
                        return false;
                    }

                    int digito = Character.getNumericValue(c);
                    suma += (digito * factor);

                    factor++;
                    if (factor > 11) {
                        factor = 2;
                    }
                }

                int resto = suma % 11;
                int dvCalculado;
                if (resto > 1) {
                    dvCalculado = 11 - resto;
                } else {
                    dvCalculado = 0;
                }

                return dvIngresado == dvCalculado;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /**
         * Inicializa el modelo de tabla de clientes, bloquea la edición directa
         * en celdas y configura el TableRowSorter para el buscador dinámico.
         */
        private void inicializarTabla() {
            String[] titulos = {
                "Código", "RUC / Cédula", "Razón Social / Nombre", "Teléfono", "Tipo", "Estado"
            };

            javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(titulos, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tblClientes.setModel(modelo);

            sorter = new javax.swing.table.TableRowSorter<>(modelo);
            tblClientes.setRowSorter(sorter);

            tblClientes.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblClientes.getColumnModel().getColumn(1).setPreferredWidth(110);
            tblClientes.getColumnModel().getColumn(2).setPreferredWidth(230);
            tblClientes.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblClientes.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblClientes.getColumnModel().getColumn(5).setPreferredWidth(75);
        }

        private void limpiarCampos() {
            txtCodigo.setText("");
            txtRuc.setText("");
            txtRazonSocial.setText("");
            txtTelefono.setText("");
            txtDireccion.setText("");
            cboTipoCliente.setSelectedIndex(0);
            chkActivo.setSelected(false);
            txtRuc.requestFocus();
        }

        private void habilitarCampos(boolean estado) {
            txtRuc.setEditable(estado);
            txtRazonSocial.setEditable(estado);
            txtTelefono.setEditable(estado);
            txtDireccion.setEditable(estado);
            cboTipoCliente.setEnabled(estado);
            chkActivo.setEnabled(estado);
        }

        private void configurarEstadoBotones(boolean estado) {
            btnNuevo.setEnabled(!estado);
            btnGuardar.setEnabled(estado);
            btnCancelar.setEnabled(estado);
        }

        private boolean validarCampos() {
            String ruc = txtRuc.getText().trim();
            String razonSocial = txtRazonSocial.getText().trim();

            if (ruc.isEmpty() || ruc.equals("RUC")) {
                javax.swing.JOptionPane.showMessageDialog(this, "El campo RUC es obligatorio.", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
                txtRuc.requestFocus();
                return false;
            }

            if (!validarRucParaguay(ruc)) {
                javax.swing.JOptionPane.showMessageDialog(this, "El RUC ingresado no es válido según el algoritmo del Módulo 11.", "Error de RUC", javax.swing.JOptionPane.ERROR_MESSAGE);
                txtRuc.requestFocus();
                return false;
            }

            if (razonSocial.isEmpty() || razonSocial.equals("Razón Social")) {
                javax.swing.JOptionPane.showMessageDialog(this, "La Razón Social es obligatoria.", "Validación", javax.swing.JOptionPane.WARNING_MESSAGE);
                txtRazonSocial.requestFocus();
                return false;
            }

            return true;
        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCodigo = new javax.swing.JTextField();
        txtRuc = new javax.swing.JTextField();
        txtRazonSocial = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        cboTipoCliente = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        chkActivo = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Clientes");

        txtCodigo.setEditable(false);
        txtCodigo.setColumns(10);
        txtCodigo.setText("Código");

        txtRuc.setColumns(15);
        txtRuc.setText("RUC");
        txtRuc.setToolTipText("Ej: 80015698-3");

        txtRazonSocial.setColumns(35);
        txtRazonSocial.setText("Razón Social");
        txtRazonSocial.setToolTipText("Nombre comercial");

        txtTelefono.setColumns(15);
        txtTelefono.setText("Teléfono");
        txtTelefono.setToolTipText("Ej: 0981-456789");

        txtDireccion.setColumns(40);
        txtDireccion.setText("Dirección");

        cboTipoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Persona", "Física", "Jurídica" }));

        txtBuscar.setText("Buscar");
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "RUC / Cédula", "Razón Social", "Teléfono", "Tipo", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        chkActivo.setText("Activo para Facturación");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cboTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(chkActivo)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir)))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTipoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkActivo))
                .addGap(26, 26, 26)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalir))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(javax.swing.RowFilter.regexFilter("(?i)" + texto));
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(true);
        configurarEstadoBotones(true);
        txtRuc.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
            // TODO add your handling code here:
            // 1. Validar reglas de negocio y formato de RUC:
            if (!validarCampos()) {
                return; // Interrumpe el flujo si falló alguna regla
            }

            // 2. Extraer datos sanitizados:
            String cod = txtCodigo.getText().trim();
            String ruc = txtRuc.getText().trim();
            String razonSocial = txtRazonSocial.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String tipo = cboTipoCliente.getSelectedItem().toString();
            boolean activo = chkActivo.isSelected();

            // 3. Notificación de éxito:
            javax.swing.JOptionPane.showMessageDialog(this,
                    "¡Cliente [" + razonSocial + "] registrado con éxito!\nRUC Validado: " + ruc,
                    "Sistema Comercial UTIC",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

            // 4. Regresar a modo de reposo:
            limpiarCampos();
            habilitarCampos(false);
            configurarEstadoBotones(false);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        int fila = tblClientes.getSelectedRow();
        if (fila != -1) {
            // Si hay un sorter activo, convertir el índice visual al índice del modelo:
            int filaModelo = tblClientes.convertRowIndexToModel(fila);

            txtCodigo.setText(tblClientes.getModel().getValueAt(filaModelo, 0).toString());
            txtRuc.setText(tblClientes.getModel().getValueAt(filaModelo, 1).toString());
            txtRazonSocial.setText(tblClientes.getModel().getValueAt(filaModelo, 2).toString());
            txtTelefono.setText(tblClientes.getModel().getValueAt(filaModelo, 3).toString());
            cboTipoCliente.setSelectedItem(tblClientes.getModel().getValueAt(filaModelo, 4).toString());

            // Habilitar botones de acción para modificar o eliminar:
            //btnModificar.setEnabled(true);
            //btnEliminar.setEnabled(true);
            btnNuevo.setEnabled(false);
        }
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
        habilitarCampos(false);
        configurarEstadoBotones(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cboTipoCliente;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
