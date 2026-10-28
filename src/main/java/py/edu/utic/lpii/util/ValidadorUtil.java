/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.edu.utic.lpii.util;

/**
 *
 * @author smayot
 */
public class ValidadorUtil {

    /**
     * Validador oficial de RUC paraguayo aplicando el algoritmo Módulo 11 (SET
     * / DNIT).
     *
     * @param rucCompleto Cadena con formato "1234567-8"
     * @return true si el Dígito Verificador coincide matemáticamente
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
        String dvIngresadoStr = partes[1].trim();

        try {
            int dvIngresado = Integer.parseInt(dvIngresadoStr);
            int factor = 2;
            int suma = 0;

            // Algoritmo Módulo 11 de derecha a izquierda:
            for (int i = numeroBase.length() - 1; i >= 0; i--) {
                int digito = Character.getNumericValue(numeroBase.charAt(i));
                suma += digito * factor;
                factor = (factor >= 11) ? 2 : factor + 1;
            }

            int resto = suma % 11;
            int dvCalculado = (resto > 1) ? (11 - resto) : 0;

            return dvIngresado == dvCalculado;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
