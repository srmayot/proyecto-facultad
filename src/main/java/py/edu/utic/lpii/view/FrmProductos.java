/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package py.edu.utic.lpii.view;

import py.edu.utic.lpii.dao.IProductoDAO;
import py.edu.utic.lpii.dao.ProductoDAO;
import py.edu.utic.lpii.model.Producto;

import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;

/**
 *
 * @author srmayot
 */
public class FrmProductos extends javax.swing.JInternalFrame {

    // 🎯 1. Capa de acceso a datos (Instancia del DAO):
    private IProductoDAO productoDAO = new ProductoDAO();

    // 🎯 2. Control de estado de edición:
    // null = Estamos agregando un producto NUEVO
    // número = Estamos EDITANDO un producto existente en PostgreSQL
    private Integer idProductoSeleccionado = null;

    // 🎯 3. Modelo de la tabla Swing:
    private DefaultTableModel modeloTabla;
    private final DecimalFormat formatoGs = new DecimalFormat("#,##0 'Gs.'");

    // Métodos y eventos a continuación...
    /**
     * Creates new form FrmProductos
     */
    public FrmProductos() {
        initComponents();

        // 🟢 1. INICIALIZAR EL MODELO DE LA TABLA (¡ESTO FALTABA!)
        // Opción A: Si ya tienes el método inicializarTabla() creado abajo:
        inicializarTabla();

        // O si prefieres asignarlo directamente aquí:
        // modeloTabla = (DefaultTableModel) tblProductos.getModel();
        // 🟢 2. Configuración inicial del formulario:
        this.setTitle("Mantenimiento de Productos");
        limpiarCampos();
        habilitarCampos(false);
        configurarEstadoBotones(false);

        // 🟢 3. Cargar los datos desde la BD ahora que la tabla ya tiene modelo:
        cargarTablaDesdeBD();
    }

