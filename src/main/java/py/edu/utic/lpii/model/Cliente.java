package py.edu.utic.lpii.model;

/**
 * Entidad de Dominio: Cliente
 * Modela los datos fiscales y de contacto de clientes para facturación legal.
 * Incorpora la validación del RUC paraguayo mediante el algoritmo Módulo 11 oficial.
 */
public class Cliente {
    // =========================================================================
    // BLOQUE 1: DATOS FISCALES Y DE CONTACTO
    // =========================================================================
    private Integer id;
    private String ruc; // Ej: "80012345-6" (Empresas) o "4567890-1" (Personas físicas)
    private String razonSocial;
    private String telefono;
    private String email;
    private String direccion;
    private boolean activo;

    // =========================================================================
    // BLOQUE 2: CONSTRUCTORES
    // =========================================================================
    public Cliente() {
        this.activo = true;
    }

    public Cliente(Integer id, String ruc, String razonSocial, String telefono, String email, String direccion) {
        this.id = id;
        this.setRuc(ruc); // Aplica validación obligatoria
        this.setRazonSocial(razonSocial);
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.activo = true;
    }

    // =========================================================================
    // BLOQUE 3: ALGORITMO OFICIAL MÓDULO 11 (SET / DNIT PARAGUAY)
    // =========================================================================
    /**
     * Valida la estructura formal del RUC: verifica presencia de guion y compara
     * el Dígito Verificador (DV) ingresado contra el DV calculado matemáticamente.
     */
    public static boolean validarRucParaguay(String rucCompleto) {
        if (rucCompleto == null || !rucCompleto.contains("-")) {
            return false;
        }
        String[] partes = rucCompleto.trim().split("-");
        if (partes.length != 2) return false;

        String numero = partes[0];
        int dvIngresado;
        try {
            dvIngresado = Integer.parseInt(partes[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        int dvCalculado = calcularDigitoVerificador(numero);
        return dvIngresado == dvCalculado;
    }

    /**
     * Implementación oficial de la SET / DNIT para el cálculo del Dígito Verificador:
     * 1. Recorre los dígitos del número de derecha a izquierda.
     * 2. Multiplica cada dígito por un factor ponderador k que inicia en 2 y llega hasta 11.
     * 3. Si k supera 11, se reinicia en 2.
     * 4. Se calcula el residuo de la suma módulo 11: si residuo > 1, DV = 11 - residuo; sino DV = 0.
     */
    public static int calcularDigitoVerificador(String numero) {
        int basemax = 11;
        int k = 2;
        int total = 0;
        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            total += digito * k;
            k++;
            if (k > basemax) k = 2;
        }
        int resto = total % 11;
        return resto > 1 ? 11 - resto : 0;
    }

    // =========================================================================
    // BLOQUE 4: ACCESORES Y MUTADORES CON VALIDACIONES
    // =========================================================================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) {
        if (ruc == null || ruc.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUC o Cédula de Identidad es un dato obligatorio.");
        }
        this.ruc = ruc.trim();
    }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) {
        if (razonSocial == null || razonSocial.trim().isEmpty()) {
            throw new IllegalArgumentException("La Razón Social o Nombre del cliente no puede estar vacía.");
        }
        this.razonSocial = razonSocial.trim();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return ruc + " - " + razonSocial;
    }
}
