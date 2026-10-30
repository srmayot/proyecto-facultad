/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.edu.utic.lpii.util;

/**
 * Validador oficial de RUC paraguayo aplicando el algoritmo Módulo 11 (SET /
 * DNIT).
 *
 * @author smayot
 */
public class ValidadorUtil {

    /**
     * Valida la estructura formal y matemática del RUC paraguayo.
     *
     * @param rucCompleto Cadena con formato "1234567-8"
     * @return true si la estructura es correcta y el Dígito Verificador
     * coincide matemáticamente
     */
    public static boolean validarRucParaguay(String rucCompleto) {
        if (rucCompleto == null || !rucCompleto.contains("-")) {
            return false;
        }

        String[] partes = rucCompleto.trim().split("-");
        if (partes.length != 2) {
            return false;
        }

        String numeroBase = partes[0].trim();
        String dvStr = partes[1].trim();

        // Validación estricta: base de 5 a 9 dígitos numéricos y DV de un solo dígito
        if (!numeroBase.matches("\\d{5,9}") || !dvStr.matches("\\d")) {
            return false;
        }

        try {
            int suma = 0;
            int factor = 2;
            int basemax = 11; // Constante máxima del factor ponderador

            // Recorrido de derecha a izquierda del número base
            for (int i = numeroBase.length() - 1; i >= 0; i--) {
                int digito = Character.getNumericValue(numeroBase.charAt(i));
                suma += digito * factor;

                // Incremento y rotación del factor tributario
                factor++;
                if (factor > basemax) {
                    factor = 2;
                }
            }

            int resto = suma % 11;
            int dvCalculado = (resto > 1) ? (11 - resto) : 0;

            int dvIngresado = Integer.parseInt(dvStr);
            return dvIngresado == dvCalculado;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
