package me.imsergioh.pluginsapi.util;

import java.awt.*;

public class GradientUtil {

    /**
     * Genera un color más claro a partir de un color hexadecimal.
     *
     * @param hexColor El color base en formato hexadecimal (e.g., "#00ff22").
     * @param lightenFactor Un valor entre 0 y 1 que determina cuán claro será el color resultante.
     * @return El color aclarado en formato hexadecimal.
     */
    public static String lightenColor(String hexColor, float lightenFactor) {
        // Limitar el factor de aclarado para evitar extremos
        float minFactor = 0.4f;
        float maxFactor = 0.6f;
        lightenFactor = Math.max(minFactor, Math.min(maxFactor, lightenFactor));

        Color color = Color.decode(hexColor.replace(":", ""));

        // Calcular el color aclarado
        int red = (int) (color.getRed() + (255 - color.getRed()) * lightenFactor);
        int green = (int) (color.getGreen() + (255 - color.getGreen()) * lightenFactor);
        int blue = (int) (color.getBlue() + (255 - color.getBlue()) * lightenFactor);
        Color lightColor = new Color(red, green, blue);

        return String.format("#%02x%02x%02x", lightColor.getRed(), lightColor.getGreen(), lightColor.getBlue());
    }

}