    private void inicializarTabla() {
        String[] titulos = {
            "Código", "Descripción", "Categoría", "Precio (Gs.)", "Stock", "IVA", "Estado"
        };

        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(titulos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProductos.setModel(modelo);

        // 🎯 ENLAZAR A TU VARIABLE GLOBAL QUE USAS EN TODO EL FORMULARIO:
        this.modeloTabla = modelo;

        // Configuración de anchos...
        tblProductos.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblProductos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblProductos.getColumnModel().getColumn(2).setPreferredWidth(110);
        tblProductos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProductos.getColumnModel().getColumn(4).setPreferredWidth(60);
        tblProductos.getColumnModel().getColumn(5).setPreferredWidth(70);
        tblProductos.getColumnModel().getColumn(6).setPreferredWidth(80);
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

    // =========================================================================
// 💡 REFRESCADO ATÓMICO DE LA GRILLA DESDE POSTGRESQL
// =========================================================================
    private void cargarTablaDesdeBD() {
        // 1. Limpiar filas existentes en la tabla visual:
        modeloTabla.setRowCount(0);

        try {
            // 2. Consultar al DAO la lista fresca de productos desde PostgreSQL:
            List<Producto> productos = productoDAO.listarTodos();

            // 3. Recorrer y agregar cada tupla al DefaultTableModel:
            for (Producto p : productos) {
                modeloTabla.addRow(new Object[]{
                    p.getId(), // Columna 0 (ID de PostgreSQL)
                    p.getCodigo(), // Columna 1
                    p.getNombre(), // Columna 2
                    formatoGs.format(p.getPrecio()), // Columna 3 (Formateado en Gs.)
                    p.getStock(), // Columna 4
                    p.getIva() + "%", // Columna 5
                    p.isActivo() ? "Activo" : "Inactivo" // Columna 6
                });
            }

            // Si no tienes el JLabel en tu diseño, puedes omitir la siguiente línea:
            // lblEstadoRegistros.setText("Total de productos en base de datos: " + productos.size());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al leer datos desde PostgreSQL 16:\n" + ex.getMessage(),
                    "Error de Persistencia",
                    JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
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
        chkActivo = new javax.swing.JCheckBox();
        txtDescripcion = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblPrecioUnit = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        spnStock = new javax.swing.JSpinner();
        cmbIva = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

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

        lblCodigo.setText("Código del Producto");

        lblDescripcion.setText("Descripción Comercial / Nombre");

        lblPrecioUnit.setText("Precio Unitario (Gs)");

        lblStock.setText("Stock Inicial");

        spnStock.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));

        cmbIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10%", "5%", "Exenta (Ley 6380/19)" }));

        jLabel1.setText("Tasa IVA");

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosLayout.createSequentialGroup()
                                .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosLayout.createSequentialGroup()
                                        .addComponent(cmbIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(185, 185, 185))
                                    .addGroup(pnlDatosLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(19, 19, 19))))
                            .addGroup(pnlDatosLayout.createSequentialGroup()
                                .addComponent(lblDescripcion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(chkActivo)
                        .addGap(18, 18, 18))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(92, 92, 92)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrecioUnit))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(lblStock)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(cmbCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(40, 40, 40))))
        );
        pnlDatosLayout.setVerticalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblPrecioUnit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(chkActivo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(lblDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStock)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))))
        );

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalir))
                    .addComponent(pnlDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
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
        limpiarCampos();
        habilitarCampos(true);
        configurarEstadoBotones(true);

        // 🎯 Habilitar el campo de código para ingreso manual:
        txtCodigo.setEditable(true);
        txtCodigo.requestFocus(); // Envía el cursor directamente al campo código
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // 1. Validar reglas de negocio y campos requeridos:
        if (!validarCampos()) {
            return; // Detiene el flujo si algún campo no cumple las reglas
        }

        try {
            // 2. Extraer y limpiar los datos de la interfaz gráfica:
            String cod = txtCodigo.getText().trim();
            String desc = txtDescripcion.getText().trim();

            // Usar BigDecimal para el precio (Estándar comercial y contable):
            java.math.BigDecimal precio = new java.math.BigDecimal(txtPrecio.getText().trim());

            int stock = (Integer) spnStock.getValue();

            // Convertir el texto del ComboBox de IVA a un número entero (10, 5 o 0):
            String ivaStr = cmbIva.getSelectedItem().toString();
            int ivaVal = 10; // Por defecto
            if (ivaStr.contains("5")) {
                ivaVal = 5;
            } else if (ivaStr.contains("Exenta") || ivaStr.contains("0")) {
                ivaVal = 0;
            }

            boolean activo = chkActivo.isSelected();

            // 3. Crear o poblar el objeto Producto:
            Producto p = new Producto();
            p.setCodigo(cod);
            p.setNombre(desc);
            p.setPrecio(precio);
            p.setStock(stock);
            p.setIva(ivaVal);
            p.setActivo(activo);

            // 4. Evaluar si es una INSERCIÓN NUEVA o una MODIFICACIÓN:
            if (idProductoSeleccionado == null) {
                // INSERTAR EN POSTGRESQL
                productoDAO.insertar(p);
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "¡Producto registrado exitosamente!",
                        "Sistema Comercial UTIC",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                // ACTUALIZAR EN POSTGRESQL
                p.setId(idProductoSeleccionado);
                productoDAO.actualizar(p);
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "¡Producto actualizado exitosamente!",
                        "Sistema Comercial UTIC",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
            }

            // 5. Refrescar la grilla y regresar el formulario al modo de reposo:
            cargarTablaDesdeBD();
            limpiarCampos();
            habilitarCampos(false);
            configurarEstadoBotones(false);
            idProductoSeleccionado = null; // Liberar la selección

        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Por favor, ingrese un valor numérico válido para el precio.",
                    "Error de Formato",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar en PostgreSQL 16:\n" + ex.getMessage(),
                    "Error de Persistencia",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
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
        int filaSeleccionada = tblProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            return;
        }

        // 🎯 1. Recuperar el ID real de PostgreSQL desde la primera columna:
        idProductoSeleccionado = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);

        // 🎯 2. Poblar los campos de texto con los datos de la fila:
        txtCodigo.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
        txtDescripcion.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());

        // Extraer monto numérico sin los caracteres de moneda:
        String precioStr = modeloTabla.getValueAt(filaSeleccionada, 3).toString()
                .replace("Gs.", "").replace(".", "").replace(",", "").trim();
        txtPrecio.setText(precioStr);

        spnStock.setValue(Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 4).toString()));

        // 🎯 Manejo inteligente del IVA para que matchee con las opciones del ComboBox:
        String ivaTablaStr = modeloTabla.getValueAt(filaSeleccionada, 5).toString().replace("%", "").trim();
        int valorIva = Integer.parseInt(ivaTablaStr);

        if (valorIva == 10) {
            cmbIva.setSelectedItem("10%");
        } else if (valorIva == 5) {
            cmbIva.setSelectedItem("5%");
        } else {
            cmbIva.setSelectedItem("Exenta (Ley 6380/19)");
        }

        String estadoStr = modeloTabla.getValueAt(filaSeleccionada, 6).toString();
        chkActivo.setSelected("Activo".equalsIgnoreCase(estadoStr));

        // 🎯 3. Activar botones para permitir Modificación o Eliminación:
        habilitarCampos(false); // Cajas bloqueadas hasta que pulse "Modificar"
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // 1. Validar que se haya seleccionado un registro de la tabla previamente:
        if (idProductoSeleccionado == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Por favor, seleccione un producto de la tabla para modificar.",
                    "Advertencia",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2. 🔓 Habilitar los campos para permitir la edición:
        habilitarCampos(true);

        // Si prefieres que el código no se modifique para mantener la integridad, déjalo en falso:
        txtCodigo.setEditable(true); // O true si deseas permitir corregir el código tipeado mal
        txtDescripcion.requestFocus();

        // 3. Cambiar el estado de los botones (Bloquear nuevo/modificar/eliminar, habilitar guardar/cancelar):
        btnNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        // 1. Validar selección previa:
        if (idProductoSeleccionado == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Por favor, seleccione un producto de la tabla para dar de baja.",
                    "Advertencia",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // 2. Cuadro de diálogo de confirmación de seguridad (UX profesional):
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea dar de baja (desactivar) este producto?",
                "Confirmación de Baja",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            try {
                // 3. Ejecutar la baja lógica llamando al método correspondiente de tu DAO:
                // (Si en tu DAO manejas un método eliminar físico o lógico, asegúrate de invocar el correcto. 
                // Lo estándar en LP II es actualizar el estado activo a false, o llamar a productoDAO.eliminar(idProductoSeleccionado))

                productoDAO.eliminar(idProductoSeleccionado); // O tu equivalente lógico

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "¡Producto dado de baja exitosamente!",
                        "Sistema Comercial UTIC",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                // 4. Refrescar la grilla y reposicionar el formulario:
                cargarTablaDesdeBD();
                limpiarCampos();
                habilitarCampos(false);
                configurarEstadoBotones(false);
                idProductoSeleccionado = null;

            } catch (java.sql.SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Error al procesar la baja en PostgreSQL 16:\n" + ex.getMessage(),
                        "Error de Persistencia",
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
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbIva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblPrecioUnit;
    private javax.swing.JLabel lblStock;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JSpinner spnStock;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables

}
